package edu.grinnell.cs;

import java.io.FileNotFoundException;

public class Main {

    private static final String KEYWORDS_FILENAME = "res/keywords.txt";


    public static void main(String[] args) throws FileNotFoundException {

        InputParser inputParser = new InputParser(KEYWORDS_FILENAME);

        System.out.println("Enter");


    }
}
