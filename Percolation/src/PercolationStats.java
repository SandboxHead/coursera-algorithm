import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] arr;
    private int tri;
    public PercolationStats(int n, int trials){
        if (n<0 || trials<0)throw new IllegalArgumentException();
        arr=new double[trials];
        tri=trials;

        for (int i=0;i<trials;i++) {
            Percolation perc = new Percolation(n);
            while(!perc.percolates()) {
                perc.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
                //System.out.println(i);
            }
            int x=perc.numberOfOpenSites();
            //System.out.println((double)x);
            arr[i]=((double)x/(double)(n*n));
            //System.out.println(arr[i]);
        }
    }
    public double mean(){
        return StdStats.mean(arr);
    }
    public double stddev(){
        return StdStats.stddev(arr);
    }
    public double confidenceLo(){
        return mean()-(1.96*stddev()/Math.sqrt((double)tri));
    }
    public double confidenceHi(){
        return mean()+(1.96*stddev()/Math.sqrt((double)tri));
    }

    public static void main(String[] args){
        PercolationStats p=new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        System.out.println("mean                    = "+p.mean());
        System.out.println("stddev                  = "+p.stddev());
        System.out.println("95% confidence interval = ["+p.confidenceLo()+", "+p.confidenceLo()+"]");

    }
}
