package edu.grinnell.cs;


import java.io.FileNotFoundException;
import java.util.*;

public class InputParser {

    private List<String> acceptedMethods;
    private Map<String, Set<String>> keywordMap;
    private static final String unrecognizedMethod = "UnrecognizedMethod";

    public InputParser(String keywordsFilename)
            throws FileNotFoundException {
        TextParser textParser = new TextParser();
        textParser.parseTextFile(keywordsFilename);
        this.keywordMap = textParser.getKeyWordMap();
        this.acceptedMethods = textParser.getAcceptedMethods();
    }

    /**
     * Parse the given input
     * @param input, a string
     * @return methods, a set of string methods
     */
    public List<String> parseInput(String input) {
        input = input.toLowerCase();

        // data structure to keep score
        Map<String, Integer> scores = new HashMap<>();
        // populate data structure, setting all scores to 0
        for (String s : acceptedMethods) {
            scores.put(s, 0);
        }
        // keep track of the max score
        int maxScore = 0;

        // go through each keyword
        for (String keyword : keywordMap.keySet()) {
            // look for keyword in the input string
            if (input.contains(keyword.toLowerCase())) {
                // get the set of methods that the keyword is associated with
                Set<String> methods = keywordMap.get(keyword);
                for (String method : methods) {
                    // add the score of each of the methods
                    int score = scores.get(method) + 1;
                    maxScore = Math.max(maxScore, score);
                    scores.replace(method, score);
                }
            }
        }

        // list of methods to return
        ArrayList<String> methods = new ArrayList<>();

        // if none of the keywords match, then return unrecognizedMethod
        if (maxScore == 0) {
            methods.add(unrecognizedMethod);
        } else {

            // return the highest scored methods
            for (String method : acceptedMethods) {
                if (scores.get(method) == maxScore)
                    methods.add(method);
            }
        }

        return methods;
    }

}
