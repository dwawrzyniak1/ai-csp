package csp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ResultFactory {

    private static String[] files = {
//            "test_futo_4_0.txt", "test_futo_4_1.txt", "test_futo_4_2.txt",
//            "test_futo_5_0.txt", "test_futo_5_1.txt", "test_futo_5_2.txt",
//            "test_futo_6_0.txt", "test_futo_6_1.txt", "test_futo_6_2.txt",
//            "test_futo_7_0.txt", "test_futo_7_1.txt", "test_futo_7_2.txt",
//            "test_futo_8_0.txt", "test_futo_8_1.txt", "test_futo_8_2.txt",
//            "test_futo_9_0.txt", "test_futo_9_1.txt", "test_futo_9_2.txt",

            "test_sky_6_0.txt"
    };

    private static Map<String, List<Result>> filenameResultsMapping = new HashMap<>();

    static {
        for(String file : files){
            filenameResultsMapping.put(file, new ArrayList<>());
        }
    }

    public static void addResult(Result result){
        filenameResultsMapping.get(result.getFilename()).add(result);
    }

    public static List<Result> getResults(String filename){
        return filenameResultsMapping.get(filename);
    }

}
