import java.io.File

fun main() = with(Configuration) {
    File(sourceDirectory).walk().forEach {
        if (it.isDirectory) return@forEach

        val tree = parse(it.readText())
        val compressedTree = makeNCompression(tree, nCompressions)

        println(it.path)
        if (printTreeBefore) {
            println("###################")
            println(tree)
        }
        if (printTreeAfter) {
            println("###################")
            println(compressedTree)
        }
        println("###################")
        println("\n\n\n")

        if (saveToFile) {
            File(outputFile).apply {
                if (!exists()) createNewFile()

                appendText(it.path)
                appendText("\n###################\n")
                appendText(compressedTree.toString())
                appendText("\n\n\n")
            }
        }
    }
}
