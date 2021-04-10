import org.jetbrains.kotlin.spec.grammar.tools.KotlinParseTree
import org.jetbrains.kotlin.spec.grammar.tools.KotlinParseTreeNodeType
import org.jetbrains.kotlin.spec.grammar.tools.parseKotlinCode
import org.jetbrains.kotlin.spec.grammar.tools.tokenizeKotlinCode
import java.io.File

fun parseAll(sourceFiles: List<String>): KotlinParseTree {
    //NOTE: "root" is wrapper for "file" in case it turns into a forest.
    //"root" should never compressed

    val trees = sourceFiles
        .map { sourceCode -> tokenizeKotlinCode(sourceCode) }
        .map { tokens -> parseKotlinCode(tokens) }
        .toTypedArray()

    return new("root", *trees)
}

fun parse(sourceCode: String): KotlinParseTree = parseAll(listOf(sourceCode))

/**
 * Return name of node
 *
 * NOTE: a bit dirty, but it depends on parser
 */
fun KotlinParseTree.name(): String {
    val field = this::class.java.getDeclaredField("name")
    field.isAccessible = true
    return field.get(this) as String
}

/**
 * Creates new KotlinParseTree
 */
fun new(name: String, vararg children: KotlinParseTree): KotlinParseTree {
    return KotlinParseTree(KotlinParseTreeNodeType.RULE, name,null, children.toMutableList())
}

/**
 * Return name for compressed `this` and `other`
 */
infix fun KotlinParseTree.with(other: KotlinParseTree): String {
    assert(name() != "root")

    return name() + "_" + other.name()
}

fun File.isNotKtFile(): Boolean {
    return !name.endsWith(".kt")
}

fun Configuration.makeOutput(tree: KotlinParseTree, compressedTree: KotlinParseTree) {
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
            parentFile.mkdirs()
            if (!exists()) createNewFile()

            appendText("\n###################\n")
            appendText(compressedTree.toString())
            appendText("\n\n\n")
        }
    }
}
