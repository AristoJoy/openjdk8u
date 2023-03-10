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
 */
package java.util.stream;

import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;

/**
 * Test scenarios for int streams.
 *
 * Each scenario is provided with a data source, a function that maps a fresh
 * stream (as provided by the data source) to a new stream, and a sink to
 * receive results.  Each scenario describes a different way of computing the
 * stream contents.  The test driver will ensure that all scenarios produce
 * the same output (modulo allowable differences in ordering).
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public enum IntStreamTestScenario implements OpTestCase.BaseStreamTestScenario {

    STREAM_FOR_EACH_WITH_CLOSE(false) {
        <T, S_IN extends BaseStream<T, S_IN>>
        void _run(TestData<T, S_IN> data, IntConsumer b, Function<S_IN, IntStream> m) {
            IntStream s = m.apply(data.stream());
            if (s.isParallel()) {
                s = s.sequential();
            }
            s.forEach(b);
            s.close();
        }
    },

    STREAM_TO_ARRAY(false) {
        <T, S_IN extends BaseStream<T, S_IN>>
        void _run(TestData<T, S_IN> data, IntConsumer b, Function<S_IN, IntStream> m) {
            for (int t : m.apply(data.stream()).toArray()) {
                b.accept(t);
            }
        }
    },

    STREAM_ITERATOR(false) {
        <T, S_IN extends BaseStream<T, S_IN>>
        void _run(TestData<T, S_IN> data, IntConsumer b, Function<S_IN, IntStream> m) {
            for (PrimitiveIterator.OfInt seqIter = m.apply(data.stream()).iterator(); seqIter.hasNext(); )
                b.accept(seqIter.nextInt());
        }
    },

    // Wrap as stream, and spliterate then iterate in pull mode
    STREAM_SPLITERATOR(false) {
        <T, S_IN extends BaseStream<T, S_IN>>
        void _run(TestData<T, S_IN> data, IntConsumer b, Function<S_IN, IntStream> m) {
            for (Spliterator.OfInt spl = m.apply(data.stream()).spliterator(); spl.tryAdvance(b); ) {
            }
        }
    },

    // Wrap as stream, spliterate, then split a few times mixing advances with forEach
    STREAM_SPLITERATOR_WITH_MIXED_TRAVERSE_AND_SPLIT(false) {
        <T, S_IN extends BaseStream<T, S_IN>>
        void _run(TestData<T, S_IN> data, IntConsumer b, Function<S_IN, IntStream> m) {
            SpliteratorTestHelper.mixedTraverseAndSplit(b, m.apply(data.stream()).spliterator());
        }
    },

    // Wrap as stream, and spliterate then iterate in pull mode
    STREAM_SPLITERATOR_FOREACH(false) {
        <T, S_IN extends BaseStream<T, S_IN>>
        void _run(TestData<T, S_IN> data, IntConsumer b, Function<S_IN, IntStream> m) {
            m.apply(data.stream()).spliterator().forEachRemaining(b);
        }
    },

    PAR_STREAM_SEQUENTIAL_FOR_EACH(true) {
        <T, S_IN extends BaseStream<T, S_IN>>
        void _run(TestData<T, S_IN> data, IntConsumer b, Function<S_IN, IntStream> m) {
            m.apply(data.parallelStream()).sequential().forEach(b);
        }
    },

    // Wrap as parallel stream + forEachOrdered
    PAR_STREAM_FOR_EACH_ORDERED(true) {
        <T, S_IN extends BaseStream<T, S_IN>>
        void _run(TestData<T, S_IN> data, IntConsumer b, Function<S_IN, IntStream> m) {
            // @@@ Want to explicitly select ordered equalator
            m.apply(data.parallelStream()).forEachOrdered(b);
        }
    },

    // Wrap as stream, and spliterate then iterate sequentially
    PAR_STREAM_SPLITERATOR(true) {
        <T, S_IN extends BaseStream<T, S_IN>>
        void _run(TestData<T, S_IN> data, IntConsumer b, Function<S_IN, IntStream> m) {
            for (Spliterator.OfInt spl = m.apply(data.parallelStream()).spliterator(); spl.tryAdvance(b); ) {
            }
        }
    },

    // Wrap as stream, and spliterate then iterate sequentially
    PAR_STREAM_SPLITERATOR_FOREACH(true) {
        <T, S_IN extends BaseStream<T, S_IN>>
        void _run(TestData<T, S_IN> data, IntConsumer b, Function<S_IN, IntStream> m) {
            m.apply(data.parallelStream()).spliterator().forEachRemaining(b);
        }
    },

    PAR_STREAM_TO_ARRAY(true) {
        <T, S_IN extends BaseStream<T, S_IN>>
        void _run(TestData<T, S_IN> data, IntConsumer b, Function<S_IN, IntStream> m) {
            for (int t : m.apply(data.parallelStream()).toArray())
                b.accept(t);
        }
    },

    // Wrap as parallel stream, get the spliterator, wrap as a stream + toArray
    PAR_STREAM_SPLITERATOR_STREAM_TO_ARRAY(true) {
        <T, S_IN extends BaseStream<T, S_IN>>
        void _run(TestData<T, S_IN> data, IntConsumer b, Function<S_IN, IntStream> m) {
            IntStream s = m.apply(data.parallelStream());
            Spliterator.OfInt sp = s.spliterator();
            IntStream ss = StreamSupport.intStream(() -> sp,
                                                   StreamOpFlag.toCharacteristics(OpTestCase.getStreamFlags(s))
                                                   | (sp.getExactSizeIfKnown() < 0 ? 0 : Spliterator.SIZED),
                                                   true);
            for (int t : ss.toArray())
                b.accept(t);
        }
    },

    PAR_STREAM_TO_ARRAY_CLEAR_SIZED(true) {
        <T, S_IN extends BaseStream<T, S_IN>>
        void _run(TestData<T, S_IN> data, IntConsumer b, Function<S_IN, IntStream> m) {
            S_IN pipe1 = (S_IN) OpTestCase.chain(data.parallelStream(),
                                                 new FlagDeclaringOp(StreamOpFlag.NOT_SIZED, data.getShape()));
            IntStream pipe2 = m.apply(pipe1);

            for (int t : pipe2.toArray())
                b.accept(t);
        }
    },;

    private boolean isParallel;

    IntStreamTestScenario(boolean isParallel) {
        this.isParallel = isParallel;
    }

    public StreamShape getShape() {
        return StreamShape.INT_VALUE;
    }

    public boolean isParallel() {
        return isParallel;
    }

    public <T, U, S_IN extends BaseStream<T, S_IN>, S_OUT extends BaseStream<U, S_OUT>>
    void run(TestData<T, S_IN> data, Consumer<U> b, Function<S_IN, S_OUT> m) {
        _run(data, (IntConsumer) b, (Function<S_IN, IntStream>) m);
    }

    abstract <T, S_IN extends BaseStream<T, S_IN>>
    void _run(TestData<T, S_IN> data, IntConsumer b, Function<S_IN, IntStream> m);

}
