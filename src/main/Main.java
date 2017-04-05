package main;

import util.Util;

import java.io.IOException;

public class Main {

    private static final String FILE_PATH_IN = "./resources/poems.txt";
    private static final String FILE_PATH_OUT_BINARY = "./resources/outputBinary.txt";
    private static final String FILE_PATH_OUT_CODED = "./resources/outputCoded.txt";

    public static void main(String[] args) throws IOException {

        String text = Util.getTextFromFile(FILE_PATH_IN);
        int length = text.length();

        double[] charFrequencies = new double[256];
        for (char c : text.toCharArray()) {
            charFrequencies[c]++;
        }

        HuffmanTree tree = HuffmanTree.buildHuffmanTree(charFrequencies);
        tree.printCodes(length);

        System.out.println(" ");
        System.out.printf("size before compression = %d%n", text.length() * 8);
        String encode = tree.encode(text);
        System.out.printf("size after compression = %d%n", encode.length());

        System.out.println(" ");
        Util.writeToFile(encode, FILE_PATH_OUT_BINARY);

        String coded = Util.convertBinaryToString(encode);
        System.out.println(coded);
        Util.writeToFile(coded, FILE_PATH_OUT_CODED);
    }
}
