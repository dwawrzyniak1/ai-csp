package csp;

import java.util.List;

public class Result {

    private List<Variable[][]> solutions;
    private double amountOfTimeInSeconds;
    private int numberOfIterations;
    private String algorithmName;
    private String filename;

    public Result(List<Variable[][]> solutions, double amountOfTimeInSeconds, int numberOfIterations, String algorithmName, String filename) {
        this.solutions = solutions;
        this.amountOfTimeInSeconds = amountOfTimeInSeconds;
        this.numberOfIterations = numberOfIterations;
        this.algorithmName = algorithmName;
        this.filename = filename;
    }

    public List<Variable[][]> getSolutions() {
        return solutions;
    }

    public double getAmountOfTimeInSeconds() {
        return amountOfTimeInSeconds;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public String toString() {
        return "Result{" +
                "solutions=" + solutions +
                ", amountOfTimeInSeconds=" + amountOfTimeInSeconds +
                ", numberOfIterations=" + numberOfIterations +
                ", algorithmName='" + algorithmName + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }
}
