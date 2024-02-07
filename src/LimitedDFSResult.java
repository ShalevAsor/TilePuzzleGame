import java.util.Stack;


/**
 *This class represents  the result pf the algorithm DFID
 * DFID has 3 types of results ,
 * 1 ) cutoff  -  The search reach the current limit and didn't find the goal state
 * 2 ) fail - there is no exists such goal  state or its not reachable
 * 3 ) path - Stack contains the Nodes that represents the states that need to operate for reaching the goal state
 *
 *
 */
public class LimitedDFSResult {
    private String _cutOff;
    private String _fail;
    private Stack<Node> _path;

    // Constructors, getters, and setters

    public LimitedDFSResult() {
        this._path = new Stack<>();
        this._fail = "";
        this._cutOff = "";
    }

    public String getCutOff() {
        return _cutOff;
    }

    public void setCutOff(String cutOff) {
        this._cutOff = cutOff;
    }

    public String getFail() {
        return _fail;
    }

    public void setFail(String fail) {
        this._fail = fail;
    }

    public Stack<Node> getPath() {
        return _path;
    }
    public void printPath(){
        String s ="";
        while(!_path.isEmpty()){
            s += _path.pop().toString();
        }
        System.out.println(s);
    }

    public void setPath(Stack<Node> path) {
        this._path = path;
    }
}
