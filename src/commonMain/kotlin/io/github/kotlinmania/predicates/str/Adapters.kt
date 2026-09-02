// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source str/adapters.rs
package io.github.kotlinmania.predicates.str

import io.github.kotlinmania.predicates.core.Predicate
import io.github.kotlinmania.predicates.core.reflection.Case
import io.github.kotlinmania.predicates.core.reflection.Child
import io.github.kotlinmania.predicates.core.reflection.Product

/**
 * Predicate adapter that trims the variable being tested.
 *
 * This is created by `pred.trim()`.
 */
class TrimPredicate internal constructor(
    internal val p: Predicate<String>,
) : Predicate<String> {
    override fun eval(variable: String): Boolean = p.eval(variable.trim())

    override fun findCase(expected: Boolean, variable: String): Case? =
        p.findCase(expected, variable.trim())

    override fun children(): Iterator<Child> =
        listOf(Child("predicate", p)).iterator()

    fun fmt(): String = p.toString()

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TrimPredicate) return false
        return p == other.p
    }

    override fun hashCode(): Int = p.hashCode()
}

/**
 * Predicate adapter that converts a `str` predicate to byte predicate.
 *
 * This is created by `pred.fromUtf8()`.
 */
class Utf8Predicate internal constructor(
    internal val p: Predicate<String>,
) : Predicate<ByteArray> {
    override fun eval(variable: ByteArray): Boolean =
        try {
            p.eval(variable.decodeToString())
        } catch (_: Exception) {
            false
        }

    override fun findCase(expected: Boolean, variable: ByteArray): Case? =
        try {
            val varStr = variable.decodeToString()
            p.findCase(expected, varStr)?.addProduct(
                Product("var as str", varStr),
            )
        } catch (e: Exception) {
            if (expected) {
                null
            } else {
                Case(this, false).addProduct(
                    Product("error", e.message ?: "Invalid UTF-8 string"),
                )
            }
        }

    override fun children(): Iterator<Child> =
        listOf(Child("predicate", p)).iterator()

    fun fmt(): String = p.toString()

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Utf8Predicate) return false
        return p == other.p
    }

    override fun hashCode(): Int = p.hashCode()
}

/**
 * Returns a [TrimPredicate] that ensures the data passed to this predicate is trimmed.
 */
fun Predicate<String>.trim(): TrimPredicate = TrimPredicate(this)

/**
 * Returns a [Utf8Predicate] that adapts this predicate to a [ByteArray] predicate.
 */
fun Predicate<String>.fromUtf8(): Utf8Predicate = Utf8Predicate(this)

/**
 * Returns a [NormalizedPredicate] that ensures the newlines within the data
 * passed to this predicate are normalised.
 */
fun Predicate<String>.normalize(): NormalizedPredicate = NormalizedPredicate(this)
