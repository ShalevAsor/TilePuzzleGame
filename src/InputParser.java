import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * This class parsing the input from input.txt and constructing TilePuzzle instance according to the given input
 */

public class InputParser {

    public static TilePuzzle parseInput(File inputFile) {
        /* Init tilePuzzle instance and board  */
        TilePuzzle tilePuzzle = new TilePuzzle();
        Board board = new Board();
        boolean time;
        boolean openList;

        try {
            Scanner scanner = new Scanner(inputFile);

            // Read the algorithm type
            String algorithmType = scanner.nextLine().trim();
            // Read whether to print with time or not
            String timeLine = scanner.nextLine().trim();
            time = timeLine.equals("with time");//time = true iff timeLine is "with time"

            // Read Open list line
            String openListLine = scanner.nextLine().trim();
            openList = openListLine.equals("with open");//openList = true iff openListLine is "with open"
            // Read the size of the game board
            String sizeLine = scanner.nextLine().trim();
            String[] sizeTokens = sizeLine.split("x");
            int rows = Integer.parseInt(sizeTokens[0]);
            int columns = Integer.parseInt(sizeTokens[1]);
            board.setRows(rows);
            board.setColumns(columns);

            // Read the white tuples data
            Map<Integer, int[]> whiteTiles = parseWhiteTuples(scanner.nextLine().trim());
            board.setWhiteTiles(whiteTiles);
            // Read the game board
            int[] gameBoard = parseGameBoard(scanner, rows, columns);
            board.setGameBoard(gameBoard);

            scanner.close();
            /*  set the start node and tile puzzle according to the given input  */
            Node start = new Node();
            start.setBoard(board);
            tilePuzzle.setStart(start);
            tilePuzzle.setTime(time);
            tilePuzzle.setOpenList(openList);
            tilePuzzle.setAlgo(algorithmType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tilePuzzle;
    }

    private static Map<Integer, int[]> parseWhiteTuples(String line) {
        /* The key is the tile Number , the value is tuple [tileNumber,Moves] */
        Map<Integer, int[]> whiteTiles = new HashMap<>();
        if (line.startsWith("White:")) {
            // split the line
            String tuplesLine = line.substring("White:".length()).trim();
            if(tuplesLine.isEmpty())return whiteTiles;
            String[] tuples = tuplesLine.split("\\),");
            for (String tuple : tuples) {
                String cleanedTuple = tuple.replaceAll("[()]", "").trim();
                String[] tupleValues = cleanedTuple.split(",");
                //its a valid tuple
                if (tupleValues.length == 2) {
                    try {
                        int tileNumber = Integer.parseInt(tupleValues[0]);
                        int[] tupleValue = { Integer.parseInt(tupleValues[0]), Integer.parseInt(tupleValues[1]) };
                        whiteTiles.put(tileNumber, tupleValue);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Error parsing white tuple: " + tuple);
                }
            }
        }
        return whiteTiles;
    }

    private static int[] parseGameBoard(Scanner scanner, int rows, int columns) {
        int[] gameBoard = new int[rows * columns];
        // insert the tiles numbers according to input
        for (int i = 0; i < rows; i++) {
            //Read tiles line
            String TilesLine = scanner.nextLine().trim();
            //split the tiles
            String[] tilesNumbers = TilesLine.split(",");
            for (int j = 0; j < columns; j++) {
                //insert to the gameBoard
                String token = tilesNumbers[j];
                if (token.equals("_")) { // the empty tile
                    gameBoard[i * columns + j] = 0;
                } else {
                    gameBoard[i * columns + j] = Integer.parseInt(token);
                }
            }
        }
        return gameBoard;
    }


}
