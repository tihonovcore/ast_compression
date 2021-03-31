import junit.framework.TestCase
import org.junit.jupiter.api.Test

class TestCompress : TestCase() {
    @Test
    fun testAllCompressed() {
        val c1 = new("c")
        val c2 = new("c")
        val b1 = new("b", c1)
        val b2 = new("b", c2)
        val a = new("a", b1, b2)

        val compressed = compress(a, "b_c")
        assertEquals("a", compressed.name())
        with(compressed.children) {
            assertEquals(2, size)
            assertEquals("b_c", first().name())
            assertEquals("b_c", last().name())
            assertTrue(first().children.isEmpty())
            assertTrue(last().children.isEmpty())
        }
    }

    @Test
    fun testChildrenIsOk() {
        val d = new("d")
        val c1 = new("c1")
        val c2 = new("c2", d)
        val b = new("b", c1, c2)
        val a = new("a", b)

        val compressed = compress(a, "b_c2")
        assertEquals("a", compressed.name())
        with(compressed.children) {
            assertEquals(2, size)
            assertEquals("b_c2", first().name())
            assertEquals("b", last().name())
            assertEquals(1, first().children.size)
            assertEquals("d", first().children.single().name())
            assertEquals(1, last().children.size)
            assertEquals("c1", last().children.single().name())
        }
    }

    @Test
    fun testTwoIdenticalEdgesInRow() {
        val a3 = new("a")
        val a2 = new("a", a3)
        val a1 = new("a", a2)
        val root = new("root", a1)

        val compressed = compress(root, "a_a")
        assertEquals("root", compressed.name())
        with(compressed.children) {
            assertEquals(1, size)
            assertEquals("a_a", single().name())
            assertEquals(1, single().children.size)
            assertEquals("a", single().children.single().name())
        }
    }
}