import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the Board of the game,
 * each board contains the White Tiles - this tiles has bounded amount of moves,
 * gameBoard - array of integers of size N*M with exactly one 0 , and N*M-1 positive integers
 * from the range [N*M -1]
 */
public class Board {
    private int _rows;
    private int _columns;
    private Map<Integer, int[]> _whiteTiles;
    private int[] _gameBoard;

    public Board() {
    }

    /**
     * Copy Constructor
     * @param b - Board to copy from
     */
    public Board(Board b) {

        this._rows = b.getRows();
        this._columns = b.getColumns();
        this._whiteTiles = new HashMap<>();
        NodeUtils.deepCopyWhiteTiles(this._whiteTiles,b.getWhiteTiles());
        this._gameBoard = new int[b.getGameBoard().length];
        System.arraycopy(b.getGameBoard(), 0, this._gameBoard, 0, b.getGameBoard().length);
    }
    /* Setters and Getters  */

    public int getRows() {
        return this._rows;
    }

    public void setRows(int rows) {
        this._rows = rows;
    }

    public int getColumns() {
        return this._columns;
    }

    public void setColumns(int columns) {
        this._columns = columns;
    }

    public Map<Integer, int[]> getWhiteTiles() {
        return _whiteTiles;
    }

    public void setWhiteTiles(Map<Integer, int[]> whiteTiles) {
        this._whiteTiles = whiteTiles;
    }

    public int[] getGameBoard() {
        return _gameBoard;
    }

    public void setGameBoard(int[] gameBoard) {
        this._gameBoard = gameBoard;
    }


    /**
     * This method return True iff the give tile Number represents white tile
     * @param tileNumber - the value of the Tile (Not index!)
     * @return True iff the give tile Number represents white tile
     */
    public boolean isWhiteTile(int tileNumber) {
        return _whiteTiles.containsKey(tileNumber);
    }

    /**
     * This method return the Value (Number) of a tile on the Board
     * @param index - the index of the tile on the gameBoard
     * @return the tile value
     */
    public int getTileValue(int index) {
        return _gameBoard[index];
    }

    /**
     * This method return the #moves that this tiles has left
     * @param tileValue - the tile Number
     * @return the #moves that this tile left , if this tile is not white , return null
     */
    public int getTileMoves(int tileValue){
        return _whiteTiles.get(tileValue)[1];
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  GameBoard=").append(Arrays.toString(_gameBoard));
        if (!_whiteTiles.isEmpty()) {
            sb.append(", WhiteTiles= ");

            _whiteTiles.values().forEach(value -> {
                sb.append(Arrays.toString(value)).append(", ");
            });
            sb.setLength(sb.length() - 2); // remove last ','
        }
        return sb.toString();
    }


}
