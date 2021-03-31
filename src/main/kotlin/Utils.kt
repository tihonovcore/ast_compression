import org.jetbrains.kotlin.spec.grammar.tools.KotlinParseTree
import org.jetbrains.kotlin.spec.grammar.tools.KotlinParseTreeNodeType
import org.jetbrains.kotlin.spec.grammar.tools.parseKotlinCode
import org.jetbrains.kotlin.spec.grammar.tools.tokenizeKotlinCode

fun parse(sourceCode: String): KotlinParseTree {
    //NOTE: "root" is wrapper for "file" in case it turns into a forest.
    //"root" should never compressed

    val tokens = tokenizeKotlinCode(sourceCode)
    val parseTree = parseKotlinCode(tokens)
    return new("root", parseTree)
}

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
