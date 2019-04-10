package csp.heuristics;

import csp.Variable;

public class FirstInDomainValueSelection implements ValueSelectionHeuristic {
    @Override
    public int[] domainValues(Variable variable, Variable[][] state) {
        return variable.getDomainValues();
    }
}
