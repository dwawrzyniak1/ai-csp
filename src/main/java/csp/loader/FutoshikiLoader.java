package csp.loader;

import csp.Variable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.Predicate;

public class FutoshikiLoader extends BoardLoader {

    private static final int ASCII_TO_INT = 65;

    public void readInitialState(BufferedReader reader, Variable[][] state) throws IOException {
        omitTitleString(reader);
        readVariables(reader, state);
        readConstraints(reader, state);
        setDomains(state);
    }

    private String omitTitleString(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    private void readVariables(BufferedReader reader, Variable[][] state) throws IOException {
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

    private void readConstraints(BufferedReader reader, Variable[][] state) throws IOException {
        omitTitleString(reader);
        String relations;
        while ((relations = reader.readLine()) != null && !"".equals(relations.trim())){
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

    private void setDomains(Variable[][] state) {
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
