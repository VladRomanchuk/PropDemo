package util;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Util {
    public static void convertStringToBinary(String inputText) {
        byte[] bytes = inputText.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(' ');
        }
        System.out.println(" to binary: " + binary);
    }

    public static String convertBinaryToString(String inputText) {
        String output = "";
        for (int i = 0; i < inputText.length() / 8; i++) {

            int a = Integer.parseInt(inputText.substring(8 * i, (i + 1) * 8), 2);
            output += (char) (a);
        }
        return output;
    }

    public static void writeToFile(String incoded, String fileRec) {
        Path p1 = Paths.get(fileRec);

        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileRec, false), "utf-8"));
            writer.write(incoded);
        } catch (IOException ex) {
            System.out.println("file is broken");
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
                System.out.println("file not found");
            }
        }
    }

    public static String getTextFromFile(String pathFile) throws IOException {
        Scanner input = new Scanner(new File(pathFile));
        return input.nextLine();
    }
}
