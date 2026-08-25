// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: tests float/close.rs
package io.github.kotlinmania.predicates

import io.github.kotlinmania.predicates.float.isClose
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FloatTest {
    @Test
    fun isCloseEvaluation() {
        val pred = isClose(1.0).epsilon(0.01)
        assertTrue(pred.eval(1.005))
        assertFalse(pred.eval(1.05))
    }
}
