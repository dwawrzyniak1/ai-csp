package csp.heuristics;

import csp.Variable;

public interface ValueSelectionHeuristic {

    int[] domainValues(Variable variable);
}
