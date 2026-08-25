// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: tests str/regex.rs
package io.github.kotlinmania.predicates.str

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class StrTest {
    @Test
    fun regexMatch() {
        val pred = isMatch("^Hello.*$")
        assertTrue(pred.eval("Hello World"))
        assertFalse(pred.eval("Food World"))
        assertNotNull(pred.findCase(true, "Hello World"))
        assertNull(pred.findCase(false, "Hello World"))
    }

    @Test
    fun regexCount() {
        val pred = isMatch("T[a-z]*").count(3)
        assertTrue(pred.eval("One Two Three Two One"))
        assertFalse(pred.eval("One Two Three"))
    }

    @Test
    fun diffPredicate() {
        val pred = diff("Hello World")
        assertTrue(pred.eval("Hello World"))
        assertFalse(pred.eval("Goodbye World"))
        assertNull(pred.findCase(false, "Hello World"))
        assertNotNull(pred.findCase(false, "Goodbye World"))
    }

    @Test
    fun trimAdapter() {
        val pred = startsWith("Hello").trim()
        assertTrue(pred.eval("   Hello World   "))
    }

    @Test
    fun normalizeAdapter() {
        val pred = contains("line1\nline2").normalize()
        assertTrue(pred.eval("line1\r\nline2"))
    }
}
