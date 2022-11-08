import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MyScanner {
    private final ArrayList<String> OPERATORS = new ArrayList<>(
            List.of("+", "-", "*", "/", "!=" ,"<=", ">=", "==", "<", ">", "%", "="));
    private final ArrayList<String> SEPARATORS = new ArrayList<>(
            List.of("{", "}", ";", " ", "'", ",", "(", ")", "\n"));
    private final ArrayList<String> RESERVED_WORDS = new ArrayList<>(
            List.of("create", "read", "show", "if", "else", "then", "endif", "while", "endwhile", "array", "start", "finish", "and", "or"));
    private final String filePath;
    private final SymbolTable symbolTable;
    private final PIF pif;
    private final ArrayList<Pair<String, Integer>> tokenTable;

    public MyScanner(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
        this.symbolTable = new SymbolTable(20);
        this.pif = new PIF();
        tokenTable = new ArrayList<>();
        GenerateTokenTable();
    }

    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public void scan() {
        List<Pair<String, Integer>> tokens = this.tokenize();

        if (tokens == null) {
            return;
        }
        for(Pair<String, Integer> tokenLinePair : tokens){
            String token = tokenLinePair.GetFirst();
            if (this.RESERVED_WORDS.contains(token)) {
                this.pif.add(new Pair<>(token, new Pair<>(-1, -1)), GetTokenPosition(token));
            }
            else if (this.OPERATORS.contains(token)) {
                this.pif.add(new Pair<>(token, new Pair<>(-1, -1)), GetTokenPosition(token));
            }
            else if (Pattern.compile("#[a-zA-Z0-9]*|\\$[a-zA-Z0-9]").matcher(token).matches()) { // identifier
                this.symbolTable.AddElement(token);
                this.pif.add(new Pair<>(token, this.symbolTable.FindTermPosition(token)), 0);
            }
            else if (Pattern.compile("\"[a-zA-Z0-9]*\"|'[a-zA-Z0-9]*'").matcher(token).matches()) { //string constant
                this.symbolTable.AddElement(token);
                this.pif.add(new Pair<>(token, this.symbolTable.FindTermPosition(token)), 1);
            }
            else if(Pattern.compile("^-?\\d*(\\.\\d+)?$").matcher(token).matches()){ //number constant
                this.symbolTable.AddElement(token);
                this.pif.add(new Pair<>(token, this.symbolTable.FindTermPosition(token)), 1);
            }
            else {
                System.out.println("Lexical error, line " + tokenLinePair.GetSecond() + " token: " + token);
                break;
            }
        }
        System.out.println(this.symbolTable);
        System.out.println("===============");
        System.out.println(this.pif);
    }

    private List<Pair<String, Integer>> tokenize() {
        try {
            String fileContent = this.readFile();
            String separators = this.SEPARATORS.stream().reduce("", (a, b) -> a + b);
            List<String> tokensIncludingSeparators = Collections.list(new StringTokenizer(fileContent, separators, true)).stream()
                    .map(token -> (String) token)
                    .collect(Collectors.toList());
            return this.tokenizeWithCompleteStringsAndLineNumbers(tokensIncludingSeparators);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String readFile() throws FileNotFoundException {
        StringBuilder fileContent = new StringBuilder();
        Scanner scanner = new Scanner(new File(this.filePath));
        while (scanner.hasNextLine()) {
            fileContent.append(scanner.nextLine()).append("\n");
        }
        return fileContent.toString().replace("\t", "");
    }

    private List<Pair<String, Integer>> tokenizeWithCompleteStringsAndLineNumbers(List<String> tokensIncludingSeparators) {
        List<Pair<String, Integer>> tokensWithCompleteStrings = new ArrayList<>();
        boolean inString = false;
        StringBuilder currentString = new StringBuilder();
        currentString.append("'");
        int lineNumber = 1;

        for (String token : tokensIncludingSeparators) {
            if (token.equals("'")) {
                if (inString) { // end of string
                    currentString.append("'");
                    tokensWithCompleteStrings.add(new Pair<>(currentString.toString(), lineNumber));
                    currentString = new StringBuilder();
                    currentString.append("'");
                }
                inString = !inString;
            }
            else if (token.equals("\n")) {
                lineNumber++;
            }
            else {
                if (inString) { // add the current token to the string
                    currentString.append(token);
                }
                else if (!this.SEPARATORS.contains(token)) {
                    tokensWithCompleteStrings.add(new Pair<>(token, lineNumber));
                }
            }
        }
        return tokensWithCompleteStrings;
    }

    private void GenerateTokenTable() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("D:\\Matei\\University\\FLCD\\src\\token.txt"));
        int index = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            tokenTable.add(new Pair<>(line.toString(), index));
            index++;
        }
    }

    public PIF getPif() {
        return pif;
    }

    private int GetTokenPosition(String token){
        int position = 0;
        for(Pair<String, Integer> pair : tokenTable){
            if(pair.GetFirst().equals(token)){
                position = pair.GetSecond();;
                break;
            }
        }
        return position;
    }
}
