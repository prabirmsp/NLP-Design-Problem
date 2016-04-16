# NLP-Design-Problem
__*Last Edit:* Fri Apr 15 22:21:52 CDT 2016__

*__CSC-301__ Prof. Ursula Wolz*
Grinnell College

## Authors
- Albert Owusu-Asare
- Uzo Nwike
- Larry Asante-Boateng
- Prabir Pradhan


Project Structure:
------------------
```
.
├── bin
├── lib
│   └── jSpellCorrect-0.4.jar
├── res
│   ├── keywords.txt
│   ├── negatives.txt
│   └── Stopwords.txt
└── src
    ├── InputParser.java
    ├── Levenshtein.java
    ├── Main.java
    ├── test
    │   └── InputParserTest.java
    ├── TestApp.java
    └── TextParser.java

```
The source code can be found in the directory `src`. This directory contains:

- `InputParser.java` : Implements the algorithm for reading and parsing student input
- `Levenshtein.java` : A Utility program that provides functionality for calculating the levenshtein distances needed for the algorithm
- `Main.java` : A simple test client that can shows how the algorithm works
- `TestApp.java` : A simple application to look at outputs, given the string inputs
- `test` : a directory containing `InputParserTest.java` which is a jUnit test suite for testing edge cases

The resources can be found in the directory `res`. This directory contains:

- `keywords.txt` : a list of keywords for each recognized method
- `negatives.txt` : a list of all the recognized negative words, including hedge cues
- `Stopwords.txt` : a list of stopwords

Installation:
----------
For the compilation and execution of the programs can be done in the UNIX environment. Specifically the following command can be used issued in the bash shell to compile.
```bash
javac -d bin -sourcepath src -cp lib/jSpellCorrect-0.4.jar src/*.java
```
To run the java file use the command :
```bash
java -cp bin:lib/jSpellCorrect-0.4.jar edu.grinnell.cs.Main <input sentence>
```

Sample Run and Output:
----------------------
Command:
```bash
java -cp bin:lib/jSpellCorrect-0.4.jar edu.grinnell.cs.Main "I will use quadratic"
```
Output:
```bash
{QuadraticFormula, FactorQuadratic}
```
For more examples, see `src/InputParserTest.java`




