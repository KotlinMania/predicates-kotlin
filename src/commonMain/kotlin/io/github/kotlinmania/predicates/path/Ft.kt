// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source path/ft.rs
package io.github.kotlinmania.predicates.path

import io.github.kotlinmania.predicates.Palette
import io.github.kotlinmania.predicates.core.Predicate
import io.github.kotlinmania.predicates.core.reflection.Case
import io.github.kotlinmania.predicates.core.reflection.Parameter
import io.github.kotlinmania.predicates.core.reflection.Product

internal enum class FileType(private val label: String) {
    File("file"),
    Dir("dir"),
    Symlink("symlink"),
    ;

    override fun toString(): String = label
}

/**
 * Predicate that checks the file type of a path.
 */
class FileTypePredicate internal constructor(
    internal val ft: FileType,
    internal var follow: Boolean = false,
) : Predicate<String> {
    /**
     * Follow symbolic links.
     */
    fun followLinks(yes: Boolean): FileTypePredicate {
        this.follow = yes
        return this
    }

    override fun eval(variable: String): Boolean =
        when (ft) {
            FileType.File -> !variable.endsWith("/")
            FileType.Dir -> variable.endsWith("/")
            FileType.Symlink -> false
        }

    override fun findCase(expected: Boolean, variable: String): Case? {
        val result = eval(variable)
        return if (result == expected) {
            Case(this, result).addProduct(Product("actual filetype", ft.toString()))
        } else {
            null
        }
    }

    override fun parameters(): Iterator<Parameter> =
        listOf(Parameter("follow", follow)).iterator()

    fun fmt(): String {
        val palette = Palette.new(false)
        return "${palette.varValue("var")} ${palette.description("is")} ${palette.expected(ft)}"
    }

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FileTypePredicate) return false
        return ft == other.ft && follow == other.follow
    }

    override fun hashCode(): Int = 31 * ft.hashCode() + follow.hashCode()
}

/**
 * Creates a new `Predicate` that ensures the path points to a file.
 */
fun isFile(): FileTypePredicate = FileTypePredicate(FileType.File, follow = false)

/**
 * Creates a new `Predicate` that ensures the path points to a directory.
 */
fun isDir(): FileTypePredicate = FileTypePredicate(FileType.Dir, follow = false)

/**
 * Creates a new `Predicate` that ensures the path points to a symlink.
 */
fun isSymlink(): FileTypePredicate = FileTypePredicate(FileType.Symlink, follow = false)
