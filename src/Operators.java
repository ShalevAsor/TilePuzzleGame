import java.util.*;


/**
 * This class generating child nodes from a given parent node according to this tile-puzzle variant rules
 *  there are at most 4 possible moves
 */
public class Operators {

    static int generatedChilds = 0;


    /**
     * Generates a list of valid child nodes based on the possible moves from the given parent node.
     *
     * @param n - The parent node.
     * @return  list of valid child nodes.
     */
    public static List<Node> allowedOperators(Node n) {
        List<Node> operators = new ArrayList<>();
        //get the index of the empty tile for this current Node n
        int emptyTileIndex = n.getEmptyTileIndex();
        //get all the indexes around the empty tile by this order of moves-> left , up , right , down (at most 4 )
        List<Integer> aroundEmptyTile = getAroundEmptyTile(emptyTileIndex, n.getGameBoard().length, n.getBoard().getRows(), n.getBoard().getColumns());

        for (int tileIndex : aroundEmptyTile) { //for each possible move that represents as tile index
            int tileNumber = n.getBoard().getTileValue(tileIndex); // get this tile number
            // if this tile is white and still have moves left
            if (isMovesLeft(n.getBoard(), emptyTileIndex, tileIndex, tileNumber)) {
                // generate this child and add it to the valid child list
                Node child = generateChild(n, emptyTileIndex, tileIndex, tileNumber);
                if(child != null){
                    operators.add(child);
                }
            }
        }
        return operators;
    }

    /**
     * This method return true iff this tile has moves left
     * @param board - the current board
     * @param emptyTileIndex - the empty tile index
     * @param tileIndex - this tile index
     * @param tileNumber - this tile value
     * @return true iff this tile has moves left
     */
    private static boolean isMovesLeft(Board board, int emptyTileIndex, int tileIndex, int tileNumber) {
        if (board.isWhiteTile(tileNumber)) { // if this tile is white and it has moves left return true
            int moves = board.getTileMoves(tileNumber);
            return moves > 0;
        }
        return true;
    }

    /**
     * This method generate child for the parent node
     * @param parent - the parent of the generated node
     * @param emptyTileIndex - the empty tile index
     * @param tileIndex - this tile index
     * @param tileNumber - this tile value
     * @return Node child
     */
    private static Node generateChild(Node parent, int emptyTileIndex, int tileIndex, int tileNumber) {
        Board parentBoard = parent.getBoard();
        int moveCost = 30;

        // deep copy white tiles
        Map<Integer, int[]> whiteTiles = new HashMap<>();
        NodeUtils.deepCopyWhiteTiles(whiteTiles, parentBoard.getWhiteTiles());

        // deep copy game board
        int[] gameBoard = Arrays.copyOf(parentBoard.getGameBoard(), parentBoard.getGameBoard().length);
        gameBoard[emptyTileIndex] = (parentBoard.isWhiteTile(tileNumber)) ? whiteTiles.get(tileNumber)[0] : tileNumber;
        gameBoard[tileIndex] = 0;

        // if this tile is white update it moves on the new board

        if (parentBoard.isWhiteTile(tileNumber)) {
            whiteTiles.get(tileNumber)[1]--;
            moveCost = 1;

        }
        if (parent.getParent() != null) {//verify its not the oppose move
            if (Arrays.equals(gameBoard, parent.getParent().getGameBoard())) {
                return null; // if this node parent gameBoard equals to this child , its oppose move (for examle L->R)
            }
        }
            // Create a new node
            Board newBoard = new Board();
            newBoard.setGameBoard(gameBoard);
            newBoard.setWhiteTiles(whiteTiles);
            newBoard.setColumns(parentBoard.getColumns());
            newBoard.setRows(parentBoard.getRows());

            Node child = new Node(parent,newBoard,moveCost+parent.getCostToNode(),false);
            generatedChilds++;
            return child;
        }


    /**
     * This method return the indexes around the empty tile ->  left , up , right , down (at most 4 )
     * @param emptyTileIndex - index of the empty tile
     * @param size - size of the gameBoard
     * @return list of indexes of the tiles around the empty tile
     */

    private static List<Integer> getAroundEmptyTile(int emptyTileIndex, int size,int rows,int columns) {
        List<Integer> result = new ArrayList<>();

        // Move left - the index that right of the empty tile is in the bounds
        //  the row and column of the empty tile
        int emptyTileRow = emptyTileIndex / columns;
        int emptyTileCol = emptyTileIndex % columns;

        // the row and column of the neighbor to the right
        int rightNeighborRow = (emptyTileIndex + 1) / columns;
        boolean canMoveLeft = (rightNeighborRow == emptyTileRow )&&(emptyTileIndex+1 < size);
        if (canMoveLeft) {
            result.add(emptyTileIndex + 1);
        }



        // Move up  -if the index that under the empty tile is in the bounds
        boolean canMoveUp = (emptyTileIndex + columns < size);

        if (canMoveUp) {
            result.add(emptyTileIndex + columns);
        }

        // Move right
        int leftNeighborRow = (emptyTileIndex - 1) / columns;
        boolean canMoveRight = (leftNeighborRow == emptyTileRow )&&(emptyTileIndex-1 >= 0);
        if (canMoveRight) {
            result.add(emptyTileIndex - 1);
        }

        // Move down
        boolean canMoveDown = (emptyTileIndex - columns >= 0);
        if (canMoveDown) {
            result.add(emptyTileIndex - columns);
        }

        return result;
    }

    public static void resetCounter(){
        generatedChilds = 0;
    }
}


