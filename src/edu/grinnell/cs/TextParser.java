package edu.grinnell.cs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Custom text parser for the NLP algorithm
 * @author Albert Owusu-Asare
 * @author  Uzo Nwike
 * @version 1.1 Sun Apr 10 15:19:51 CDT 2016
 *
 */
public class TextParser {

    private List<String> acceptedMethods;
    private Map<String, Set<String>> keyWordMap;
    private static final String ARROW_DELIMITER ="=>";
    private static final String COMMA_DELIMITER=",";
    private String fileName;


    TextParser(String fileName){
        this.acceptedMethods = new ArrayList<>();
        this.keyWordMap = new HashMap<>();
        this.fileName = fileName;
    }



    /**
     * Parses the input text file @code{fileName} and populates respective maps for further processing
     * @throws FileNotFoundException
     */
    public void parseTextFile() throws FileNotFoundException {
        File file  = new File(fileName);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            String nextLine = scanner.nextLine();
            String [] terminologyKeyWordsTokens = nextLine.split(ARROW_DELIMITER);
            populateMaps(terminologyKeyWordsTokens);
        }

    }

    /*
        Helper method for populating Maps;
     */
    private void populateMaps(String [] terminologyKeyWordsToken) {
        String terminology = terminologyKeyWordsToken[0]; //Complete the square
        String keyWords = terminologyKeyWordsToken[1]; // [ complete, square ]
        acceptedMethods.add(terminology);
        String [] keyWordTokens = keyWords.split(COMMA_DELIMITER);
        for(String keyWord : keyWordTokens){
            Set<String> terminologies = this.keyWordMap.get(keyWord);
            if(terminologies == null){
                terminologies = new HashSet();
            }
            terminologies.add(terminology);
            keyWordMap.put(keyWord,terminologies);
            terminologies.add(terminology);
        }
    }

    public List<String> getAcceptedMethods(){
        return this.acceptedMethods;
    }

    public Map<String, Set<String>> getKeyWordMap(){
        return this.keyWordMap;
    }

    /*
    for testing purposes
     */
    public static void main(String ...args) throws FileNotFoundException {
        final String FILE_NAME = "res/keywords.txt";
        TextParser textParser = new TextParser(FILE_NAME);
        textParser.parseTextFile();
        List<String> acceptedMethods =textParser.getAcceptedMethods();
        Map<String, Set<String>> keyWordMap = textParser.getKeyWordMap();
        System.out.println("Bag of accepted methods:  "+ acceptedMethods);
        System.out.println("KeyWordsMap" + keyWordMap);
    }
}
