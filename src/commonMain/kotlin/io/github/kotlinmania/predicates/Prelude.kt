// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source prelude.rs
package io.github.kotlinmania.predicates.prelude

import io.github.kotlinmania.predicates.BooleanPredicate
import io.github.kotlinmania.predicates.EqPredicate
import io.github.kotlinmania.predicates.FnPredicate
import io.github.kotlinmania.predicates.HashableInPredicate
import io.github.kotlinmania.predicates.InPredicate
import io.github.kotlinmania.predicates.OrdPredicate
import io.github.kotlinmania.predicates.always as constAlways
import io.github.kotlinmania.predicates.eq as ordEq
import io.github.kotlinmania.predicates.float.IsClosePredicate
import io.github.kotlinmania.predicates.float.isClose as floatIsClose
import io.github.kotlinmania.predicates.function as fnFunction
import io.github.kotlinmania.predicates.ge as ordGe
import io.github.kotlinmania.predicates.gt as ordGt
import io.github.kotlinmania.predicates.inHash as iterInHash
import io.github.kotlinmania.predicates.inIter as iterInIter
import io.github.kotlinmania.predicates.le as ordLe
import io.github.kotlinmania.predicates.lt as ordLt
import io.github.kotlinmania.predicates.ne as ordNe
import io.github.kotlinmania.predicates.never as constNever
import io.github.kotlinmania.predicates.path.BinaryFilePredicate
import io.github.kotlinmania.predicates.path.ExistencePredicate
import io.github.kotlinmania.predicates.path.FileTypePredicate
import io.github.kotlinmania.predicates.path.eqFile as pathEqFile
import io.github.kotlinmania.predicates.path.exists as pathExists
import io.github.kotlinmania.predicates.path.isDir as pathIsDir
import io.github.kotlinmania.predicates.path.isFile as pathIsFile
import io.github.kotlinmania.predicates.path.isSymlink as pathIsSymlink
import io.github.kotlinmania.predicates.path.missing as pathMissing
import io.github.kotlinmania.predicates.str.ContainsPredicate
import io.github.kotlinmania.predicates.str.DifferencePredicate
import io.github.kotlinmania.predicates.str.EndsWithPredicate
import io.github.kotlinmania.predicates.str.IsEmptyPredicate
import io.github.kotlinmania.predicates.str.RegexPredicate
import io.github.kotlinmania.predicates.str.StartsWithPredicate
import io.github.kotlinmania.predicates.str.contains as strContains
import io.github.kotlinmania.predicates.str.diff as strDiff
import io.github.kotlinmania.predicates.str.endsWith as strEndsWith
import io.github.kotlinmania.predicates.str.isEmpty as strIsEmpty
import io.github.kotlinmania.predicates.str.isMatch as strIsMatch
import io.github.kotlinmania.predicates.str.startsWith as strStartsWith

/**
 * Predicate factories.
 *
 * Kotlin equivalent of upstream's `predicates::prelude::predicate` module.
 * Renamed from lowercase `predicate` because Kotlin object names follow
 * PascalCase.
 */
object PredicateFactory {
    fun always(): BooleanPredicate = constAlways()

    fun never(): BooleanPredicate = constNever()

    fun <T> function(fn: (T) -> Boolean): FnPredicate<T> = fnFunction(fn)

    fun <T> inHash(iter: Iterable<T>): HashableInPredicate<T> = iterInHash(iter)

    fun <T> inIter(iter: Iterable<T>): InPredicate<T> = iterInIter(iter)

    fun <T> eq(constant: T): EqPredicate<T> = ordEq(constant)

    fun <T> ne(constant: T): EqPredicate<T> = ordNe(constant)

    fun <T : Comparable<T>> lt(constant: T): OrdPredicate<T> = ordLt(constant)

    fun <T : Comparable<T>> le(constant: T): OrdPredicate<T> = ordLe(constant)

    fun <T : Comparable<T>> ge(constant: T): OrdPredicate<T> = ordGe(constant)

    fun <T : Comparable<T>> gt(constant: T): OrdPredicate<T> = ordGt(constant)

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

        /** See [io.github.kotlinmania.predicates.str.diff]. */
        fun diff(orig: String): DifferencePredicate = strDiff(orig)

        /** See [io.github.kotlinmania.predicates.str.isMatch]. */
        fun isMatch(pattern: String): RegexPredicate = strIsMatch(pattern)
    }

    /** `path` Predicate factories — predicates specific to path handling. */
    object Path {
        fun eqFile(path: String, content: ByteArray = ByteArray(0)): BinaryFilePredicate =
            pathEqFile(path, content)

        fun exists(): ExistencePredicate = pathExists()

        fun missing(): ExistencePredicate = pathMissing()

        fun isFile(): FileTypePredicate = pathIsFile()

        fun isDir(): FileTypePredicate = pathIsDir()

        fun isSymlink(): FileTypePredicate = pathIsSymlink()
    }

    /** `float` Predicate factories — predicates specific to float handling. */
    object Float {
        fun isClose(target: Double): IsClosePredicate = floatIsClose(target)
    }
}
