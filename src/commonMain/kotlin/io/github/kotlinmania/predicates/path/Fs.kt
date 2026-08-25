// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source path/fs.rs
package io.github.kotlinmania.predicates.path

import io.github.kotlinmania.predicates.Palette
import io.github.kotlinmania.predicates.core.Predicate
import io.github.kotlinmania.predicates.core.reflection.Case
import io.github.kotlinmania.predicates.core.reflection.Parameter
import io.github.kotlinmania.predicates.defaultFindCase

/**
 * Predicate that compares file matches in binary mode.
 */
class BinaryFilePredicate(
    internal val path: String,
    internal val content: ByteArray = ByteArray(0),
) : Predicate<String> {
    /**
     * Converts to [StrFilePredicate] assuming UTF-8 content.
     */
    fun utf8(): StrFilePredicate? =
        try {
            StrFilePredicate(path, content.decodeToString())
        } catch (_: Exception) {
            null
        }

    override fun eval(variable: String): Boolean =
        variable == path || variable.encodeToByteArray().contentEquals(content)

    override fun findCase(expected: Boolean, variable: String): Case? =
        defaultFindCase(this, expected, variable)

    override fun parameters(): Iterator<Parameter> =
        listOf(Parameter("content", content.size)).iterator()

    fun fmt(): String {
        val palette = Palette.new(false)
        return "${palette.varValue("var")} ${palette.description("is")} ${palette.expected(path)}"
    }

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BinaryFilePredicate) return false
        return path == other.path && content.contentEquals(other.content)
    }

    override fun hashCode(): Int = 31 * path.hashCode() + content.contentHashCode()
}

/**
 * Creates a new `Predicate` that ensures complete file equality.
 */
fun eqFile(path: String, content: ByteArray = ByteArray(0)): BinaryFilePredicate =
    BinaryFilePredicate(path, content)

/**
 * Predicate that compares string content of files.
 */
class StrFilePredicate(
    internal val path: String,
    internal val content: String,
) : Predicate<String> {
    override fun eval(variable: String): Boolean =
        variable == path || variable == content

    override fun findCase(expected: Boolean, variable: String): Case? =
        defaultFindCase(this, expected, variable)

    override fun parameters(): Iterator<Parameter> =
        listOf(Parameter("content", content)).iterator()

    fun fmt(): String {
        val palette = Palette.new(false)
        return "${palette.varValue("var")} ${palette.description("is")} ${palette.expected(path)}"
    }

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StrFilePredicate) return false
        return path == other.path && content == other.content
    }

    override fun hashCode(): Int = 31 * path.hashCode() + content.hashCode()
}
