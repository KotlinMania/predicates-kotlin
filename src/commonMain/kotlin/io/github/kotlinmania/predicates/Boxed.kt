// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source boxed.rs
package io.github.kotlinmania.predicates

import io.github.kotlinmania.predicates.core.Predicate
import io.github.kotlinmania.predicates.core.reflection.Case
import io.github.kotlinmania.predicates.core.reflection.Child
import io.github.kotlinmania.predicates.core.reflection.Parameter

/**
 * `Predicate` that wraps another `Predicate` as a trait object, allowing
 * sized storage of predicate types.
 */
class BoxPredicate<Item>(
    internal val inner: Predicate<Item>,
) : Predicate<Item> {
    companion object {
        /**
         * Creates a new `BoxPredicate`, a wrapper around a dynamically-dispatched
         * `Predicate` type with useful trait impls.
         */
        fun <Item> new(inner: Predicate<Item>): BoxPredicate<Item> = BoxPredicate(inner)
    }

    override fun eval(variable: Item): Boolean = inner.eval(variable)

    override fun findCase(expected: Boolean, variable: Item): Case? =
        defaultFindCase(this, expected, variable)

    override fun parameters(): Iterator<Parameter> = inner.parameters()

    override fun children(): Iterator<Child> = inner.children()

    fun fmt(): String = inner.toString()

    override fun toString(): String = fmt()
}

/**
 * Returns a `BoxPredicate` wrapper around this `Predicate` type.
 */
fun <Item> Predicate<Item>.boxed(): BoxPredicate<Item> = BoxPredicate.new(this)
