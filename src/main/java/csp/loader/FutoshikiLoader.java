package csp.loader;

import csp.Variable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Predicate;

public class FutoshikiLoader {

    private static final int ASCII_TO_INT = 65;
    private static String separator = ";";

    public void readInitialState(BufferedReader reader, Variable[][] state) throws IOException {
        reader.readLine(); // OMIT TITLE STRING
        for(int i = 0; i < state.length; i++){
            String values = reader.readLine();
            String[] splitedValues = values.split(separator);
            for(int j = 0; j < state[i].length; j++){
                state[i][j] = new Variable(Integer.parseInt(splitedValues[j]));
                final int xCoord = i;
                final int yCoord = j;
                state[i][j].addConstraint((board) -> allDiffrent(board, xCoord, yCoord));
                state[i][j].setCoordinates(i, j);
            }
        }
    }

    public boolean allDiffrent(Variable[][] state, int x, int y) {
        boolean[] wasPresent = new boolean[state.length];
        for(int i = 0; i < state.length; i++){
            int value = state[i][y].getValue();
            if(value != 0){
                if(!wasPresent[value - 1])
                    wasPresent[value - 1] = true;
                else{
                    return false;
                }
            }
        }
        long distinct = Arrays.stream(state[x]).filter(var -> var.getValue() != 0).distinct().count();
        long assigned = Arrays.stream(state[x]).filter(var -> var.getValue() != 0).count();
        return distinct == assigned;
    }

    public void readConstraints(BufferedReader reader, Variable[][] state) throws IOException {
        reader.readLine(); // OMIT TITLE STRING
        String relations;
        while ((relations = reader.readLine()) != null){
            int[] indexes = parseRelationsToIndexes(relations);
            Predicate<Variable[][]> constraint = stateMatrix ->
                    oneOfVariablesIsUnassigned(indexes, stateMatrix) || variablesFulfilConstraints(indexes, stateMatrix);
            state[indexes[0]][indexes[1]].addConstraint(constraint);
            state[indexes[2]][indexes[3]].addConstraint(constraint);
        }
    }

    private boolean variablesFulfilConstraints(int[] indexes, Variable[][] stateMatrix) {
        return stateMatrix[indexes[0]][indexes[1]].getValue() < stateMatrix[indexes[2]][indexes[3]].getValue();
    }

    private boolean oneOfVariablesIsUnassigned(int[] indexes, Variable[][] stateMatrix) {
        return stateMatrix[indexes[0]][indexes[1]].getValue() == 0 || stateMatrix[indexes[2]][indexes[3]].getValue() == 0;
    }

    private int[] parseRelationsToIndexes(String relations) {
        String[] splittedRelations = relations.split(separator);
        char charRepresentationOfIndex = splittedRelations[0].charAt(0);
        int firstX = charRepresentationOfIndex - ASCII_TO_INT;
        int firstY = Character.getNumericValue(splittedRelations[0].charAt(1)) - 1;
        charRepresentationOfIndex = splittedRelations[1].charAt(0);
        int secondX = charRepresentationOfIndex - ASCII_TO_INT;
        int secondY = Character.getNumericValue(splittedRelations[1].charAt(1)) - 1;
        return new int[]{firstX, firstY, secondX, secondY};
    }

    public void setDomains(Variable[][] state) {
        for(int i = 0; i < state.length; i++){
            for(int j = 0; j < state[i].length; j++){

                state[i][j].setDomain(new boolean[state.length]);

                if(state[i][j].getValue() != 0){
                    setDomainForInitialySettledVariable(state, i, j);
                }else{
                    setDomainsForEmptyVariable(state, i, j);
                }

            }
        }
    }

    private void setDomainForInitialySettledVariable(Variable[][] state, int i, int j) {
        boolean[] domain = new boolean[state.length];
        for(int valueIndex = 0; valueIndex < state.length; valueIndex++){
            domain[valueIndex] = false;
        }
        state[i][j].setDomain(domain);
    }

    private void setDomainsForEmptyVariable(Variable[][] state, int i, int j) {
        Variable currentVariable = state[i][j];
        int initialValue = currentVariable.getValue();
        for(int value = 1; value <= state.length; value++){
            currentVariable.setValue(value);
            if(checkAllConstraints(currentVariable, state)){
                currentVariable.changeDomainAtPosition(value - 1, true);
            }
        }
        currentVariable.setValue(initialValue);
    }

    private boolean checkAllConstraints(Variable variable, Variable[][] state) {
        // Jeżeli wszystkie ograniczenia binarne zostały spełnione oraz pola w kolumnie i rzędzie są różne
        return variable.getConstraints().stream().allMatch(con -> con.test(state));
    }
}
