// Copyright (c) 2018 The predicates-rs Project Developers.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// port-lint: source ../predicates-core/src/reflection.rs
package io.github.kotlinmania.predicates.core.reflection

/**
 * Introspect into the state of a `Predicate`.
 */

/**
 * Introspect the state of a `Predicate`.
 *
 * Every implementation also produces a human-readable rendering via
 * [toString]; that rendering is the Kotlin analogue of the Rust
 * `fmt::Display` super-bound on the trait.
 */
interface PredicateReflection {
    /** Parameters of the current `Predicate`. */
    fun parameters(): Iterator<Parameter> = emptyList<Parameter>().iterator()

    /** Nested `Predicate`s of the current `Predicate`. */
    fun children(): Iterator<Child> = emptyList<Child>().iterator()
}

/**
 * A view of a `Predicate` parameter, provided by reflection.
 *
 * ```kotlin
 * val param = Parameter("key", 10)
 * println(param)
 * ```
 */
class Parameter(private val key: String, private val displayValue: Any) {
    /** Access the `Parameter` name. */
    fun name(): String = key

    /** Access the `Parameter` value. */
    fun value(): Any = displayValue

    override fun toString(): String = "$key: $displayValue"

    fun debugString(): String = "(\"$key\", $displayValue)"
}

/**
 * A view of a `Predicate` child, provided by reflection.
 */
class Child(private val key: String, private val displayValue: PredicateReflection) {
    /** Access the `Child`'s name. */
    fun name(): String = key

    /** Access the `Child` `Predicate`. */
    fun value(): PredicateReflection = displayValue

    override fun toString(): String = "$key: $displayValue"

    fun debugString(): String = "(\"$key\", $displayValue)"
}

/**
 * A descriptive explanation for why a predicate failed.
 */
class Case(
    private val predicate: PredicateReflection?,
    private val result: Boolean,
) {
    private val products: MutableList<Product> = mutableListOf()
    private val children: MutableList<Case> = mutableListOf()

    /** Add an additional by-product to a `Case`. */
    fun addProduct(product: Product): Case {
        products.add(product)
        return this
    }

    /** Add an additional by-product to a `Case`. */
    fun addChild(child: Case): Case {
        children.add(child)
        return this
    }

    /** The `Predicate` that produced this case. */
    fun predicate(): PredicateReflection? = predicate

    /** The result of this case. */
    fun result(): Boolean = result

    /** Access the by-products from determining this case. */
    fun products(): CaseProducts = CaseProducts(products.iterator())

    /** Access the sub-cases. */
    fun children(): CaseChildren = CaseChildren(children.iterator())

    override fun toString(): String {
        val predicateField = predicate?.let { "Some($it)" } ?: "None"
        val productsField = products.joinToString(prefix = "[", postfix = "]") { it.debugString() }
        val childrenField = children.joinToString(prefix = "[", postfix = "]") { it.toString() }
        return "Case { predicate: \"$predicateField\", result: $result, " +
            "products: $productsField, children: $childrenField }"
    }
}

/** Iterator over a `Case`s by-products. */
class CaseProducts(private val inner: Iterator<Product>) : Iterator<Product> {
    override fun hasNext(): Boolean = inner.hasNext()

    override fun next(): Product = inner.next()
}

/** Iterator over a `Case`s sub-cases. */
class CaseChildren(private val inner: Iterator<Case>) : Iterator<Case> {
    override fun hasNext(): Boolean = inner.hasNext()

    override fun next(): Case = inner.next()
}

/**
 * A by-product of a predicate evaluation.
 *
 * ```kotlin
 * val product = Product("key", "value")
 * println(product)
 * val product2 = Product("key-${5}", 30)
 * println(product2)
 * ```
 */
class Product(private val key: String, private val displayValue: Any) {
    /** Access the `Product` name. */
    fun name(): String = key

    /** Access the `Product` value. */
    fun value(): Any = displayValue

    override fun toString(): String = "$key: $displayValue"

    fun debugString(): String = "(\"$key\", $displayValue)"
}
