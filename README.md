# TilePuzzleGame

TilePuzzleGame is an advanced puzzle-solving application that allows users to input various start configurations and solve tile puzzles using multiple sophisticated algorithms. This project supports algorithms such as A*, IDA*, DFS, BFS, and DFBNB, providing a comprehensive and efficient puzzle-solving experience.

## Features

- **Input Flexibility**: Accepts diverse input formats to set up the initial game board configuration.
- **Algorithm Variety**: Implements several state-of-the-art algorithms to solve tile puzzles.
- **Detailed Output**: Provides clear and concise outputs, including the sequence of moves, the number of moves, total cost, and execution time.
- **Performance Tracking**: Displays the efficiency of the solution in terms of computation time.

## Input Format

The input to the TilePuzzleGame specifies the algorithm to use, whether to track time or not, the board size, the positions of the white tiles, and the initial configuration of the board. Below is an example of the input format:
DFID
with time
no open
3x4
White: (7,2),(8,3)
1,2,3,4
5,6,11,7
9,10,8,_


## Output Format

The output provides the sequence of moves required to solve the puzzle, the total number of moves, the cost, and the execution time. Below is an example of the output format:
8R-11D-7L-8U
Num: 34
Cost: 33
0.002 seconds


## Algorithms Implemented

- **A***: A heuristic-based algorithm for finding the shortest path.
- **IDA***: Iterative Deepening A*, which combines the benefits of depth-first and breadth-first search.
- **DFS**: Depth-First Search, a fundamental algorithm for traversing or searching tree or graph data structures.
- **BFS**: Breadth-First Search, an algorithm for searching tree or graph data structures level by level.
- **DFBNB**: Depth-First Branch and Bound, an advanced algorithm for solving combinatorial optimization problems.

## How to Run

To run the TilePuzzleGame, follow these steps:

1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/TilePuzzleGame.git
    ```

2. Navigate to the project directory:
    ```bash
    cd TilePuzzleGame
    ```

3. Run the application with your desired input:
    ```bash
    java TilePuzzleGame <input-file>
    ```

## Example

Here is an example of running the TilePuzzleGame:

**Input file `input.txt`:**

DFID
with time
no open
3x4
White: (7,2),(8,3)
1,2,3,4
5,6,11,7
9,10,8,_

**Command:**
```bash
java TilePuzzleGame input.txt
```
Output:
8R-11D-7L-8U
Num: 34
Cost: 33
0.002 seconds


## 🌐 Connect with Me

- **Website:** [My Website](https://shalevasor.github.io/)
- **Contact:** [shalevasor@gmail.com](mailto:shalevasor@gmail.com)
- **Connect with me on LinkedIn:** [Shalev Asor](https://www.linkedin.com/in/shalev-asor)

## Contributing

Contributions are welcome! Please feel free to submit a pull request or open an issue to improve the project.
