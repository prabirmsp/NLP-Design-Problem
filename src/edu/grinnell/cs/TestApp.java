package edu.grinnell.cs;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by pradhanp on 4/15/16.
 */
public class TestApp {



    public static void main(String[] args) throws FileNotFoundException {

        InputParser inputParser = new InputParser();
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.printf("What method do you plan to use?\n-> ");
            input = scanner.nextLine();
            if(input.equals("") || input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit"))
                return;

            List<String> outputs = inputParser.parseInput(input);
            String out = String.join(", ", outputs);
            System.out.println("{" + out + "}\n");
        }


    }
}
