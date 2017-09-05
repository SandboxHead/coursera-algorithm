import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int f = 0;
    private int l = 0;

    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return (size() == 0);
    }

    public int size() {
        return (arr.length + l - f) % arr.length;
    }

    public void enqueue(Item item) throws IllegalArgumentException {
        if (item == null) throw new IllegalArgumentException();
        int arr_length = arr.length;
        if (size() == arr.length - 1) {
            Item[] arr1 = (Item[]) new Object[2 * arr_length];
            for (int i = 0; i < arr_length; i++) {
                arr1[i] = arr[(f + i) % arr_length];
            }
            f = 0;
            l = arr_length - 1;
            arr = arr1;
        }
        arr[l] = item;
        l = (l + 1) % arr.length;
    }

    public Item deqeue() throws NoSuchElementException {
        if (size() == 0) throw new NoSuchElementException();
        int rand = StdRandom.uniform(f, f + size());
        Item temp = arr[rand % arr.length];
        arr[rand%arr.length] = arr[(arr.length+l-1)%arr.length];
        l=(arr.length+l-1)%arr.length;
        int arr_length = arr.length;
        if (size() < arr_length / 4) {
            Item[] arr1 = (Item[]) new Object[arr.length / 2];
            for (int i = 0; i < arr1.length; i++) {
                arr1[i] = arr[(f + i) % arr.length];
            }
            f = 0;
            l = size();
            arr = arr1;
        }
        return temp;
    }


    public Item sample() throws NoSuchElementException {
        if (size() == 0) throw new NoSuchElementException();
        int rand = StdRandom.uniform(f, f + l);
        return arr[rand % arr.length];
    }

    public class queueIterator implements Iterator<Item> {
        private Item[] arr1 = (Item[]) new Object[size()];
        private int l = arr1.length;

        private queueIterator() {
            //RandomizedQueue r = new RandomizedQueue();
            for (int i = 0; i < arr1.length; i++) arr1[i] = arr[(f + i) % arr.length];
        }

        public boolean hasNext() {
            return (l != 0);
        }

        public Item next() throws NoSuchElementException {
            if (!hasNext()) throw new NoSuchElementException();
            int rand = StdRandom.uniform(l);
            Item temp = arr1[rand];
            arr1[rand] = arr1[l-1];
            l--;
            return temp;
        }
        public void remove()throws UnsupportedOperationException{
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return new queueIterator();
    }

  /*  public static void main(String args[]) {
        RandomizedQueue<Integer> d = new RandomizedQueue<>();
        d.enqueue(10);
        //System.out.println(d.removeLast());
        d.enqueue(20);
        d.enqueue(30);
        d.enqueue(40);
        d.enqueue(50);
        System.out.println(d.deqeue());
        System.out.println(d.deqeue());
        for (int i : d) {
            System.out.println(i);
        }

    }*/
}



