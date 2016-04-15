package edu.grinnell.cs;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Testing suite for the input parser
 * @author Albert Owusu-Asare
 * @author Larry Asante Boateng
 * @author Prabir Pradhan
 * @author Uzo Nwike
 */
public class InputParserTest {

    @Test
    public void testPositiveStatements() throws Exception {
        InputParser inputParser = new InputParser();
        /*Test 1*/
        String sampleInput ="I will use quadratic";
        List <String> results =inputParser.parseInput(sampleInput);
        assertTrue(results.contains("QuadraticFormula"));
        assertTrue(results.contains("FactorQuadratic"));

        /* Test 2 */
        sampleInput = "Dunno, maybe take squares?";
        results = inputParser.parseInput(sampleInput);
        assertTrue(results.contains("CompleteSquare"));
        assertTrue(results.contains("TakeSquareRoot"));
        assertTrue(results.contains("Help"));
    }

    @Test
    public void testUnrecognizableMethod() throws FileNotFoundException {
        InputParser inputParser = new InputParser();
        /*Test 1 */
        String sampleInput ="I love oliver the dog";
        List <String> results =inputParser.parseInput(sampleInput);
        assertTrue(results.contains("UnrecognizedMethod"));

        /* Test 2 */
        sampleInput ="ZYZZZ";
        results =inputParser.parseInput(sampleInput);
        assertTrue(results.contains("UnrecognizedMethod"));
    }

    @Test
    public void testMisspelledWords() throws FileNotFoundException {
        InputParser inputParser = new InputParser();
        /*Test 1 */
        String sampleInput ="I will use kompl3te the squa and factr quadatic";
        List <String> results =inputParser.parseInput(sampleInput);
        assertTrue(results.contains("FactorQuadratic"));
        assertTrue(results.contains("CompleteSquare"));
    }
}