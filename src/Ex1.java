
import java.io.File;


/**
 * Main class . reading the input and parse it. after the puzzle is set, solve the game.
 */

public class Ex1 {
    public static void main(String[] args) {

        File inputFile = new File("input.txt");
        TilePuzzle tilePuzzle = InputParser.parseInput(inputFile);
        tilePuzzle.solve();

    }

}