import csp.Backtracking;
import csp.CSP;
import csp.HTMLPrinter;
import csp.Result;
import csp.heuristics.FirstInDomainValueSelection;
import csp.heuristics.FirstUnassignedVariableSelection;
import csp.heuristics.LCVValueSelection;
import csp.heuristics.MRVVariableSelection;
import csp.loader.Loader;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Loader loader = new Loader();
        Backtracking withHeuristics = new Backtracking(new MRVVariableSelection(), new LCVValueSelection());
        Backtracking withoutHeuristics = new Backtracking(new FirstUnassignedVariableSelection(), new FirstInDomainValueSelection());

        String filename = "test_futo_8_0.txt";
        CSP board = loader.load(filename);
        List<Result> results = new ArrayList<>();

        results.add(withoutHeuristics.search(board));
        results.add(withoutHeuristics.searchWithForwardChecking(board));
        results.add(withHeuristics.searchWithForwardChecking(board));
        results.add(withHeuristics.search(board));

        new HTMLPrinter().printHtml(results, filename);
    }
}
