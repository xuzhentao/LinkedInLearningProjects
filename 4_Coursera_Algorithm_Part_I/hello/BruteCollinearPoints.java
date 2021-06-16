/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BruteCollinearPoints {

    private final List<LineSegment> results = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        // sanity check
        if (points == null) throw new IllegalArgumentException("points == null");
        for (Point point : points)
            if (point == null)
                throw new IllegalArgumentException("point " + point + " can't be null");
        for (int idx1 = 0; idx1 < points.length - 1; idx1++)
            for (int idx2 = idx1 + 1; idx2 < points.length; idx2++)
                if (points[idx1].equals(points[idx2]))
                    throw new IllegalArgumentException(
                            "duplicated point detected " + points[idx1] + " and " + points[idx2]);


        // start working.
        if (points.length < 4)
            return;

        for (int idx1 = 0; idx1 < points.length - 3; idx1++) {
            for (int idx2 = idx1 + 1; idx2 < points.length - 2; idx2++) {
                for (int idx3 = idx2 + 1; idx3 < points.length - 1; idx3++) {
                    for (int idx4 = idx3 + 1; idx4 < points.length - 0; idx4++) {
                        Point pt1 = points[idx1];
                        Point pt2 = points[idx2];
                        Point pt3 = points[idx3];
                        Point pt4 = points[idx4];
                        double slope12 = pt1.slopeTo(pt2);
                        double slope23 = pt2.slopeTo(pt3);
                        double slope34 = pt3.slopeTo(pt4);
                        if (slope12 == slope23 && slope23 == slope34) {
                            ArrayList<Point> listPoints = new ArrayList<Point>();
                            listPoints.add(pt1);
                            listPoints.add(pt2);
                            listPoints.add(pt3);
                            listPoints.add(pt4);
                            Point minPoint = min(listPoints);
                            Point maxPoint = max(listPoints);
                            results.add(new LineSegment(minPoint, maxPoint));
                        }
                    }
                }
            }
        }

    }

    private Point min(ArrayList<Point> listPoints) {
        Point res = listPoints.get(0);
        for (Point point : listPoints) {
            if (point.compareTo(res) < 0)
                res = point;
        }
        return res;
    }

    private Point max(ArrayList<Point> listPoints) {
        Point res = listPoints.get(0);
        for (Point point : listPoints) {
            if (point.compareTo(res) > 0)
                res = point;
        }
        return res;
    }

    public static void main(String[] args) {
        Point[] points = {
                new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(3, 0),
                new Point(3, 1), new Point(3, 2)
        };
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        System.out.println(Arrays.toString(bcp.segments()));
    }

    public LineSegment[] segments() {
        LineSegment[] ls = new LineSegment[results.size()];
        for (int idx = 0; idx < results.size(); idx++)
            ls[idx] = results.get(idx);
        return ls;
    }

    public int numberOfSegments() {
        return results.size();
    }
}
