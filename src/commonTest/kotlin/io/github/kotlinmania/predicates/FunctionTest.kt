// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: tests function.rs
package io.github.kotlinmania.predicates

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FunctionTest {
    @Test
    fun functionPredicate() {
        val isEven = function<Int> { it % 2 == 0 }
        assertTrue(isEven.eval(4))
        assertFalse(isEven.eval(5))
        assertNotNull(isEven.findCase(true, 4))
        assertNull(isEven.findCase(false, 4))
    }
}
