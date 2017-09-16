import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class LinkedList implements CompressedImageInterface {

    private class ListNode {
        private int element;
        private ListNode next;

        public ListNode(int element) {
            this(element, null);
        }

        public ListNode(int element, ListNode node) {
            this.element = element;
            this.next = node;
        }

        public ListNode getNext() {
            return next;
        }

        public int getElement() {
            return element;
        }

        public void setNext(ListNode n) {
            next = n;
        }
    }

    private ListNode[] image;
    int width, height;
    public LinkedList(String filename) throws FileNotFoundException {

        String[] wh;
        String[] row;
        File file = new File(filename);
        Scanner input = new Scanner(file);
        //System.out.println(input.nextLine());
        wh = input.nextLine().split("\\s");
        //System.out.println(wh.length);
        height = Integer.parseInt(wh[0]);
        width = Integer.parseInt(wh[1]);
        image = new ListNode[height + 1];
        ListNode one = new ListNode(height);
        image[0] = new ListNode(width, one);
        for (int h = 1; h < height + 1; h++) {
            row = input.nextLine().split("\\s");
            String previous = "1";
            ListNode head = new ListNode(-1);
            for (int w = width - 1; w > 0; w--) {
                String current = row[w];
                String next = row[w - 1];
                if (previous.equals("1") && current.equals("0")) {
                    head = new ListNode(w, head);
                }
                if (next.equals("1") && current.equals("0")) {
                    head = new ListNode(w, head);
                }
                previous = current;
            }
            if (row[0].equals("0")) {
                head = new ListNode(0, head);
            }
            if (row[0].equals("0") && previous.equals("1")){
                head = new ListNode(0,head);
            }
            image[h] = head;
        }
        //you need to implement this
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    public LinkedList(boolean[][] grid, int width, int height) {
        this.height = height;
        this.width = width;
        image = new ListNode[height + 1];
        ListNode one = new ListNode(height);
        image[0] = new ListNode(width, one);
        for (int h = 1; h < height + 1; h++) {
            boolean previous = true;
            ListNode head = new ListNode(-1);
            for (int w = width - 1; w > 0; w++) {
                boolean current = grid[h - 1][w];
                boolean next = grid[h - 1][w - 1];
                if (previous && !current) {
                    head = new ListNode(w, head);
                }
                if (!current && next) {
                    head = new ListNode(w, head);
                }
                previous = current;
            }
            if (!grid[h - 1][0]) {
                head = new ListNode(0, head);
            }
            image[h] = head;
        }

    }

    public boolean getPixelValue(int x, int y)  throws PixelOutOfBoundException{
        if (x>height-1 || y>width-1) throw new PixelOutOfBoundException("This value of x and y is not valid.");
        ListNode row = image[x + 1];
        int count = 0;
        while (row.getElement() != -1) {
            if (row.getElement() == y) return false;
            if (row.getElement() > y) break;
            count++;
            row = row.getNext();
        }
        return count % 2 == 0;

    }

    public void setPixelValue(int x, int y, boolean val)//to be done
    {
        ListNode row = image[x + 1];
        ListNode previous;
        while (true) {
            if (row.getElement() < y) {
                previous = row;
                row = row.getNext();
            }
        }
        //you need to implement this
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    public int[] numberOfBlackPixels() {
        int[] Black = new int[image.length - 1];
        int total = 0;
        int count;
        for (int h = 1; h < image.length; h++) {
            count = 0;
            ListNode current = image[h];
            while (current.getElement() != -1) {
                count = count + (current.getNext().getElement() - current.getElement() + 1);
                current = current.getNext().getNext();
            }
            total =total+count;
            Black[h - 1] = count;
        }
        System.out.println(total);
        return Black;
    }

    public void invert() {
        for (int h = 1; h < image.length; h++) {
            ListNode current = image[h];
            if (current.getElement() == 0) ;
        }
        //you need to implement this
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void performAnd(CompressedImageInterface img) {
        //you need to implement this
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void performOr(CompressedImageInterface img) {
        //you need to implement this
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void performXor(CompressedImageInterface img) {
        //you need to implement this
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public String toStringUnCompressed() {
        //you need to implement this
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public String toStringCompressed() {
        //you need to implement this
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static void main(String[] args) throws FileNotFoundException, PixelOutOfBoundException {
        // testing all methods here :
        boolean success = true;

        // check constructor from file
        CompressedImageInterface img1 = new LinkedList("C:\\Users\\hp\\Desktop\\coding\\java\\coursera\\algorithm\\assigment2\\src\\sampleInputFile.txt");
        img1.getPixelValue(6,5);
        img1.numberOfBlackPixels();


    }
}
