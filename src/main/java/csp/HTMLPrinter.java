package csp;

import java.io.*;
import java.util.List;

public class HTMLPrinter {

    private static final String FC = "FC  ";
    private static final int FC_INDEX_0 = 6;
    private static final int FC_INDEX_1 = 7;

    private static final String BT = "BT  ";
    private static final int BT_INDEX_0 = 2;
    private static final int BT_INDEX_1 = 3;

    private static final String BT_HEUR = "BT MRV LCV";
    private static final int BT_HEUR_INDEX_0 = 4;
    private static final int BT_HEUR_INDEX_1 = 5;

    private static final String FC_HEUR = "FC MRV LCV";
    private static final int FC_HEUR_INDEX_0 = 8;
    private static final int FC_HEUR_INDEX_1 = 9;


    private static String startOfFile = "<!doctype html>\n" +
            "\n" +
            "        <html lang=\"en\">\n" +
            "        <head>\n" +
            "          <meta charset=\"utf-8\">\n" +
            "        \n" +
            "            <title>Rozwiazania CSP</title>\n" +
            "          <meta name=\"description\" content=\"The HTML5 Herald\">\n" +
            "          <meta name=\"author\" content=\"SitePoint\">\n" +
            "<!-- Latest compiled and minified CSS -->\n" +
            "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css\">\n" +
            "\n" +
            "<!-- jQuery library -->\n" +
            "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
            "\n" +
            "<!-- Latest compiled JavaScript -->\n" +
            "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js\"></script>" +
            "        \n" +
            "          <link rel=\"stylesheet\" href=\"css/styles.css\">\n" +
            "        \n" +
            "        </head>\n" +
            "        \n" +
            "        <body style=\"width: 600px; margin: auto;\">";

    private static String endOfFile = " <script src=\"js/scripts.js\"></script>\n" +
            "        </body>\n" +
            "    </html>";
    private static String[] headers = {
            "algorytm",
            "czas wykonania",
            "liczba iteracji"
    };

    public void printHtml(List<Result> results, String file){
        StringBuilder builder = new StringBuilder(startOfFile);
        builder.append("<h1>").append(file).append("</h1>");
        appendStatistics(results, builder);
        appendResult(results.get(0), builder);
        appendInitialState(file, builder);

        builder.append(endOfFile);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file + ".html"))){

            writer.write(builder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendInitialState(String file, StringBuilder builder) {
        builder.append("<h3>").append("Stan początkowy: ").append("</h3>");
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + file)))) {

            String line;
            while((line = reader.readLine()) != null){
                builder.append(line).append("<br>");
            }

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void appendResult(Result result, StringBuilder builder) {
        builder.append("<h2>").append("Rozwiazania: ").append("</h2>");
        for(Variable[][] solution : result.getSolutions()){
            builder.append("<table class=\"table\">");

            for(Variable[] row : solution){
                builder.append("<tr>");
                for(Variable column : row){
                    builder.append("<td>").append(column.getValue()).append("</td>");
                }
                builder.append("</tr>");
            }

            builder.append("</table>");
        }
    }

    private void appendStatistics(List<Result> results, StringBuilder builder) {
        builder.append("<table class=\"table\">");
        appendHeaders(builder);
        for(Result result : results){
            builder.append("<tr>");
            builder.append("<th scope=\"row\">").append(result.getAlgorithmName()).append("</th>");
            appendRowElementToHtml(builder, String.valueOf(result.getAmountOfTimeInSeconds()));
            appendRowElementToHtml(builder, String.valueOf(result.getNumberOfIterations()));
            builder.append("</tr>");
        }
        builder.append("</table>");
    }

    private void appendHeaders(StringBuilder builder) {
        builder.append("<tr>");
        for(String header : headers){
            builder.append("<th>").append(header).append("</th>");
        }
        builder.append("</tr>");
    }

    private void appendResultHTML(StringBuilder builder, String filename) {
        List<Result> results = ResultFactory.getResults(filename);
        String[] display = new String[3]; // nazwa pliku, rozwiazanie plus sekundy i iteracje dla kazdej metody
        for(Result result : results){
            appendRowElement(result, display);
        }

        builder.append("\t<tr>\n");
        for(String rowElement : display){
            appendRowElementToHtml(builder, rowElement);
        }
        builder.append("\t</tr>\n");
    }

    private void appendRowElement(Result result, String[] display) {
        switch (result.getAlgorithmName()){
            case BT:
                display[BT_INDEX_0] = String.valueOf(result.getAmountOfTimeInSeconds());
                display[BT_INDEX_1] = String.valueOf(result.getNumberOfIterations());
                return;
            case BT_HEUR:
                display[BT_HEUR_INDEX_0] = String.valueOf(result.getAmountOfTimeInSeconds());
                display[BT_HEUR_INDEX_1] = String.valueOf(result.getNumberOfIterations());
                return;
            case FC:
                display[FC_INDEX_0] = String.valueOf(result.getAmountOfTimeInSeconds());
                display[FC_INDEX_1] = String.valueOf(result.getNumberOfIterations());
                return;
            case FC_HEUR:
                display[FC_HEUR_INDEX_0] = String.valueOf(result.getAmountOfTimeInSeconds());
                display[FC_HEUR_INDEX_1] = String.valueOf(result.getNumberOfIterations());
                return;
            default:
                System.err.println("Blad dopasowania rozwiazania");
                return;
        }
    }

    private void appendSolutionToHTMLTableRow(StringBuilder builder, List<Variable[][]> solutions) {
        builder.append("\t<td>\n");

        builder.append("<span>").append(solutions.size()).append("</span>");

        builder.append("\t</td>\n");
    }

    private void appendRowElementToHtml(StringBuilder builder, String content) {
        builder.append("<td>").append(content).append("</td>");
    }


}
