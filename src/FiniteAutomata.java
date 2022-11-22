import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

public class FiniteAutomata {
    private boolean isDeterministic;
    private String initialState;
    private List<String> states;
    private List<String> alphabet;
    private Map<String, Set<Pair<String, String>>> transitions;
    private List<String> finalStates;

    public boolean checkIfDeterministic() {
        for (String state : this.transitions.keySet()) {
            long uniqueSymbols = this.transitions.get(state).stream().map(Pair::GetFirst).distinct().count();
            if (uniqueSymbols != this.transitions.get(state).size()) {
                return false; // at least one symbol leads to different states
            }
        }
        return true;
    }

    private void readFromFile(String filePath) {
        // assume the file is valid
        try (Scanner scanner = new Scanner(new File(filePath))) {
            ArrayList<String> lineElements = new ArrayList<>();
            while(!(lineElements = new ArrayList<>(List.of(scanner.nextLine().split(" ")))).isEmpty())
            {
                if(lineElements.get(0).equals("states")){
                    this.states = lineElements.stream().skip(2).toList();
                }
                else if(lineElements.get(0).equals("alphabet")){
                    this.alphabet = lineElements.stream().skip(2).toList();
                }
                else if(lineElements.get(0).equals("initial_state")){
                    this.initialState = lineElements.stream().skip(2).findFirst().get();
                }
                else if(lineElements.get(0).equals("final_states")){
                    this.finalStates = lineElements.stream().skip(2).toList();
                }
                else {
                    String line = null;
                    this.transitions = new HashMap<>();
                    while((line = scanner.nextLine()) != null){
                        String[] transitionElements = line.split(" ");
                        this.transitions.putIfAbsent(transitionElements[0], new HashSet<>());
                        this.transitions.get(transitionElements[0]).add(new Pair<>(transitionElements[1], transitionElements[2]));
                    }
                }
                lineElements.clear();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (NoSuchElementException e){

        }
    }

    public FiniteAutomata(String filePath) {
        this.readFromFile(filePath);
    }

    public List<String> getStates() {
        return this.states;
    }

    public List<String> getAlphabet() {
        return this.alphabet;
    }

    public String getInitialState() {
        return initialState;
    }

    public Map<String, Set<Pair<String, String>>> getTransitions() {
        return this.transitions;
    }

    public List<String> getFinalStates() {
        return this.finalStates;
    }

    public boolean acceptsSequence(String sequence) {
        if (! checkIfDeterministic()) {
            return false;
        }

        String currentState = this.initialState;
        for (int i = 0; i < sequence.length(); i++) {
            String currentSymbol = sequence.substring(i, i + 1);
            Set<Pair<String, String>> nextTransitions = this.transitions.get(currentState);
            if (nextTransitions == null) {
                return false;
            }

            boolean foundNext = false;
            for (Pair<String, String> stateSymbolPair: nextTransitions) {
                if (stateSymbolPair.GetFirst().equals(currentSymbol)) {
                    currentState = stateSymbolPair.GetSecond();
                    foundNext = true;
                    break;
                }
            }

            if (!foundNext) {
                return false;
            }
        }

        return this.finalStates.contains(currentState);
    }
}
