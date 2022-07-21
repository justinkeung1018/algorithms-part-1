import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        segments = new ArrayList<>();
        if (points == null) {
            throw new IllegalArgumentException("Points array cannot be null");
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

        for (int i = 0; i < sortedPoints.length; i++) {
            Point p = sortedPoints[i];
            for (int j = i + 1; j < sortedPoints.length; j++) {
                Point q = sortedPoints[j];
                for (int k = j + 1; k < sortedPoints.length; k++) {
                    Point r = sortedPoints[k];
                    for (int m = k + 1; m < sortedPoints.length; m++) {
                        Point s = sortedPoints[m];
                        double slopePQ = p.slopeTo(q);
                        double slopePR = p.slopeTo(r);
                        double slopePS = p.slopeTo(s);
                        if (slopePQ == slopePR && slopePR == slopePS) {
                            LineSegment segment = new LineSegment(p, s);
                            segments.add(segment);
                        }
                    }
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
        Point[] points = {
                new Point(31435, 26989), new Point(28058, 15925), new Point(16430, 29750),
                new Point(16430, 29750)
        };
        BruteCollinearPoints test = new BruteCollinearPoints(points);
        System.out.println(test.segments().length);
    }
}
