import java.util.*;


/**
 * This class represents a state in the state space,
 * Each node contains:
 * Board - custom Objects that contains the Node state a.k.a GameBoard and the White Tiles
 * Parent - The Node that this Node generated from . according to TilePuzzle rules
 *
 * The space states - Each state is array of size N*M that contains exactly one 0 (The empty tile)
 * The reset of the elements are Integers in [N*M -1]
 *
 */
public class Node {
    private Node _parent;

    private Board _board;



    public Node(){

    }
    public Node getParent() {
        return _parent;
    }

    public void setParent(Node _parent) {
        this._parent = _parent;
    }

    public int[] getGameBoard(){
        return this._board.getGameBoard();
    }


    @Override
    public String toString() {
        return "Node{" +
                "Parent: " + (_parent != null ? _parent.getBoard().toString() : "null") +
                ", Board: " + _board.toString() +
                '}';
    }

    public void setBoard(Board board){
        this._board = board;
    }
    public Board getBoard(){
        return this._board;
    }


    /**
     * This method return the index of the empty tile ( represented by 0 )
     * @return The index of the empty tile
     */
    public int getEmptyTileIndex(){
        int[] gameBoard = this.getGameBoard();
        for(int i =0 ; i < gameBoard.length; i++){
            if(gameBoard[i] == 0){
                return i;
            }
        }
        return -1; // not find the empty tile , must be an error
    }

    public String getStateAsString(){
        return Arrays.toString(this.getGameBoard());
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//
//        if (obj == null || getClass() != obj.getClass()) {
//            return false;
//        }
//
//        Node otherNode = (Node) obj;
//
//        // Check equality based on the state only
//        return Arrays.equals(this.getGameBoard(), otherNode.getGameBoard());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(Arrays.hashCode(getGameBoard()), _board);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node otherNode = (Node) obj;
        return Arrays.equals(this.getGameBoard(), otherNode.getGameBoard());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.getGameBoard());
    }
