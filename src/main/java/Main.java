import csp.Backtracking;
import csp.CSP;
import csp.heuristics.LCVValueSelection;
import csp.heuristics.MRVVariableSelection;
import csp.loader.Loader;

public class Main {

    public static void main(String[] args) {

        Loader loader = new Loader();

        CSP csp = loader.loadFutoshiki("test_futo_9_0.txt");

        Backtracking backtracking = new Backtracking(new MRVVariableSelection(), new LCVValueSelection());
        backtracking.searchWithForwardChecking(csp);

    }
}
