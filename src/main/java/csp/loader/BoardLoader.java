package csp.loader;

import csp.Variable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public abstract class BoardLoader {

    protected static String separator = ";";

    protected boolean allDiffrent(Variable[][] state, int x, int y) {
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

    public abstract void readInitialState(BufferedReader reader, Variable[][] state) throws IOException;

}
