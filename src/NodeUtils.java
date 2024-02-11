import java.util.*;


/**
 *This class provide methods to handle the different operations on the Nodes
 */
public class NodeUtils {


    /**
     * This method reconstruct the path by a given node
     * @param current - The last node before the goal
     * @return Stack<Node> represents the Path from the given node to the start node
     */
    public static Stack<Node> reconstructPath(Node current) {
        Stack<Node> path = new Stack<>();

        // Add nodes to the stack from current
        while (current != null ) {
            path.push(current);
            current = current.getParent();
        }
        return path;
    }

    /**
     * This function Deep copy the white tiles , so each node can update the white tiles moves without
     * changing the tiles of his parent
     * @param to - The destination of the White Tiles
     * @param from - The source of the White Tiles - usually this node parent
     */

    public static void deepCopyWhiteTiles(Map<Integer, int[]> to, Map<Integer, int[]> from) {
        to.clear(); //remove previous values

        for (Map.Entry<Integer, int[]> entry : from.entrySet()) {
            int key = entry.getKey();
            int[] value = entry.getValue();
            // Perform a deep copy of the array before putting it into the new map
            int[] copiedValue = Arrays.copyOf(value, value.length);
            to.put(key, copiedValue);
        }
    }


    /**
     * This method generate the Moves that made from the start node to the goal node
     * @param path - the results of the algorithms
     * @return String represents the moves of the algorithms
     */
    public static String getMovesAsString(List<Node> path) {
        //we didnt move at all
        if (path.size() <= 1) {
            return "";
        }
        /*  track the moves and insert to StringBuilder according to the given format  */
        StringBuilder movesBuilder = new StringBuilder();

        for (int i = 1; i < path.size(); i++) {
            Node currentNode = path.get(i);
            Node previousNode = path.get(i - 1);

            int emptyTileIndex = currentNode.getEmptyTileIndex();
            int prevEmptyTileIndex = previousNode.getEmptyTileIndex();

            if (emptyTileIndex == prevEmptyTileIndex + 1) {
                movesBuilder.insert(0, "R-").insert(0, currentNode.getBoard().getTileValue(prevEmptyTileIndex));

            } else if (emptyTileIndex == prevEmptyTileIndex - 1) {
                movesBuilder.insert(0, "L-").insert(0, currentNode.getBoard().getTileValue(prevEmptyTileIndex));

            } else if (emptyTileIndex == prevEmptyTileIndex + currentNode.getBoard().getColumns()) {
                movesBuilder.insert(0, "D-").insert(0, currentNode.getBoard().getTileValue(prevEmptyTileIndex));

            } else if (emptyTileIndex == prevEmptyTileIndex - currentNode.getBoard().getColumns()) {
                movesBuilder.insert(0, "U-").insert(0, currentNode.getBoard().getTileValue(prevEmptyTileIndex));
            }
        }
        // Remove the last "-" char
        movesBuilder.deleteCharAt(movesBuilder.length() - 1);

        return movesBuilder.toString();
    }



    public static int[] generateGoalState(int size){
        int[] state = new int[size];
        for(int i = 0; i < size-1; i++){
            state[i] = i+1;
        }
        state[size-1] = 0;
        return state;
    }

    public static int getPathCost(List<Node> path) {
        if(path == null)return 0;
        int totalCost = 0;

        for (int i = 1; i < path.size(); i++) {
            Node currentNode = path.get(i);
            Node previousNode = path.get(i - 1);

            int moveCost = 1;//if we didnt move a white tile then the cost is 30
            if (!currentNode.getBoard().isWhiteTile(previousNode.getBoard().getTileValue(currentNode.getEmptyTileIndex()))) {
                moveCost = 30;
            }//update total cost
            totalCost += moveCost;
        }

        return totalCost;
    }

    /**
     * This method estimate the total cost from the Node n state to the goal state .
     * the calculation is based on the Manhattan Distance .
     * @param n - the current node we estimate
     * @return the mManhattan Distance cost according to this Tile Puzzle rules
     */

