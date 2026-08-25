// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source color.rs
package io.github.kotlinmania.predicates

/**
 * Palette used by `Display` implementations to optionally render fragments
 * of a predicate description with ANSI styling.
 *
 * Upstream uses the `anstyle` crate to switch styling on when the formatter
 * is in `f.alternate()` (`{:#}`) mode and the `color` feature is enabled.
 * The Kotlin port has no equivalent formatter-flag plumbing, so styling
 * always renders plain. The palette is preserved as a tracking layer so the
 * call sites in `Display` impls translate one-for-one.
 */
internal class Palette(
    private val alternate: Boolean = false,
) {
    fun description(display: Any): Styled = Styled.new(display)

    fun `var`(display: Any): Styled = Styled.new(display)

    fun varValue(display: Any): Styled = Styled.new(display)

    fun expected(display: Any): Styled = Styled.new(display)

    companion object {
        fun new(alternate: Boolean): Palette = Palette(alternate)

        fun plain(): Palette = Palette(alternate = false)
    }
}

internal class Styled(
    private val display: Any,
) {
    companion object {
        fun new(display: Any, style: Any? = null): Styled = Styled(display)
    }

    fun fmt(): String = display.toString()

    override fun toString(): String = fmt()
}
