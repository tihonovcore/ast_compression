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

    @Test
    fun testNCompression() {
        val b4 = new("b")
        val c4 = new("c")
        val a3 = new("a", b4, c4)
        val b2_fst = new("b", a3)
        val b2_snd = new("b")
        val c2 = new("c")
        val a1 = new("a", b2_fst, b2_snd, c2)
        val root = new("root", a1)

        val compressed = makeNCompression(root, 2)
        with(compressed.children) {
            assertEquals(3, size)
            assertEquals(listOf("a_b", "a_b", "a_c"), map { it.name() })
            assertEquals(2, get(0).children.size)
            assertEquals(0, get(1).children.size)
            assertEquals(0, get(2).children.size)
            assertEquals(listOf("a_b", "a_c"), first().children.map { it.name() })
            assertEquals(0, first().children.first().children.size)
            assertEquals(0, first().children.last().children.size)
        }
    }

    @Test
    fun testSameChildren() {
        val b1 = new("b")
        val b2 = new("b")
        val a = new("a", b1, b2)
        val root = new("root", a)

        val compressed = compress(root, "a_b")
        assertEquals("root", compressed.name())
        with(compressed.children) {
            assertEquals(2, size)
            assertEquals("a_b", first().name())
            assertEquals(0, first().children.size)
            assertEquals("a_b", last().name())
            assertEquals(0, last().children.size)
        }
    }

    @Test
    fun testFewFilesCompression() {
        val a1 = new("a")
        val a2 = new("a")
        val b = new("b")
        val file1 = new("file", a1)
        val file2 = new("file", a2, b)
        val root = new("root", file1, file2)

        val compressed = makeNCompression(root, 30)
        assertEquals("root", compressed.name())
        with(compressed.children) {
            assertEquals(3, size)
            assertEquals(listOf("file_a", "file_a", "file_b"), map { it.name() })
            assertTrue(all { it.children.isEmpty() })
        }
    }
}
