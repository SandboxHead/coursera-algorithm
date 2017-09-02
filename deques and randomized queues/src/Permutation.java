import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main (String args[]){
        RandomizedQueue que = new RandomizedQueue();
        while (StdIn.hasNextLine()){
            que.enqueue(StdIn.readString());
        }
        for (int i=0;i<Integer.parseInt(args[0]);i++){
            System.out.println(que.deqeue());
        }
    }
}
