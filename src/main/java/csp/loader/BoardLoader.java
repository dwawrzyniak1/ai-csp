package csp.loader;

import csp.Variable;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class BoardLoader {

    protected static String separator = ";";

    protected boolean allDiffrent(Variable[][] state, int x, int y) {
        int value = state[x][y].getValue();

        for(int i = 0; i < state.length; i++){
            if(state[x][i].getValue() == value){
                if(i != y) return false;
            }
            if(state[i][y].getValue() == value){
                if(i != x) return false;
            }
        }

        return true;
    }

    public abstract void readInitialState(BufferedReader reader, Variable[][] state) throws IOException;

}
