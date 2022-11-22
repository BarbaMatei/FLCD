import java.io.Console;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.constant.Constable;
import java.util.*;

public class Main
{
    private static void printToFile(String filePath, Object object) {
        try(PrintStream printStream = new PrintStream(filePath)) {
            printStream.println(object);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void printMenu(){
        System.out.println("0). Exit");
        System.out.println("1). Print states");
        System.out.println("2). Print alphabet");
        System.out.println("3). Print initial state");
        System.out.println("4). Print final state");
        System.out.println("5). Print transitions");
        System.out.println("6). Verify sequence");
    }

    public static void getOption(FiniteAutomata finiteAutomata){
        Scanner in = new Scanner(System.in);
        int option = -1;
        while(option != 0){
            System.out.print("Option: ");
            option = in.nextInt();
            if(option == 1){
                List<String> states = finiteAutomata.getStates();
                System.out.print("States: ");
                states.forEach(x -> {
                    System.out.print(x + " ");
                });
                System.out.println();
            }
            else if(option == 2){
                List<String> alphabet = finiteAutomata.getAlphabet();
                System.out.print("Alphabet: ");
                alphabet.forEach(x -> {
                    System.out.print(x + " ");
                });
                System.out.println();
            }
            else if(option == 3){
                String initialState = finiteAutomata.getInitialState();
                System.out.println("Initial State: " + initialState);
            }
            else if(option == 4){
                List<String> finalStates = finiteAutomata.getFinalStates();
                System.out.print("Final States: ");
                finalStates.forEach(x -> {
                    System.out.print(x + " ");
                });
                System.out.println();
            }
            else if(option == 5){
                System.out.println("Transitions: ");
                Map<String, Set<Pair<String, String>>> transitions = finiteAutomata.getTransitions();
                transitions.forEach((key, value) -> {
                    value.forEach(x -> {
                        System.out.println("\t" + key + " " + x.GetFirst() + " " + x.GetSecond());
                    });
                });
            }
            else{
                System.out.print("Give word: ");
                String word = in.next();
                System.out.println("Is sequence accepted?: " + finiteAutomata.acceptsSequence(word));
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        //p1.txt  p2.txt  p3.txt  p3err.txt
        /*MyScanner scanner = new MyScanner("D:\\Matei\\University\\FLCD\\src\\p3.txt");
        scanner.scan();
        printToFile("src\\p3ST.txt", scanner.getSymbolTable());
        printToFile("src\\p3PIF.txt", scanner.getPif());*/

        FiniteAutomata finiteAutomata = new FiniteAutomata("D:\\Matei\\University\\FLCD\\src\\finiteAutomata.txt");
        printMenu();
        getOption(finiteAutomata);
    }
}