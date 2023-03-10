/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
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

#ifndef SHARE_VM_UTILITIES_RESOURCEHASH_HPP
#define SHARE_VM_UTILITIES_RESOURCEHASH_HPP

#include "memory/allocation.hpp"
#include "utilities/top.hpp"

template<typename K> struct ResourceHashtableFns {
    typedef unsigned (*hash_fn)(K const&);
    typedef bool (*equals_fn)(K const&, K const&);
};

template<typename K> unsigned primitive_hash(const K& k) {
  unsigned hash = (unsigned)((uintptr_t)k);
  return hash ^ (hash > 3); // just in case we're dealing with aligned ptrs
}

template<typename K> bool primitive_equals(const K& k0, const K& k1) {
  return k0 == k1;
}

template<
    typename K, typename V,
    typename ResourceHashtableFns<K>::hash_fn   HASH   = primitive_hash<K>,
    typename ResourceHashtableFns<K>::equals_fn EQUALS = primitive_equals<K>,
    unsigned SIZE = 256
    >
class ResourceHashtable : public ResourceObj {
 private:

  class Node : public ResourceObj {
   public:
    unsigned _hash;
    K _key;
    V _value;
    Node* _next;

    Node(unsigned hash, K const& key, V const& value) :
        _hash(hash), _key(key), _value(value), _next(NULL) {}
  };

  Node* _table[SIZE];

  // Returns a pointer to where the node where the value would reside if
  // it's in the table.
  Node** lookup_node(unsigned hash, K const& key) {
    unsigned index = hash % SIZE;
    Node** ptr = &_table[index];
    while (*ptr != NULL) {
      Node* node = *ptr;
      if (node->_hash == hash && EQUALS(key, node->_key)) {
        break;
      }
      ptr = &(node->_next);
    }
    return ptr;
  }

  Node const** lookup_node(unsigned hash, K const& key) const {
    return const_cast<Node const**>(
        const_cast<ResourceHashtable*>(this)->lookup_node(hash, key));
  }

 public:
  ResourceHashtable() { memset(_table, 0, SIZE * sizeof(Node*)); }

  bool contains(K const& key) const {
    return get(key) != NULL;
  }

  V* get(K const& key) const {
    unsigned hv = HASH(key);
    Node const** ptr = lookup_node(hv, key);
    if (*ptr != NULL) {
      return const_cast<V*>(&(*ptr)->_value);
    } else {
      return NULL;
    }
  }

  // Inserts or replaces a value in the table
  void put(K const& key, V const& value) {
    unsigned hv = HASH(key);
    Node** ptr = lookup_node(hv, key);
    if (*ptr != NULL) {
      (*ptr)->_value = value;
    } else {
      *ptr = new Node(hv, key, value);
    }
  }

  // ITER contains bool do_entry(K const&, V const&), which will be
  // called for each entry in the table.  If do_entry() returns false,
  // the iteration is cancelled.
  template<class ITER>
  void iterate(ITER* iter) const {
    Node* const* bucket = _table;
    while (bucket < &_table[SIZE]) {
      Node* node = *bucket;
      while (node != NULL) {
        bool cont = iter->do_entry(node->_key, node->_value);
        if (!cont) { return; }
        node = node->_next;
      }
      ++bucket;
    }
  }
};


#endif // SHARE_VM_UTILITIES_RESOURCEHASH_HPP
