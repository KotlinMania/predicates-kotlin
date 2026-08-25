// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source function.rs
package io.github.kotlinmania.predicates

import io.github.kotlinmania.predicates.core.Predicate
import io.github.kotlinmania.predicates.core.reflection.Case

/**
 * Predicate that wraps a function over a reference that returns a `bool`.
 * This type is returned by the [function] function.
 */
class FnPredicate<T>(
    private val function: (T) -> Boolean,
    private var name: String = "fn",
) : Predicate<T> {
    /**
     * Provide a descriptive name for this function.
     */
    fun fnName(name: String): FnPredicate<T> {
        this.name = name
        return this
    }

    override fun eval(variable: T): Boolean = function(variable)

    override fun findCase(expected: Boolean, variable: T): Case? =
        defaultFindCase(this, expected, variable)

    fun fmt(): String {
        val palette = Palette.new(false)
        return "${palette.description(name)}(${palette.varValue("var")})"
    }

    override fun toString(): String = fmt()
}

/**
 * Creates a new predicate that wraps over the given function. The returned
 * type implements `Predicate` and therefore has all combinators available to
 * it.
 */
fun <T> function(func: (T) -> Boolean): FnPredicate<T> = FnPredicate(func)
