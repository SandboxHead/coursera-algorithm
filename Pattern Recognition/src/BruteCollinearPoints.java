import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
public class BruteCollinearPoints {
    private Point[] p;
    public BruteCollinearPoints(Point[] points) {
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
        this.p = points;
    }
    public int numberOfSegments(){
        return segments().length;

    }
    public LineSegment[] segments(){
        int count=0;
        for (int i=0; i<p.length;i++)
            for (int j=i+1; j<p.length;j++)
                for (int k=j+1; k<p.length; k++)
                    for (int l=k+1; l<p.length; l++){
                        if ( p[i].slopeOrder().compare(p[j],p[k])==0 && p[i].slopeOrder().compare(p[j],p[l])==0){
                            count++;
                        }
                    }
        //StdOut.println(count);
        LineSegment[] segs = new LineSegment[count];
        int arr_count = 0;
        for (int i=0; i<p.length;i++)
            for (int j=i+1; j<p.length;j++)
                for (int k=j+1; k<p.length; k++)
                    for (int l=k+1; l<p.length; l++){
                        if ( p[i].slopeOrder().compare(p[j],p[k])==0 && p[i].slopeOrder().compare(p[j],p[l])==0){
                            Point[] po = {p[i],p[j],p[k],p[l]};
                            Point max = po[0];
                            Point min = po[0];
                            for (int o=1; o<4;o++){
                                //StdOut.println(po[o]);
                                if (po[o].compareTo(max)>0)max=po[o];
                                if (po[o].compareTo(min)<0)min= po[o];
                            }
                            //StdOut.println(min );
                            //StdOut.println(max);
                            segs[arr_count]=new LineSegment(max,min);
                            arr_count++;
                        }
                    }
        return segs;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {

            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
