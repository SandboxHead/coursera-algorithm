import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int f=0;
    private int l=0;
    public RandomizedQueue(){
        arr=(Item[]) new Object[1];
    }
    public boolean isEmpty(){
        return (size()==0);
    }
    public int size(){
        return (arr.length+l-f)%arr.length;
    }
    public void enqueue(Item item) throws IllegalArgumentException{
        if (item==null) throw new IllegalArgumentException();
        int arr_length = arr.length;
        if (size()==arr.length-1){
            Item[] arr1=(Item[]) new Object[2*arr_length];
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

    public Item deqeue()throws NoSuchElementException {
        if (size()==0) throw new NoSuchElementException();
        if (size() == 0) throw new NoSuchElementException();
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
        Random ran = new Random();
        int index = (ran.nextInt(size())+f)%arr.length;
        Item ret = arr[index];
        for (int j=0; j<size()-index-1;j++){
            arr[(index+j)%arr.length]=arr[(index+j+1)%arr.length];
        }
        l--;
        return ret;
    }



    public Item sample() throws  NoSuchElementException{
        if (size()==0) throw new NoSuchElementException();
        Random rand = new Random();
        return arr[(rand.nextInt(size())+f)%arr.length];

    }

    public class queueIterator implements Iterator {
        private int currentIndex=0;
        public boolean hasNext(){
            return (currentIndex<size());
        }
        public Item next(){

            return arr[currentIndex];
        }
    }

    public Iterator<Item> iterator() {
        return null;
    }
}



