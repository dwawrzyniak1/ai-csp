package csp;

import csp.heuristics.ValueSelectionHeuristic;
import csp.heuristics.VariableSelectionHeuristic;

import java.util.ArrayList;
import java.util.List;

public class Backtracking {

    private static final int COMPLETED = 0;
    private static final int FAILURE = 1;
    private List<Variable[][]> results;
    private VariableSelectionHeuristic variableSelection;
    private ValueSelectionHeuristic valueSelection;

    public Backtracking(VariableSelectionHeuristic variableSelection, ValueSelectionHeuristic valueSelection) {
        this.variableSelection = variableSelection;
        this.valueSelection = valueSelection;
        results = new ArrayList<>();
    }

    public List<Variable[][]> search(CSP csp){
        results = new ArrayList<>();

        recursiveSearch(csp.getState(), csp.getNumberOfAssignedValues());

        return results;
    }

    private int recursiveSearch(Variable[][] state, int boardCompletnessCounter) {
        if(boardCompletnessCounter == state.length*state.length){
            results.add(state);
            return COMPLETED;
        }
        System.out.println("Liczba zapełnionych pól: " + boardCompletnessCounter);
        Variable variable = variableSelection.selectUnassigned(state);
        System.out.println("Wybrana zmienna: " + variable);
        for(int value : valueSelection.domainValues(variable)){
            System.out.println("Wartości zmiennej: " + value);
            if(variable.isValueConsistent(value, state)){
                Variable copied = new Variable(variable);
                copied.setValue(value);
                Variable[][] stateCopy = copyStateWithNewVariable(state, copied);
                System.out.println("Skopiowany stan: " + stateToString(stateCopy) + "\n\n\n");
                int result = recursiveSearch(stateCopy, boardCompletnessCounter++);
            }
        }
        return FAILURE;
    }

    private String stateToString(Variable[][] stateCopy) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < stateCopy.length; i++){
            for(int j = 0; j < stateCopy.length; j++){
                builder.append(stateCopy[i][j]).append("\t");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private Variable[][] copyStateWithNewVariable(Variable[][] state, Variable copied) {
        Variable[][] stateCopy = new Variable[state.length][state.length];
        for(int i = 0; i < state.length; i++){
            for(int j = 0; j < state[i].length; j++){
                stateCopy[i][j] = new Variable(state[i][j]);
            }
        }
        stateCopy[copied.getRow()][copied.getColumn()] = copied;
        return stateCopy;
    }

}
