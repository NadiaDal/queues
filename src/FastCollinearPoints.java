import edu.princeton.cs.algs4.MergeX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("");
        MergeX.sort(points);
        lineSegments = findSegments(points);
    }
    // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return lineSegments.length;
    }
    // the number of line segments

    public LineSegment[] segments() {
        return lineSegments;
    }

    // the line segments
    private LineSegment[] findSegments(Point[] points) {
        List<LineSegment> list = new ArrayList<>();
        int N = points.length;
        for (int p = 0; p < N - 1; p++) {
            int l = p + 1;
            Point[] clone = new Point[N - l];
            for (int i = l; i < N; i++) {
                clone[i - (p + 1)] = points[i];
            }
            Point point = points[p];
            Arrays.sort(clone, point.slopeOrder());
            int counter = 0;
            Point lastInSegment = clone[0];
            for (int i = 1; i < N - l; i++) {
                double last = lastInSegment.slopeTo(point);
                double current = clone[i].slopeTo(point);
                boolean hasEqualSlopes = Double.compare(last, current) == 0;
                if (hasEqualSlopes) {
                    counter++;
                    if (lastInSegment.compareTo(clone[i]) < 0) lastInSegment = clone[i];
                    if (i == N - l - 1 && counter >= 2) {
                        LineSegment ls = new LineSegment(point, lastInSegment);
                        list.add(ls);
                    }
                } else {
                    if (counter >= 2) {
                        LineSegment ls = new LineSegment(point, lastInSegment);
                        list.add(ls);
                    }
                    counter = 0;
                    lastInSegment = clone[i];
                }
            }
        }
        return list.toArray(new LineSegment[]{});
    }
}
