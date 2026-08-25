// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source str/regex.rs
package io.github.kotlinmania.predicates.str

import io.github.kotlinmania.predicates.Palette
import io.github.kotlinmania.predicates.core.Predicate
import io.github.kotlinmania.predicates.core.reflection.Case
import io.github.kotlinmania.predicates.core.reflection.Parameter
import io.github.kotlinmania.predicates.core.reflection.Product
import io.github.kotlinmania.predicates.defaultFindCase

/**
 * Predicate that uses regex matching.
 *
 * This is created by [isMatch].
 */
class RegexPredicate(
    internal val re: Regex,
) : Predicate<String> {
    /**
     * Require a specific count of matches.
     */
    fun count(count: Int): RegexMatchesPredicate =
        RegexMatchesPredicate(re, count)

    override fun eval(variable: String): Boolean =
        re.containsMatchIn(variable)

    override fun findCase(expected: Boolean, variable: String): Case? =
        defaultFindCase(this, expected, variable)?.addProduct(
            Product("var", variable),
        )

    fun fmt(): String {
        val palette = Palette.new(false)
        return "${palette.varValue("var")}.${palette.description("is_match")}(${palette.expected(re.pattern)})"
    }

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RegexPredicate) return false
        return re.pattern == other.re.pattern
    }

    override fun hashCode(): Int = re.pattern.hashCode()
}

/**
 * Predicate that checks for repeated patterns.
 *
 * This is created by `isMatch(...).count`.
 */
class RegexMatchesPredicate(
    internal val re: Regex,
    internal val count: Int,
) : Predicate<String> {
    override fun eval(variable: String): Boolean =
        re.findAll(variable).count() == count

    override fun findCase(expected: Boolean, variable: String): Case? {
        val actualCount = re.findAll(variable).count()
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

    fun fmt(): String {
        val palette = Palette.new(false)
        return "${palette.varValue("var")}.${palette.description("is_match")}(${palette.expected(re.pattern)})"
    }

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RegexMatchesPredicate) return false
        return re.pattern == other.re.pattern && count == other.count
    }

    override fun hashCode(): Int = 31 * re.pattern.hashCode() + count
}

/**
 * Creates a new `Predicate` that uses a regular expression to match the string.
 */
fun isMatch(pattern: String): RegexPredicate =
    RegexPredicate(Regex(pattern))
