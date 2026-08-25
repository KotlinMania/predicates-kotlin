// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source constant.rs
package io.github.kotlinmania.predicates

import io.github.kotlinmania.predicates.core.Predicate
import io.github.kotlinmania.predicates.core.reflection.Case
import io.github.kotlinmania.predicates.core.reflection.Parameter

/**
 * Predicate that always returns a constant (boolean) result.
 *
 * This is created by the [always] and [never] functions.
 */
class BooleanPredicate(
    internal val retval: Boolean,
) : Predicate<Any?> {
    override fun eval(variable: Any?): Boolean = retval

    override fun findCase(expected: Boolean, variable: Any?): Case? =
        defaultFindCase(this, expected, variable)

    override fun parameters(): Iterator<Parameter> =
        listOf(Parameter("value", retval)).iterator()

    fun fmt(): String {
        val palette = Palette.new(false)
        return palette.expected(retval).toString()
    }

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BooleanPredicate) return false
        return retval == other.retval
    }

    override fun hashCode(): Int = retval.hashCode()
}

/**
 * Creates a new `Predicate` that always returns `true`.
 *
 * # Examples
 *
 * ```kotlin
 * val predicateFn = always()
 * assertTrue(predicateFn.eval(5))
 * assertTrue(predicateFn.eval(10))
 * assertTrue(predicateFn.eval(15))
 * ```
 */
fun always(): BooleanPredicate = BooleanPredicate(retval = true)

/**
 * Creates a new `Predicate` that always returns `false`.
 *
 * # Examples
 *
 * ```kotlin
 * val predicateFn = never()
 * assertFalse(predicateFn.eval(5))
 * assertFalse(predicateFn.eval(10))
 * assertFalse(predicateFn.eval(15))
 * ```
 */
fun never(): BooleanPredicate = BooleanPredicate(retval = false)
