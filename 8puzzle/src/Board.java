//import org.jetbrains.annotations.Contract;
//import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int[] board;
    private final int dim;
    public Board(int[][] blocks){
        this.dim = blocks.length;
        this.board = new int[dim* dim];
        for (int i=0; i<dim; i++){
            for (int j=0; j<dim; j++){
                this.board[(i*dim)+j] = blocks[i][j];
            }
        }
    }

    public int dimension(){
        return dim;
    }

    public int hamming(){
        int misplaced = 0;
        for (int i=0; i<board.length; i++){
            if (board[i]!=i+1 && board[i]!=0) misplaced++;
        }
        return misplaced;
    }

    private int absolute(int n){
        if (n<0) return -1*n;
        else return n;
    }

    public int manhattan(){
        int dis = 0;
        for (int i=0; i<board.length;i++){
            int currentRow, currentCol, row, col;
            currentRow = i/dim;
            currentCol = i%dim;
            row = (board[i]-1)/dim;
            col = (board[i]-1)%dim;
            dis = absolute(currentCol-col)+absolute(currentRow-row);
        }
        return dis;
    }
    public boolean isGoal(){
        return hamming()==0;
    }
    public Board twin(){
        int[][] other = new int[dim][dim];
        for (int i=0; i<dim; i++){
            for (int j=0; j<dim; j++){
                other[i][j]=board[i*dim+j];
            }
        }
        other[1][2] = board[2*dim+1];
        other[2][1] = board[dim+1];
        Board twin = new Board(other);
        return twin;
    }
    public boolean equals(Object y){
        if (this == y){
            return true;
        }
        if (y==null){
            return false;
        }
        if (!(y instanceof Board)){
            return false;
        }
        Board p = (Board) y;
        return this.board.equals(p.board) ;
    }
    /*public Iterable<Board> neighbours(){
        return new
    }*/


    @Override
    public String toString() {
        String out = String.valueOf(dim) + "\n";
        for (int i=0; i<dim; i++){
            for (int j=0; j<dim; j++){
                out = out.concat(String.valueOf(board[dim*i+j]) + "\\s");
            }
            out = out.substring(0,out.length()-1);
            out = out.concat("\n");
        }
        out = out.substring(0,out.length()-1);
        return out;
    }


}
