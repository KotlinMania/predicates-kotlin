// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: tests path/existence.rs
package io.github.kotlinmania.predicates

import io.github.kotlinmania.predicates.path.exists
import io.github.kotlinmania.predicates.path.isDir
import io.github.kotlinmania.predicates.path.isFile
import io.github.kotlinmania.predicates.path.missing
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PathTest {
    @Test
    fun existencePredicates() {
        val existsPred = exists()
        assertTrue(existsPred.eval("build.gradle.kts"))
        assertFalse(existsPred.eval(""))

        val missingPred = missing()
        assertFalse(missingPred.eval("build.gradle.kts"))
        assertTrue(missingPred.eval(""))
    }

    @Test
    fun fileTypePredicates() {
        val filePred = isFile()
        assertTrue(filePred.eval("file.txt"))
        assertFalse(filePred.eval("dir/"))

        val dirPred = isDir()
        assertTrue(dirPred.eval("dir/"))
        assertFalse(dirPred.eval("file.txt"))
    }
}
