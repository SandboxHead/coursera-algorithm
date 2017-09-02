import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Item[] arr;
    private int f=0;
    private int l=0;
    public Deque(){arr=(Item[])new Object[1];}
    public boolean isEmpty(){
        return size()==0;
    }
    public void addFirst(Item item) throws IllegalArgumentException{
        if (item==null)throw new IllegalArgumentException();
        int arr_length=arr.length;
        if (arr.length==size()+1){
            Item[] arr1 = (Item[])new Object[2*arr_length];
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
    public void addLast(Item item)throws IllegalArgumentException{
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

    public Item removeFirst() throws NoSuchElementException{
        if (size()==0) throw new NoSuchElementException();
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
        f=(f+1)%arr.length;
        return arr[(arr.length+f-1)%arr.length];
    }
    public Item removeLast()throws NoSuchElementException{
        if (size()==0) throw new NoSuchElementException();
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
        l=(arr.length+l-1)%arr.length;
        return arr[l];
    }
    public int size(){
        return (arr.length+l-f)%arr.length;
    }

    private class DequeIterator implements Iterator {
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


    public DequeIterator iterator() {
        return new DequeIterator();
    }

    public static void main(String args[]){
        Deque<Integer> d = new Deque();
        d.addLast(2);
        //System.out.println(d.removeLast());
        d.addFirst(34);
        d.addLast(342);
        d.addFirst(878);
        d.addFirst(3045);
        d.removeLast();
        for (int i: d) {
            System.out.println(i);
        }

    }
}





