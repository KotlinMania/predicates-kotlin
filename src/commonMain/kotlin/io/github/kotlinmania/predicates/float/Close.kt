// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source float/close.rs
package io.github.kotlinmania.predicates.float

import io.github.kotlinmania.predicates.Palette
import io.github.kotlinmania.predicates.core.Predicate
import io.github.kotlinmania.predicates.core.reflection.Case
import io.github.kotlinmania.predicates.core.reflection.Parameter
import io.github.kotlinmania.predicates.core.reflection.Product
import kotlin.math.abs

private const val DOUBLE_EPSILON: Double = 2.220446049250313e-16

private fun doubleUlps(a: Double, b: Double): Long {
    if (a.isNaN() || b.isNaN()) return Long.MAX_VALUE
    if (a == b) return 0L
    val aBits = a.toBits()
    val bBits = b.toBits()
    val aSign = aBits < 0
    val bSign = bBits < 0
    val aMag = if (aSign) (Long.MIN_VALUE - aBits) else aBits
    val bMag = if (bSign) (Long.MIN_VALUE - bBits) else bBits
    return abs(aMag - bMag)
}

/**
 * Predicate that ensures two numbers are "close" enough, understanding that rounding errors
 * occur.
 *
 * This is created by [isClose].
 */
class IsClosePredicate(
    internal val target: Double,
    internal var epsilon: Double = 2.0 * DOUBLE_EPSILON,
    internal var ulps: Long = 2L,
) : Predicate<Double> {
    /**
     * Set the amount of error allowed.
     */
    fun distance(distance: Long): IsClosePredicate {
        this.epsilon = distance.toDouble() * DOUBLE_EPSILON
        this.ulps = distance
        return this
    }

    /**
     * Set the absolute deviation allowed.
     */
    fun epsilon(epsilon: Double): IsClosePredicate {
        this.epsilon = epsilon
        return this
    }

    /**
     * Set the relative deviation allowed.
     */
    fun ulps(ulps: Long): IsClosePredicate {
        this.ulps = ulps
        return this
    }

    override fun eval(variable: Double): Boolean {
        val diff = abs(variable - target)
        if (diff <= epsilon) return true
        val u = doubleUlps(variable, target)
        return u <= ulps
    }

    override fun findCase(expected: Boolean, variable: Double): Case? {
        val actual = eval(variable)
        return if (expected == actual) {
            Case(this, actual)
                .addProduct(Product("actual epsilon", abs(variable - target)))
                .addProduct(Product("actual ulps", doubleUlps(variable, target)))
        } else {
            null
        }
    }

    override fun parameters(): Iterator<Parameter> =
        listOf(
            Parameter("epsilon", epsilon),
            Parameter("ulps", ulps),
        ).iterator()

    fun fmt(): String {
        val palette = Palette.new(false)
        return "${palette.varValue("var")} ${palette.description("!=")} ${palette.expected(target)}"
    }

    override fun toString(): String = fmt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IsClosePredicate) return false
        return target == other.target && epsilon == other.epsilon && ulps == other.ulps
    }

    override fun hashCode(): Int {
        var result = target.hashCode()
        result = 31 * result + epsilon.hashCode()
        result = 31 * result + ulps.hashCode()
        return result
    }
}

/**
 * Create a new `Predicate` that ensures two numbers are "close" enough, understanding that
 * rounding errors occur.
 */
fun isClose(target: Double): IsClosePredicate =
    IsClosePredicate(target)
