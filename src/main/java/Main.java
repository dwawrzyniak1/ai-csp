import csp.Backtracking;
import csp.CSP;
import csp.heuristics.FirstInDomainValueSelection;
import csp.heuristics.FirstUnassignedVariableSelection;
import csp.loader.Loader;

public class Main {

    public static void main(String[] args) {

        Loader loader = new Loader();

        CSP csp = loader.loadFutoshiki("test_futo_4_0.txt");

        Backtracking backtracking = new Backtracking(new FirstUnassignedVariableSelection(), new FirstInDomainValueSelection());
        backtracking.search(csp);

        System.out.println("powodzonka");
    }
}
