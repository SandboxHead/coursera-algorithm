import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Solver {
    private class BoardInfo implements Comparable<BoardInfo>{
        private Board board;
        private int manhattan;
        private int moves;
        private BoardInfo previous;
        public BoardInfo(Board board, int manhattan, int moves, BoardInfo previous){
            this.board = board;
            this.manhattan = manhattan;
            this.moves = moves;
            this.previous = previous;
        }

        @Override
        public int compareTo(BoardInfo o) {
            int priority1 = this.manhattan+this.moves;
            int priority2 = o.manhattan+o.moves;
            if(priority1<priority2)return -1;
            else if(priority1>priority2) return 1;
            else return 0;
        }
    }
    private MinPQ<BoardInfo> pq;
    private int moves;
    private MinPQ<BoardInfo> inverse;
    private boolean valid;
    private BoardInfo min;
    public Solver(Board initial) {
        //long curr = System.currentTimeMillis();
        //long time1 = 0;
        if(initial==null) throw new IllegalArgumentException();
        pq = new MinPQ<BoardInfo>();
        BoardInfo init = new BoardInfo(initial, initial.manhattan(), 0,null);
        pq.insert(init);
        inverse = new MinPQ<>();
        Board twin = initial.twin();
        BoardInfo initTwin = new BoardInfo(twin, twin.manhattan(), 0,null);
        inverse.insert(initTwin);
        int moves = 0;
        BoardInfo twinMin;
        BoardInfo neighbour;
        BoardInfo neighbourTwin;
        while(pq.min().manhattan!=0 && inverse.min().manhattan!=0){
            min = pq.delMin();
            moves = min.moves+1;
            twinMin = inverse.delMin();
            for (Board next : min.board.neighbors()) {
                if(next.isGoal()){
                    valid = true;
                    this.moves = moves;
                    min = new BoardInfo(next, next.manhattan(), moves, min);
                    return;
                }
                if (min.previous==null || !next.equals(min.previous.board)){
                    neighbour = new BoardInfo(next, next.manhattan(), moves, min );
                    pq.insert(neighbour);
                }
            }
            for (Board next : twinMin.board.neighbors()) {
                if(next.isGoal()){
                    valid = false;
                    this.moves = moves;
                    return;
                }
                if (twinMin.previous==null || !next.equals(twinMin.previous.board)){
                    neighbourTwin = new BoardInfo(next, next.manhattan(), moves, twinMin );
                    inverse.insert(neighbourTwin);
                }
            }

        }
        valid = pq.min().board.isGoal();
        this.moves = pq.min().moves;
        min = pq.min();
    }
    public boolean isSolvable(){
        return valid;
    }
    public int moves(){
        if (isSolvable()) return moves;
        else return -1;
    }
    private class pathIterator implements Iterator<Board>{
        Board[] b = new Board[moves+1];
        int index = moves;
        BoardInfo current = min;
        int i;

        private pathIterator(){
            i=0;
            for(index = moves; index>=0; index--){
                b[index] = current.board;
                try {
                    current = current.previous;
                }
                catch (NullPointerException ignored){}
            }
        }

        @Override
        public boolean hasNext() {
            return i<=moves;
        }

        @Override
        public Board next() {
            Board out = b[i++];
            return out;
        }
    }
    public Iterable<Board> solution(){
        if (!isSolvable()) return null;
        else return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new pathIterator();
            }
        };
    }
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        long start = System.currentTimeMillis();
        Solver solver = new Solver(initial);
        //StdOut.println(System.currentTimeMillis()-start);
        //StdOut.println(solver.time);
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
