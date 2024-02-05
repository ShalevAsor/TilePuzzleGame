import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class TestAlgorithms {


    @Test
    public void DFID_TEST(){
        /*   should be    */
        Map<Integer, int[]> whiteTiles = new HashMap<>();

        Board board = new Board();
        board.setWhiteTiles(whiteTiles);
        int[] gameBoard = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,0, 11};
        board.setGameBoard(gameBoard);
        Node start = new Node();
        start.setBoard(board);
        Node goal = new Node();
        int[] goalState = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11, 0};
        Board goalBoard = new Board();
        goalBoard.setWhiteTiles(whiteTiles);
        goalBoard.setGameBoard(goalState);
        goal.setBoard(goalBoard);

        Stack<Node> answer = new Stack<>();
        answer.push(goal);
        answer.push(start);

        DFID dfid = new DFID(false,start);
        LimitedDFSResult result = dfid.DFIdAlgo(goal);
        System.out.println("The answer is :"+answer.toString());
        System.out.println("The results is : "+result.getPath().toString());
        Assertions.assertArrayEquals(result.getPath().toArray(), answer.toArray());
        System.out.println("The Path is : " + result.printPath());
    }
}
