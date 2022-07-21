import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        segments = new ArrayList<>();
        if (points == null) {
            throw new IllegalArgumentException("Constructor argument cannot be null");
        }
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("All points must not be null");
            }
        }

        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);

        for (int i = 0; i < sortedPoints.length - 1; i++) {
            Point currentPoint = sortedPoints[i];
            Point nextPoint = sortedPoints[i + 1];
            double slope = currentPoint.slopeTo(nextPoint);
            if (slope == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException(
                        "There should not be any repeated points");
            }
        }

        for (Point point : sortedPoints) {
            // Sorted by slope, then by natural order
            Point[] pointsSortedBySlope = sortedPoints.clone();
            Arrays.sort(pointsSortedBySlope, point.slopeOrder());
            ArrayList<Point> pointsWithSameSlope = new ArrayList<>();
            for (int i = 0; i < pointsSortedBySlope.length; i++) {
                Point otherPoint = pointsSortedBySlope[i];
                if (point.equals(otherPoint)) {
                    continue;
                }
                if (pointsWithSameSlope.isEmpty()) {
                    pointsWithSameSlope.add(otherPoint);
                    continue;
                }
                double targetSlope = point.slopeTo(pointsWithSameSlope.get(0));
                double currentSlope = point.slopeTo(otherPoint);
                if (currentSlope == targetSlope) {
                    pointsWithSameSlope.add(otherPoint);
                }
                if (currentSlope != targetSlope || i == pointsSortedBySlope.length - 1) {
                    Point nextPoint = pointsWithSameSlope.get(0);
                    // The first condition ensures there are at least 4 collinear points.
                    // The second condition ensures point (the iteration variable in the outermost for loop) is the lower end of the segment.
                    // The last point in pointsWithSameSlope is guaranteed to be the upper end of the segment
                    // since mergesort is stable and preserves the natural order after sorting the points by slope.
                    if (pointsWithSameSlope.size() >= 3 && point.compareTo(nextPoint) < 0) {
                        Point end = pointsWithSameSlope.get(pointsWithSameSlope.size() - 1);
                        LineSegment segment = new LineSegment(point, end);
                        segments.add(segment);
                    }
                    pointsWithSameSlope.clear();
                    pointsWithSameSlope.add(otherPoint);
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
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
