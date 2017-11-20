//import org.jetbrains.annotations.Contract;

import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {
    private final int[] board;
    private final int dim;
    public Board(int[][] blocks) {
        this.dim = blocks.length;
        this.board = new int[dim * dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                this.board[(i * dim) + j] = blocks[i][j];
            }
        }
    }
    private Board(int[] board, int dim){
        this.board = board;
        this.dim = dim;
    }

    public int dimension() {
        return dim;
    }

    public int hamming() {
        int misplaced = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] != i + 1 && board[i] != 0) misplaced++;
        }
        return misplaced;
    }

    private int absolute(int n) {
        if (n < 0) return -1 * n;
        else return n;
    }

    public int manhattan() {
        int dis = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] != 0) {
                int currentRow, currentCol, row, col;
                currentRow = i / dim;
                currentCol = i % dim;
                row = (board[i] - 1) / dim;
                col = (board[i] - 1) % dim;
                dis = dis + absolute(currentCol - col) + absolute(currentRow - row);
            }
        }
        return dis;
    }

    public boolean isGoal() {
        return manhattan() == 0;
    }

    private void replace(int i, int j){
        int temp = board[i];
        board[i] = board[j];
        board[j] = temp;
    }

    private Board makeCopy(){
        int[] board2 = board.clone();
        return new Board(board2, dim);
    }

    public Board twin() {
        Board twin = makeCopy();
        if (twin.board[0]!=0){
            if (twin.board[1]!=0){
                twin.replace(0,1);
            }
            else twin.replace(0,2);
        }
        else twin.replace(2,3);
        return twin;

    }


    public boolean equals(Object y) {
        if (y==null) return false;
        if(!(y instanceof Board)) return false;
        Board z = (Board) y;
        return Arrays.equals(this.board, z.board);
    }

    private class boardIterator implements Iterator<Board> {
        private Board[] arr;
        private int len;
        public boardIterator(){
            int index0 = 0;
            for (int i=0;i<board.length;i++){
                if (board[i]==0){
                    index0 = i;
                    break;
                }
            }
//            arr = new Board[4];
//            len = 0;
//            if(index0>=dim){
//                arr[len]=makeCopy();
//                arr[len].replace(index0, index0-dim);
//                len++;
//            }
//            if(index0<dim*dim-dim){
//                arr[len] = makeCopy();
//                arr[len].replace(index0, index0+dim);
//                len++;
//            }
//            if(index0%dim!=dim-1){
//                arr[len] = makeCopy();
//                arr[len].replace(index0, index0+1);
//                len++;
//            }
//            if(index0%dim!=0){
//                arr[len] = makeCopy();
//                arr[len].replace(index0, index0-1);
//                len++;
//            }
            if (index0==0){
                len = 2;
                arr = new Board[2];
                arr[0] = makeCopy();
                arr[1] = makeCopy();
                arr[0].replace(0,1);
                arr[1].replace(0,dim);
            }
            else if (index0==dim-1){
                len=2;
                arr = new Board[2];
                arr[0] = makeCopy();
                arr[1] = makeCopy();
                arr[0].replace(index0 , index0-1);
                arr[1].replace(index0, index0 + dim);
            }
            else if (index0 == dim*(dim-1)){
                len = 2;
                arr = new Board[2];
                arr[0] = makeCopy();
                arr[1] = makeCopy();
                arr[0].replace(index0, index0+1);
                arr[1].replace(index0, index0-dim);
            }
            else if (index0 == dim*dim - 1){
                len = 2;
                arr = new Board[2];
                arr[0] = makeCopy();
                arr[1] = makeCopy();
                arr[0].replace(index0, index0-1);
                arr[1].replace(index0, index0-dim);
            }
            else if (index0<dim){
                len = 3;
                arr = new Board[3];
                arr[0] = makeCopy();
                arr[1] = makeCopy();
                arr[2] = makeCopy();
                arr[0].replace(index0,index0-1);
                arr[1].replace(index0,index0+1);
                arr[2].replace(index0, index0+dim);
            }
            else if (index0<dim*dim && index0>dim*(dim-1)){
                len = 3;
                arr = new Board[3];
                arr[0] = makeCopy();
                arr[1] = makeCopy();
                arr[2] = makeCopy();
                arr[0].replace(index0,index0-1);
                arr[1].replace(index0,index0+1);
                arr[2].replace(index0, index0-dim);
            }
            else if (index0%dim == 0){
                len = 3;
                arr = new Board[3];
                arr[0] = makeCopy();
                arr[1] = makeCopy();
                arr[2] = makeCopy();
                arr[0].replace(index0,index0+1);
                arr[1].replace(index0,index0+dim);
                arr[2].replace(index0, index0-dim);
            }
            else if (index0%dim==dim-1){
                len = 3;
                arr = new Board[3];
                arr[0] = makeCopy();
                arr[1] = makeCopy();
                arr[2] = makeCopy();
                arr[0].replace(index0,index0-1);
                arr[1].replace(index0,index0+dim);
                arr[2].replace(index0, index0-dim);
            }
            else{
                len = 4;
                arr = new Board[4];
                arr[0] = makeCopy();
                arr[1] = makeCopy();
                arr[2] = makeCopy();
                arr[3] = makeCopy();
                arr[0].replace(index0,index0+1);
                arr[1].replace(index0,index0-1);
                arr[2].replace(index0, index0-dim);
                arr[3].replace(index0, index0+dim);
            }
        }
        private int index = 0;
        public boolean hasNext() {
            return index < len;
        }

        public Board next() {
            if (!hasNext()) throw new NoSuchElementException();
            index++;
            return arr[index-1];
        }
    }

    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new boardIterator();
            }
        };
    }


    @Override
    public String toString() {
        String out = "\n"+String.valueOf(dim) + "\n";
        for (int i=0; i<dim; i++){
            for (int j=0; j<dim; j++){
                String s = String.valueOf(board[dim*i+j]);
                if(s.length()==1){
                    out = out.concat(" "+ s+ " ");
                }
                else out = out.concat(s + " ");
            }
            out = out.substring(0,out.length()-1);
            out = out.concat("\n");
        }
        out = out.substring(0,out.length()-1);
        return out;
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
                }
            }
        Board initial = new Board(tiles);
        System.out.println(initial);
        for (Board next : initial.neighbors()){
            System.out.println(next);
        }

    }
}
