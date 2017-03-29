package util

import java.io.File
import java.nio.charset.Charset
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
    when (tree) {
        is HuffmanLeaf -> println("${tree.value}\t${tree.freq}\t$prefix")
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
//    val test = "Mon Portrait Written by the poet at the age of 15. Vous me demandez mon portrait, Mais peint d'apres nature: Mon cher, il sera bientot fait, Quoique en miniature. Je suis un jeune polisson Encore dans les classes; Point sot, je le dis sans facon,Et sans fades grimaces. Oui! il ne fut babillard Ni docteur de Sorbonne,Plus ennuyeux et plus braillardQue moi-meme en personne.Ma taille, a celle des plus longs,Elle n'est point egalee;J'ai le teint frais, les cheveux blonds,Et la tete bouclee.J'aime et le monde et son fracas,Je hais la solitude;J'abhorre et noises et debats,Et tant soit peu l'etude.Spectacles, bals, me plaisent fort,Et d'apres ma pensee, Je dirais ce que j'aime encore,Si je n'etais au Lycee.Apres cela, mon cher ami, L'on peut me reconnaitre, Oui! tel que le bon Dieu me fit, Je veux toujours paraitre.Vrai demon, par l'espieglerie,Vrai singe par sa mine,Beaucoup et trop d'etourderie,Ma foi! voila Pouchekine."

    val test = File("./resources/poems.txt").readText(charset = Charsets.UTF_8)
    print("asdsa  = $test")
    val maxIndex = test.max()!!.toInt() + 1
    val freqs = IntArray(maxIndex) //256 enough for latin ASCII table, but dynamic size is more fun
    test.toLowerCase().forEach { freqs[it.toInt()] += 1 }

    val tree = buildTree(freqs)
    println("SYMBOL\tWEIGHT\tHUFFMAN CODE")
    printCode(tree, StringBuffer())

    File("./resources/outputBinary.txt").writeText("Kotlin perfect write to file ", Charset.defaultCharset())
}
