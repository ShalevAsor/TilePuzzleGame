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
        board.setColumns(4);
        board.setRows(3);
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
        //System.out.println("The Path is : " + result.printPath());
    }
    @Test
    public void heuristicTest(){
        /*   case 1  - 1 tile different    */
        Map<Integer, int[]> whiteTiles = new HashMap<>();
        Board board = new Board();
        board.setWhiteTiles(whiteTiles);
        int[] gameBoard = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,0, 11};
        board.setGameBoard(gameBoard);
        board.setColumns(4);
        board.setRows(3);
        Node start = new Node();
        start.setBoard(board);
        Node goal = new Node();
        int[] goalState = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11, 0};
        Board goalBoard = new Board();
        goalBoard.setWhiteTiles(whiteTiles);
        goalBoard.setGameBoard(goalState);
        goal.setBoard(goalBoard);

        int estimatedCostForWhiteTile = 1;
        int estimatedCostForRegTile = 30;
        Assertions.assertEquals(estimatedCostForRegTile,start.getHeuristic());
        //change tile to white
        int[] tile = {11,1};
        whiteTiles.put(11,tile);
        Assertions.assertEquals(estimatedCostForWhiteTile,start.getHeuristic());
        /*   case 2  - 1 tile different    */
        Map<Integer, int[]> whiteTiles2 = new HashMap<>();
        Board board2 = new Board();
        board2.setWhiteTiles(whiteTiles2);
        int[] gameBoard2 = {1, 2, 3, 4, 5, 6, 7, 0, 9, 11,10, 8};
        board2.setGameBoard(gameBoard2);
        board2.setColumns(4);
        board2.setRows(3);
        Node start2 = new Node();
        start2.setBoard(board2);
        goalBoard.setWhiteTiles(whiteTiles2);
        goalBoard.setGameBoard(goalState);
        goal.setBoard(goalBoard);

        int estimatedCostForWhiteTile2 = 3;
        int estimatedCostForRegTile2 = 90;
        Assertions.assertEquals(estimatedCostForRegTile2,start2.getHeuristic());
        //change tile to white
        int[] tile2 = {11,1};

    }




//    @Test
//    public void ASTAR_TEST(){
//        /*   should be    */
//        Map<Integer, int[]> whiteTiles = new HashMap<>();
//        whiteTiles.put()
//        Board board = new Board();
//        board.setWhiteTiles(whiteTiles);
//        int[] gameBoard = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,0, 11};
//        board.setGameBoard(gameBoard);
//        Node start = new Node();
//        start.setBoard(board);
//        Node goal = new Node();
//        int[] goalState = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11, 0};
//        Board goalBoard = new Board();
//        goalBoard.setWhiteTiles(whiteTiles);
//        goalBoard.setGameBoard(goalState);
//        goal.setBoard(goalBoard);
//
//        Stack<Node> answer = new Stack<>();
//        answer.push(goal);
//        answer.push(start);
//
//        DFID dfid = new DFID(false,start);
//        LimitedDFSResult result = dfid.DFIdAlgo(goal);
//        System.out.println("The answer is :"+answer.toString());
//        System.out.println("The results is : "+result.getPath().toString());
//        Assertions.assertArrayEquals(result.getPath().toArray(), answer.toArray());
//        System.out.println("The Path is : " + result.printPath());
//    }
}
