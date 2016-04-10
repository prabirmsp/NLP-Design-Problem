package edu.grinnell.cs;


import java.io.FileNotFoundException;
import java.util.*;

public class InputParser {

    private List<String> acceptedMethods;
    private Map<String, Set<String>> keywordMap;
    private static final String unrecognizedMethod = "UnrecognizedMethod";
    private static final String helpMethod = "HELP";

    public InputParser(String keywordsFilename)
            throws FileNotFoundException {
        TextParser textParser = new TextParser();
        textParser.parseTextFile(keywordsFilename);
        this.keywordMap = textParser.getKeyWordMap();
        this.acceptedMethods = textParser.getAcceptedMethods();
    }

    public List<String> parseInput(String input) {

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
            if (input.contains(keyword)) {
                // get the set of methods that the keyword is associated with
                Set<String> values = keywordMap.get(keyword);
                for (String v : values) {
                    // add the score of each of the methods
                    int score = scores.get(v) + 1;
                    maxScore = Math.max(maxScore, score);
                    scores.put(v, score);
                }
            }
        }

        // list of methods to return
        ArrayList<String> methods = new ArrayList<>();

        // if none of the keywords match, then return unrecognizedMethod
        if (maxScore == 0) {
            methods.add(unrecognizedMethod);
        }

        // return the highest scored methods
        for (String method : acceptedMethods) {
            if (scores.get(method) == maxScore)
                methods.add(method);
        }

        return methods;
    }

}
