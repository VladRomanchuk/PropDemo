package main;

import java.math.BigDecimal;
import java.util.Formatter;
import java.util.PriorityQueue;

public class HuffmanTree implements Comparable<HuffmanTree> {

    private Node root;

    public HuffmanTree(Node root) {
        this.root = root;
    }

    private static class Node {
        private Double frequency;
        private Character character;
        private Node leftChild;
        private Node rightChild;

        public Node(Double frequency, Character character) {
            this.frequency = frequency;
            this.character = character;
        }

        public Node(HuffmanTree left, HuffmanTree right) {
            frequency = left.root.frequency + right.root.frequency;
            leftChild = left.root;
            rightChild = right.root;
        }

        @Override
        public String toString() {
            return "[id=" + frequency + ", data =" + character + "]";
        }
    }

    public static HuffmanTree buildHuffmanTree(double[] charFrequencies) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<>();
        for (int i = 0; i < charFrequencies.length; i++) {
            if (charFrequencies[i] > 0) {
                trees.offer(new HuffmanTree(new Node(charFrequencies[i], (char) i)));
            }
        }
        while (trees.size() > 1) {
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();
            trees.offer(new HuffmanTree(new Node(a, b)));
        }
        return trees.poll();
    }

//    public String decode(String bytes) {
//
//    }

    public String encode(String text) {

        String[] codes = codeTable();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(codes[text.charAt(i)]);
        }
        return result.toString();
    }


    private String[] codeTable() {
        String[] codeTable = new String[256];
        codeTable(root, new StringBuilder(), codeTable);
        return codeTable;
    }


    private void codeTable(Node node, StringBuilder code, String[] codeTable) {
        if (node.character != null) {
            codeTable[(char) node.character] = code.toString();
            return;
        }
        codeTable(node.leftChild, code.append('0'), codeTable);
        code.deleteCharAt(code.length() - 1);
        codeTable(node.rightChild, code.append('1'), codeTable);
        code.deleteCharAt(code.length() - 1);
    }

    public void printCodes(int lengthOfText) {
        Formatter formatter = new Formatter();
        String s = "binary code";
        formatter.format("%65.40s", s);
        System.out.println("char\t frequency\t" + formatter);
        printCodes(root, new StringBuilder(), lengthOfText);
    }


    private void printCodes(Node current, StringBuilder code, int length) {
        Formatter formatter = new Formatter();
        if (current.character != null) {
            System.out.println(current.character + "\t " + new BigDecimal(current.frequency / length) + "\t\t " + formatter.format("%10.20s", code));
        } else {
            printCodes(current.leftChild, code.append('0'), length);
            code.deleteCharAt(code.length() - 1);
            printCodes(current.rightChild, code.append('1'), length);
            code.deleteCharAt(code.length() - 1);
        }
    }

    @Override
    public int compareTo(HuffmanTree tree) {
        return ((int) (root.frequency - tree.root.frequency));
    }

}
