package csp.heuristics;

import csp.Variable;

public class MRVVariableSelection implements VariableSelectionHeuristic {
    @Override
    public Variable selectUnassigned(Variable[][] state) {
        int minimumDomainSize = state[0][0].getDomain().length + 1;
        Variable choosen = null;
        for(int i = 0; i < state.length; i++){
            for(int j = 0; j < state[i].length; j++){
                int domainSize = state[i][j].getDomainSize();
                if(!state[i][j].isConstant() && !state[i][j].isAssigned() && domainSize < minimumDomainSize){
                    choosen = state[i][j];
                    minimumDomainSize = domainSize;
                }
            }
        }
        return choosen;
    }
}
