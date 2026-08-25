// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source path/fc.rs
package io.github.kotlinmania.predicates.path

import io.github.kotlinmania.predicates.core.Predicate
import io.github.kotlinmania.predicates.core.reflection.Case
import io.github.kotlinmania.predicates.core.reflection.Child
import io.github.kotlinmania.predicates.core.reflection.Product

/**
 * Predicate adapter that converts a `path` predicate to a byte predicate on its content.
 *
 * This is created by `pred.fromFilePath()`.
 */
class FileContentPredicate<P : Predicate<ByteArray>>(
    internal val p: P,
) : Predicate<String> {
    override fun eval(variable: String): Boolean =
        p.eval(variable.encodeToByteArray())

    override fun findCase(expected: Boolean, variable: String): Case? =
        p.findCase(expected, variable.encodeToByteArray())?.addProduct(
            Product("var", variable),
        )

    override fun children(): Iterator<Child> =
        listOf(Child("predicate", p)).iterator()

    fun fmt(): String = p.toString()

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FileContentPredicate<*>) return false
        return p == other.p
    }

    override fun hashCode(): Int = p.hashCode()
}

/**
 * Returns a [FileContentPredicate] that adapts this predicate to a path content predicate.
 */
fun <P : Predicate<ByteArray>> P.fromFilePath(): FileContentPredicate<P> =
    FileContentPredicate(this)
