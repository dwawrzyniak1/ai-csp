package csp.loader;

import csp.CSP;
import csp.Variable;

import java.io.*;

public class Loader {

    private static final int GAME_NAME_INDEX = 1;

    private BoardLoader boardLoader;

    public CSP load(String filename){
        setBoardLoader(filename);
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + filename)))){
            int boardSize = Integer.parseInt(reader.readLine());
            Variable[][] state = new Variable[boardSize][boardSize];

            boardLoader.readInitialState(reader, state);

            return new CSP(state, filename);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setBoardLoader(String filename) {
        String[] splitted = filename.split("_");
        if("futo".equals(splitted[GAME_NAME_INDEX])){
            boardLoader = new FutoshikiLoader();
        }
        else if("sky".equals(splitted[GAME_NAME_INDEX])){
            boardLoader = new SkyscraperLoader();
        }
        else{
            System.err.println("Zła nazwa pliku");
        }
    }

}
