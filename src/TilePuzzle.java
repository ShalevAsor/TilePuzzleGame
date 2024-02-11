import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


/**
 * This class represents the Tile puzzle game ,
 *  it provides functionality to start the game, set algorithm preferences,
 *  handle different input types ,
 */
public class TilePuzzle {
    private String _algo;
    private Boolean _time;
    private Boolean _openList;
    private Node _start;


    public TilePuzzle(){

    }

    /**
     * This function set the game , its generating the goal state (The goal state is always the same)
     * creating output file with the chosen algorithm data
     */
    public void solve(){
        String output = "";

        Node goal = generateGoalNode(); // init goal
        /* Cover all the input options  */

        switch (_algo) {
            case "DFID": {
                DFID dfid = new DFID(_openList, _start); //init DFID with start Node and open list indicator
                LimitedDFSResult res = dfid.DFIdAlgo(goal);// start the search and print the output according to input
                String runningTime = getRunningTime(dfid.getStartTime(),dfid.getEndTime());
                generateOutPut(output, dfid.getMoves(), dfid.getGeneratedNodesAmount(), dfid.getCost(res.getPath()), runningTime, res.getPath().size());
                break;
            }
            case "A*": {
                AStar aStar = new AStar(_start, _openList);
                Stack<Node> res = aStar.UCS(goal);
                String runningTime = getRunningTime(aStar.getStartTime(),aStar.getEndTime());
                if(res == null){
                    generateNoPath(output,aStar.getGeneratedNodesAmount(),runningTime);
                }
                else{
                    generateOutPut(output, aStar.getMoves(), aStar.getGeneratedNodesAmount(), aStar.getCost(res), runningTime, res.size());

                }
                break;
            }
            case "IDA*": {
                IDAStar idaStar = new IDAStar(_start, _openList);
                List<Node> res = idaStar.IDAStarAlgo(goal);
                String runningTime = getRunningTime(idaStar.getStartTime(),idaStar.getEndTime());

                if(res == null){
                    generateNoPath(output,idaStar.getGeneratedNodesAmount(),runningTime);

                }
                else{
                    generateOutPut(output, idaStar.getMoves(), idaStar.getGeneratedNodesAmount(), idaStar.getCost(res), runningTime, res.size());

                }
                break;
            }
            case "DFBnB": {
                DFBnB dfBnB = new DFBnB(_start, _openList);
                List<Node> res = dfBnB.DFBnBAlgo(goal);
                String runningTime = getRunningTime(dfBnB.getStartTime(),dfBnB.getEndTime());

                generateOutPut(output, dfBnB.getMoves(), dfBnB.getGeneratedNodesAmount(), dfBnB.getCost(res), runningTime, res.size());
                break;
            }
        }
    }

    /**
     * This function stream the output to output.txt file according the given format
     * @param output- the algorithm output
     * @param moves - the moves of the algorithm for solving the puzzle
     * @param generatedNodesAmount
     * @param cost - path cost
     * @param runningTime
     * @param pathSize
     */

    private void generateOutPut(String output, String moves, String generatedNodesAmount, String cost, String runningTime,int pathSize) {
        if(pathSize == 0){
            if(Integer.parseInt(generatedNodesAmount) != 0){
                output+="no path";
            }
            output+="\n";
            output += "Num: " + generatedNodesAmount;
            output+="\n";

        }
        else{
            output += moves; //first line of the output is the algorithm moves
            output+="\n";
            output += "Num: " + generatedNodesAmount; //all the nodes that generated *include nodes that are not in the open list
            output +="\n";
            output += "Cost: " + cost;//Path cost//
            output +="\n";
        }
        if(_time){
            output += runningTime; //DFID running time
        }
        streamOutPut(output);
    }

    private void streamOutPut(String output){
        try { //output to "output.txt"
            FileOutputStream out = new FileOutputStream("output.txt");
            out.write(output.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateNoPath(String output, String generatedNodesAmount, String runningTime) {
            output += "no path";
            output += "\n";
            output += "Num: " + generatedNodesAmount;
            output += "\n";
            if(_time){
                output += runningTime;

            }
        streamOutPut(output);

    }

    /**
     * This function allows to change the algorithm type to solve the puzzle with
     * @param algo - algorithm type
     */
    public void setAlgo(String algo){
        this._algo = algo;
    }
    public String getAlgo(){
        return this._algo;
    }

    public Boolean getTime() {
        return _time;
    }

    public void setTime(Boolean time) {
        this._time = time;
    }

    public Boolean getOpenList() {
        return _openList;
    }

    public void setOpenList(Boolean openList) {
        this._openList = openList;
    }

    public void setStart(Node start){
        this._start = start;
    }
    public Node getStart(){
        return this._start;
    }


    /**
     * This method generate the goal state - the goal state depend on the size of the game board
     * @param size - the size of the game Board
     * @return goal state
     */
    public int[] generateGoalState(int size){
        int[] state = new int[size];
        for(int i = 0; i < size-1; i++){
            state[i] = i+1;
        }
        state[size-1] = 0;
        return state;
    }

    /**
     * This method generate the goal Node ,
     * The goal Node depend on the start Node gameBoard size
     * @return goal Node
     */
    private Node generateGoalNode(){
        int size = _start.getGameBoard().length;
        int[] goalState = generateGoalState(size);
        Node goal = new Node();
        Map<Integer, int[]> whiteTiles = new HashMap<>();
        Board goalBoard = new Board();
        goalBoard.setWhiteTiles(whiteTiles);
        goalBoard.setGameBoard(goalState);
        goal.setBoard(goalBoard);
        return goal;

    }

    public void printPath(Stack<Node> path){
        String s ="";
        while(!path.isEmpty()){
            s += path.pop().toString();
        }
        System.out.println(s);
    }

    public String getRunningTime(long startTime,long endTime) {
        long duration = endTime - startTime;
        long seconds = duration / 1000;
        long milliseconds = duration % 1000;
        return seconds + "." + String.format("%03d", milliseconds) + " seconds";
    }

}
