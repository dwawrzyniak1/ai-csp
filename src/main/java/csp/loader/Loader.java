package csp.loader;

import csp.CSP;
import csp.Variable;

import java.io.*;

public class Loader {

    private BoardLoader boardLoader;


    public CSP load(String filename){
        setBoardLoader(filename);
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + filename)))){
            int boardSize = Integer.parseInt(reader.readLine());
            Variable[][] state = new Variable[boardSize][boardSize];

            boardLoader.readInitialState(reader, state);

            return new CSP(state);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setBoardLoader(String filename) {
        String[] splitted = filename.split("_");
        if("futo".equals(splitted[1])){
            boardLoader = new FutoshikiLoader();
        }
        else if("sky".equals(splitted[1])){
            boardLoader = new SkyscraperLoader();
        }
        else{
            System.err.println("Zła nazwa pliku");
        }
    }

}
