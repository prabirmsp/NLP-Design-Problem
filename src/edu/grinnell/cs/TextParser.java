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

    private Map<String,Integer> keyWordFrequencyMap;
    private Map<String, Set<String>> keyWordMap;
    private static final String TAB_DELIMITER ="\t";
    private static final String COMMA_DELIMITER=",";

    TextParser(){
        this.keyWordFrequencyMap = new HashMap<>();
        this.keyWordMap = new HashMap<>();
    }

    /**
     * Parses the input text file @code{fileName} and populates respective maps for further processing
     * @param fileName, the fileName of the file to be parsed
     * @throws FileNotFoundException
     */
    public void parseTextFile(String fileName ) throws FileNotFoundException {
        File file  = new File(fileName);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNext()){
            String nextLine = scanner.next();
            String [] terminologyKeyWordsTokens = nextLine.split(TAB_DELIMITER);
            populateMaps(terminologyKeyWordsTokens);
        }

    }

    /*
        Helper method for populating Maps;
     */
    private void populateMaps(String [] terminologyKeyWordsToken) {
        String terminology = terminologyKeyWordsToken[0]; //Complete the square
        String keyWords = terminologyKeyWordsToken[1]; // [ complete, square ]
        keyWordFrequencyMap.put(terminology,0);
        String [] keyWordTokens = keyWords.split(COMMA_DELIMITER);
        for(String keyWord : keyWordTokens){
            Set<String> terminologies = this.keyWordMap.get(keyWord);
            if(terminologies == null){
                terminologies = new HashSet();
            }
            terminologies.add(terminology);
        }
    }

    public Map<String, Integer> getKeyWordFrequencyMap(){
        return this.keyWordFrequencyMap;
    }

    public Map<String, Set<String>> getKeyWordMap(){
        return this.keyWordMap;
    }


    public static void main(String ...args){

    }
}
