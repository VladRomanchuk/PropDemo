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


    //    private static int frequenceCount() throws IOException {
//
//        int length = getTextFromFile(FILE_PATH).length();
//        char character;
//        int totalCount = 0;
//
//        Map<Character, Integer> map = new HashMap<>();
//
//        for (int i = 0; i < length; i++) {
//            character = getTextFromFile(FILE_PATH).charAt(i);
//            totalCount++;
//            Integer countForCharacter = 0;
//            if (map.containsKey(character)) {
//                countForCharacter = map.get(character);
//                countForCharacter++;
//            } else {
//                countForCharacter = 1;
//            }
//
//            map.put(character, countForCharacter);
//        }
//
//        Character[] charactersFound = map.keySet().toArray(new Character[0]);
//
//        System.out.println("Letters\tFrequency\tCount");
//        for (int k = 0; k < charactersFound.length; k++) {
//            character = charactersFound[k];
//            System.out.println(character +
//                    "\t" + new BigDecimal(map.get(character) / ((double) totalCount)) +
//                    "\t" +
//                    map.get(character));
//        }
//        return totalCount;
//    }
//
//
//    private static byte[] readBytesFromFile(String filePath) throws IOException {
//        String s1 = null;
//        FileInputStream fileInputStream = null;
//        byte[] bytesArray = null;
//
//        try {
//            File file = new File(filePath);
//            bytesArray = new byte[(int) file.length()];
//            fileInputStream = new FileInputStream(file);
//            fileInputStream.read(bytesArray);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fileInputStream != null) {
//                try {
//                    fileInputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//        for (int i = 0; i < getTextFromFile(FILE_PATH).length(); i++) {
//            s1 = String.format("%8s", Integer.toBinaryString(bytesArray[i] & 0xFF)).replace(' ', '0');
//            System.out.print(s1 + ", ");
//        }
//        return bytesArray;
//
//    }

}
