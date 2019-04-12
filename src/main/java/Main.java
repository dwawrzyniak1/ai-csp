import csp.Backtracking;
import csp.CSP;
import csp.heuristics.LCVValueSelection;
import csp.heuristics.MRVVariableSelection;
import csp.loader.Loader;

public class Main {

    public static void main(String[] args) {

        Loader loader = new Loader();

        CSP csp = loader.load("test_sky_4_0.txt");

        Backtracking backtracking = new Backtracking(new MRVVariableSelection(), new LCVValueSelection());
        System.out.println(backtracking.searchWithForwardChecking(csp));

    }
}
