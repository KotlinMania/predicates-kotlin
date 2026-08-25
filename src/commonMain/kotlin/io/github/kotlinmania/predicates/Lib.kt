// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source lib.rs
package io.github.kotlinmania.predicates

/**
 * Composable first-order predicate functions.
 *
 * This library implements an interface to "predicates" - boolean-valued
 * functions of one argument. This allows combinatorial logic to be created
 * and assembled at runtime and then used one or more times for evaluating
 * values. This sort of object is really useful when creating filters and
 * checks that can be changed at runtime with user interaction - it allows a
 * clean separation of concerns where the configuration code can be used to
 * build up a predicate, and then that predicate can be given to the code that
 * does the actual filtering without the filtering code knowing anything about
 * user configuration.
 *
 * ## Installation
 *
 * Gradle:
 *
 *     implementation("io.github.kotlinmania:predicates-kotlin:<version>")
 *
 * A prelude is available to bring in all extension symbols as well as
 * providing `PredicateFactory` which focuses on the 90% case of the API.
 *
 *     import io.github.kotlinmania.predicates.prelude.PredicateFactory
 *     import io.github.kotlinmania.predicates.and
 *     import io.github.kotlinmania.predicates.or
 *     import io.github.kotlinmania.predicates.not
 *
 * ## Examples
 *
 * The simplest predicates would be `always()` and `never()` (not yet ported
 * in this snapshot), which always return `true` and `false` respectively.
 *
 * Any extension function over a `Predicate` participates in the boolean
 * algebra exposed by [PredicateBooleanExt]:
 *
 *     val between5And10 = startsWith("5").or(startsWith("10"))
 *     between5And10.eval("5 dollars")
 *
 * The `Predicate` type is an interface, and the boolean combinators are
 * extension functions, so a custom `Predicate` implementation still
 * participates in all the usual combinators.
 *
 * ## Choosing a Predicate
 *
 * `String` predicates available in this snapshot:
 *  - [io.github.kotlinmania.predicates.str.isEmpty] — specified string must
 *    be empty.
 *  - [io.github.kotlinmania.predicates.str.startsWith] — specified string
 *    must start with the given needle.
 *  - [io.github.kotlinmania.predicates.str.endsWith] — specified string must
 *    end with the given needle.
 *  - [io.github.kotlinmania.predicates.str.contains] — specified string must
 *    contain the given needle.
 *    - `contains(...).count` — required number of times the needle must show
 *      up.
 *
 * Combinators:
 *  - `predA.and(predB)` — both predicates must succeed.
 *  - `predA.or(predB)` — one or both predicates must succeed.
 *  - `predA.not()` — the predicate must fail.
 *
 * General, ord-based, iter-based, float, path, and reflection-driven
 * factories from the upstream crate are not yet exposed in Kotlin; their
 * entries land here once the backing files are ported.
 */

// Upstream layout translates into Kotlin packages:
//
//   io.github.kotlinmania.predicates.core            (Predicate interface)
//   io.github.kotlinmania.predicates.core.reflection (reflection types)
//   io.github.kotlinmania.predicates.str             (string predicates)
//   io.github.kotlinmania.predicates.path            (path predicates)
//   io.github.kotlinmania.predicates.float           (float predicates)
//   io.github.kotlinmania.predicates.prelude         (factory namespace)
//   io.github.kotlinmania.predicates                 (boolean combinators, constant, function, iter, ord)

