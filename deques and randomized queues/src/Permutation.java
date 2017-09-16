import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;


public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> que = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            que.enqueue(StdIn.readString());
        }

        Iterator<String> iterator = que.iterator();
        for (int i = 0; iterator.hasNext() && i < k; i++) {
            StdOut.println(iterator.next());
        }
    }
}