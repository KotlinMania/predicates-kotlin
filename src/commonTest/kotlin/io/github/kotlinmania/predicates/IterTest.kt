// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: tests iter.rs
package io.github.kotlinmania.predicates

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class IterTest {
    @Test
    fun inIterAndInHash() {
        val list = listOf(1, 2, 3)
        val inIterPred = inIter(list)
        assertTrue(inIterPred.eval(2))
        assertFalse(inIterPred.eval(4))
        assertNotNull(inIterPred.findCase(true, 2))
        assertNull(inIterPred.findCase(false, 2))

        val inHashPred = inHash(list)
        assertTrue(inHashPred.eval(3))
        assertFalse(inHashPred.eval(0))
    }
}
