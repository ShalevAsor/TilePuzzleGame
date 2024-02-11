import java.util.*;

/**
 * This AStar class represents an implementation of the A* algorithm,
 * that based on Uniform Cost Search algorithm with f(n) = g(n) + h(n)
 * where h(n) is the heuristic function (Manhattan distance )
 * and g(n) is the lowest path cost to n
 * for solving a variant of the Tile puzzle game.
 */

public class AStar {

    private Node _start;
    private final boolean _openList;

    private String moves = "";
    private long startTime;
    private long endTime;

    private int nodesGenerated = 0;




    public AStar(Node start,boolean openList){
        this._start = start;
        this._openList = openList;
    }

    /**
     * Uniform cost search algorithm
     * @param goal state
     * @return lowest cost path from start to goal
     */

//    public Stack<Node> UCS(Node goal){
//        startTime = System.currentTimeMillis();
//        PriorityQueue<Node> PQ = new PriorityQueue<Node>(new NodeComparator());
//        Stack<Node> path = new Stack<>();
//        //Hash table for open list
//        Hashtable<String,Node> OL = new Hashtable<>();
//        PQ.add(_start);
//        OL.put(_start.getStateAsString(),_start);
//        //Hash table for closed list
//        Hashtable<String,Node> CL = new Hashtable<>();
//
//        while(!PQ.isEmpty()){
//            System.out.println("SIZE: "+PQ.size());
//
//            Node current = PQ.poll();
//            String currentKey = current.getStateAsString();
//            if(_openList){
//                System.out.println("Open List: "+OL.toString());
//            }
//            OL.remove(currentKey);
//            if(Arrays.equals(current.getGameBoard(),goal.getGameBoard())){
//                path =  NodeUtils.reconstructPath(current);
//                moves = NodeUtils.getMovesAsString(path);
//                return path;
//            }
//            CL.put(current.getStateAsString(),current);
//            for(Node operator: Operators.allowedOperators(current)){
//                nodesGenerated++;
//                String currentOperatorKey = operator.getStateAsString();
//                if(!CL.containsKey(currentOperatorKey) && !OL.containsKey(currentOperatorKey)){
//                    System.out.println("VISTED3");
//
//                    PQ.add(operator);
//                    OL.put(currentOperatorKey,operator);
//                }
//                else if(OL.containsKey(currentOperatorKey)){
//                    System.out.println("VISTED1");
//                    if(OL.get(currentOperatorKey).getEstimatedCost() > operator.getEstimatedCost()){
//                        System.out.println("VISTED2");
//
//                        Node toRemove = OL.get(currentOperatorKey);
//                        OL.remove(currentOperatorKey);
//                        PQ.remove(toRemove);
//                        PQ.add(operator);
//                        OL.put(currentOperatorKey,current);
//                    }
//                }
//            }
//
//        }
//        return path;
//    }

    public Stack<Node> UCS(Node goal){
        startTime = System.currentTimeMillis();
        PriorityQueue<Node> PQ = new PriorityQueue<Node>(new NodeComparator());
        Stack<Node> path = new Stack<>();
        //Hash table for open list
        Hashtable<String,Node> OL = new Hashtable<>();
        //Hash table for closed list
        Hashtable<String,Node> CL = new Hashtable<>();
        PQ.add(_start);
        OL.put(_start.getStateAsString(),_start);


        while(!PQ.isEmpty()){
            Node current = PQ.poll();
            String currentKey = current.getStateAsString();
            if(_openList){
                System.out.println("Open List: "+OL.toString());
            }
            OL.remove(currentKey);
            if(Arrays.equals(current.getGameBoard(),goal.getGameBoard())){
                path =  NodeUtils.reconstructPath(current);
                moves = NodeUtils.getMovesAsString(path);
                endTime = System.currentTimeMillis();
                return path;
            }
            CL.put(current.getStateAsString(),current);
            for(Node operator: Operators.allowedOperators(current)){
                nodesGenerated++;
                String currentOperatorKey = operator.getStateAsString();
                if(!CL.containsKey(currentOperatorKey) && !OL.containsKey(currentOperatorKey)){
                    PQ.add(operator);
                    OL.put(currentOperatorKey,operator);
                }
                else if(OL.containsKey(currentOperatorKey)){
                    Node g = OL.get(currentOperatorKey);
                    if(g.getEstimatedCost() > operator.getEstimatedCost()){
                        PQ.remove(g);
                        PQ.add(operator);
                        OL.remove(currentOperatorKey);
                        OL.put(currentOperatorKey,operator);
                    }
                }
            }

        }
        endTime = System.currentTimeMillis();
        return null;
    }


    /**
     *
     * @return the moves done by the algorithm
     */

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

    public long getEndTime(){
        return this.endTime;
    }
    public long getStartTime(){
        return this.startTime;
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
