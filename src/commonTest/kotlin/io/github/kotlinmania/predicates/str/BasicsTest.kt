// port-lint: ignore
// Smoke tests for the string predicate factories. The upstream test
// coverage lives in `tmp/predicates/src/boolean.rs` and the doctests
// inside `tmp/predicates/src/str/basics.rs`; those tests reference
// `predicate::always` / `predicate::never`, which have not been ported
// yet, so the relevant boolean find-case tests will land alongside
// `constant.rs`.
package io.github.kotlinmania.predicates.str

import io.github.kotlinmania.predicates.and
import io.github.kotlinmania.predicates.not
import io.github.kotlinmania.predicates.or
import io.github.kotlinmania.predicates.prelude.PredicateFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BasicsTest {
    @Test
    fun isEmptyMatches() {
        val pred = isEmpty()
        assertTrue(pred.eval(""))
        assertFalse(pred.eval("Food World"))
    }

    @Test
    fun startsWithMatches() {
        val pred = startsWith("Hello")
        assertTrue(pred.eval("Hello World"))
        assertFalse(pred.eval("Goodbye World"))
    }

    @Test
    fun endsWithMatches() {
        val pred = endsWith("World")
        assertTrue(pred.eval("Hello World"))
        assertFalse(pred.eval("Hello Moon"))
    }

    @Test
    fun containsMatches() {
        val pred = contains("Two")
        assertTrue(pred.eval("One Two Three"))
        assertFalse(pred.eval("Four Five Six"))
    }

    @Test
    fun containsCountRequiresExactMatches() {
        val pred = contains("Two").count(2)
        assertTrue(pred.eval("One Two Three Two One"))
        assertFalse(pred.eval("One Two Three"))
    }

    @Test
    fun containsCountWithEmptyPatternMatchesCharBoundaries() {
        val pred = contains("").count(4)
        // Rust `"abc".matches("").count() == 4` — match at every char boundary.
        assertTrue(pred.eval("abc"))
        assertFalse(pred.eval("ab"))
    }

    @Test
    fun andCombinatorRunsBothSides() {
        val pred = startsWith("Hello").and(endsWith("World"))
        assertTrue(pred.eval("Hello World"))
        assertFalse(pred.eval("Hello Moon"))
        assertFalse(pred.eval("Goodbye World"))
    }

    @Test
    fun orCombinatorRunsEitherSide() {
        val pred = startsWith("Hello").or(endsWith("World"))
        assertTrue(pred.eval("Hello Moon"))
        assertTrue(pred.eval("Goodbye World"))
        assertFalse(pred.eval("Goodbye Moon"))
    }

    @Test
    fun notCombinatorInvertsResult() {
        val pred = contains("bad").not()
        assertTrue(pred.eval("only good things"))
        assertFalse(pred.eval("nothing but bad news"))
    }

    @Test
    fun preludeFactoriesMatchDirectFactories() {
        assertEquals(contains("foo"), PredicateFactory.Str.contains("foo"))
        assertEquals(startsWith("a"), PredicateFactory.Str.startsWith("a"))
        assertEquals(endsWith("z"), PredicateFactory.Str.endsWith("z"))
        assertEquals(isEmpty(), PredicateFactory.Str.isEmpty())
    }

    @Test
    fun displayRendersUpstreamShape() {
        assertEquals("var.contains(needle)", contains("needle").toString())
        assertEquals("var.starts_with(\"H\")", startsWith("H").toString())
        assertEquals("var.ends_with(\"!\")", endsWith("!").toString())
        assertEquals("var.is_empty()", isEmpty().toString())
        assertEquals("(var.starts_with(\"H\") && var.ends_with(\"!\"))",
            startsWith("H").and(endsWith("!")).toString())
        assertEquals("(! var.contains(x))", contains("x").not().toString())
    }
}
