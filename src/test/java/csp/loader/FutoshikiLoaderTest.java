package csp.loader;

import csp.Variable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

public class FutoshikiLoaderTest {

    private FutoshikiLoader futoshikiLoader = new FutoshikiLoader();
    private Variable[][] state;

    @Before
    private void setState(){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/test_futo_4_0.txt")))) {
            // GIVEN
            int boardSize = Integer.parseInt(reader.readLine());
            Variable[][] state = new Variable[boardSize][boardSize];
            futoshikiLoader.readInitialState(reader, state);
            futoshikiLoader.readConstraints(reader, state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReadProperVariableValuesFromState(){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/test_futo_4_0.txt")))) {
            // GIVEN
            int boardSize = Integer.parseInt(reader.readLine());
            Variable[][] state = new Variable[boardSize][boardSize];

            int[][] board = {
                    {0, 0, 0 ,2},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            };

            // WHEN
            futoshikiLoader.readInitialState(reader, state);

            // THEN
            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++){
                    assertEquals(board[i][j], state[i][j].getValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnFalseForNotFulfiledAllDiffrentConstraint(){
            // GIVEN

            // WHEN
            state[0][0].setValue(2);
            state[3][3].setValue(2);

            // THEN
            assertFalse(state[0][0].isConsistent(state));
            assertFalse(state[3][3].isConsistent(state));

    }

    @Test
    public void shouldReturnTrueForFulfiledAllConstraints(){
        // WHEN
        state[0][0].setValue(3);
        state[0][1].setValue(1);
        state[0][2].setValue(4);

        // THEN
        assertTrue(state[0][3].isConsistent(state));
    }

    @Test
    public void shouldReturnFalseForNotFulfiledBinaryConstraint(){
        // WHEN
        state[0][0].setValue(1);

        // THEN
        assertFalse(state[0][1].isValueConsistent(2, state));
    }



}
