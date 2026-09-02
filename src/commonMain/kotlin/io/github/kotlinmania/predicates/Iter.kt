// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source iter.rs
package io.github.kotlinmania.predicates

import io.github.kotlinmania.predicates.core.Predicate
import io.github.kotlinmania.predicates.core.reflection.Case
import io.github.kotlinmania.predicates.core.reflection.Parameter
import io.github.kotlinmania.predicates.core.reflection.Product

/**
 * Predicate that returns `true` if `variable` is a member of the pre-defined
 * set, otherwise returns `false`.
 */
class InPredicate<T>(
    internal val inner: List<T>,
) : Predicate<T> {
    override fun eval(variable: T): Boolean = inner.contains(variable)

    override fun findCase(expected: Boolean, variable: T): Case? =
        defaultFindCase(this, expected, variable)?.addProduct(
            Product("var", DebugAdapter.new(variable).toString()),
        )

    override fun parameters(): Iterator<Parameter> =
        listOf(Parameter("values", inner)).iterator()

    fun fmt(): String {
        val palette = Palette.new(false)
        return "${palette.varValue("var")} ${palette.description("in")} ${palette.expected("values")}"
    }

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InPredicate<*>) return false
        return inner == other.inner
    }

    override fun hashCode(): Int = inner.hashCode()
}

/**
 * Creates a new predicate that will return `true` when the given `variable` is
 * contained with the set of items provided and sorted for binary search.
 */
fun <T : Comparable<*>> InPredicate<T>.sort(): OrdInPredicate<T> {
    val items = inner.toMutableList()
    @Suppress("UNCHECKED_CAST")
    (items as MutableList<Comparable<Any?>>).sort()
    return OrdInPredicate(items)
}

/**
 * Creates a new predicate that will return `true` when the given `variable` is
 * contained with the set of items provided.
 */
fun <T> inIter(iter: Iterable<T>): InPredicate<T> =
    InPredicate(iter.toList())

/**
 * Predicate that returns `true` if `variable` is a member of the pre-defined
 * sorted set, otherwise returns `false`.
 */
class OrdInPredicate<T : Comparable<*>>(
    internal val inner: List<T>,
) : Predicate<T> {
    @Suppress("UNCHECKED_CAST")
    override fun eval(variable: T): Boolean =
        (inner as List<Comparable<Any?>>).binarySearch(variable as Comparable<Any?>) >= 0

    override fun findCase(expected: Boolean, variable: T): Case? =
        defaultFindCase(this, expected, variable)?.addProduct(
            Product("var", DebugAdapter.new(variable).toString()),
        )

    override fun parameters(): Iterator<Parameter> =
        listOf(Parameter("values", inner)).iterator()

    fun fmt(): String {
        val palette = Palette.new(false)
        return "${palette.varValue("var")} ${palette.description("in")} ${palette.expected("values")}"
    }

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OrdInPredicate<*>) return false
        return inner == other.inner
    }

    override fun hashCode(): Int = inner.hashCode()
}

/**
 * Predicate that returns `true` if `variable` is a member of the pre-defined
 * `HashSet`, otherwise returns `false`.
 */
class HashableInPredicate<T>(
    internal val inner: Set<T>,
) : Predicate<T> {
    override fun eval(variable: T): Boolean = inner.contains(variable)

    override fun findCase(expected: Boolean, variable: T): Case? =
        defaultFindCase(this, expected, variable)?.addProduct(
            Product("var", DebugAdapter.new(variable).toString()),
        )

    override fun parameters(): Iterator<Parameter> =
        listOf(Parameter("values", inner)).iterator()

    fun fmt(): String {
        val palette = Palette.new(false)
        return "${palette.varValue("var")} ${palette.description("in")} ${palette.expected("values")}"
    }

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HashableInPredicate<*>) return false
        return inner == other.inner
    }

    override fun hashCode(): Int = inner.hashCode()
}

/**
 * Creates a new predicate that will return `true` when the given `variable` is
 * contained with the set of items provided.
 */
fun <T> inHash(iter: Iterable<T>): HashableInPredicate<T> =
    HashableInPredicate(iter.toSet())
