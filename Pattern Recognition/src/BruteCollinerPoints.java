public class BruteCollinearPoints {
    private Point[] p;
    public BruteCollinearPoints(Point[] points){
        this.p = points;
    }
    public int numberOfSegments(){
        return segments().length;

    }
    public LineSegment[] segments(){
        int count=0;
        for (int i=0; i<p.length;i++)
            for (int j=0; j<p.length;j++)
                for (int k=0; k<p.length; k++)
                    for (int l=0; l<p.length; l++){
                        if (p[i].slopeTo(p[j])>0){
                            double s1 = p[i].slopeTo(p[j]);
                            double s2 = p[i].slopeTo(p[k]);
                            double s3 = p[i].slopeTo(p[l]);
                            if (Double.compare(s1,s2)==0 && Double.compare(s1,s3)==0 ){
                                count++;
                            }
                        }
                    }
        LineSegment[] segs = new LineSegment[count];
        int arr_count = 0;
        for (int i=0; i<p.length;i++)
            for (int j=0; j<p.length;j++)
                for (int k=0; k<p.length; k++)
                    for (int l=0; l<p.length; l++){
                        if (p[i].slopeTo(p[j])>0){
                            double s1 = p[i].slopeTo(p[j]);
                            double s2 = p[i].slopeTo(p[k]);
                            double s3 = p[i].slopeTo(p[l]);
                            if (Double.compare(s1,s2)==0 && Double.compare(s1,s3)==0 ){
                                segs[arr_count]= new LineSegment(p[i],p[l]);
                            }
                        }
                    }
        return segs;
    }
}
