package edu.grinnell.cs;


import org.gauner.jSpellCorrect.ToySpellingCorrector;
import org.tartarus.martin.Stemmer;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * CITATIONS:
 * - Spelling Corrector <a href="http://developer.gauner.org/jspellcorrect/"> Here</a>
 */
public class InputParser {
    private static final String HEDGE_CUE_KEY = "HC";
    private static final String ABSOLUTE_NEGATIVE_KEY = "AN";
    public static final String HELP = "Help";
    private static final String METHODS_FILENAME = "res/keywords.txt";
    private static final String NEGATIVES_FILENAME = "res/negatives.txt";

    public static final String[] HELP_KEYWORDS = {
            "not use", "not using", "not do", "not doing", "won't do", "won't use", "wont do", "wont use"};
    public static final String[] HEDGE_CUE_KEYWORDS = {
            "help", "hint", "dont know", "don't know", "no idea", "do not know", "not sure", "no clue","maybe"};

    private Map<String, Set<String>> negativesMap;
    private List<String> acceptedMethods;
    private Map<String, Set<String>> methodsKeyWordMap;

    private static final String unrecognizedMethod = "UnrecognizedMethod";
    private ToySpellingCorrector spellingCorrector;

    public InputParser()
            throws FileNotFoundException {
        TextParser methodsTextParser = new TextParser(METHODS_FILENAME);
        TextParser negativeTextParser = new TextParser(NEGATIVES_FILENAME);
        methodsTextParser.parseTextFile();
        negativeTextParser.parseTextFile();
        this.methodsKeyWordMap = methodsTextParser.getKeyWordMap();
        this.acceptedMethods = methodsTextParser.getAcceptedMethods();
        this.negativesMap = negativeTextParser.getKeyWordMap();
        spellingCorrector = new ToySpellingCorrector();
        trainSpellingCorrector();
    }


    /**
     * Parse the given input
     *
     * @param input, a string
     * @return methods, a set of string methods
     */
    public List<String> parseInput(String input) {
        String correctedInput = correctSpelling(input);

        // data structure to keep score
        Map<String, Integer> scores = new HashMap<>();
        // populate data structure, setting all scores to 0
        for (String s : acceptedMethods) {
            scores.put(s, 0);
        }
        // keep track of the max score
        int maxScore = 0;

        // go through each keyword
        for (String keyword : methodsKeyWordMap.keySet()) {
            // stem the keyword
            String stemKeyword = stem(keyword);
            // look for keyword in the input string
            if (correctedInput.contains(stemKeyword)) {
                // get the set of methods that the keyword is associated with
                Set<String> methods = methodsKeyWordMap.get(keyword);
                for (String method : methods) {
                    // add the score of each of the methods
                    int score = scores.get(method) + 1;
                    maxScore = Math.max(maxScore, score);
                    scores.replace(method, score);
                }
            }
        }

        boolean containsHelp = false;
        boolean containsHedgeCue = false;

        // look for help words
        for (String keyword: negativesMap.get(ABSOLUTE_NEGATIVE_KEY)) {
            if (correctedInput.contains(keyword)) {
                containsHelp = true;
                break;
            }
        }

        // look for hedge cues
        for (String keyword: negativesMap.get(HEDGE_CUE_KEY)) {
            if (correctedInput.contains(keyword)) {
                containsHedgeCue = true;
                break;
            }
        }

        // list of methods to return
        ArrayList<String> methods = new ArrayList<>();

        if (containsHelp || containsHedgeCue) {
            methods.add(HELP);
        }
        // if none of the keywords match, then return unrecognizedMethod
        else if (maxScore == 0) {
            methods.add(unrecognizedMethod);
        } else {


            // if there are no negatives, or if the negative phrase is a hedge cue
            if (!(containsHelp || containsHedgeCue) || containsHedgeCue) {
                // return the highest scored methods
                for (String method : acceptedMethods) {
                    if (scores.get(method) == maxScore)
                        methods.add(method);
                }
            }
        }

        return methods;
    }

    private String stem(String word) {
        Stemmer stemmer = new Stemmer();
        stemmer.add(word.toCharArray(), word.length());
        stemmer.stem();
        return stemmer.toString();
    }

    private String correctSpelling(String input) {
        input = input.toLowerCase();
        StringBuilder builder = new StringBuilder(input.length());

        String[] arr = input.split(" ");

        for (String s : arr) {
            s = spellingCorrector.correct(s);
            s = stem(s);
            builder.append(s).append(" ");
        }
        return builder.toString();
    }
    private void trainSpellingCorrector() {
        for (String s : methodsKeyWordMap.keySet()) {
            String[] arr = s.split(" ");
            for (String word : arr)
                spellingCorrector.trainSingle(word);
        }
    }


}
