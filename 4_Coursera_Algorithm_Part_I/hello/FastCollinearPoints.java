/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class FastCollinearPoints {


    private final List<LineSegment> results = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
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

        // core algorithm.
        if (points.length < 4)
            return;

        Point[] pointsCopy = points.clone();
        for (int idx = 0; idx < points.length; idx++) {

            Point pivotPoint = pointsCopy[idx];
            Comparator<Point> pivotPointSlopeOrder = pivotPoint.slopeOrder();
            Arrays.sort(points, 0, points.length, pivotPointSlopeOrder);
            int ldx = 0;
            int rdx = 0;
            while (rdx < points.length) {

                // continue
                while (rdx < points.length
                        && pivotPointSlopeOrder.compare(points[ldx], points[rdx]) == 0)
                    rdx++;

                // add the index between ldx and rdx to result is allegible.
                if (rdx - ldx >= 3) {
                    ArrayList<Point> listPoints = new ArrayList<Point>();
                    listPoints.add(pivotPoint);
                    for (int tdx = ldx; tdx < rdx; tdx++) listPoints.add(points[tdx]);
                    Point minPoint = min(listPoints);
                    Point maxPoint = max(listPoints);

                    // uniqueness gurantee
                    if (minPoint.equals(pivotPoint))
                        results.add(new LineSegment(minPoint, maxPoint));
                }
                ldx = rdx;
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
                new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(6, 6),
                new Point(3, 0),
                new Point(3, 1), new Point(3, 2)
        };
        FastCollinearPoints bcp = new FastCollinearPoints(points);
        System.out.println(Arrays.toString(bcp.segments()));
        System.out.println(bcp.numberOfSegments());

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
