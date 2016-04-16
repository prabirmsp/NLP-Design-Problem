package edu.grinnell.cs;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * This is a simple client that utilises the algorithm.
 */
public class Main {


    public static void main(String[] args) throws FileNotFoundException {

        if (args.length < 1) {
            System.out.println("NLP - Invalid arguments");
            System.out.println("usage: java Main <input sentence>");
        } else {
            InputParser inputParser = new InputParser();
            String input = String.join(" ", args);
            List<String> outputs = inputParser.parseInput(input);
            String out = String.join(", ", outputs);
            System.out.println("{" + out + "}\n");
        }
    }
}