//    }
//    public List<Node> allowedOperators(){
//        List<Node> operators = new ArrayList<>();
//        int rows = this._board.getRows();
//        int columns = this._board.getColumns();
//        int[][] whiteTiles = new int[rows][columns];
//        NodeUtils.deepCopyWhiteTiles(whiteTiles,this._board.getWhiteTiles());
//        int[] gameBoard = new int[rows*columns -1];
//        NodeUtils.deepCopyGameBoard(gameBoard,this.getGameBoard());
//        int emptyTileIndex = this.getEmptyTileIndex();
//
//
//        switch (emptyTileIndex) {
//            case 0: // the empty tile is on the top left corner of the game board
//                operators = handleTopLeft(rows,columns,whiteTiles,emptyTileIndex,gameBoard);
//                break;
//
//            case columns -1: // the empty tile is on the top right corner of the game board
//                operators = handleTopRight(rows,columns,whiteTiles,emptyTileIndex,gameBoard);
//                break;
//
//            case (rows*columns -1) -(columns-1): // the empty tile is on the bottom left corner of the game board
//                operators = handleBottomLeft(rows,columns,whiteTiles,emptyTileIndex,gameBoard);
//                break;
//
//            case (rows*columns -1): // the empty tile is on the top right corner of the game board
//                operators = handleBottomRight(rows,columns,whiteTiles,emptyTileIndex,gameBoard);
//                break;
//
//            default:
//                // The empty tile is somewhere in the middle
//                operators = handleMiddle(rows,columns,whiteTiles,emptyTileIndex,gameBoard);
//        }
//
//
//    }
//
//    private List<Node> handleTopLeft(int rows, int columns, int[][] whiteTiles, int emptyTileIndex,int[] gameBoard) {
//        List<Node> operators = new ArrayList<>();
//        int tileIndex = this._board.getWhiteTileIndex(gameBoard[1]);
//        if(tileIndex != -1){// gameBoard[1] is white tile
//            if(whiteTiles[tileIndex][1] > 0){//there are moves left for this tile
//                gameBoard[emptyTileIndex] = whiteTiles[tileIndex][0]; // replace positions on board
//                gameBoard[1] = 0;
//                whiteTiles[tileIndex][1]--;//decrease the moves amount
//                /* create new node */
//                Node child = generateChild(rows, columns, whiteTiles, gameBoard);
//                if(!Arrays.equals(child.getGameBoard(),this.getGameBoard())) {//check that its not his parent
//                    operators.add(child);
//                }
//            }
//
//
//        }
//        else{ // this tile is not white
//            gameBoard[emptyTileIndex] = gameBoard[1]; // replace positions on board
//            gameBoard[1] = 0;
//            /* create new node */
//            Node child = generateChild(rows, columns, whiteTiles, gameBoard);
//            if(!Arrays.equals(child.getGameBoard(),this.getGameBoard())) {//check that its not his parent
//                operators.add(child);
//            }
//        }
//        tileIndex = this._board.getWhiteTileIndex(gameBoard[columns]);
//        if(tileIndex != -1){// gameBoard[1] is white tile
//            if(whiteTiles[tileIndex][1] > 0){//there are moves left for this tile
//                gameBoard[emptyTileIndex] = whiteTiles[tileIndex][0]; // replace positions on board
//                gameBoard[columns] = 0;
//                whiteTiles[tileIndex][1]--;//decrease the moves amount
//                /* create new node */
//                Node child = generateChild(rows, columns, whiteTiles, gameBoard);
//                if(!Arrays.equals(child.getGameBoard(),this.getGameBoard())) {//check that its not his parent
//                    operators.add(child);
//                }
//            }
//
//
//        }
//        else{ // this tile is not white
//            gameBoard[emptyTileIndex] = gameBoard[columns]; // replace positions on board
//            gameBoard[columns] = 0;
//            /* create new node */
//            Node child = generateChild(rows, columns, whiteTiles, gameBoard);
//            if(!Arrays.equals(child.getGameBoard(),this.getGameBoard())) {//check that its not his parent
//                operators.add(child);
//            }
//        }
//        return operators;
//
//    }
//
//    private List<Node> handleTopRight(int rows, int columns, int[][] whiteTiles, int emptyTileIndex,int[] gameBoard) {
//        List<Node> operators = new ArrayList<>();
//        int tileIndex = this._board.getWhiteTileIndex(gameBoard[emptyTileIndex-1]);
//        if(tileIndex != -1){// gameBoard[1] is white tile
//            if(whiteTiles[tileIndex][1] > 0){//there are moves left for this tile
//                gameBoard[emptyTileIndex] = whiteTiles[tileIndex][0]; // replace positions on board
//                gameBoard[emptyTileIndex-1] = 0;
//                whiteTiles[tileIndex][1]--;//decrease the moves amount
//                /* create new node */
//                Node child = generateChild(rows, columns, whiteTiles, gameBoard);
//                if(!Arrays.equals(child.getGameBoard(),this.getGameBoard())) {//check that its not his parent
//                    operators.add(child);
//                }
//            }
//
//
//        }
//        else{ // this tile is not white
//            gameBoard[emptyTileIndex] = gameBoard[emptyTileIndex-1]; // replace positions on board
//            gameBoard[emptyTileIndex-1] = 0;
//            /* create new node */
//            Node child = generateChild(rows, columns, whiteTiles, gameBoard);
//            if(!Arrays.equals(child.getGameBoard(),this.getGameBoard())) {//check that its not his parent
//                operators.add(child);
//            }
//        }
//        tileIndex = this._board.getWhiteTileIndex(gameBoard[emptyTileIndex+columns]);//get the tile under him
//        if(tileIndex != -1){// gameBoard[1] is white tile
//            if(whiteTiles[tileIndex][1] > 0){//there are moves left for this tile
//                gameBoard[emptyTileIndex] = whiteTiles[tileIndex][0]; // replace positions on board
//                gameBoard[emptyTileIndex+columns] = 0;
//                whiteTiles[tileIndex][1]--;//decrease the moves amount
//                /* create new node */
//                Node child = generateChild(rows, columns, whiteTiles, gameBoard);
//                if(!Arrays.equals(child.getGameBoard(),this.getGameBoard())) {//check that its not his parent
//                    operators.add(child);
//                }
//            }
//
//
//        }
//        else{ // this tile is not white
//            gameBoard[emptyTileIndex] = gameBoard[emptyTileIndex+columns]; // replace positions on board
//            gameBoard[emptyTileIndex+columns] = 0;
//            /* create new node */
//            Node child = generateChild(rows, columns, whiteTiles, gameBoard);
//            if(!Arrays.equals(child.getGameBoard(),this.getGameBoard())) {//check that its not his parent
//                operators.add(child);
//            }
//        }
//        return operators;
//
//    }
//
//    private List<Node> handleBottomLeft(int rows, int columns, int[][] whiteTiles, int emptyTileIndex,int[] gameBoard) {
//        List<Node> operators = new ArrayList<>();
//        int tileIndex = this._board.getWhiteTileIndex(gameBoard[emptyTileIndex+1]);
//        if(tileIndex != -1){// gameBoard[1] is white tile
//            if(whiteTiles[tileIndex][1] > 0){//there are moves left for this tile
//                gameBoard[emptyTileIndex] = whiteTiles[tileIndex][0]; // replace positions on board
//                gameBoard[emptyTileIndex+1] = 0;
//                whiteTiles[tileIndex][1]--;//decrease the moves amount
//                /* create new node */
//                Node child = generateChild(rows, columns, whiteTiles, gameBoard);
//                if(!Arrays.equals(child.getGameBoard(),this.getGameBoard())) {//check that its not his parent
//                    operators.add(child);
//                }
//            }
//
//
//        }
//        else{ // this tile is not white
//            gameBoard[emptyTileIndex] = gameBoard[emptyTileIndex+1]; // replace positions on board
//            gameBoard[emptyTileIndex+1] = 0;
//            /* create new node */
//            Node child = generateChild(rows, columns, whiteTiles, gameBoard);
//            if(!Arrays.equals(child.getGameBoard(),this.getGameBoard())) {//check that its not his parent
//                operators.add(child);
//            }
//        }
//        tileIndex = this._board.getWhiteTileIndex(gameBoard[emptyTileIndex-columns]);//get the tile under him
//        if(tileIndex != -1){// gameBoard[1] is white tile
//            if(whiteTiles[tileIndex][1] > 0){//there are moves left for this tile
//                gameBoard[emptyTileIndex] = whiteTiles[tileIndex][0]; // replace positions on board
//                gameBoard[emptyTileIndex-columns] = 0;
//                whiteTiles[tileIndex][1]--;//decrease the moves amount
//                /* create new node */
//                Node child = generateChild(rows, columns, whiteTiles, gameBoard);
//                if(!Arrays.equals(child.getGameBoard(),this.getGameBoard())) {//check that its not his parent
//                    operators.add(child);
//                }
//            }
//
//
//        }
//        else{ // this tile is not white
//            gameBoard[emptyTileIndex] = gameBoard[emptyTileIndex-columns]; // replace positions on board
//            gameBoard[emptyTileIndex-columns] = 0;
//            /* create new node */
//            Node child = generateChild(rows, columns, whiteTiles, gameBoard);
//            if(!Arrays.equals(child.getGameBoard(),this.getGameBoard())) {//check that its not his parent
//                operators.add(child);
//            }
//        }
//        return operators;
//
//    }
//
//    private List<Node> handleBottomRight(int rows, int columns, int[][] whiteTiles, int emptyTileIndex,int[] gameBoard) {
//        List<Node> operators = new ArrayList<>();
//        int tileIndex = this._board.getWhiteTileIndex(gameBoard[emptyTileIndex-1]);
//        if(tileIndex != -1){// gameBoard[1] is white tile
//            if(whiteTiles[tileIndex][1] > 0){//there are moves left for this tile
//                gameBoard[emptyTileIndex] = whiteTiles[tileIndex][0]; // replace positions on board
//                gameBoard[emptyTileIndex-1] = 0;
//                whiteTiles[tileIndex][1]--;//decrease the moves amount
//                /* create new node */
//                Node child = generateChild(rows, columns, whiteTiles, gameBoard);
//                if(!Arrays.equals(child.getGameBoard(),this.getGameBoard())) {//check that its not his parent
//                    operators.add(child);
//                }
//            }
//
//
//        }
//        else{ // this tile is not white
//            gameBoard[emptyTileIndex] = gameBoard[emptyTileIndex-1]; // replace positions on board
//            gameBoard[emptyTileIndex-1] = 0;
//            /* create new node */
//            Node child = generateChild(rows, columns, whiteTiles, gameBoard);
//            if(!Arrays.equals(child.getGameBoard(),this.getGameBoard())) {//check that its not his parent
//                operators.add(child);
//            }
//        }
//        tileIndex = this._board.getWhiteTileIndex(gameBoard[emptyTileIndex-columns]);//get the tile under him
//        if(tileIndex != -1){// gameBoard[1] is white tile
//            if(whiteTiles[tileIndex][1] > 0){//there are moves left for this tile
//                gameBoard[emptyTileIndex] = whiteTiles[tileIndex][0]; // replace positions on board
//                gameBoard[emptyTileIndex-columns] = 0;
//                whiteTiles[tileIndex][1]--;//decrease the moves amount
//                /* create new node */
//                Node child = generateChild(rows, columns, whiteTiles, gameBoard);
//                if(!Arrays.equals(child.getGameBoard(),this.getGameBoard())) {//check that its not his parent
//                    operators.add(child);
//                }
//            }
//
//
//        }
//        else{ // this tile is not white
//            gameBoard[emptyTileIndex] = gameBoard[emptyTileIndex-columns]; // replace positions on board
//            gameBoard[emptyTileIndex-columns] = 0;
//            /* create new node */
//            Node child = generateChild(rows, columns, whiteTiles, gameBoard);
//            if(!Arrays.equals(child.getGameBoard(),this.getGameBoard())) {//check that its not his parent
//                operators.add(child);
//            }
//        }
//        return operators;
//
//    }




//    private Node generateChild(int rows, int columns, int[][] whiteTiles, int[] gameBoard) {
//        Board newBoard = new Board();
//        newBoard.setGameBoard(gameBoard);
//        newBoard.setWhiteTiles(whiteTiles);
//        newBoard.setColumns(rows);
//        newBoard.setColumns(columns);
//        Node newNode = new Node();
//        newNode.setBoard(newBoard);
//        newNode.setParent(this);
//        return newNode;
//    }






}
