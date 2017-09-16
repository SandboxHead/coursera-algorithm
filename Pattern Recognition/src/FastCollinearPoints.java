import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private final Point[] points;

    public FastCollinearPoints(Point[] points) {
        this.points = points;
    }

    public int numberOfSegments() {
        return segments().length;
    }

    public LineSegment[] segments() {
        int segs_count = 0;
        LineSegment[] result=new LineSegment[0];

        for (int i = 0; i < points.length; i++) {
            double[] slopes = new double[points.length];
            for (int j = 0; j < points.length; j++) {
                slopes[j] = points[i].slopeTo(points[j]);
            }
            Point[] points_temp = points;
            for (int h =0; h<slopes.length ; h++)StdOut.println(slopes[i]);
            sort(slopes,points_temp);
            for (int h =0; h<slopes.length ; h++)StdOut.println(slopes[i]);
            int same = 1;
            Point min = points_temp[0];
            Point max = points_temp[0];
            double previous = slopes[0];
            int count = 0;
            for (int h=1;h<points_temp.length;h++){
                double current = slopes[h];
                if (current==previous){
                    same++;
                }
                else{
                    if (same>3) count++;
                    same = 1;
                }
            }
            same = 1;
            LineSegment[] segs = new LineSegment[count];
            count = 0;
            for (int h=1;h<points_temp.length;h++){
                double current = slopes[h];
                if (current==previous){
                    same++;
                    if (min.compareTo(points_temp[h])<0) min=points_temp[h];
                    if (max.compareTo(points_temp[h])>0) max=points_temp[h];
                }
                else{
                    if (same>3){
                        segs[count]=new LineSegment(min,max);
                        count++;
                    }
                    min = points_temp[h];
                    max = points_temp[h];
                    same = 1;
                }
                previous = current;
            }
            segs_count=segs_count+count;
            LineSegment[] temp = new LineSegment[segs_count];
            for (int a=0;a<count;a++)temp[a]=segs[a];
            for (int a=0;a<segs_count-count;a++)temp[count+a]=result[a];
            result = temp;
        }
        int count = 0;
        for (int a=0; a<segs_count;a++){
            int temp_count=0;
            for (int b=0;b<a;b++){
                if (result[a]!=result[b]) temp_count++;
            }
            if (temp_count==a)count++;
        }
        LineSegment[] seggg = new LineSegment[count];
        count = 0;
        for (int a=0; a<segs_count;a++){
            int temp_count=0;
            for (int b=0;b<a;b++){
                if (result[a]!=result[b]) temp_count++;
            }
            if (temp_count==a){
                seggg[count] = result[a];
                count++;
            }
        }
        return seggg;
    }
    private static void merge(double[] a, double[] aux, Point[]p, Point[]poi, int lo, int mid, int hi){
        for (int k = lo;k<=hi;k++){aux[k]=a[k];
            poi[k]=p[k];
        }
        int i = lo, j=mid+1;
        for (int k = lo;k<=hi; k++){
            if (i>mid)       {
                a[k] = aux[j];
                p[k]=poi[j];
                j++;
            }
            else if(j>hi)     {
                a[k] = aux[i];
                p[k] = poi[i];
                i++;
            }
            else if (aux[j]<(aux[i])) {
                a[k] = aux[j];
                p[k] = poi[j];
                j++;
            }
            else   {
                a[k] = aux[i];
                p[k] = poi[i];
                i++;
            }
        }
    }
    private static void sort(double[] a, double[] aux, Point[]p, Point[]poi, int lo, int hi){
        if (hi<=lo) return;
        int mid = lo+(hi-lo)/2;
        sort(a,aux,p,poi,lo,mid);
        sort(a,aux,p, poi,mid+1,hi);
        merge(a,aux,p, poi,lo,mid,hi);
    }
    public static void sort(double[] a, Point[] p){
        Point[] poi = new Point[p.length];
        double[] aux = new double[a.length];
        sort(a,aux,p,poi,0,a.length-1);
    }
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            //StdOut.println(p);
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {

            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

