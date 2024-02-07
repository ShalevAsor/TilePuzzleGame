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

    private int _heuristic;

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
        this._heuristic = heuristic();
        return this._heuristic;
    }

    public int getTotalCost() {
        return getCostToNode() + getHeuristic();
    }

    public void setHeuristic(int heuristic) {
        this._heuristic = _heuristic;
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

    private int heuristic(){
        Board currBoard = getBoard();
        int[] currGameBoard = getGameBoard();
        int currGameBoardSize = currGameBoard.length;
        int[] goalGameBaord = NodeUtils.generateGoalState(currGameBoardSize);
        int hCost = 0;
        for(int i = 0; i < currGameBoardSize;i++){
            hCost +=calcDistance(currBoard,i,goalGameBaord);
        }
        return hCost;
    }


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

    private int calcDistance(Board currBoard, int index, int[] goalGameBoard) {
        int[] currGameBoard = currBoard.getGameBoard();
        int currValue = currGameBoard[index];
        int goalIndex = findIndex(goalGameBoard, currValue);
        boolean isWhite =  currBoard.isWhiteTile(currValue);
        int currX = (int) (index % Math.sqrt(currGameBoard.length));
        int currY = (int) (index / Math.sqrt(currGameBoard.length));
        int goalX = (int) (goalIndex % Math.sqrt(currGameBoard.length));
        int goalY = (int) (goalIndex / Math.sqrt(currGameBoard.length));

        int manhattanDistance = Math.abs(currX - goalX) + Math.abs(currY - goalY);

        // Adjust the Manhattan Distance based on tile type
        if (currValue != 0) { // Not an empty tile
            int tileCost = isWhite ? 1 : 30;
            manhattanDistance += tileCost;
        }

        return manhattanDistance;
    }
    private static int findIndex(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;  // Value not found
    }

}
