package csp.loader;

import csp.Variable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Predicate;

public class SkyscraperLoader extends BoardLoader{

    private static final int UNASSIGNED = 0;

    public void readInitialState(BufferedReader reader, Variable[][] state) throws IOException {
        initSkyscrapper(state);
        setConstraints(reader, state);
        setCoordinates(state);
        setAllDifferentConstraints(state);
    }

    private void setConstraints(BufferedReader reader, Variable[][] state) throws IOException {
        String constraintsRepresentation;
        while((constraintsRepresentation = reader.readLine()) != null){
            String[] splitted = constraintsRepresentation.split(separator);
            String direction = splitted[0];
            String[] constraints = Arrays.copyOfRange(splitted, 1, splitted.length);
            setDirectionalConstraints(state, direction, constraints);
        }
    }

    private void setCoordinates(Variable[][] state) {
        for(int i = 0; i < state.length; i++){
            for(int j = 0; j < state[i].length; j++){
                state[i][j].setCoordinates(i, j);
            }
        }
    }

    private void setAllDifferentConstraints(Variable[][] state) {
        for(int i = 0; i < state.length; i++){
            for(int j = 0; j < state[i].length; j++){
                final int xCoord = i;
                final int yCoord = j;
                state[i][j].addConstraint((board) -> allDiffrent(board, xCoord, yCoord));
            }
        }
    }

    private void setDirectionalConstraints(Variable[][] state, String direction, String[] constraints) {
        switch (direction){
            case "L":
                for(int row = 0; row < state.length; row++){
                    int constraintValue = Integer.parseInt(constraints[row]);
                    for(int column = 0; column < state[row].length; column++){
                        int finalRow = row;
                        if(constraintValue != 0){
                            Predicate<Variable[][]> constraint = (stateMatrix) ->
                                    notAllAssignedHorizontally(stateMatrix, finalRow) || skyscraperLeftConstraint(stateMatrix, finalRow, constraintValue);
                            state[row][column].addConstraint(constraint);
                        }
                    }
                }
                return;
            case "P":
                for(int row = 0; row < state.length; row++){
                    int constraintValue = Integer.parseInt(constraints[row]);
                    for(int column = 0; column < state[row].length; column++){
                        int finalRow = row;
                        if(constraintValue != 0){
                            Predicate<Variable[][]> constraint = (stateMatrix) ->
                                    notAllAssignedHorizontally(stateMatrix, finalRow) || skyscraperRightConstraint(stateMatrix, finalRow, constraintValue);
                            state[row][column].addConstraint(constraint);
                        }
                    }
                }
                return;
            case "G":
                for(int row = 0; row < state.length; row++){
                    for(int column = 0; column < state[row].length; column++){
                        int finalColumn = column;
                        int constraintValue = Integer.parseInt(constraints[column]);
                        if(constraintValue != 0){
                            Predicate<Variable[][]> constraint = (stateMatrix) ->
                                    notAllAssignedVertically(stateMatrix, finalColumn) || skyscraperUpperConstraint(stateMatrix, finalColumn, constraintValue);
                            state[row][column].addConstraint(constraint);
                        }
                    }
                }
                return;
            case "D":
                for(int row = 0; row < state.length; row++){
                    for(int column = 0; column < state[row].length; column++){
                        int finalColumn = column;
                        int constraintValue = Integer.parseInt(constraints[column]);
                        if(constraintValue != 0){
                            Predicate<Variable[][]> constraint = (stateMatrix) ->
                                    notAllAssignedVertically(stateMatrix, finalColumn) || skyscraperLowerConstraint(stateMatrix, finalColumn, constraintValue);
                            state[row][column].addConstraint(constraint);
                        }
                    }
                }
                return;
            default:
                return;
        }
    }

    private boolean notAllAssignedHorizontally(Variable[][] stateMatrix, int row){
        for(int i = 0; i < stateMatrix[row].length; i++){
            if(stateMatrix[row][i].getValue() == 0){
                return true;
            }
        }
        return false;
    }

    private boolean notAllAssignedVertically(Variable[][] stateMatrix, int column){
        for(int i = 0; i < stateMatrix.length; i++){
            if(stateMatrix[i][column].getValue() == 0){
                return true;
            }
        }
        return false;
    }

    private boolean skyscraperLowerConstraint(Variable[][] stateMatrix, int column, int constraintValue) {
        int counter = 0;
        int currMax = 0;
        for(int i = stateMatrix.length - 1; i >= 0; --i){
            int currentValue = stateMatrix[i][column].getValue();
            if(currMax < currentValue){
                counter++;
                currMax = currentValue;
            }
        }
        return counter == constraintValue;
    }

    private boolean skyscraperUpperConstraint(Variable[][] stateMatrix, int column, int constraintValue) {
        int counter = 0;
        int currMax = 0;
        for(int i = 0; i < stateMatrix.length; i++){
            int currentValue = stateMatrix[i][column].getValue();
            if(currMax < currentValue){
                counter++;
                currMax = currentValue;
            }
        }
        return counter == constraintValue;
    }

    private boolean skyscraperRightConstraint(Variable[][] stateMatrix, int row, int constraintValue) {
        int counter = 0;
        int currMax = 0;
        for(int i = stateMatrix.length - 1; i >= 0; --i){
            int currentValue = stateMatrix[row][i].getValue();
            if(currMax < currentValue){
                counter++;
                currMax = currentValue;
            }
        }
        return counter == constraintValue;
    }

    private boolean skyscraperLeftConstraint(Variable[][] stateMatrix, int row, int constraintValue) {
        int counter = 0;
        int currMax = 0;
        for(int i = 0; i < stateMatrix.length; i++){
            int currentValue = stateMatrix[row][i].getValue();
            if(currMax < currentValue){
                counter++;
                currMax = currentValue;
            }
        }
        return counter == constraintValue;
    }

    private void initSkyscrapper(Variable[][] state) {
        for(int i = 0; i < state.length; i++){
            for(int j = 0; j < state[i].length; j++){
                state[i][j] = new Variable(UNASSIGNED, arrayOfTrueBooleans(state.length));
                state[i][j].setCoordinates(i, j);
            }
        }
    }

    private boolean[] arrayOfTrueBooleans(int length) {
        boolean[] array = new boolean[length];
        for(int i = 0; i < array.length; i++){
            array[i] = true;
        }
        return array;
    }


}
