import java.util.*;

/**
 * This class implements the Iterative Deepening A* (IDA*) search algorithm.
 * This algorithm searches through the state space from the start state
 * to the goal state by iteratively deepening the search with a threshold.
 */

public class IDAStar {

    private Node _start;
    private final boolean _openList;

    private String moves = "";
    private long startTime;

    private int nodesGenerated = 1;



    public IDAStar(Node start,boolean openList){
        this._start = start;
        this._openList = openList;
    }

    /**
     * IDA* implementation
     * @param goal state
     * @return
     */
    public Stack<Node> IDAStarSearch(Node goal) {
        startTime = System.currentTimeMillis();
        int t = _start.getHeuristic(); // Initial threshold set to heuristic value of start node
        while (t != Integer.MAX_VALUE) {
            int minF = Integer.MAX_VALUE;
            Stack<Node> L = new Stack<>();
            Hashtable<String, Node> OL = new Hashtable<>();
            L.push(_start);
            OL.put(_start.getStateAsString(), _start);
            while (!L.isEmpty()) {
                if(_openList){
                    System.out.println("Open List: "+OL.toString());
                }
                Node current = L.pop();
                if (current.getHeuristic() > t) {
                    minF = Math.min(minF, current.getHeuristic()); // Update minF with heuristic value
                    continue;
                }
                if (current.equals(goal)) { // Use equals method to check for goal node
                    // Goal node found
                    Stack<Node> path = NodeUtils.reconstructPath(current);
                    moves = NodeUtils.getMovesAsString(path);
                    return path;
                }
                for (Node operator : Operators.allowedOperators(current)) {
                    nodesGenerated++;
                    String operatorKey = operator.getStateAsString();
                    if (!OL.containsKey(operatorKey) || operator.getEstimatedCost() < OL.get(operatorKey).getEstimatedCost()) {
                        L.push(operator);
                        OL.put(operatorKey, operator);
                    }
                }
            }
            t = minF; // Update threshold with minF
        }
        return null; // Goal not found within threshold
    }


    public String getMoves() {
        return moves;
    }

    public String getGeneratedNodesAmount() {
        return Integer.toString(nodesGenerated);
    }

    public String getCost(List<Node> path) {
        int totalCost = NodeUtils.getPathCost(path);
        return String.valueOf(totalCost);
    }

    /**
     *
     * @return the running time of the IDAStar algorithm with 3 digits after the point
     */
    public String getRunningTime() {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        long seconds = duration / 1000;
        long milliseconds = duration % 1000;
        return seconds + "." + String.format("%03d", milliseconds) + " seconds";
    }

}
