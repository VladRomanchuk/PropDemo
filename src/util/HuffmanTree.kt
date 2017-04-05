package util

import java.io.File
import java.nio.charset.Charset
import java.text.Format
import java.util.*

abstract class HuffmanTree(var freq: Int) : Comparable<HuffmanTree> {
    override fun compareTo(other: HuffmanTree) = freq - other.freq
}

class HuffmanLeaf(freq: Int, var value: Char) : HuffmanTree(freq)

class HuffmanNode(var left: HuffmanTree, var right: HuffmanTree) : HuffmanTree(left.freq + right.freq)

fun buildTree(charFreqs: IntArray): HuffmanTree {
    val trees = PriorityQueue<HuffmanTree>()

    charFreqs.forEachIndexed { index, freq ->
        if (freq > 0) trees.offer(HuffmanLeaf(freq, index.toChar()))
    }

    assert(trees.size > 0)
    while (trees.size > 1) {
        val a = trees.poll()
        val b = trees.poll()
        trees.offer(HuffmanNode(a, b))
    }
    return trees.poll()
}

fun printCode(tree: HuffmanTree, prefix: StringBuffer) {
    val formatter : Formatter = Formatter()
    val formatterPrefix : Formatter = Formatter()
    when (tree) {
        is HuffmanLeaf -> println("${tree.value}\t${formatter.format("%10.5s", tree.freq)}\t${formatterPrefix.format("%12.5s", prefix)}")
        is HuffmanNode -> {
            prefix.append('0')
            printCode(tree.left, prefix)
            prefix.deleteCharAt(prefix.lastIndex)
            //traverse right
            prefix.append('1')
            printCode(tree.right, prefix)
            prefix.deleteCharAt(prefix.lastIndex)
        }
    }
}

fun main(args: Array<String>) {

    val test = File("./resources/poems.txt").readText(charset = Charsets.UTF_8)
    val maxIndex = test.max()!!.toInt() + 1
    val freqs = IntArray(maxIndex)
    test.toLowerCase().forEach { freqs[it.toInt()] += 1 }

    val tree = buildTree(freqs)
    val stringFormatter :Formatter = Formatter()

    println("SYMBOL\tWEIGHT\t${stringFormatter.format("%15.12s", "HUFFMAN CODE")}")
    printCode(tree, StringBuffer())

    File("./resources/outputBinary.txt").writeText("Kotlin perfect write to file ", Charset.defaultCharset())
}
