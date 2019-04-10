package csp.heuristics;

import csp.Variable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class LCVValueSelection implements ValueSelectionHeuristic {
    @Override
    public int[] domainValues(Variable variable, Variable[][] state) {
        int[] values = variable.getDomainValues();
        int[] numberOfDomainsChanges = new int[values.length];
        Variable copy = new Variable(variable);

        for(int i = 0; i < values.length; i++){
            copy.setValue(values[i]);
            Variable[][] stateCopy = copyStateWithNewVariable(state, copy);
            numberOfDomainsChanges[i] = calculateDomainConstraintingRatio(variable, stateCopy);
        }

        return valuesArraySortedByConstrainingRatio(values, numberOfDomainsChanges);
    }

    private int[] valuesArraySortedByConstrainingRatio(int[] values, int[] numberOfDomainsChanges) {
        Integer[] boxed = IntStream.of(values).boxed().toArray(Integer[]::new);
        Comparator<Integer> compareByDomainConstraintingRatio = Comparator.comparing(value -> numberOfDomainsChanges[indexOf(value, values)]);
        Arrays.sort(boxed, compareByDomainConstraintingRatio);
        return Arrays.stream(boxed).mapToInt(Integer::intValue).toArray();
    }

    private int indexOf(int value, int[] array){
        for(int i = 0; i < array.length; i++){
            if(array[i] == value){
                return i;
            }
        }
        return -1;
    }

    private int calculateDomainConstraintingRatio(Variable variable, Variable[][] state) {
        int domainConstraintingRatio = 0;
        for(int i = 0; i < state.length; i++){
            domainConstraintingRatio += state[variable.getRow()][i].calculateDomainChanges(state);
        }
        for(int i = 0; i < state.length; i++){
            domainConstraintingRatio += state[i][variable.getColumn()].calculateDomainChanges(state);
        }
        return domainConstraintingRatio;
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
