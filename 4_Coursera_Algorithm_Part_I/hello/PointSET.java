import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> setPoint = new TreeSet<>();

    public PointSET() {
    }

    public static void main(String[] args) {
        PointSET pSet = new PointSET();
        pSet.insert(new Point2D(0.0, 0.5));
        pSet.insert(new Point2D(0.1, 0.5));
        pSet.insert(new Point2D(0.2, 0.5));
        pSet.insert(new Point2D(0.3, 0.5));
        pSet.insert(new Point2D(0.4, 0.5));
        pSet.insert(new Point2D(0.5, 0.5));
        System.out.println(pSet);
        RectHV rec = new RectHV(-1.0, 0.0, 0.7, 1.0);
        System.out.println(pSet.range(rec));

        Point2D point2D = new Point2D(0.32, 0.5);
        System.out.println(pSet.nearest(point2D));


    }

    public void insert(Point2D p) {
        if (p.equals(null)) throw new IllegalArgumentException();
        setPoint.add(p);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect.equals(null)) {
            throw new IllegalArgumentException();
        }
        List<Point2D> res = new ArrayList<>();
        for (Point2D point2D : setPoint) {
            if (rect.contains(point2D)) {
                res.add(point2D);
            }
        }
        return res;
    }

    public Point2D nearest(Point2D p) {
        if (p.equals(null)) throw new IllegalArgumentException();

        if (isEmpty()) {
            return null;
        }
        else {
            Point2D res = null;
            double minDistance = Double.POSITIVE_INFINITY;
            for (Point2D point2D : setPoint) {
                minDistance = Double.min(minDistance, point2D.distanceSquaredTo(p));
            }
            for (Point2D point2D : setPoint) {
                if (minDistance == point2D.distanceSquaredTo(p)) {
                    res = point2D;
                }
            }
            return res;
        }

    }

    public boolean isEmpty() {
        return setPoint.isEmpty();
    }

    public int size() {
        return setPoint.size();
    }

    public void draw() {
        for (Point2D point2D : setPoint) {
            StdDraw.point(point2D.x(), point2D.y());
        }

    }

    public boolean contains(Point2D p) {
        if (p.equals(null)) throw new IllegalArgumentException();
        return setPoint.contains(p);
    }
}
