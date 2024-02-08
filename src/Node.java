import java.util.*;


/**
 * This class represents a state in the state space,
 * Each node contains:
 * Board - custom Objects that contains the Node state a.k.a GameBoard and the White Tiles
 * Parent - The Node that this Node generated from . according to TilePuzzle rules
 * The space states - Each state is array of size N*M that contains exactly one 0 (The empty tile)
 * The reset of the elements are Integers in [N*M -1] range
 *
 */
public class Node {
    private Node _parent;

    private Board _board;

    private int _costToNode = 0;


    private boolean _isOut; //indicator if IDAStar and DFBnB

    public Node() {

        this._isOut = false;
    }

    public Node(Node parent,Board board,int costToNode,boolean isOut){
        this._parent = parent;
        this._board = board;
        this._costToNode = costToNode;
        this._isOut = isOut;
    }

    public void setIsOut(boolean out){
        this._isOut = out;
    }
    public boolean getIsOut(){
        return this._isOut;
    }

    public Node getParent() {
        return _parent;
    }

    public void setParent(Node _parent) {
        this._parent = _parent;
    }

    public int[] getGameBoard() {
        return this._board.getGameBoard();
    }

    public int getCostToNode() {
        return this._costToNode;
    }

    public void setCostToNode(int cost) {
        this._costToNode = cost;
    }

    public int getHeuristic() {
        return heuristic();
    }

    public int getEstimatedCost() {
        return getCostToNode() + getHeuristic();
    }


    @Override
    public String toString() {
        return "Node{" +
                "Parent: " + (_parent != null ? _parent.getBoard().toString() : "null") +
                ", This: " + _board.toString() +
                '}';
    }

    public void setBoard(Board board) {
        this._board = board;
    }

    public Board getBoard() {
        return this._board;
    }


    /**
     * This method return the index of the empty tile ( represented by 0 )
     *
     * @return The index of the empty tile
     */
    public int getEmptyTileIndex() {
        int[] gameBoard = this.getGameBoard();
        for (int i = 0; i < gameBoard.length; i++) {
            if (gameBoard[i] == 0) {
                return i;
            }
        }
        return -1; // not find the empty tile , must be an error
    }

    /**
     * Heuristic function based on Manhattan distance
     * @return estimate path cost from this node to the goal node
     */

    private int heuristic(){
        Board currBoard = getBoard();
        int[] currGameBoard = getGameBoard();
        int currGameBoardSize = currGameBoard.length;
        int[] goalGameBaord = NodeUtils.generateGoalState(currGameBoardSize);
        int hCost = 0;
        for(int i = 0; i < currGameBoardSize;i++){// calculate the estimate cost from each tile in this gameBoard
            hCost +=calcDistance(currBoard,i,goalGameBaord); // to the according tile in the goal gameBoard
        }
        return hCost;
    }


    /**
     * This method return String represents the gameBoard , its used for unique key for the states
     * @return
     */
    public String getStateAsString() {
        return Arrays.toString(this.getGameBoard());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Node otherNode = (Node) obj;

        // two states are equals if their gameBoard are equals
        return Arrays.equals(this.getGameBoard(), otherNode.getGameBoard());
    }

    /**
     * This method return the Manhattan distance + the cost of moving the tile in the index position
     * @param currBoard - the gameBoard of this node
     * @param index - the index of the tile we estimate
     * @param goalGameBoard - goal state gameBoard
     * @return estimate cost from index to his position in goal gameBoard
     */
    private int calcDistance(Board currBoard, int index, int[] goalGameBoard) {
        int[] currGameBoard = currBoard.getGameBoard();
        int currValue = currGameBoard[index]; // get this tile value
        int goalIndex = findIndex(goalGameBoard, currValue); // what is the index of this tile on the goal gameBoard
        boolean isWhite =  currBoard.isWhiteTile(currValue); // if its white tile it cost 1 to move it
        //Manhattan distance
        int currX = (int) (index % Math.sqrt(currGameBoard.length));
        int currY = (int) (index / Math.sqrt(currGameBoard.length));
        int goalX = (int) (goalIndex % Math.sqrt(currGameBoard.length));
        int goalY = (int) (goalIndex / Math.sqrt(currGameBoard.length));

        int manhattanDistance = Math.abs(currX - goalX) + Math.abs(currY - goalY);

        // add the correct cost of this tile to move
        if (currValue != 0) { // Not an empty tile
            int tileCost = isWhite ? 1 : 30;
            manhattanDistance *= tileCost;
        }

        return manhattanDistance;
    }

    /**
     *  This method return the index of the given tile value
     * @param array
     * @param value
     * @return the index of the given tile value
     */
    private static int findIndex(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;  // value not found
    }

}
