fun main() {
    var newTree = parse("val x = foo() + 10;")
    repeat(50) {
        try {
            val mp = findMostPopular(newTree).also { println(it) }
            newTree = compress(newTree, mp)
        } catch (_: MaxCompression) {
            println("max compression")
        }
    }
    println("\n\n\n\n")
    println(newTree)
}
