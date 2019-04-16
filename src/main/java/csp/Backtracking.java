package csp;

import csp.heuristics.ValueSelectionHeuristic;
import csp.heuristics.VariableSelectionHeuristic;

import java.util.ArrayList;
import java.util.List;

public class Backtracking {

    private static final int COMPLETED = 0;
    private static final int FAILURE = 1;

    public static int iterations = 0;

    private List<Variable[][]> results;
    private VariableSelectionHeuristic variableSelection;
    private ValueSelectionHeuristic valueSelection;

    public Backtracking(VariableSelectionHeuristic variableSelection, ValueSelectionHeuristic valueSelection) {
        this.variableSelection = variableSelection;
        this.valueSelection = valueSelection;
        results = new ArrayList<>();
    }

    public Result searchWithForwardChecking(CSP csp){
        results = new ArrayList<>();
        iterations = 0;
        long startTime = System.nanoTime();
        recursiveSearchWithForwardChecking(csp.getState());
        long endTime = System.nanoTime();
        double amountOfTimeInSeconds = (endTime - startTime)/1_000_000_000.0;
        return new Result(
                results,
                amountOfTimeInSeconds,
                iterations,
                "FC " + variableSelection.getName() + " " + valueSelection.getName(),
                csp.getFilename()
        );
    }


    private int recursiveSearchWithForwardChecking(Variable[][] state) {
        if(isComplete(state)){
            results.add(state);
            return COMPLETED;
        }

        Variable variable = variableSelection.selectUnassigned(state);

        for(int value : valueSelection.domainValues(variable, state)){
            iterations++;
            Variable copied = new Variable(variable);
            copied.setValueAndUpdateDomain(value, copied.getValue());
            Variable[][] stateCopy = copyStateWithNewVariable(state, copied);
            updateDomains(stateCopy, copied);
            recursiveSearchWithForwardChecking(stateCopy);
        }
        return FAILURE;
    }

    private void updateDomains(Variable[][] state, Variable changed) {
        for(int i = 0; i < state.length; i++){
            state[changed.getRow()][i].updateDomain(state);
        }
        for(int i = 0; i < state.length; i++){
            state[i][changed.getColumn()].updateDomain(state);
        }
    }

    public Result search(CSP csp){
        results = new ArrayList<>();
        iterations = 0;
        long startTime = System.nanoTime();
        recursiveSearch(csp.getState());
        long endTime = System.nanoTime();
        double amountOfTimeInSeconds = (endTime - startTime)/1_000_000_000.0;
        return new Result(
                results,
                amountOfTimeInSeconds,
                iterations,
                "BT " + variableSelection.getName() + " " + valueSelection.getName(),
                csp.getFilename());
    }

    private int recursiveSearch(Variable[][] state) {
        if(isComplete(state)){
            results.add(state);
            return COMPLETED;
        }

//        printState(state);

        Variable variable = variableSelection.selectUnassigned(state);

        for(int value : valueSelection.domainValues(variable, state)){
            iterations++;
            if(variable.isValueConsistent(value, state)){
                Variable copied = new Variable(variable);
                copied.setValueAndUpdateDomain(value, copied.getValue());
                Variable[][] stateCopy = copyStateWithNewVariable(state, copied);
                recursiveSearch(stateCopy);
            }
        }
        return FAILURE;
    }

    private void printState(Variable[][] state) {

        System.out.println("--------------");
        for(int i = 0; i < state.length; i++){
            for(int j = 0; j < state.length; j++){
                System.out.println(state[i][j] + "\t");
            }
            System.out.println("");
        }

    }

    private boolean isComplete(Variable[][] state) {
        for(Variable[] row : state){
            for(Variable variable : row){
                if (!variable.isAssigned()){
                    return false;
                }
            }
        }
        return true;
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
