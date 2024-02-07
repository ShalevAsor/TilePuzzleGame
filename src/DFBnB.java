import java.math.BigInteger;
import java.util.*;

/**
 *
 * This class implements the Depth-First Branch and Bound search algorithm
 * this algorithm searches through the state space from the start state to the goal state
 *
 *  */
 */
public class DFBnB {

    private Node _start;
    private  boolean _openList;

    private String moves = "";
    private long startTime;



    public DFBnB(Node start,boolean openList){
        this._start = start;
        this._openList = openList;
    }

    /**
     * DFBnB search algorithm
     * @param goal state
     * @return lowest cost path from start to goal
     */

    public List<Node> DFBnBSearch(Node goal) {
        startTime = System.currentTimeMillis();
        Stack<Node> L = new Stack<>();
        Hashtable<String, Node> OL = new Hashtable<>();
        L.push(_start);
        OL.put(_start.getStateAsString(), _start);
        Stack<Node> result = new Stack<>();
        int t = Math.min(Integer.MAX_VALUE, factorial(_start.getGameBoard().length));
        while (!L.isEmpty()) {
            if(_openList){
                System.out.println("Open List: "+OL.toString());
            }
            Node current = L.pop();
            String currentKey = current.getStateAsString();
            if (current.getIsOut()) {
                OL.remove(currentKey);
            } else {
                current.setIsOut(true); //mark this node as out
                List<Node> operators = Operators.allowedOperators(current);
                operators.sort(new NodeComparator()); // Sort the operators using NodeComparator
                for (Node g : operators) {
                    if (g.getTotalCost() >= t) {
                        List<Node> updatedOperators = new ArrayList<>();
                        for (Node node : operators) {
                            if (node.equals(g) || operators.indexOf(node) >= operators.indexOf(g)) {
                                // Skip nodes that need to be removed
                                continue;
                            }
                            // Add the node to the updated list
                            updatedOperators.add(node);
                        }
                        // Replace the operators list with the updated list
                        operators = updatedOperators;
                    } else if (OL.containsKey(g.getStateAsString())) {
                        Node gPrime = OL.get(g.getStateAsString());
                        if (gPrime.getIsOut()) {
                            operators.remove(gPrime);
                        } else {
                            if (gPrime.getTotalCost() <= g.getTotalCost()) {
                                operators.remove(g);
                            } else {
                                operators.remove(gPrime);
                                OL.remove(gPrime.getStateAsString());
                            }
                        }
                    } else if (Arrays.equals(g.getGameBoard(), goal.getGameBoard())) {//then f(g)< t
                        t = g.getTotalCost();
                        result = NodeUtils.reconstructPath(g);
                        break; // Stop processing further operators since goal is found
                    }
                }
                // Insert operators in a reverse order to L and OL
                Collections.reverse(operators);
                for (Node op : operators) {
                    L.push(op);
                    OL.put(op.getStateAsString(), op);
                }
            }
        }
        moves = NodeUtils.getMovesAsString(result);
        return result;
    }

    public String getMoves() {
        return moves;
    }

    public String getGeneratedNodesAmount() {
        int generatedNodesCounter = Operators.generatedChilds;
        Operators.resetCounter();
        return Integer.toString(generatedNodesCounter);
    }

    public String getCost(List<Node> path) {
        int totalCost = NodeUtils.getPathCost(path);
        return String.valueOf(totalCost);
    }

    /**
     *
     * @return the running time of the DFID algorithm with 3 digits after the point
     */
    public String getRunningTime() {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        long seconds = duration / 1000;
        long milliseconds = duration % 1000;
        return seconds + "." + String.format("%03d", milliseconds) + " seconds";
    }

    private int factorial(int size){
        int ans = 1;
        for(int i = 2; i < size; i++){
            ans *=i;
        }
        return ans;
    }

}
