import java.math.BigInteger;
import java.util.*;

/**
 *
 * This class implements the Depth-First Branch and Bound search algorithm
 * this algorithm searches through the state space from the start state to the goal state
 *
 */

public class DFBnB {

    private Node _start;
    private  boolean _openList;

    private String moves = "";
    private long startTime;

    private long endTime;

    private int nodesGenerated = 0;



    public DFBnB(Node start,boolean openList){
        this._start = start;
        this._openList = openList;
    }

    /**
     * DFBnB search algorithm
     * @param goal state
     * @return lowest cost path from start to goal
     */

    public Stack<Node> DFBnBAlgo(Node goal) {
        startTime = System.currentTimeMillis();
        Stack<Node> L = new Stack<>();
        Hashtable<String, Node> OL = new Hashtable<>();
        L.push(_start);
        OL.put(_start.getStateAsString(), _start);
        Stack<Node> result = new Stack<>();
        if(Arrays.equals(goal.getGameBoard(),_start.getGameBoard())){
            endTime = System.currentTimeMillis();
            return result;
        }
        int t = Integer.MAX_VALUE;
        if (_start.getGameBoard().length <= 12) {
            t = factorial(_start.getGameBoard().length);
        }
        while (!L.isEmpty()) {
            if (_openList) {
                System.out.println("Open List: " + OL.toString());
            }
            Node current = L.pop();
            String currentKey = current.getStateAsString();
            if (current.getIsOut()) {
                OL.remove(currentKey);
            } else {
                current.setIsOut(true); // mark this node as out
                List<Node> N = Operators.allowedOperators(current);
                nodesGenerated += N.size();
                N.sort(new NodeComparator());

                // Create a copy of the list N before iterating over it
                List<Node> copyOfN = new ArrayList<>(N);
                for (Node operator : copyOfN) {
                    String operatorKey = operator.getStateAsString();
                    if (operator.getEstimatedCost() >= t) {
                        N.clear();
                    } else if (OL.containsKey(operatorKey)) {
                        Node gPrime = OL.get(operatorKey);
                        if (gPrime.getIsOut()) {
                            N.remove(gPrime);
                        } else {
                            if (gPrime.getEstimatedCost() <= operator.getEstimatedCost()) {
                                N.remove(operator);
                            } else {
                                OL.remove(gPrime.getStateAsString());
                                L.remove(gPrime);
                            }
                        }
                    } else if (Arrays.equals(operator.getGameBoard(), goal.getGameBoard())) {
                        t = operator.getEstimatedCost();
                        result = NodeUtils.reconstructPath(operator);
                        N.clear();
                    }
                }
                // insert N in reverse order to L and OL
                for (int i = N.size() - 1; i >= 0; i--) {
                    Node newNode = N.get(i);
                    L.push(newNode);
                    OL.put(newNode.getStateAsString(), newNode);
                }
            }
        }
        moves = NodeUtils.getMovesAsString(result);
        endTime = System.currentTimeMillis();
        return result;
    }

//    public Stack<Node> DFBnBSearch(Node goal) {
//        startTime = System.currentTimeMillis();
//        Stack<Node> L = new Stack<>();
//        Hashtable<String, Node> OL = new Hashtable<>();
//        L.push(_start);
//        OL.put(_start.getStateAsString(), _start);
//        Stack<Node> result = new Stack<>();
//        int t = Integer.MAX_VALUE;
//        if(_start.getGameBoard().length<=12){
//            t= factorial(_start.getGameBoard().length);
//        }
//        while (!L.isEmpty()) {
//            if(_openList){
//                System.out.println("Open List: "+OL.toString());
//            }
//            Node current = L.pop();
//            String currentKey = current.getStateAsString();
//            if (current.getIsOut()) {
//                OL.remove(currentKey);
//            } else {
//                current.setIsOut(true); //mark this node as out
//                List<Node> operators = Operators.allowedOperators(current);
//                operators.sort(new NodeComparator()); // Sort the operators using NodeComparator
//                for (Node g : operators) {
//                    nodesGenerated++;
//                    if (g.getEstimatedCost() >= t) {
//                        List<Node> updatedOperators = new ArrayList<>();
//                        for (Node node : operators) {
//                            if (node.equals(g) || operators.indexOf(node) >= operators.indexOf(g)) {
//                                // Skip nodes that need to be removed
//                                continue;
//                            }
//                            // Add the node to the updated list
//                            updatedOperators.add(node);
//                        }
//                        // Replace the operators list with the updated list
//                        operators = updatedOperators;
//                    } else if (OL.containsKey(g.getStateAsString())) {
//                        Node gPrime = OL.get(g.getStateAsString());
//                        if (gPrime.getIsOut()) {
//                            operators.remove(gPrime);
//                        } else {
//                            if (gPrime.getEstimatedCost() <= g.getEstimatedCost()) {
//                                operators.remove(g);
//                            } else {
//                                operators.remove(gPrime);
//                                OL.remove(gPrime.getStateAsString());
//                            }
//                        }
//                    } else if (Arrays.equals(g.getGameBoard(), goal.getGameBoard())) {//then f(g)< t
//                        t = g.getEstimatedCost();
//                        result = NodeUtils.reconstructPath(g);
//                        break; // Stop processing further operators since goal is found
//                    }
//                }
//                // insert operators in a reverse order to L and OL
//                Collections.reverse(operators);
//                for (Node op : operators) {
//                    L.push(op);
//                    OL.put(op.getStateAsString(), op);
//                }
//            }
//        }
//        moves = NodeUtils.getMovesAsString(result);
//        return result;
//    }


    public String getMoves() {
        return moves;
    }

    public String getGeneratedNodesAmount() {
        return Integer.toString(nodesGenerated);
    }

    public long getEndTime(){
        return this.endTime;
    }
    public long getStartTime(){
        return this.startTime;
    }

    public String getCost(List<Node> path) {
        int totalCost = NodeUtils.getPathCost(path);
        return String.valueOf(totalCost);
    }

    /**
     *
     * @return the running time of the DFBnB algorithm with 3 digits after the point
     */
    public String getRunningTime() {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        long seconds = duration / 1000;
        long milliseconds = duration % 1000;
        return seconds + "." + String.format("%03d", milliseconds) + " seconds";
    }

    /**
     * return the factorial of the size - use this method when gameBoard size <= 12
     * @param size - the gameBaord size
     * @return
     */
    private int factorial(int size){
        int ans = 1;
        for(int i = 2; i < size; i++){
            ans *=i;
        }
        return ans;
    }

}
