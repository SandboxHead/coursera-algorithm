import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation{
    private WeightedQuickUnionUF grid;
    private int box;
    private int index;
    private int[] open;
    private int opensites=0;
    public Percolation(int n){
        if (n<=0){
            throw new IllegalArgumentException();
        }
        index=n;
        grid=new WeightedQuickUnionUF(n*n+2);
        open=new int[n*n];
    }

    public void open(int row, int col){
        if (row<1 || row>index || col<1 || col>index)throw new IllegalArgumentException();
        box=(row-1)*index+(col-1);

        if (isOpen(row,col)==false){
            open[box]=1;
            opensites++;
            if (index==1){grid.union(box,1); grid.union(box,2);}
            if (row==1) {
                grid.union(box,index*index);
                grid.union(box, box+index);
            }
            else if (row==index){ grid.union(box, index*index+1); grid.union(box,box-index);}
            else{
                if (isOpen(row-1,col))grid.union(box,box-index);
                if (isOpen(row+1,col))grid.union(box,box+index);
                if (col!=1) if(isOpen(row,col-1)) grid.union(box,box-1);
                if (col!=index) if (isOpen(row,col+1)) grid.union(box,box+1);
            }

        }
        //catch (java.lang.IllegalArgumentException e){};
    }
   public boolean isOpen(int row,int col){
       if (row<1 || row>index || col<1 || col>index)throw new IllegalArgumentException();

       if (open[(row-1)*index+col-1]==1) return true;
        else return false;
   }

   public boolean isFull(int row,int col){
       if (row<1 || row>index || col<1 || col>index)throw new IllegalArgumentException();

       if (isOpen(row,col))
           return grid.connected(index*index,index*(row-1)+(col-1));
       else return false;
   }
   public int numberOfOpenSites(){
       return opensites;
   }

   public boolean percolates(){
       return grid.connected(index*index,index*index+1);
   }

   public static void main(String args[]){};
}