// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source ../predicates-core/src/core.rs
package io.github.kotlinmania.predicates.core

import io.github.kotlinmania.predicates.core.reflection.Case
import io.github.kotlinmania.predicates.core.reflection.PredicateReflection

/**
 * Trait for generically evaluating a type against a dynamically created
 * predicate function.
 *
 * The exact meaning of `eval` depends on the situation, but will usually
 * mean that the evaluated item is in some sort of pre-defined set. This is
 * different from `Comparable` and equality in that an `item` will almost
 * never be the same type as the implementing `Predicate` type.
 */
interface Predicate<Item> : PredicateReflection {
    /**
     * Execute this `Predicate` against `variable`, returning the resulting
     * boolean.
     */
    fun eval(variable: Item): Boolean

    /**
     * Find a case that proves this predicate as `expected` when run against
     * `variable`.
     */
    fun findCase(expected: Boolean, variable: Item): Case? {
        val actual = eval(variable)
        return if (expected == actual) {
            Case(null, actual)
        } else {
            null
        }
    }
}
