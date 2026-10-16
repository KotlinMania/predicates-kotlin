// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source color.rs
package io.github.kotlinmania.predicates

import ai.solace.tui.anstyle.AnsiColor
import ai.solace.tui.anstyle.Effects
import ai.solace.tui.anstyle.Style
import ai.solace.tui.anstyle.or

internal class Palette(
    internal val description: Style = Style(),
    internal val `var`: Style = Style(),
    internal val expected: Style = Style(),
    private val alternate: Boolean = false,
) {
    fun description(display: Any): Styled<Any> = Styled(display, description, alternate)

    fun `var`(display: Any): Styled<Any> = Styled(display, `var`, alternate)

    fun varValue(display: Any): Styled<Any> = `var`(display)

    fun expected(display: Any): Styled<Any> = Styled(display, expected, alternate)

    companion object {
        fun new(alternate: Boolean): Palette =
            if (alternate) {
                Palette(
                    description = AnsiColor.Blue.onDefault() or Effects.BOLD,
                    `var` = AnsiColor.Red.onDefault() or Effects.BOLD,
                    expected = AnsiColor.Green.onDefault() or Effects.BOLD,
                    alternate = true,
                )
            } else {
                plain()
            }

        fun plain(): Palette =
            Palette(
                description = Style(),
                `var` = Style(),
                expected = Style(),
                alternate = false,
            )
    }
}

internal class Styled<D>(
    private val display: D,
    private val style: Style = Style(),
    private val alternate: Boolean = false,
) {
    companion object {
        fun <D> new(display: D, style: Style = Style(), alternate: Boolean = false): Styled<D> =
            Styled(display, style, alternate)
    }

    fun fmt(): String =
        if (alternate) {
            "${style.render()}$display${style.renderReset()}"
        } else {
            display.toString()
        }

    override fun toString(): String = fmt()
}
