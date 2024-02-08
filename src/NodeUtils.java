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

}