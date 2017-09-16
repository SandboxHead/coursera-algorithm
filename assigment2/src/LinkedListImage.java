import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class LinkedListImage implements CompressedImageInterface {

    private class ListNode{
        private int element;
        private ListNode next;
        public ListNode(int element){
            this(element, null);
        }
        public ListNode(int element, ListNode node){
            this.element = element;
            this.next  = node;
        }
        public ListNode getNext(){return next;}

        public int getElement() {
            return element;
        }
        public void setNext(ListNode n){next = n;}
    }

    private ListNode[] image ;
	public LinkedListImage(String filename) throws FileNotFoundException
	{
	    int width, height;
	    String[] wh;
	    String[] row;
        File file = new File(filename);
        Scanner input = new Scanner(file);
        //System.out.println(input.nextLine());
        wh = input.nextLine().split("\\s");
        //System.out.println(wh.length);
        height= Integer.parseInt(wh[0]);
        width = Integer.parseInt(wh[1]);
        image = new ListNode[height+1];
        ListNode one = new ListNode(height);
        image[0] = new ListNode(width, one);
        for(int h=1;h<height+1;h++){
            row = input.nextLine().split("\\s");
            String previous = "1";
            ListNode head = new ListNode(-1);
            for (int w=width-1; w>0;w-- ){
                String current = row[w];
                String next = row[w-1];
                if (previous.equals("1") && current.equals("0")){
                    head = new ListNode(w,head);
                }
                if (next.equals("1") && current.equals("0")){
                    head = new ListNode(w,head);
                }
                previous = current;
            }
            if (row[0].equals("0")) {
                head = new ListNode(0,head);
            }
            image[h]=head;
        }
		//you need to implement this
	}

    public LinkedListImage(boolean[][] grid, int width, int height)
    {
        image = new ListNode[height+1];
        ListNode one = new ListNode(height);
        image[0] = new ListNode(width, one);
        for (int h=1; h<height+1;h++){
            boolean previous=true;
            ListNode head = new ListNode(-1);
            for(int w=width-1; w>0;w++){
                boolean current = grid[h-1][w];
                boolean next = grid[h-1][w-1];
                if (previous && !current){
                    head = new ListNode(w, head);
                }
                if (!current && next){
                    head = new ListNode(w, head);
                }
                previous = current;
            }
            if (!grid[h-1][0]){head = new ListNode(0,head);}
            image[h]= head;
        }

    }

    public boolean getPixelValue(int x, int y)
    {
        ListNode row = image[x+1];
        int count = 0;
        while(row.getElement()!=-1){
            if (row.getElement()==y) return false;
            if (row.getElement()>y) break;
            count++;
            row = row.getNext();
        }
        return count%2==0;

    }

    public void setPixelValue(int x, int y, boolean val)//to be done
    {
        ListNode current = image[x+1];
        if (current.getElement()==-1 && !val){
            current = new ListNode(y,current);
            current = new ListNode(y,current);
            image[x+1]=current;
            return ;
        }
        int count = 0;
        ListNode previous = null;
        ListNode next;
        while(current.getElement()!=-1){
            next = current.getNext();
            if (current.getElement()==y && val){
                if (count%2==0 && next.getElement()!=y || next.getElement()!=-1){
                    current.element++;
                }
                else if (count%2!=0 && previous!=null)
                    if (previous.getElement()!=y){
                        current.element--;
                }

            }
            if (current.getElement()==y && val && next.getElement()==y){
                if (previous!=null){
                    image[x+1]=next.getNext();
                }
                else previous.next = next.getNext();
            }

        }
		//you need to implement this
		//throw new UnsupportedOperationException("Not implemented yet.");
    }

    public int[] numberOfBlackPixels()
    {
        int[] Black = new int[image.length-1];
        int count;
        for (int h=1;h<image.length;h++){
            count = 0;
            ListNode current = image[h];
            while(current.getElement()!=-1){
                count=count+(current.getNext().getElement()-current.getElement()+1);
                current = current.getNext().getNext();
            }
            Black[h-1] = count;
        }
		return Black;
    }
    
    public void invert()
    {
        for (int h =1;h<image.length;h++){
            ListNode current = image[h];
            if (current.getElement()==0);
        }
		//you need to implement this
		throw new UnsupportedOperationException("Not implemented yet.");
    }
    
    public void performAnd(CompressedImageInterface img)
    {
		//you need to implement this
		throw new UnsupportedOperationException("Not implemented yet.");
    }
    
    public void performOr(CompressedImageInterface img)
    {
		//you need to implement this
		throw new UnsupportedOperationException("Not implemented yet.");
    }
    
    public void performXor(CompressedImageInterface img)
    {
		//you need to implement this
		throw new UnsupportedOperationException("Not implemented yet.");
    }
    
    public String toStringUnCompressed()
    {
		//you need to implement this
		throw new UnsupportedOperationException("Not implemented yet.");
    }
    
    public String toStringCompressed()
    {
		//you need to implement this
		throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static void main(String[] args) throws FileNotFoundException {
    	// testing all methods here :
    	boolean success = true;

    	// check constructor from file
    	CompressedImageInterface img1 = new LinkedListImage("C:\\Users\\hp\\Desktop\\coding\\java\\coursera\\algorithm\\assigment2\\src\\sampleInputFile.txt");

    	// check toStringCompressed
    	String img1_compressed = img1.toStringCompressed();
    	String img_ans = "16 16, -1, 5 7 -1, 3 7 -1, 2 7 -1, 2 2 6 7 -1, 6 7 -1, 6 7 -1, 4 6 -1, 2 4 -1, 2 3 14 15 -1, 2 2 13 15 -1, 11 13 -1, 11 12 -1, 10 11 -1, 9 10 -1, 7 9 -1";
    	success = success && (img_ans.equals(img1_compressed));

    	if (!success)
    	{
    		System.out.println("Constructor (file) or toStringCompressed ERROR");
    		return;
    	}

    	// check getPixelValue
    	boolean[][] grid = new boolean[16][16];
    	for (int i = 0; i < 16; i++)
    		for (int j = 0; j < 16; j++)
    		{
                try
                {
        			grid[i][j] = img1.getPixelValue(i, j);                
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
    		}

    	// check constructor from grid
    	CompressedImageInterface img2 = new LinkedListImage(grid, 16, 16);
    	String img2_compressed = img2.toStringCompressed();
    	success = success && (img2_compressed.equals(img_ans));

    	if (!success)
    	{
    		System.out.println("Constructor (array) or toStringCompressed ERROR");
    		return;
    	}

    	// check Xor
        try
        {
        	img1.performXor(img2);       
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
    	for (int i = 0; i < 16; i++)
    		for (int j = 0; j < 16; j++)
    		{
                try
                {
        			success = success && (!img1.getPixelValue(i,j));                
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
    		}

    	if (!success)
    	{
    		System.out.println("performXor or getPixelValue ERROR");
    		return;
    	}

    	// check setPixelValue
    	for (int i = 0; i < 16; i++)
        {
            try
            {
    	    	img1.setPixelValue(i, 0, true);            
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }

    	// check numberOfBlackPixels
    	int[] img1_black = img1.numberOfBlackPixels();
    	success = success && (img1_black.length == 16);
    	for (int i = 0; i < 16 && success; i++)
    		success = success && (img1_black[i] == 15);
    	if (!success)
    	{
    		System.out.println("setPixelValue or numberOfBlackPixels ERROR");
    		return;
    	}

    	// check invert
        img1.invert();
        for (int i = 0; i < 16; i++)
        {
            try
            {
                success = success && !(img1.getPixelValue(i, 0));            
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }
        if (!success)
        {
            System.out.println("invert or getPixelValue ERROR");
            return;
        }

    	// check Or
        try
        {
            img1.performOr(img2);        
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && img1.getPixelValue(i,j);
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performOr or getPixelValue ERROR");
            return;
        }

        // check And
        try
        {
            img1.performAnd(img2);    
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && (img1.getPixelValue(i,j) == img2.getPixelValue(i,j));             
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performAnd or getPixelValue ERROR");
            return;
        }

    	// check toStringUnCompressed
        String img_ans_uncomp = "16 16, 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1, 1 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1, 1 1 1 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1 1, 1 1 0 0 0 1 1 1 1 1 1 1 1 1 1 1, 1 1 0 0 1 1 1 1 1 1 1 1 1 1 0 0, 1 1 0 1 1 1 1 1 1 1 1 1 1 0 0 0, 1 1 1 1 1 1 1 1 1 1 1 0 0 0 1 1, 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1, 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1, 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1, 1 1 1 1 1 1 1 0 0 0 1 1 1 1 1 1";
        success = success && (img1.toStringUnCompressed().equals(img_ans_uncomp)) && (img2.toStringUnCompressed().equals(img_ans));

        if (!success)
        {
            System.out.println("toStringUnCompressed ERROR");
            return;
        }
        else
            System.out.println("ALL TESTS SUCCESSFUL! YAYY!");
    }
}