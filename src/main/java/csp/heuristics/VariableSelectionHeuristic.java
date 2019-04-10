package csp.heuristics;

import csp.Variable;

public interface VariableSelectionHeuristic {

    // pseudokod: select-unassigned-variable(variables, csp, assignement) <- tutaj assignment bedzie wyciagane z variables
    Variable selectUnassigned(Variable[][] state);

}
