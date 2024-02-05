import java.util.*;

//public class DFID {
//    String CUTOFF ="cutoff";
//    String FAIL = "fail";
//
//    Node _start;
//    private String moves = "";
//
//    private int generatedNodesCounter = 0;
//
//
//
//    private long startTime;
//
//
//    public LimitedDFSResult DFIdAlgo(Node start, Node goal){
//        startTime = System.currentTimeMillis();
//        _start = start;
//        for(int depth = 1; depth < Integer.MAX_VALUE ; depth ++){
//            Hashtable<String,Node> onPath = new Hashtable<>();
//            LimitedDFSResult result = Limited_DFS(start,goal,depth,onPath);
//            if(!result.getCutOff().equals(CUTOFF)){
//                return result;
//            }
//
//
//        }
//        return null;
//    }
//
//    private LimitedDFSResult Limited_DFS(Node n, Node goal, int limit, Hashtable<String,Node> onPath) {
//        LimitedDFSResult result = new LimitedDFSResult();
//
//        if (n.equals(goal)){
//            result.setPath(NodeUtils.reconstructPath(_start,n));
//            return result;
//        }
//        else if(limit == 0){
//            result.setCutOff(CUTOFF);
//            return result;
//        }
//        else{
//            onPath.put(n.getStateAsString(), n);
//            boolean isCutOff = false;
//            //System.out.println("Allowed Operators:" + Operators.allowedOperators(n));
//            for(Node operator : Operators.allowedOperators(n)){
//                generatedNodesCounter++;
//                if(onPath.contains(operator.getStateAsString())){ //loop avoidance
//                    continue;
//                }
//                result = Limited_DFS(operator,goal,limit-1,onPath);
//                if(Objects.equals(result.getCutOff(), CUTOFF)){
//                    isCutOff = true;
//                }
//                else if(!Objects.equals(result.getFail(), FAIL)){
//                    return result;
//                }
//            }
//            onPath.remove(n.getStateAsString());
//            if(isCutOff){
//                result.setCutOff(CUTOFF);
//                return result;
//            }
//            else{
//                result.setFail(FAIL);
//                return result;
//            }
//
//
//        }
//    }
//
//
//
//    private int calculateCost(List<Node> path) {
//        int totalCost = 0;
//
//        for (int i = 1; i < path.size(); i++) {
//            Node currentNode = path.get(i);
//            Node previousNode = path.get(i - 1);
//
//            int moveCost = (currentNode.getBoard().isWhiteTile(previousNode.getBoard().getTileValue(currentNode.getEmptyTileIndex()))) ? 1 : 30;
//            totalCost += moveCost;
//        }
//
//        return totalCost;
//    }
//
//    public String getMoves() {
//        return moves;
//    }
//
//    public String getGeneratedNodesAmount() {
//        return Integer.toString(generatedNodesCounter);
//    }
//
//    public String getCost(List<Node> path) {
//        int totalCost = calculateCost(path);
//        return String.valueOf(totalCost);
//    }
//
//    public String getRunningTime() {
//        long endTime = System.currentTimeMillis(); // Record the end time
//        long duration = endTime - startTime;
//
//        // Convert duration to seconds for better readability
//        long seconds = duration / 1000;
//        long milliseconds = duration % 1000;
//
//        return seconds + "." + String.format("%03d", milliseconds) + " seconds";
//    }
//}

/**
 * This DFID class represents an implementation of the DFID algorithm
 * for solving a variant of the Tile puzzle game.
 */

public class DFID {
    String CUTOFF ="cutoff";
    String FAIL = "fail";

    Node _start;
    private String moves = "";

    private final boolean _openList;

    private long startTime;

    public DFID(boolean openList,Node start){
        this._openList = openList;
        this._start = start;
    }

    /**
     * This method
     * @param goal - The node represents the goal state
     * @return The LimitedDFSResults - There are 3 types of results for the DFID algorithm
     * ( more information in LimitedDFSResults class documentation )
     */

    public LimitedDFSResult DFIdAlgo( Node goal){
        startTime = System.currentTimeMillis();
        for(int depth = 1; depth < Integer.MAX_VALUE ; depth ++){ // increasing the depth search each iteration
            Hashtable<String,Node> onPath = new Hashtable<>(); // hash table - to check if the Node we want to
            // generate is already on the path we are searching on

            LimitedDFSResult result = Limited_DFS(_start,goal,depth,onPath);

            if(!result.getCutOff().equals(CUTOFF)){
                return result; // we reach the limit of the search depth , then return cutoff
            }
        }
        return null;
    }

    /**
     * This function executes  the Limited DFS algorithm with loop avoidance
     * @param n - the current node we are working on, first call its start state
     * @param goal - the goal Node
     * @param limit - current limit of the depth search
     * @param onPath - Hashtable -  return true if the node we are want to generate is already in the path we are searching on
     * @return 3 types of results cutoff,path,fail
     */

    private LimitedDFSResult Limited_DFS(Node n, Node goal, int limit, Hashtable<String, Node> onPath) {
        LimitedDFSResult result = new LimitedDFSResult();

        if (n.equals(goal)) {//we reach the goal
            result.setPath(NodeUtils.reconstructPath( n)); //reconstruct the path from this node
            moves = NodeUtils.getMovesAsString(result.getPath());
            return result;
        } else if (limit == 0) { // we reach the limit but not path found
            result.setCutOff(CUTOFF);
            return result;
        } else { //start the search
            onPath.put(n.getStateAsString(), n);
            if(_openList){
                System.out.println(onPath.toString());
            }
            boolean isCutOff = false;

            //  there are at most 4 possible moves , allowedOperators generating this node children

            for (Node operator : Operators.allowedOperators(n)) {
                if (onPath.contains(operator.getStateAsString())) {// loop avoidance
                    continue;
                }

                result = Limited_DFS(operator, goal, limit - 1, onPath);

                if (Objects.equals(result.getCutOff(), CUTOFF)) {// reached the limit
                    isCutOff = true;
                } else if (!Objects.equals(result.getFail(), FAIL)) {
                 //   moves = NodeUtils.getMovesAsString(result.getPath());  //update the moves
                    return result;
                }
            }
            onPath.remove(n.getStateAsString());//done searching with this node

            if (isCutOff) {
                result.setCutOff(CUTOFF);
                return result;
            } else {
                result.setFail(FAIL);
                return result;
            }
        }
    }


    /**
     * This method return the total cost of a path
     * @param path -
     * @return total cost of a path
     */
    private int getPathCost(List<Node> path) {
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

    public String getMoves() {
        return moves;
    }

    public String getGeneratedNodesAmount() {
        int generatedNodesCounter = Operators.generatedChilds;
        Operators.resetCounter();
        return Integer.toString(generatedNodesCounter);
    }

    public String getCost(List<Node> path) {
        int totalCost = getPathCost(path);
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

}
