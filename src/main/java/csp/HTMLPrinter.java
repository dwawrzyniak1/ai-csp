package csp;

import java.io.*;
import java.util.List;

public class HTMLPrinter {

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

    public void printHtml(List<Result> results, String filename){
        StringBuilder builder = new StringBuilder(startOfFile);
        builder.append("<h1>").append(filename).append("</h1>");

        appendStatistics(results, builder);
        appendResult(results.get(0), builder);
        appendInitialState(filename, builder);

        builder.append(endOfFile);

        saveToFile(filename, builder);
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

    private void appendRowElementToHtml(StringBuilder builder, String content) {
        builder.append("<td>").append(content).append("</td>");
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

    private void saveToFile(String filename, StringBuilder builder) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename + ".html"))){

            writer.write(builder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
