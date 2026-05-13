// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source src/str/basics.rs
package io.github.kotlinmania.predicates.str

import io.github.kotlinmania.predicates.Palette
import io.github.kotlinmania.predicates.core.Predicate
import io.github.kotlinmania.predicates.core.reflection.Case
import io.github.kotlinmania.predicates.core.reflection.Parameter
import io.github.kotlinmania.predicates.core.reflection.Product
import io.github.kotlinmania.predicates.defaultFindCase

/**
 * Predicate that checks for empty strings.
 *
 * This is created by `predicates::str::isEmpty`.
 */
class IsEmptyPredicate : Predicate<String> {

    override fun eval(variable: String): Boolean = variable.isEmpty()

    override fun findCase(expected: Boolean, variable: String): Case? =
        defaultFindCase(this, expected, variable)?.addProduct(Product("var", variable))

    override fun toString(): String {
        val palette = Palette.plain()
        return "${palette.`var`("var")}.${palette.description("is_empty")}()"
    }

    override fun equals(other: Any?): Boolean = other is IsEmptyPredicate

    override fun hashCode(): Int = IsEmptyPredicate::class.hashCode()
}

/**
 * Creates a new `Predicate` that ensures a str is empty.
 *
 * # Examples
 *
 * ```kotlin
 * val predicateFn = isEmpty()
 * assertEquals(true, predicateFn.eval(""))
 * assertEquals(false, predicateFn.eval("Food World"))
 * ```
 */
fun isEmpty(): IsEmptyPredicate = IsEmptyPredicate()

/**
 * Predicate checks start of str.
 *
 * This is created by `predicates::str::startsWith`.
 */
class StartsWithPredicate internal constructor(private val pattern: String) : Predicate<String> {

    override fun eval(variable: String): Boolean = variable.startsWith(pattern)

    override fun findCase(expected: Boolean, variable: String): Case? =
        defaultFindCase(this, expected, variable)?.addProduct(Product("var", variable))

    override fun toString(): String {
        val palette = Palette.plain()
        return "${palette.`var`("var")}.${palette.description("starts_with")}(\"$pattern\")"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StartsWithPredicate) return false
        return pattern == other.pattern
    }

    override fun hashCode(): Int = pattern.hashCode()
}

/**
 * Creates a new `Predicate` that ensures a str starts with `pattern`.
 *
 * # Examples
 *
 * ```kotlin
 * val predicateFn = startsWith("Hello")
 * assertEquals(true, predicateFn.eval("Hello World"))
 * assertEquals(false, predicateFn.eval("Goodbye World"))
 * ```
 */
fun startsWith(pattern: String): StartsWithPredicate = StartsWithPredicate(pattern)

/**
 * Predicate checks end of str.
 *
 * This is created by `predicates::str::endsWith`.
 */
class EndsWithPredicate internal constructor(private val pattern: String) : Predicate<String> {

    override fun eval(variable: String): Boolean = variable.endsWith(pattern)

    override fun findCase(expected: Boolean, variable: String): Case? =
        defaultFindCase(this, expected, variable)?.addProduct(Product("var", variable))

    override fun toString(): String {
        val palette = Palette.plain()
        return "${palette.`var`("var")}.${palette.description("ends_with")}(\"$pattern\")"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EndsWithPredicate) return false
        return pattern == other.pattern
    }

    override fun hashCode(): Int = pattern.hashCode()
}

/**
 * Creates a new `Predicate` that ensures a str ends with `pattern`.
 *
 * # Examples
 *
 * ```kotlin
 * val predicateFn = endsWith("World")
 * assertEquals(true, predicateFn.eval("Hello World"))
 * assertEquals(false, predicateFn.eval("Hello Moon"))
 * ```
 */
fun endsWith(pattern: String): EndsWithPredicate = EndsWithPredicate(pattern)

/**
 * Predicate that checks for patterns.
 *
 * This is created by `predicates::str::contains`.
 */
class ContainsPredicate internal constructor(internal val pattern: String) : Predicate<String> {

    /**
     * Require a specific count of matches.
     *
     * # Examples
     *
     * ```kotlin
     * val predicateFn = contains("Two").count(2)
     * assertEquals(true, predicateFn.eval("One Two Three Two One"))
     * assertEquals(false, predicateFn.eval("One Two Three"))
     * ```
     */
    fun count(count: Int): MatchesPredicate = MatchesPredicate(pattern, count)

    override fun eval(variable: String): Boolean = variable.contains(pattern)

    override fun findCase(expected: Boolean, variable: String): Case? =
        defaultFindCase(this, expected, variable)?.addProduct(Product("var", variable))

    override fun toString(): String {
        val palette = Palette.plain()
        return "${palette.`var`("var")}.${palette.description("contains")}(${palette.expected(pattern)})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ContainsPredicate) return false
        return pattern == other.pattern
    }

    override fun hashCode(): Int = pattern.hashCode()
}

/**
 * Predicate that checks for repeated patterns.
 *
 * This is created by `contains(...).count`.
 */
class MatchesPredicate internal constructor(
    private val pattern: String,
    private val count: Int,
) : Predicate<String> {

    private fun countMatches(variable: String): Int {
        if (pattern.isEmpty()) {
            // Upstream `str::matches("")` yields a match between every char
            // boundary, so the count is `len(chars) + 1`.
            return variable.length + 1
        }
        var found = 0
        var index = 0
        while (true) {
            val hit = variable.indexOf(pattern, index)
            if (hit < 0) return found
            found += 1
            index = hit + pattern.length
        }
    }

    override fun eval(variable: String): Boolean = countMatches(variable) == count

    override fun findCase(expected: Boolean, variable: String): Case? {
        val actualCount = countMatches(variable)
        val result = count == actualCount
        return if (result == expected) {
            Case(this, result)
                .addProduct(Product("var", variable))
                .addProduct(Product("actual count", actualCount))
        } else {
            null
        }
    }

    override fun parameters(): Iterator<Parameter> =
        listOf(Parameter("count", count)).iterator()

    override fun toString(): String {
        val palette = Palette.plain()
        return "${palette.`var`("var")}.${palette.description("contains")}(${palette.expected(pattern)})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MatchesPredicate) return false
        return pattern == other.pattern && count == other.count
    }

    override fun hashCode(): Int = 31 * pattern.hashCode() + count
}

/**
 * Creates a new `Predicate` that ensures a str contains `pattern`.
 *
 * # Examples
 *
 * ```kotlin
 * val predicateFn = contains("Two")
 * assertEquals(true, predicateFn.eval("One Two Three"))
 * assertEquals(false, predicateFn.eval("Four Five Six"))
 * ```
 */
fun contains(pattern: String): ContainsPredicate = ContainsPredicate(pattern)
