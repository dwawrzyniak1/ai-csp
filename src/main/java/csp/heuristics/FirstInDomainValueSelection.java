package csp.heuristics;

import csp.Variable;

public class FirstInDomainValueSelection implements ValueSelectionHeuristic {
    @Override
    public int[] domainValues(Variable variable) {
        boolean[] domain = variable.getDomain();
        int domainSize = getDomainSize(domain);
        return getDomainValues(domainSize, domain);
    }

    private int[] getDomainValues(int domainSize, boolean[] domain) {
        int[] values = new int[domainSize];
        int index = 0;
        for(int i = 0; i < domain.length; i++){
            if(domain[i]){
                values[index++] = i + 1;
            }
        }
        return values;
    }

    private int getDomainSize(boolean[] domain) {
        int domainSize = 0;
        for(boolean member : domain){
            if(member){
                domainSize++;
            }
        }
        return domainSize;
    }
}
