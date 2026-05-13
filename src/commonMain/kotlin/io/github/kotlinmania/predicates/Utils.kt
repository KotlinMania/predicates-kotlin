// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source src/utils.rs
package io.github.kotlinmania.predicates

import io.github.kotlinmania.predicates.core.Predicate
import io.github.kotlinmania.predicates.core.reflection.Case

/**
 * Adapter that produces a pretty-printed rendering of an arbitrary value.
 * Mirrors upstream `DebugAdapter`, which delegates to Rust's `{:#?}`
 * pretty-printing. The Kotlin counterpart simply forwards to [toString];
 * the upstream `Display` implementation rendered the same string.
 */
internal class DebugAdapter<T>(internal val debug: T) {
    override fun toString(): String = debug.toString()
}

internal fun <P : Predicate<Item>, Item> defaultFindCase(
    pred: P,
    expected: Boolean,
    variable: Item,
): Case? {
    val actual = pred.eval(variable)
    return if (expected == actual) {
        Case(pred, actual)
    } else {
        null
    }
}