    public static int heuristic(Node n){
        int[] currGameBoard = n.getGameBoard();
        int gameBoardSize = currGameBoard.length;
        int hCost = 0;
        for(int i = 0; i < gameBoardSize ; i++){//for each index that in the wrong position
            if(!isOnCorrectPosition(i,currGameBoard) && currGameBoard[i] != 0){
                int moves =  getManhattanDistance(i,n.getBoard().getColumns(),currGameBoard); // calculate the distance from the correct postion
                boolean isWhite = n.getBoard().isWhiteTile(currGameBoard[i]);
                if(isWhite){ // get the moves cost
                    hCost += moves;
                }
                else{
                    hCost+= (moves*30);
                }
            }
        }
        return hCost;
    }
//    public static int heuristic(Node n){
//        int[] currGameBoard = n.getGameBoard();
//        int gameBoardSize = currGameBoard.length;
//        int hCost = 0;
//        for(int i = 0; i < gameBoardSize ; i++){//for each index that in the wrong position
//            if(!isOnCorrectPosition(i,currGameBoard) && currGameBoard[i] != 0){
//                int moves =  getManhattanDistance(i,n.getBoard().getColumns(),currGameBoard); // calculate the distance from the correct postion
//                boolean isWhite = n.getBoard().isWhiteTile(currGameBoard[i]);
//                if(isWhite){ // get the moves cost
//                    int movesLeft = n.getBoard().getTileMoves(currGameBoard[i]);
//                    int moveToMake = moves - movesLeft;
//                    hCost += moveToMake;
//                }
//                else{
//                    hCost+= (moves*30);
//                }
//            }
//        }
//        return hCost;
//    }

//    public static int heuristic(Node n){
//        int[] currGameBoard = n.getGameBoard();
//        int gameBoardSize = currGameBoard.length;
//        int hCost = 0;
//        for(int i = 0; i < gameBoardSize ; i++){//for each index that in the wrong position
//            if(!isOnCorrectPosition(i,currGameBoard) && currGameBoard[i] != 0){
//                int moves =  getManhattanDistance(i,n.getBoard().getColumns(),currGameBoard); // calculate the distance from the correct postion
//                boolean isWhite = n.getBoard().isWhiteTile(currGameBoard[i]);
//                if(isWhite){ // get the moves cost
//                    int movesLeft = n.getBoard().getTileMoves(currGameBoard[i]);
//                    int moveToMake = moves - movesLeft;
//                    hCost += moveToMake;
//                }
//                else{
//                    hCost+= (moves*30);
//                }
//            }
//        }
//        return hCost;
//    }

    /**
     * This method return the Manhattan Distance from the tileIndex to the correct position on the goal state
     * @param tileIndex - index of current tile
     * @param columns - #columns of the game board
     * @param currGameBoard - current game board
     * @return Manhattan Distance from the tileIndex to the correct position on the goal state
     */

    private static int getManhattanDistance(int tileIndex,int columns, int[] currGameBoard) {
        int rowOfTileIndex =  tileIndex/columns;
        int columnOfTileIndex = tileIndex % columns;
        int destIndex = currGameBoard[tileIndex]-1;
        int rowOfDest = destIndex/columns;
        int columnOfDest = destIndex % columns;
        int distance = Math.abs(rowOfDest -rowOfTileIndex)+ Math.abs(columnOfDest - columnOfTileIndex);
        return distance;

    }

    /**
     * This method  return true iff this tile index is on the correct position
     * @param tileIndex index of the current tile
     * @param currGameBoard - current game board
     * @return - true iff this tile index is on the correct position
     */

    private static boolean isOnCorrectPosition(int tileIndex, int[] currGameBoard) {
        //its on the correct position on the goal game board
//        if(currGameBoard[tileIndex] == 0 && tileIndex == currGameBoard.length-1){return true;}//then the empty tile is on the correct postion
         return currGameBoard[tileIndex] == tileIndex + 1;
    }

}