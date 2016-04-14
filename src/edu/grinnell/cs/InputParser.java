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

    public static final String HELP = "Help";

    private List<String> acceptedMethods;
    private Map<String, Set<String>> keywordMap;
    private static final String unrecognizedMethod = "UnrecognizedMethod";
    private ToySpellingCorrector spellingCorrector;

    public InputParser(String keywordsFilename)
            throws FileNotFoundException {
        TextParser textParser = new TextParser();
        textParser.parseTextFile(keywordsFilename);
        this.keywordMap = textParser.getKeyWordMap();
        this.acceptedMethods = textParser.getAcceptedMethods();
        spellingCorrector = new ToySpellingCorrector();
        for (String s : keywordMap.keySet()) {
            String[] arr = s.split(" ");
            for (String word : arr)
                spellingCorrector.trainSingle(word);
        }
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
        for (String keyword : keywordMap.keySet()) {
            // stem the keyword
            String stemKeyword = stem(keyword);
            // look for keyword in the input string
            if (correctedInput.contains(stemKeyword)) {
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


            // if there are no negatives, or if the negative phrase is a hedge cue
            if (negativeScore == 0 || hedgeCueScore > 0) {
                // return the highest scored methods
                for (int i = 2; i < acceptedMethods.size(); i++) {
                    if (scores.get(acceptedMethods.get(i)) == maxScore)
                        methods.add(acceptedMethods.get(i));
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

}
