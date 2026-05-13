// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source src/prelude.rs
package io.github.kotlinmania.predicates.prelude

/**
 * Module that contains the essentials for working with predicates.
 *
 * Upstream re-exports:
 *
 *   pub use crate::boolean::PredicateBooleanExt;
 *   pub use crate::boxed::PredicateBoxExt;
 *   pub use crate::name::PredicateNameExt;
 *   pub use crate::path::PredicateFileContentExt;
 *   pub use crate::str::PredicateStrExt;
 *   pub use crate::Predicate;
 *
 * Kotlin lacks wildcard re-exports, so callers reference the defining
 * packages directly:
 *
 *   io.github.kotlinmania.predicates.core.Predicate
 *   io.github.kotlinmania.predicates.PredicateBooleanExt
 *   io.github.kotlinmania.predicates.and / .or / .not  (boolean combinators)
 *   io.github.kotlinmania.predicates.str.contains, .startsWith, …
 *
 * Upstream also nests a `predicate` factory module inside the prelude that
 * lets users write `predicate::str::contains("...")`. Kotlin object naming
 * is PascalCase, so the same shape is exposed via [PredicateFactory].
 *
 * Factories whose backing files have not yet been ported (always, never, eq,
 * lt, ge, gt, le, ne, function, inIter, inHash, diff, isMatch, exists,
 * missing, isDir, isFile, isSymlink, isClose, eqFile) intentionally do not
 * appear here; the entries return to this file unchanged once the backing
 * predicate files land.
 */

import io.github.kotlinmania.predicates.str.ContainsPredicate
import io.github.kotlinmania.predicates.str.EndsWithPredicate
import io.github.kotlinmania.predicates.str.IsEmptyPredicate
import io.github.kotlinmania.predicates.str.StartsWithPredicate
import io.github.kotlinmania.predicates.str.contains as strContains
import io.github.kotlinmania.predicates.str.endsWith as strEndsWith
import io.github.kotlinmania.predicates.str.isEmpty as strIsEmpty
import io.github.kotlinmania.predicates.str.startsWith as strStartsWith

/**
 * Predicate factories.
 *
 * Kotlin equivalent of upstream's `predicates::prelude::predicate` module.
 * Renamed from lowercase `predicate` because Kotlin object names follow
 * PascalCase.
 */
object PredicateFactory {

    /** `str` Predicate factories — predicates specific to string handling. */
    object Str {
        /** See [io.github.kotlinmania.predicates.str.isEmpty]. */
        fun isEmpty(): IsEmptyPredicate = strIsEmpty()

        /** See [io.github.kotlinmania.predicates.str.contains]. */
        fun contains(pattern: String): ContainsPredicate = strContains(pattern)

        /** See [io.github.kotlinmania.predicates.str.endsWith]. */
        fun endsWith(pattern: String): EndsWithPredicate = strEndsWith(pattern)

        /** See [io.github.kotlinmania.predicates.str.startsWith]. */
        fun startsWith(pattern: String): StartsWithPredicate = strStartsWith(pattern)
    }
}
