package csp.heuristics;

import csp.Variable;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class FirstInDomainValueSelectionTest {

    private FirstInDomainValueSelection selection = new FirstInDomainValueSelection();

    @Test
    public void shouldReturnTrueForVariableWith4sizedDomain(){
        Variable variable = new Variable(1, new boolean[]{true, true, true, true});

        int[] domain = selection.domainValues(variable);

        assertArrayEquals(new int[]{1, 2, 3, 4}, domain);
    }

    @Test
    public void shouldReturnTrueForVariableWith0sizedDomain(){
        Variable variable = new Variable(1, new boolean[]{false, false, false, false});

        int[] domain = selection.domainValues(variable);

        assertArrayEquals(new int[]{}, domain);
    }

}
