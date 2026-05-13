// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source src/boolean.rs
package io.github.kotlinmania.predicates

/**
 * Definition of boolean logic combinators over `Predicate`s.
 */

import io.github.kotlinmania.predicates.core.Predicate
import io.github.kotlinmania.predicates.core.reflection.Case
import io.github.kotlinmania.predicates.core.reflection.Child
import io.github.kotlinmania.predicates.core.reflection.PredicateReflection

/**
 * Predicate that combines two `Predicate`s, returning the AND of the results.
 *
 * This is created by the `Predicate.and` extension function.
 */
class AndPredicate<M1 : Predicate<Item>, M2 : Predicate<Item>, Item>(
    private val a: M1,
    private val b: M2,
) : Predicate<Item> {

    override fun eval(variable: Item): Boolean = a.eval(variable) && b.eval(variable)

    override fun findCase(expected: Boolean, variable: Item): Case? {
        val childA = a.findCase(expected, variable)
        return when {
            expected && childA != null -> {
                b.findCase(expected, variable)?.let { childB ->
                    Case(this, expected).addChild(childA).addChild(childB)
                }
            }
            expected && childA == null -> null
            !expected && childA != null -> Case(this, expected).addChild(childA)
            else -> b.findCase(expected, variable)?.let { childB ->
                Case(this, expected).addChild(childB)
            }
        }
    }

    override fun children(): Iterator<Child> =
        listOf(Child("left", a), Child("right", b)).iterator()

    override fun toString(): String = "($a && $b)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AndPredicate<*, *, *>) return false
        return a == other.a && b == other.b
    }

    override fun hashCode(): Int = 31 * a.hashCode() + b.hashCode()

    companion object {
        /** Create a new `AndPredicate` over predicates `a` and `b`. */
        fun <M1 : Predicate<Item>, M2 : Predicate<Item>, Item> new(
            a: M1,
            b: M2,
        ): AndPredicate<M1, M2, Item> = AndPredicate(a, b)
    }
}

/**
 * Predicate that combines two `Predicate`s, returning the OR of the results.
 *
 * This is created by the `Predicate.or` extension function.
 */
class OrPredicate<M1 : Predicate<Item>, M2 : Predicate<Item>, Item>(
    private val a: M1,
    private val b: M2,
) : Predicate<Item> {

    override fun eval(variable: Item): Boolean = a.eval(variable) || b.eval(variable)

    override fun findCase(expected: Boolean, variable: Item): Case? {
        val childA = a.findCase(expected, variable)
        return when {
            expected && childA != null -> Case(this, expected).addChild(childA)
            expected && childA == null -> {
                b.findCase(expected, variable)?.let { childB ->
                    Case(this, expected).addChild(childB)
                }
            }
            !expected && childA != null -> {
                b.findCase(expected, variable)?.let { childB ->
                    Case(this, expected).addChild(childA).addChild(childB)
                }
            }
            else -> null
        }
    }

    override fun children(): Iterator<Child> =
        listOf(Child("left", a), Child("right", b)).iterator()

    override fun toString(): String = "($a || $b)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OrPredicate<*, *, *>) return false
        return a == other.a && b == other.b
    }

    override fun hashCode(): Int = 31 * a.hashCode() + b.hashCode()

    companion object {
        /** Create a new `OrPredicate` over predicates `a` and `b`. */
        fun <M1 : Predicate<Item>, M2 : Predicate<Item>, Item> new(
            a: M1,
            b: M2,
        ): OrPredicate<M1, M2, Item> = OrPredicate(a, b)
    }
}

/**
 * Predicate that returns a `Predicate` taking the logical NOT of the result.
 *
 * This is created by the `Predicate.not` extension function.
 */
class NotPredicate<M : Predicate<Item>, Item>(
    private val inner: M,
) : Predicate<Item> {

    override fun eval(variable: Item): Boolean = !inner.eval(variable)

    override fun findCase(expected: Boolean, variable: Item): Case? {
        return inner.findCase(!expected, variable)?.let { child ->
            Case(this, expected).addChild(child)
        }
    }

    override fun children(): Iterator<Child> =
        listOf(Child("predicate", inner)).iterator()

    override fun toString(): String = "(! $inner)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NotPredicate<*, *>) return false
        return inner == other.inner
    }

    override fun hashCode(): Int = inner.hashCode()

    companion object {
        /** Create a new `NotPredicate` over predicate `inner`. */
        fun <M : Predicate<Item>, Item> new(inner: M): NotPredicate<M, Item> = NotPredicate(inner)
    }
}

/**
 * Compute the logical AND of two `Predicate` results, returning the result.
 *
 * # Examples
 *
 * ```kotlin
 * val predicateFn1 = always().and(always())
 * val predicateFn2 = always().and(never())
 * assertEquals(true, predicateFn1.eval(4))
 * assertEquals(false, predicateFn2.eval(4))
 * ```
 */
fun <M1 : Predicate<Item>, M2 : Predicate<Item>, Item> M1.and(
    other: M2,
): AndPredicate<M1, M2, Item> = AndPredicate.new(this, other)

/**
 * Compute the logical OR of two `Predicate` results, returning the result.
 *
 * # Examples
 *
 * ```kotlin
 * val predicateFn1 = always().or(always())
 * val predicateFn2 = always().or(never())
 * val predicateFn3 = never().or(never())
 * assertEquals(true, predicateFn1.eval(4))
 * assertEquals(true, predicateFn2.eval(4))
 * assertEquals(false, predicateFn3.eval(4))
 * ```
 */
fun <M1 : Predicate<Item>, M2 : Predicate<Item>, Item> M1.or(
    other: M2,
): OrPredicate<M1, M2, Item> = OrPredicate.new(this, other)

/**
 * Compute the logical NOT of a `Predicate`, returning the result.
 *
 * # Examples
 *
 * ```kotlin
 * val predicateFn1 = always().not()
 * val predicateFn2 = never().not()
 * assertEquals(false, predicateFn1.eval(4))
 * assertEquals(true, predicateFn2.eval(4))
 * ```
 */
fun <M : Predicate<Item>, Item> M.not(): NotPredicate<M, Item> = NotPredicate.new(this)

/**
 * `Predicate` extension marker that documents the boolean-logic surface.
 *
 * The upstream Rust trait `PredicateBooleanExt<Item>` is blanket-implemented
 * for every `P: Predicate<Item>` and only carries default `and`/`or`/`not`
 * methods. Kotlin renders the same surface as the three top-level extension
 * functions above (`and`, `or`, `not`), so the trait itself collapses to a
 * documentation anchor for callers that `import predicates.prelude.*`.
 *
 * Callers do not need to reference [PredicateBooleanExt] directly; the
 * extensions are picked up by the compiler whenever the receiver is a
 * `Predicate<Item>`. The marker interface stays here so the upstream import
 * path `predicates.prelude.PredicateBooleanExt` has a Kotlin landing target.
 */
interface PredicateBooleanExt<Item> : Predicate<Item>
