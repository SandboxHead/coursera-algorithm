import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private Point[] points;

    public FastCollinearPoints(Point[] points) {
        //if (points.length==0) throw new IllegalArgumentException();
        if (points == null) throw new IllegalArgumentException();
        if(points.length==0) throw new IllegalArgumentException();
        for (int i=0; i<points.length;i++){
            if (points[i]==null) {
                throw new IllegalArgumentException();
            }
        }
        for (int i=0;i<points.length;i++){
            for (int j=i+1;j<points.length;j++){
                if (points[i].compareTo(points[j])==0) throw new IllegalArgumentException();
            }
        }
        this.points = points;
    }

    public int numberOfSegments() {
        return segments().length;
    }

    public LineSegment[] segments() {
        int segs_count = 0;
        LineSegment[] result = new LineSegment[0];

        for (int i = 0; i < points.length; i++) {
            Point[] points_temp = new Point[points.length];
            for (int x = 0; x < points_temp.length; x++) points_temp[x] = points[x];
            sort(points[i], points_temp);
            int same = 1;
            if (points_temp.length > 3) {
                Point previous = points_temp[1];
                int count = 0;
                for (int h = 1; h < points_temp.length; h++) {
                    Point current = points_temp[h];

                    if (points[i].slopeOrder().compare(current, previous) == 0) {
                        same++;
                        if (same > 3) {
                            count++;
                        }
                    } else {

                        same = 2;
                    }
                    previous = current;
                }
                


                same = 1;
                LineSegment[] segs = new LineSegment[count];
                count = 0;
                Point min = points_temp[0];
                Point max = points_temp[0];
                previous = points_temp[1];
                for (int h = 1; h < points_temp.length; h++) {
                    Point current = points_temp[h];
                    if (points[i].slopeOrder().compare(current, previous) == 0) {
                        same++;
                        if (min.compareTo(current) > 0) min = points_temp[h];
                        if (max.compareTo(current) < 0) max = points_temp[h];
                    } else {
                        if (same > 3) {
                            segs[count] = new LineSegment(min, max);
                            count++;
                        }
                        if (points_temp[0].compareTo(current) > 0) {
                            min = current;
                            max = points_temp[0];
                        } else {
                            min = points_temp[0];
                            max = current;
                        }
                        same = 2;
                    }
                    previous = current;
                }
                if (same > 3) {
                    segs[count] = new LineSegment(min, max);
                    count++;
                }
                segs_count = segs_count + count;
                LineSegment[] temp = new LineSegment[segs_count];
                for (int a = 0; a < count; a++) temp[a] = segs[a];
                for (int a = 0; a < segs_count - count; a++) temp[count + a] = result[a];
                result = temp;
            }
        }
            int count = 0;
            int count1 = 0;
            for (int a = 0; a < segs_count; a++) {
                int temp_count = 0;
                for (int b = a + 1; b < segs_count; b++) {
                    if (result[a].toString().equals(result[b].toString())) break;
                    temp_count = b;
                }
                if (temp_count == segs_count - 1) count++;
            }
            if (result.length==0)return result;
            LineSegment[] seggg = new LineSegment[count + 1];
            count = 0;
            for (int a = 0; a < segs_count; a++) {
                int temp_count = 0;
                for (int b = a + 1; b < segs_count; b++) {
                    if (result[a].toString().equals(result[b].toString())) break;
                    temp_count = b;
                }
                if (temp_count == segs_count - 1) {
                    seggg[count] = result[a];
                    count++;
                }
            }
            seggg[seggg.length - 1] = result[result.length - 1];
            return seggg;

        }

    private static void merge(Point c, Point[]p, Point[]poi, int lo, int mid, int hi){
        for (int k = lo;k<=hi;k++){
            poi[k]=p[k];
        }
        int i = lo, j=mid+1;
        for (int k = lo;k<=hi; k++){
            if (i>mid)       {
                p[k]=poi[j];
                j++;
            }
            else if(j>hi)     {
                p[k] = poi[i];
                i++;
            }
            else if (c.slopeOrder().compare(poi[i],poi[j])>0) {
                p[k] = poi[j];
                j++;
            }
            else   {
                p[k] = poi[i];
                i++;
            }
        }
    }
    private static void sort(Point c, Point[]p, Point[]poi, int lo, int hi){
        if (hi<=lo) return;
        int mid = lo+(hi-lo)/2;
        sort(c,p,poi,lo,mid);
        sort(c,p, poi,mid+1,hi);
        merge(c,p, poi,lo,mid,hi);
    }
    private static void sort(Point c,Point[] p){
        Point[] poi = new Point[p.length];
        sort(c,p,poi,0,p.length-1);
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
        StdOut.println(collinear.segments().length);
        StdDraw.show();
    }


}

