// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: tests ord.rs
package io.github.kotlinmania.predicates

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class OrdTest {
    @Test
    fun eqAndNe() {
        val eq5 = eq(5)
        assertTrue(eq5.eval(5))
        assertFalse(eq5.eval(6))
        assertNotNull(eq5.findCase(true, 5))
        assertNull(eq5.findCase(false, 5))

        val ne5 = ne(5)
        assertFalse(ne5.eval(5))
        assertTrue(ne5.eval(6))
    }

    @Test
    fun ltLeGeGt() {
        val lt5 = lt(5)
        assertTrue(lt5.eval(4))
        assertFalse(lt5.eval(5))

        val le5 = le(5)
        assertTrue(le5.eval(5))
        assertFalse(le5.eval(6))

        val ge5 = ge(5)
        assertTrue(ge5.eval(5))
        assertFalse(ge5.eval(4))

        val gt5 = gt(5)
        assertTrue(gt5.eval(6))
        assertFalse(gt5.eval(5))
    }
}
