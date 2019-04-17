package csp;

import csp.heuristics.ValueSelectionHeuristic;
import csp.heuristics.VariableSelectionHeuristic;

import java.util.ArrayList;
import java.util.List;

public class Backtracking {

    private static final int COMPLETED = 0;
    private static final int FAILURE = 1;

    private int iterations;
    private List<Variable[][]> results;
    private VariableSelectionHeuristic variableSelection;
    private ValueSelectionHeuristic valueSelection;

    public Backtracking(VariableSelectionHeuristic variableSelection, ValueSelectionHeuristic valueSelection) {
        this.variableSelection = variableSelection;
        this.valueSelection = valueSelection;
        results = new ArrayList<>();
        iterations = 0;
    }

    public Result searchWithForwardChecking(CSP csp){
        resetResultsAndIterationsData();
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
        updateVertically(state, state[changed.getRow()]);
        updateHorizontally(state, changed);
    }

    private void updateVertically(Variable[][] state, Variable[] variables) {
        for(int column = 0; column < state.length; column++){
            variables[column].updateDomain(state);
        }
    }

    private void updateHorizontally(Variable[][] state, Variable changed) {
        for(int row = 0; row < state.length; row++){
            state[row][changed.getColumn()].updateDomain(state);
        }
    }

    public Result search(CSP csp){
        resetResultsAndIterationsData();
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

    private void resetResultsAndIterationsData() {
        results = new ArrayList<>();
        iterations = 0;
    }

    private int recursiveSearch(Variable[][] state) {
        if(isComplete(state)){
            results.add(state);
            return COMPLETED;
        }

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
        for(int row = 0; row < state.length; row++){
            for(int column = 0; column < state[row].length; column++){
                stateCopy[row][column] = new Variable(state[row][column]);
            }
        }
        stateCopy[copied.getRow()][copied.getColumn()] = copied;
        return stateCopy;
    }

}
