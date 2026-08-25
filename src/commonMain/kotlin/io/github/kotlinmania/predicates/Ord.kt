// Copyright (c) 2018, 2022 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source ord.rs
package io.github.kotlinmania.predicates

import io.github.kotlinmania.predicates.core.Predicate
import io.github.kotlinmania.predicates.core.reflection.Case
import io.github.kotlinmania.predicates.core.reflection.Product

internal enum class EqOps(private val op: String) {
    Equal("=="),
    NotEqual("!="),
    ;

    override fun toString(): String = op
}

/**
 * Predicate that returns `true` if `variable` matches the pre-defined `Eq`
 * value, otherwise returns `false`.
 *
 * This is created by the [eq] and [ne] functions.
 */
class EqPredicate<T> internal constructor(
    internal val constant: T,
    internal val op: EqOps,
) : Predicate<T> {
    override fun eval(variable: T): Boolean =
        when (op) {
            EqOps.Equal -> variable == constant
            EqOps.NotEqual -> variable != constant
        }

    override fun findCase(expected: Boolean, variable: T): Case? =
        defaultFindCase(this, expected, variable)?.addProduct(
            Product("var", DebugAdapter.new(variable).toString()),
        )

    fun fmt(): String {
        val palette = Palette.new(false)
        return "${palette.varValue("var")} ${palette.description(op)} ${palette.expected(DebugAdapter.new(constant))}"
    }

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EqPredicate<*>) return false
        return constant == other.constant && op == other.op
    }

    override fun hashCode(): Int {
        var result = constant?.hashCode() ?: 0
        result = 31 * result + op.hashCode()
        return result
    }
}

/**
 * Creates a new predicate that will return `true` when the given `variable` is
 * equal to a pre-defined value.
 */
fun <T> eq(constant: T): EqPredicate<T> =
    EqPredicate(constant, EqOps.Equal)

/**
 * Creates a new predicate that will return `true` when the given `variable` is
 * _not_ equal to a pre-defined value.
 */
fun <T> ne(constant: T): EqPredicate<T> =
    EqPredicate(constant, EqOps.NotEqual)

internal enum class OrdOps(private val op: String) {
    LessThan("<"),
    LessThanOrEqual("<="),
    GreaterThanOrEqual(">="),
    GreaterThan(">"),
    ;

    override fun toString(): String = op
}

/**
 * Predicate that returns `true` if `variable` matches the pre-defined `Ord`
 * value, otherwise returns `false`.
 *
 * This is created by the [gt], [ge], [lt], [le] functions.
 */
class OrdPredicate<T : Comparable<T>> internal constructor(
    internal val constant: T,
    internal val op: OrdOps,
) : Predicate<T> {
    override fun eval(variable: T): Boolean =
        when (op) {
            OrdOps.LessThan -> variable < constant
            OrdOps.LessThanOrEqual -> variable <= constant
            OrdOps.GreaterThanOrEqual -> variable >= constant
            OrdOps.GreaterThan -> variable > constant
        }

    override fun findCase(expected: Boolean, variable: T): Case? =
        defaultFindCase(this, expected, variable)?.addProduct(
            Product("var", DebugAdapter.new(variable).toString()),
        )

    fun fmt(): String {
        val palette = Palette.new(false)
        return "${palette.varValue("var")} ${palette.description(op)} ${palette.expected(DebugAdapter.new(constant))}"
    }

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OrdPredicate<*>) return false
        return constant == other.constant && op == other.op
    }

    override fun hashCode(): Int {
        var result = constant.hashCode()
        result = 31 * result + op.hashCode()
        return result
    }
}

/**
 * Creates a new predicate that will return `true` when the given `variable` is
 * less than a pre-defined value.
 */
fun <T : Comparable<T>> lt(constant: T): OrdPredicate<T> =
    OrdPredicate(constant, OrdOps.LessThan)

/**
 * Creates a new predicate that will return `true` when the given `variable` is
 * less than or equal to a pre-defined value.
 */
fun <T : Comparable<T>> le(constant: T): OrdPredicate<T> =
    OrdPredicate(constant, OrdOps.LessThanOrEqual)

/**
 * Creates a new predicate that will return `true` when the given `variable` is
 * greater than or equal to a pre-defined value.
 */
fun <T : Comparable<T>> ge(constant: T): OrdPredicate<T> =
    OrdPredicate(constant, OrdOps.GreaterThanOrEqual)

/**
 * Creates a new predicate that will return `true` when the given `variable` is
 * greater than a pre-defined value.
 */
fun <T : Comparable<T>> gt(constant: T): OrdPredicate<T> =
    OrdPredicate(constant, OrdOps.GreaterThan)
