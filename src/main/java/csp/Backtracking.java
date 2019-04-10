package csp;

import csp.heuristics.ValueSelectionHeuristic;
import csp.heuristics.VariableSelectionHeuristic;
import csp.Variable;

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

        recursiveSearch(csp.getState());

        return results;
    }

    private int recursiveSearch(Variable[][] state) {

        if(isComplete(state)){
            results.add(state);
            return COMPLETED;
        }

        Variable variable = variableSelection.selectUnassigned(state);

        for(int value : valueSelection.domainValues(variable)){
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
                if (!variable.isAssigned() || !variable.isConsistent(state)){
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
