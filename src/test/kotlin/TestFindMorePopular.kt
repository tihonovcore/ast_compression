import junit.framework.TestCase
import org.junit.jupiter.api.Test

class TestFindMorePopular : TestCase() {
    @Test
    fun testFindMostPopular() {
        val fun1 = new("func")
        val fun2 = new("func")
        val fun3 = new("func")
        val file = new("file", fun1, fun2, fun3)
        val root = new("root", file)

        assertEquals("file_func", findMostPopular(root))
    }

    @Test
    fun testFindNotRoot() {
        val loop = new("while")
        val fun1 = new("func")
        val fun2 = new("func")
        val fun3 = new("func", loop)
        val root = new("root", fun1, fun2, fun3)

        assertEquals("func_while", findMostPopular(root))
    }
}
