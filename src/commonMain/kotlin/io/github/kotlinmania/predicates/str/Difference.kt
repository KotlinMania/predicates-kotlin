// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source str/difference.rs
package io.github.kotlinmania.predicates.str

import io.github.kotlinmania.predicates.Palette
import io.github.kotlinmania.predicates.core.Predicate
import io.github.kotlinmania.predicates.core.reflection.Case
import io.github.kotlinmania.predicates.core.reflection.Parameter
import io.github.kotlinmania.predicates.core.reflection.Product

/**
 * Predicate that diffs two strings.
 *
 * This is created by [diff].
 */
class DifferencePredicate(
    internal val orig: String,
) : Predicate<String> {
    override fun eval(variable: String): Boolean = variable == orig

    override fun findCase(expected: Boolean, variable: String): Case? {
        val result = variable != orig
        return if (result == expected) {
            null
        } else {
            val palette = Palette.new(true)
            val origLines = orig.lines()
            val varLines = variable.lines()
            val diffLines = mutableListOf<String>()
            diffLines.add("\n")
            diffLines.add("--- ${palette.expected("orig")}\n")
            diffLines.add("+++ ${palette.varValue("var")}\n")
            var i = 0
            var j = 0
            while (i < origLines.size || j < varLines.size) {
                if (i < origLines.size && j < varLines.size && origLines[i] == varLines[j]) {
                    diffLines.add(" ${origLines[i]}\n")
                    i++
                    j++
                } else if (i < origLines.size && (j >= varLines.size || !varLines.contains(origLines[i]))) {
                    diffLines.add("-${origLines[i]}\n")
                    i++
                } else if (j < varLines.size) {
                    diffLines.add("+${varLines[j]}\n")
                    j++
                }
            }
            Case(this, result).addProduct(Product("diff", diffLines.joinToString("")))
        }
    }

    override fun parameters(): Iterator<Parameter> =
        listOf(Parameter("original", orig)).iterator()

    fun fmt(): String {
        val palette = Palette.new(false)
        return "${palette.description("diff")} ${palette.expected("original")} ${palette.varValue("var")}"
    }

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DifferencePredicate) return false
        return orig == other.orig
    }

    override fun hashCode(): Int = orig.hashCode()
}

/**
 * Creates a new `Predicate` that diffs two strings.
 */
fun diff(orig: String): DifferencePredicate = DifferencePredicate(orig)
