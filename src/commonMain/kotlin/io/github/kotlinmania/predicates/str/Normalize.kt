// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source str/normalize.rs
package io.github.kotlinmania.predicates.str

import io.github.kotlinmania.predicates.core.Predicate
import io.github.kotlinmania.predicates.core.reflection.Case
import io.github.kotlinmania.predicates.core.reflection.Child

private fun normalizeLineEndings(input: String): String =
    input.replace("\r\n", "\n").replace("\r", "\n")

/**
 * Predicate adapter that normalizes the newlines contained in the variable being tested.
 *
 * This is created by `pred.normalize()`.
 */
class NormalizedPredicate<P : Predicate<String>>(
    internal val p: P,
) : Predicate<String> {
    override fun eval(variable: String): Boolean {
        val normalized = normalizeLineEndings(variable)
        return p.eval(normalized)
    }

    override fun findCase(expected: Boolean, variable: String): Case? {
        val normalized = normalizeLineEndings(variable)
        return p.findCase(expected, normalized)
    }

    override fun children(): Iterator<Child> =
        listOf(Child("predicate", p)).iterator()

    fun fmt(): String = p.toString()

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NormalizedPredicate<*>) return false
        return p == other.p
    }

    override fun hashCode(): Int = p.hashCode()
}
