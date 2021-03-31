import org.jetbrains.kotlin.spec.grammar.tools.KotlinParseTree
import org.jetbrains.kotlin.spec.grammar.tools.KotlinParseTreeNodeType

object MaxCompression : Exception()

fun findMostPopular(tree: KotlinParseTree): String {
    val stat = mutableMapOf<String, Int>()

    fun dfs(current: KotlinParseTree) {
        current.children.filter { it.type === KotlinParseTreeNodeType.RULE }.forEach {
            if (current.name() == "root") return@forEach

            val name = current with it
            val old = stat[name] ?: 0
            stat[name] = old + 1
        }

        current.children.forEach { dfs(it) }
    }

    dfs(tree)
    val mostPopular = stat.maxByOrNull { it.value } ?: throw MaxCompression
    return mostPopular.key
}

fun compress(root: KotlinParseTree, mostPopular: String): KotlinParseTree {
    fun compress(tree: KotlinParseTree): List<KotlinParseTree> {
        val (toCompress, newChildren) = tree.children.partition { mostPopular == tree with it }

        val compressed = toCompress.map { new(tree with it, *it.children.toTypedArray()) }
        if (toCompress.isNotEmpty() && newChildren.isEmpty()) {
            return compressed
        }

        tree.children.clear()
        tree.children += newChildren.flatMap { compress(it) }

        return compressed + tree
    }

    return root.apply {
        val compressed = children.flatMap { compress(it) }
        children.clear()
        children.addAll(compressed)
    }
}
