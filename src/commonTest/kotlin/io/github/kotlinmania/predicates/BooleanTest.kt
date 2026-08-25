// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: tests boolean.rs
package io.github.kotlinmania.predicates

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class BooleanTest {
    @Test
    fun findCaseTrue() {
        assertNotNull(always().and(always()).findCase(true, 5))
    }

    @Test
    fun findCaseTrueLeftFail() {
        assertNull(never().and(always()).findCase(true, 5))
    }

    @Test
    fun findCaseTrueRightFail() {
        assertNull(always().and(never()).findCase(true, 5))
    }

    @Test
    fun findCaseTrueFails() {
        assertNull(never().and(never()).findCase(true, 5))
    }

    @Test
    fun findCaseFalse() {
        assertNotNull(never().and(never()).findCase(false, 5))
    }

    @Test
    fun findCaseFalseFails() {
        assertNull(always().and(always()).findCase(false, 5))
    }

    @Test
    fun findCaseFalseLeftFail() {
        assertNotNull(never().and(always()).findCase(false, 5))
    }

    @Test
    fun findCaseFalseRightFail() {
        assertNotNull(always().and(never()).findCase(false, 5))
    }

    @Test
    fun orFindCaseTrue() {
        assertNotNull(always().or(always()).findCase(true, 5))
        assertNotNull(never().or(always()).findCase(true, 5))
        assertNotNull(always().or(never()).findCase(true, 5))
        assertNull(never().or(never()).findCase(true, 5))
    }

    @Test
    fun orFindCaseFalse() {
        assertNotNull(never().or(never()).findCase(false, 5))
        assertNull(always().or(always()).findCase(false, 5))
        assertNull(never().or(always()).findCase(false, 5))
        assertNull(always().or(never()).findCase(false, 5))
    }

    @Test
    fun notFindCase() {
        assertNotNull(always().not().findCase(false, 5))
        assertNull(always().not().findCase(true, 5))
        assertNotNull(never().not().findCase(true, 5))
        assertNull(never().not().findCase(false, 5))
    }

    @Test
    fun evalTruthTable() {
        assertTrue(always().and(always()).eval(4))
        assertFalse(always().and(never()).eval(4))
        assertFalse(never().and(always()).eval(4))
        assertFalse(never().and(never()).eval(4))

        assertTrue(always().or(always()).eval(4))
        assertTrue(always().or(never()).eval(4))
        assertTrue(never().or(always()).eval(4))
        assertFalse(never().or(never()).eval(4))

        assertFalse(always().not().eval(4))
        assertTrue(never().not().eval(4))
    }
}
