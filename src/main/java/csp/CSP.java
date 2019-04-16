package csp;

import java.util.Arrays;

public class CSP {

    private Variable[][] state;
    private String filename;

    public CSP(Variable[][] state, String filename) {
        this.state = state;
        this.filename = filename;
    }

    public Variable[][] getState() {
        return state;
    }

    public String getFilename() {
        return filename;
    }

    public int getNumberOfAssignedValues() {
        return (int)Arrays.stream(state)
                .flatMap(arr -> Arrays.stream(arr))
                .filter(var -> var.isAssigned())
                .count();
    }

    public int getBoardSize() {
        return state.length;
    }
}
