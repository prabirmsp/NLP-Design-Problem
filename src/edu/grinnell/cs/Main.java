package edu.grinnell.cs;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String KEYWORDS_FILENAME = "res/keywords.txt";

    public static void main(String[] args) throws FileNotFoundException {

        InputParser inputParser = new InputParser(KEYWORDS_FILENAME);

        System.out.printf("What method do you plan to use?\n-> ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        List<String> outputs = inputParser.parseInput(input);
        String out = String.join(", ", outputs);
        System.out.println("{" + out + "}");


    }
}
