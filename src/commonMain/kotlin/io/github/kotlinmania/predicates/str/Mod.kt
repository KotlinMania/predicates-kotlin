// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source src/str/mod.rs
package io.github.kotlinmania.predicates.str

/**
 * String Predicates.
 *
 * This module contains predicates specific to string handling.
 *
 * Upstream layout:
 *
 *   mod basics;
 *   pub use self::basics::*;
 *   mod adapters;
 *   pub use self::adapters::*;
 *
 * Both are flat re-exports into the parent `str` module. Kotlin callers
 * pick up the symbols directly from `io.github.kotlinmania.predicates.str.*`
 * without an intermediate alias — `Basics.kt` and (eventually) `Adapters.kt`
 * already declare them in this package.
 *
 * Feature-gated submodules `difference`, `normalize`, and `regex` are not
 * yet ported. Their entries will land in dedicated files when the upstream
 * features they expose are required by a downstream caller.
 */

// pub use self::basics::*;
// Callers migrated: ContainsPredicate, StartsWithPredicate, EndsWithPredicate,
//   IsEmptyPredicate, MatchesPredicate are referenced via their defining
//   package `io.github.kotlinmania.predicates.str` directly.
