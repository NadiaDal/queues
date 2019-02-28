import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("");
        Arrays.sort(points);
        lineSegments = findSegments(points);
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments;
    }

    private LineSegment[] findSegments(Point[] points) {
        List<LineSegment> list = new ArrayList<>();
        int N = points.length;
        for (int p = 0; p < N; p++) {
            for (int q = p + 1; q < N; q++) {
                for (int r = q + 1; r < N; r++) {
                    for (int s = r + 1; s < N; s++) {
                        double pq = points[p].slopeTo(points[q]);
                        double qr = points[q].slopeTo(points[r]);
                        double qs = points[q].slopeTo(points[s]);
                        if (Double.compare(pq, qr) == 0 && Double.compare(qr, qs) == 0) {
                            LineSegment ls = new LineSegment(points[p], points[s]);
                            list.add(ls);
                        }
                    }
                }
            }
        }
        return list.toArray(new LineSegment[]{});
    }
}
