package csp;

import java.util.Arrays;

public class CSP {

    private Variable[][] state;

    public CSP(Variable[][] state) {
        this.state = state;
    }

    public Variable[][] getState() {
        return state;
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
