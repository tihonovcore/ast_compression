import java.io.File

fun main() = with(Configuration) {
    if (compressFilesSeparately) {
        File(sourceDirectory).walk().forEach {
            if (it.isDirectory) return@forEach
            if (it.isNotKtFile()) return@forEach

            val tree = parse(it.readText())
            val compressedTree = makeNCompression(tree, nCompressions)

            makeOutput(tree, compressedTree)
        }
    } else {
        val sourceFiles = mutableListOf<String>()
        File(sourceDirectory).walk().forEach {
            if (it.isDirectory) return@forEach
            if (it.isNotKtFile()) return@forEach

            sourceFiles += it.readText()
        }

        val tree = parseAll(sourceFiles)
        val compressedTree = makeNCompression(tree, nCompressions)

        makeOutput(tree, compressedTree)
    }
}
