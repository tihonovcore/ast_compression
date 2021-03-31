import junit.framework.TestCase
import org.jetbrains.kotlin.spec.grammar.tools.KotlinParseTree
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TestUtils : TestCase() {
    private fun KotlinParseTree.last(drop: Int = 0) = children.dropLast(drop).last()

    @Test
    fun testName() {
        val root = parse("val x = 4").name()
        val clas = parse("class T").last().last(1).last(1).last().name()
        val func = parse("fun foo() {}").last().last(1).last(1).last().name()

        assertEquals("root", root)
        assertEquals("classDeclaration", clas)
        assertEquals("functionDeclaration", func)
    }

    @Test
    fun testWith() {
        val root = parse("val x = 4")
        val func = parse("fun foo() {}").last().last(1).last(1).last()
        val decl = parse("val x = 5").last().last(1).last(1).last()

        assertEquals("functionDeclaration_propertyDeclaration", func with decl)
        assertThrows<AssertionError> { root with func }
    }
}
