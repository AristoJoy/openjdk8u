/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 *
 */

#include "precompiled.hpp"
#include "gc_implementation/shared/copyFailedInfo.hpp"
#include "gc_implementation/shared/gcHeapSummary.hpp"
#include "gc_implementation/shared/gcTimer.hpp"
#include "gc_implementation/shared/gcTrace.hpp"
#include "gc_implementation/shared/objectCountEventSender.hpp"
#include "memory/heapInspection.hpp"
#include "memory/referenceProcessorStats.hpp"
#include "runtime/os.hpp"
#include "utilities/globalDefinitions.hpp"
#include "utilities/ticks.inline.hpp"

#if INCLUDE_ALL_GCS
#include "gc_implementation/g1/evacuationInfo.hpp"
#endif

#define assert_unset_gc_id() assert(_shared_gc_info.id() == SharedGCInfo::UNSET_GCID, "GC already started?")
#define assert_set_gc_id() assert(_shared_gc_info.id() != SharedGCInfo::UNSET_GCID, "GC not started?")

static GCId GCTracer_next_gc_id = 0;
static GCId create_new_gc_id() {
  return GCTracer_next_gc_id++;
}

void GCTracer::report_gc_start_impl(GCCause::Cause cause, const Ticks& timestamp) {
  assert_unset_gc_id();

  GCId gc_id = create_new_gc_id();
  _shared_gc_info.set_id(gc_id);
  _shared_gc_info.set_cause(cause);
  _shared_gc_info.set_start_timestamp(timestamp);
}

void GCTracer::report_gc_start(GCCause::Cause cause, const Ticks& timestamp) {
  assert_unset_gc_id();

  report_gc_start_impl(cause, timestamp);
}

bool GCTracer::has_reported_gc_start() const {
  return _shared_gc_info.id() != SharedGCInfo::UNSET_GCID;
}

void GCTracer::report_gc_end_impl(const Ticks& timestamp, TimePartitions* time_partitions) {
  assert_set_gc_id();

  _shared_gc_info.set_sum_of_pauses(time_partitions->sum_of_pauses());
  _shared_gc_info.set_longest_pause(time_partitions->longest_pause());
  _shared_gc_info.set_end_timestamp(timestamp);

  send_phase_events(time_partitions);
  send_garbage_collection_event();
}

void GCTracer::report_gc_end(const Ticks& timestamp, TimePartitions* time_partitions) {
  assert_set_gc_id();

  report_gc_end_impl(timestamp, time_partitions);

  _shared_gc_info.set_id(SharedGCInfo::UNSET_GCID);
}

void GCTracer::report_gc_reference_stats(const ReferenceProcessorStats& rps) const {
  assert_set_gc_id();

  send_reference_stats_event(REF_SOFT, rps.soft_count());
  send_reference_stats_event(REF_WEAK, rps.weak_count());
  send_reference_stats_event(REF_FINAL, rps.final_count());
  send_reference_stats_event(REF_PHANTOM, rps.phantom_count());
}

#if INCLUDE_SERVICES
class ObjectCountEventSenderClosure : public KlassInfoClosure {
  const GCId _gc_id;
  const double _size_threshold_percentage;
  const size_t _total_size_in_words;
  const Ticks _timestamp;

 public:
  ObjectCountEventSenderClosure(GCId gc_id, size_t total_size_in_words, const Ticks& timestamp) :
    _gc_id(gc_id),
    _size_threshold_percentage(ObjectCountCutOffPercent / 100),
    _total_size_in_words(total_size_in_words),
    _timestamp(timestamp)
  {}

  virtual void do_cinfo(KlassInfoEntry* entry) {
    if (should_send_event(entry)) {
      ObjectCountEventSender::send(entry, _gc_id, _timestamp);
    }
  }

 private:
  bool should_send_event(const KlassInfoEntry* entry) const {
    double percentage_of_heap = ((double) entry->words()) / _total_size_in_words;
    return percentage_of_heap >= _size_threshold_percentage;
  }
};

void GCTracer::report_object_count_after_gc(BoolObjectClosure* is_alive_cl) {
  assert_set_gc_id();
  assert(is_alive_cl != NULL, "Must supply function to check liveness");

  if (ObjectCountEventSender::should_send_event()) {
    ResourceMark rm;

    KlassInfoTable cit(false);
    if (!cit.allocation_failed()) {
      HeapInspection hi(false, false, false, NULL);
      hi.populate_table(&cit, is_alive_cl);
      ObjectCountEventSenderClosure event_sender(_shared_gc_info.id(), cit.size_of_instances_in_words(), Ticks::now());
      cit.iterate(&event_sender);
    }
  }
}
#endif // INCLUDE_SERVICES

void GCTracer::report_gc_heap_summary(GCWhen::Type when, const GCHeapSummary& heap_summary, const MetaspaceSummary& meta_space_summary) const {
  assert_set_gc_id();

  send_gc_heap_summary_event(when, heap_summary);
  send_meta_space_summary_event(when, meta_space_summary);
}

void YoungGCTracer::report_gc_end_impl(const Ticks& timestamp, TimePartitions* time_partitions) {
  assert_set_gc_id();
  assert(_tenuring_threshold != UNSET_TENURING_THRESHOLD, "Tenuring threshold has not been reported");

  GCTracer::report_gc_end_impl(timestamp, time_partitions);
  send_young_gc_event();

  _tenuring_threshold = UNSET_TENURING_THRESHOLD;
}

void YoungGCTracer::report_promotion_failed(const PromotionFailedInfo& pf_info) {
  assert_set_gc_id();

  send_promotion_failed_event(pf_info);
}

void YoungGCTracer::report_tenuring_threshold(const uint tenuring_threshold) {
  _tenuring_threshold = tenuring_threshold;
}

void OldGCTracer::report_gc_end_impl(const Ticks& timestamp, TimePartitions* time_partitions) {
  assert_set_gc_id();

  GCTracer::report_gc_end_impl(timestamp, time_partitions);
  send_old_gc_event();
}

void ParallelOldTracer::report_gc_end_impl(const Ticks& timestamp, TimePartitions* time_partitions) {
  assert_set_gc_id();

  OldGCTracer::report_gc_end_impl(timestamp, time_partitions);
  send_parallel_old_event();
}

void ParallelOldTracer::report_dense_prefix(void* dense_prefix) {
  assert_set_gc_id();

  _parallel_old_gc_info.report_dense_prefix(dense_prefix);
}

void OldGCTracer::report_concurrent_mode_failure() {
  assert_set_gc_id();

  send_concurrent_mode_failure_event();
}

#if INCLUDE_ALL_GCS
void G1NewTracer::report_yc_type(G1YCType type) {
  assert_set_gc_id();

  _g1_young_gc_info.set_type(type);
}

void G1NewTracer::report_gc_end_impl(const Ticks& timestamp, TimePartitions* time_partitions) {
  assert_set_gc_id();

  YoungGCTracer::report_gc_end_impl(timestamp, time_partitions);
  send_g1_young_gc_event();
}

void G1NewTracer::report_evacuation_info(EvacuationInfo* info) {
  assert_set_gc_id();

  send_evacuation_info_event(info);
}

void G1NewTracer::report_evacuation_failed(EvacuationFailedInfo& ef_info) {
  assert_set_gc_id();

  send_evacuation_failed_event(ef_info);
  ef_info.reset();
}
#endif
