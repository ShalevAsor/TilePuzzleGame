import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


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
    public void startGame(){
        String output = "";
        Node goal = generateGoalNode(); // init goal
        /* Cover all the input options  */

        if(_algo.equals("DFID")){ // Need to run DFID algorithm
            DFID dfid = new DFID(_openList,_start);
            LimitedDFSResult res = dfid.DFIdAlgo(goal);//run algorithm from start to goal
            output += dfid.getMoves(); //first line of the output is the algorithm moves
            output+="\n";
            output += "Num: " +dfid.getGeneratedNodesAmount(); //all the nodes that generated *include nodes that are not in the open list
            output +="\n";
            output += "Cost: " +dfid.getCost(res.getPath());//Path cost
            output +="\n";
            if(_time){
                output += dfid.getRunningTime(); //DFID running time
            }
            try { //output to "output.txt"
                FileOutputStream out = new FileOutputStream("output.txt");
                out.write(output.getBytes());
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



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
}
