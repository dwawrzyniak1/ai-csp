package csp.heuristics;

import csp.Variable;

public interface VariableSelectionHeuristic {

    Variable selectUnassigned(Variable[][] state);

    String getName();
}
