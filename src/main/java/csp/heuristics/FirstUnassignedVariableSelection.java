package csp.heuristics;

import csp.Variable;

public class FirstUnassignedVariableSelection implements VariableSelectionHeuristic {
    @Override
    public Variable selectUnassigned(Variable[][] state) {
        for(int i = 0; i < state.length; i++){
            for(int j = 0; j < state[i].length; j++){
                if(!state[i][j].isAssigned()){
                    return state[i][j];
                }
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "";
    }
}
