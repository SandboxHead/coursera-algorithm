import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Item[] arr;
    private int f = 0;
    private int l = 0;
    public Deque(){arr = (Item[]) new Object[1];}

    public boolean isEmpty(){
        return size()==0;
    }
    public void addFirst(Item item) {
        if (item==null)throw new IllegalArgumentException();
        int arr_length=arr.length;
        if (arr.length==size()+1){
            Item[] arr1 = (Item[])new Object[2*arr.length];
            for (int i=0;i<arr_length;i++){
                arr1[i]=arr[(f+i)%arr_length];
            }
            f=0;
            l=arr_length-1;
            arr=arr1;
        }
        arr[(arr.length+f-1)%arr.length]=item;
        f=(arr.length+f-1)%arr.length;
    }
    public void addLast(Item item){
        if (item==null) throw new IllegalArgumentException();
        int arr_length = arr.length;
        if (arr_length==size()+1) {
            Item[] arr1 = (Item[]) new Object[2 * arr_length];
            for (int i = 0; i < arr_length; i++) {
                arr1[i] = arr[(f + i) % arr_length];
            }
            f = 0;
            l = arr_length - 1;
            arr = arr1;
        }
        arr[l]=item;
        l=(l+1)%arr.length;
    }

    public Item removeFirst() {
        if (size()==0) throw new NoSuchElementException();
        f=(f+1)%arr.length;
        Item temp= arr[(arr.length+f-1)%arr.length];
        int arr_length=arr.length;
        if (size()<arr_length/4){
            Item[] arr1=(Item[]) new Object[arr.length/2];
            for (int i=0;i<arr1.length;i++){
                arr1[i]=arr[(f+i)%arr.length];
            }
            f=0;
            l=size();
            arr=arr1;
        }
        return temp;
    }
    public Item removeLast(){
        if (size()==0) throw new NoSuchElementException();
        l=(arr.length+l-1)%arr.length;
        Item temp= arr[l];
        int arr_length=arr.length;
        if (size()<arr_length/4){
            Item[] arr1=(Item[]) new Object[arr.length/2];
            for (int i=0;i<arr1.length;i++){
                arr1[i]=arr[(f+i)%arr.length];
            }
            f=0;
            l=size();
            arr=arr1;
        }
        return temp;
    }
    public int size(){
        return (arr.length+l-f)%arr.length;
    }

    private class DequeIterator implements Iterator<Item> {
        private int currentindex=0;

        public boolean hasNext() {
            return currentindex<size();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            currentindex++;
            //System.out.println(currentindex);
            return arr[(f+currentindex-1)%arr.length];
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }


    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String args[]){
        Deque<Integer> d = new Deque<Integer>();
        d.addFirst(0);
        //System.out.println(d.removeLast());
        d.addFirst(1);
        d.removeLast();
        d.removeLast();
        d.addFirst(4);
        System.out.println(d.removeLast());
        for (int i: d) {
            System.out.println(i);
        }

    }
}





