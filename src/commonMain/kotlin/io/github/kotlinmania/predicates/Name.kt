// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source name.rs
package io.github.kotlinmania.predicates

import io.github.kotlinmania.predicates.core.Predicate
import io.github.kotlinmania.predicates.core.reflection.Case
import io.github.kotlinmania.predicates.core.reflection.Child

/**
 * Augment an existing predicate with a name.
 *
 * This is created by the [name] extension function.
 */
class NamePredicate<Item>(
    internal val inner: Predicate<Item>,
    internal val name: String,
) : Predicate<Item> {
    override fun eval(variable: Item): Boolean = inner.eval(variable)

    override fun findCase(expected: Boolean, variable: Item): Case? =
        inner.findCase(expected, variable)?.let { childCase ->
            Case(this, expected).addChild(childCase)
        }

    override fun children(): Iterator<Child> =
        listOf(Child(name, inner)).iterator()

    fun fmt(): String {
        val palette = Palette.new(false)
        return palette.description(name).toString()
    }

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NamePredicate<*>) return false
        return inner == other.inner && name == other.name
    }

    override fun hashCode(): Int {
        var result = inner.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}

/**
 * Name a predicate expression.
 *
 * # Examples
 *
 * ```kotlin
 * val predicateFn = not(isEmpty()).name("non-empty")
 * println(predicateFn)
 * ```
 */
fun <Item> Predicate<Item>.name(name: String): NamePredicate<Item> =
    NamePredicate(this, name)
