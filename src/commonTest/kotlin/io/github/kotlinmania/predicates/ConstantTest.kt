// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: tests constant.rs
package io.github.kotlinmania.predicates

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ConstantTest {
    @Test
    fun alwaysEval() {
        val pred = always()
        assertTrue(pred.eval(5))
        assertTrue(pred.eval("foo"))
        assertNotNull(pred.findCase(true, 5))
        assertNull(pred.findCase(false, 5))
    }

    @Test
    fun neverEval() {
        val pred = never()
        assertFalse(pred.eval(5))
        assertFalse(pred.eval("foo"))
        assertNotNull(pred.findCase(false, 5))
        assertNull(pred.findCase(true, 5))
    }
}
