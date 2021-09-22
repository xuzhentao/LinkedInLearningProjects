import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.List;

public class Solver {

    // private final List<String> previousBoardsStr = new ArrayList<>();
    private final boolean solvable;
    private List<Board> solution = new ArrayList<>();
    private int move = -1;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null)
            throw new IllegalArgumentException("Input initial should not be null!");

        boolean isGoal = initial.isGoal();
        if (isGoal) {
            move = 0;
        }
        MinPQ<BoardPQElement> minPQ = new MinPQ<>();

        int ctr = 0;
        minPQ.insert(new BoardPQElement(initial, 0));
        while (!minPQ.isEmpty()) {
            // System.out.println(ctr + " " + minPQ.size());
            ctr++;
            if (minPQ.min().getBoard().isGoal()) {
                isGoal = true;
                move = minPQ.min().getMove();
                break;
            }
            // early stop
            Board minBoard = minPQ.min().getBoard();


            BoardPQElement minBoardQPElement = minPQ.delMin();
            // System.out.println("minBoardQPElement = " + minBoardQPElement.getBoard());
            for (Board board : minBoardQPElement.getBoard().neighbors()) {
                // System.out.println(board);
                boolean ifSeen = false;
                BoardPQElement tempBoardQPElement = minBoardQPElement.getPrevBoardElement();
                while (tempBoardQPElement != null && !ifSeen) {
                    if (tempBoardQPElement.getBoard().equals(board)) {
                        ifSeen = true;
                    }
                    tempBoardQPElement = tempBoardQPElement.getPrevBoardElement();
                }
                if (ifSeen) {
                    continue;
                }

                BoardPQElement boardPQElement = new BoardPQElement(board,
                                                                   minBoardQPElement.getMove() + 1);
                boardPQElement.setPrevBoardElement(minBoardQPElement);
                minPQ.insert(boardPQElement);
            }

        }
        solvable = isGoal;
        if (solvable) {
            BoardPQElement thisBoardElement = minPQ.min();
            while (thisBoardElement != null) {
                solution.add(thisBoardElement.getBoard());
                thisBoardElement = thisBoardElement.getPrevBoardElement();
            }
            List<Board> solutionCopy = new ArrayList<>();
            for (int idx = solution.size() - 1; idx >= 0; idx--)
                solutionCopy.add(solution.get(idx));
            solution = solutionCopy;
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        int[][] arr = { { 1, 2, 3 }, { 4, 6, 5 }, { 7, 8, 0 } };
        Board bd = new Board(arr);
        Solver solver = new Solver(bd);
        System.out.println("move = " + solver.moves());
        if (solver.moves() >= 0) {
            System.out.println(solver.solution());
        }


    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return move;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    private class BoardPQElement implements Comparable<BoardPQElement> {

        int move;
        Board board;
        BoardPQElement prevBoardElement;
        Integer manhattanDistance = null;

        public BoardPQElement(Board board, int move) {
            this.board = board;
            this.move = move;
        }

        public BoardPQElement getPrevBoardElement() {
            return prevBoardElement;
        }

        public void setPrevBoardElement(BoardPQElement prevBoardElement) {
            this.prevBoardElement = prevBoardElement;
        }

        public int compareTo(BoardPQElement boardPQElement) {
            return getPriority() - boardPQElement.getPriority();
        }

        public int getPriority() {
            if (manhattanDistance == null) {
                manhattanDistance = board.manhattan();
            }
            return move + manhattanDistance;
        }

        public Board getBoard() {
            return board;
        }

        public int getMove() {
            return move;
        }
    }


}
