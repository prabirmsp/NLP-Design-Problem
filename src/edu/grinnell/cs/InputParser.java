package edu.grinnell.cs;


import org.gauner.jSpellCorrect.ToySpellingCorrector;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This is the main class that implements the algorithm for reading student free input text.
 *
 * <p>
 *     The class relies on data that describes the various math terminologies as well as the associations of keywords
 *     to those math terminologies.
 *     Additionally this class relies on data that describes words with negative connotations and stop words in the
 *     English language.

        See :
            "res/keywords.txt";
            "res/negatives.txt";
            "res/Stopwords.txt";
 * </p>
 *
 * @author Albert Owusu-Asare
 * @author Larry Asante Boateng
 * @author Prabir Pradhan
 * @author Uzo Nwike
 *
 * CITATIONS:
 * - Spelling Corrector <a href="http://developer.gauner.org/jspellcorrect/"> Here</a>
 */
public class InputParser {

    private static final String HEDGE_CUE_KEY = "HC";
    private static final String ABSOLUTE_NEGATIVE_KEY = "AN";
    private static final String HELP = "Help";
    private static final String METHODS_FILENAME = "res/keywords.txt";
    private static final String NEGATIVES_FILENAME = "res/negatives.txt";
    private static final String STOPWORDS_FILENAME = "res/Stopwords.txt";
    private static final int MAX_LEVENSHTEIN_DISTANCE = 3;
    private static final String UNRECOGNIZED_METHOD = "UnrecognizedMethod";
    private Map<String, Set<String>> negativesMap;
    private List<String> acceptedMethods;
    private Set<String> stopWords;
    private Map<String, Set<String>> keywordMap;
    private ToySpellingCorrector spellingCorrector;

    public InputParser()
            throws FileNotFoundException {
        TextParser methodsTextParser = new TextParser(METHODS_FILENAME);
        TextParser negativeTextParser = new TextParser(NEGATIVES_FILENAME);
        stopWords = parseStopWordsFile(STOPWORDS_FILENAME);
        methodsTextParser.parseTextFile();
        negativeTextParser.parseTextFile();
        this.keywordMap = methodsTextParser.getKeyWordMap();
        this.acceptedMethods = methodsTextParser.getAcceptedMethods();
        this.negativesMap = negativeTextParser.getKeyWordMap();
        spellingCorrector = new ToySpellingCorrector();
        trainSpellingCorrector();
    }

    private Set<String> parseStopWordsFile(String stopwordsFilename) throws FileNotFoundException {
        Set<String> results = new HashSet<>();
        File file  = new File(stopwordsFilename);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            String nextLine = scanner.nextLine();
            results.add(nextLine);
        }
        return results;
    }


    /**
     * Parse the given input
     *
     * @param input, a string
     * @return hitMethods, all the hit methods after parsing the input
     */
    public List<String> parseInput(String input) {
        String modifiedInput = removeStopWords(input.toLowerCase());
        String correctedInput = correctSpelling(modifiedInput);
        boolean containsAbsoluteNegation = checkAbsoluteNegation(correctedInput);
        boolean containsHedgeCues = checkHedgeCues(correctedInput);
        List<String> hitMethods = new ArrayList<>();

        if (!containsAbsoluteNegation || containsHedgeCues){
                hitMethods = getHitMethods(correctedInput);
        }

        if (containsAbsoluteNegation || containsHedgeCues) {
            hitMethods.add(HELP);
        }
        return hitMethods;
    }

    /**
     * Checks the corrected input for hedge cues.
     * @param correctedInput
     * @return @{true} if there is a hedge cue. @{false} if otherwise
     */
    private boolean checkHedgeCues(String correctedInput) {
        boolean containsHedgeCue = false;
        for (String keyword : negativesMap.get(HEDGE_CUE_KEY)) {
            if (correctedInput.contains(keyword)) {
                containsHedgeCue = true;
                break;
            }
        }
        return containsHedgeCue;
    }

    private boolean checkAbsoluteNegation(String correctedInput) {
        boolean containsAbsoluteNegation = false;
        for (String keyword : negativesMap.get(ABSOLUTE_NEGATIVE_KEY)) {
            if (correctedInput.contains(keyword)) {
                containsAbsoluteNegation = true;
                break;
            }
        }
        return containsAbsoluteNegation;
    }

    private List<String> getHitMethods(String correctedInput) {
        String [] correctedInputTokens = correctedInput.split("\\s+");
        // data structure to keep score
        Map<String, Integer> scores = new HashMap<>();
        // populate data structure, setting all scores to 0
        for (String s : acceptedMethods) {
            scores.put(s, 0);
        }
        // keep track of the max score
        int maxScore = 0;
        for(String token : correctedInputTokens){
            for(String keyword : keywordMap.keySet()){
                int levenshteinDistance = Levenshtein.distance(token,keyword);
                if(levenshteinDistance <= MAX_LEVENSHTEIN_DISTANCE){
                    Set<String> methods = keywordMap.get(keyword);
                    for (String method : methods) {
                        // add the score of each of the methods
                        int score = scores.get(method) + 1;
                        maxScore = Math.max(maxScore, score);
                        scores.replace(method, score);
                    }
                }
            }
        }
        List<String> hitMethods = new ArrayList<>();
        for (String method : acceptedMethods) {
            if (scores.get(method) == maxScore && maxScore != 0)
                hitMethods.add(method);
        }
        if(maxScore == 0)
            hitMethods.add(UNRECOGNIZED_METHOD);
        return hitMethods;
    }

    private String removeStopWords(String input) {
        StringBuilder builder = new StringBuilder();
        String[] tokens = input.split("\\s+");
        for (String word : tokens){
            if(!stopWords.contains(word)){
                builder.append(word + " ");
            }
        }
        return builder.toString().trim();
    }


    private String correctSpelling(String input) {
        input = input.toLowerCase();
        StringBuilder builder = new StringBuilder(input.length());

        String[] arr = input.split(" ");

        for (String s : arr) {
            s = spellingCorrector.correct(s);
            builder.append(s).append(" ");
        }
        return builder.toString();
    }

    private void trainSpellingCorrector() {
        for (String s : keywordMap.keySet()) {
            String[] arr = s.split(" ");
            for (String word : arr)
                spellingCorrector.trainSingle(word);
        }

        for (Map.Entry<String, Set<String>> entry : negativesMap.entrySet()) {
            Set<String> values = entry.getValue();
            for (String value : values) {
                String[] arr = value.split(" ");
                for (String word : arr)
                    spellingCorrector.trainSingle(word);
            }
        }
    }


}
