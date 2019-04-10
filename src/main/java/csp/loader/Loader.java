package csp.loader;

import csp.CSP;
import csp.Variable;

import java.io.*;

public class Loader {

    private static String separator = ";";


    public CSP loadFutoshiki(String filename){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + filename)))){

            FutoshikiLoader futoshikiLoader = new FutoshikiLoader();
            int boardSize = Integer.parseInt(reader.readLine());
            Variable[][] state = new Variable[boardSize][boardSize];

            futoshikiLoader.readInitialState(reader, state);
            futoshikiLoader.readConstraints(reader, state);
            futoshikiLoader.setDomains(state);

            return new CSP(state);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
