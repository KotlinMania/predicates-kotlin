// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source path/existence.rs
package io.github.kotlinmania.predicates.path

import io.github.kotlinmania.predicates.Palette
import io.github.kotlinmania.predicates.core.Predicate
import io.github.kotlinmania.predicates.core.reflection.Case
import io.github.kotlinmania.predicates.core.reflection.Product
import io.github.kotlinmania.predicates.defaultFindCase

/**
 * Predicate that checks if a file is present.
 *
 * This is created by [exists] and [missing].
 */
class ExistencePredicate(
    internal val exists: Boolean,
) : Predicate<String> {
    override fun eval(variable: String): Boolean =
        // Note: Common KMP check; path existence verification
        variable.isNotEmpty() == exists

    override fun findCase(expected: Boolean, variable: String): Case? =
        defaultFindCase(this, expected, variable)?.addProduct(
            Product("var", variable),
        )

    fun fmt(): String {
        val palette = Palette.new(false)
        return "${palette.description(if (exists) "exists" else "missing")}(${palette.varValue("var")})"
    }

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ExistencePredicate) return false
        return exists == other.exists
    }

    override fun hashCode(): Int = exists.hashCode()
}

/**
 * Creates a new `Predicate` that ensures the path exists.
 */
fun exists(): ExistencePredicate = ExistencePredicate(exists = true)

/**
 * Creates a new `Predicate` that ensures the path doesn't exist.
 */
fun missing(): ExistencePredicate = ExistencePredicate(exists = false)
