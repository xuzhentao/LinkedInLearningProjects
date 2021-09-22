import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private KdTreeNode root;
    private int size = 0;

    public KdTree() {
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));
        kdTree.draw();
        Iterable<Point2D> res = kdTree.range(new RectHV(0.7, 0.2, 1, 1));
        System.out.println(res);
        System.out.println(kdTree.contains(new Point2D(0.9, 0.6)));
        System.out.println(kdTree.nearest(new Point2D(0.45, 0.551)));
    }

    public void insert(Point2D p) {
        root = KdTreeNode.insert(root, p, 0);
        size++;
    }

    public void draw() {
        root.draw(0.0, 1.0, 0.0, 1.0);
    }

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> res = new ArrayList<>();
        KdTreeNode.range(root, res, rect);
        return res;

    }

    public boolean contains(Point2D p) {
        return KdTreeNode.contains(root, p);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        double[] minDistance = { Double.POSITIVE_INFINITY };
        KdTreeNode[] minNode = { null };
        KdTreeNode.nearest(root, p, minDistance, minNode);
        if (minNode[0] != null) {
            return minNode[0].getPoint2D();
        }
        return null;

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private static class KdTreeNode {
        Point2D point2D;
        String dir;
        KdTreeNode left;
        KdTreeNode right;

        public KdTreeNode(Point2D point2D, String dir) {
            this.point2D = point2D;
            this.dir = dir;
        }


        public static KdTreeNode insert(KdTreeNode kdTreeNode, Point2D point2D, int level) {
            // leaf case
            if (kdTreeNode == null) {
                if (level % 2 == 0) {
                    return new KdTreeNode(point2D, "x");
                }
                else {
                    return new KdTreeNode(point2D, "y");
                }
            }

            // the node kdTreeNode is vertical splitting
            if (kdTreeNode.getDir().equals("x")) {
                if (kdTreeNode.getPoint2D().x() > point2D.x())
                    kdTreeNode.setLeft(insert(kdTreeNode.getLeft(), point2D, level + 1));
                else
                    kdTreeNode.setRight(insert(kdTreeNode.getRight(), point2D, level + 1));
            }

            // the node kdTreeNode is horizontal splitting
            else {
                if (kdTreeNode.getPoint2D().y() > point2D.y())
                    kdTreeNode.setLeft(insert(kdTreeNode.getLeft(), point2D, level + 1));
                else
                    kdTreeNode.setRight(insert(kdTreeNode.getRight(), point2D, level + 1));
            }
            return kdTreeNode;
        }

        public static void nearest(KdTreeNode kdTreeNode, Point2D point2D,
                                   double[] minDistance, KdTreeNode[] minNode) {
            if (kdTreeNode == null)
                return;

            if (minDistance[0] >= point2D.distanceTo(kdTreeNode.getPoint2D())) {
                minDistance[0] = point2D.distanceTo(kdTreeNode.getPoint2D());
                minNode[0] = kdTreeNode;
            }


            KdTreeNode first, second;
            RectHV secondRec;
            if ((kdTreeNode.getDir().equals("x") && point2D.x() <= kdTreeNode.getPoint2D().x()) ||
                    (kdTreeNode.getDir().equals("y") && point2D.y() <= kdTreeNode.getPoint2D()
                                                                                 .y())) {
                first = kdTreeNode.getLeft();
                second = kdTreeNode.getRight();
            }
            else {
                first = kdTreeNode.getRight();
                second = kdTreeNode.getLeft();
            }


            double[] secondMinDistance = { minDistance[0] };
            KdTreeNode[] secondMinNode = { minNode[0] };

            if (first != null) {
                nearest(first, point2D, minDistance, minNode);
            }
            if (minDistance[0] > Math
                    .abs(kdTreeNode.getDir().equals("x") ?
                         point2D.x() - kdTreeNode.getPoint2D().x() :
                         point2D.y() - kdTreeNode.getPoint2D().y()) && second != null) {
                nearest(second, point2D, secondMinDistance, secondMinNode);
                if (secondMinDistance[0] < minDistance[0]) {
                    minDistance[0] = secondMinDistance[0];
                    minNode[0] = secondMinNode[0];
                }
            }
        }

        public static boolean contains(KdTreeNode kdTreeNode, Point2D point2D) {
            if (kdTreeNode == null) {
                return false;
            }

            if (point2D.equals(kdTreeNode.getPoint2D())) {
                return true;
            }

            if (kdTreeNode.getDir() == "x") {
                if (point2D.x() <= kdTreeNode.getPoint2D().x()) {
                    return contains(kdTreeNode.getLeft(), point2D);
                }
                else {
                    return contains(kdTreeNode.getRight(), point2D);
                }
            }
            else {
                if (point2D.y() <= kdTreeNode.getPoint2D().y()) {
                    return contains(kdTreeNode.getLeft(), point2D);
                }
                else {
                    return contains(kdTreeNode.getRight(), point2D);
                }
            }

        }

        public static void range(KdTreeNode kdTreeNode,
                                 List<Point2D> restPoint2D,
                                 RectHV rect) {
            if (kdTreeNode == null)
                return;

            if (rect.contains(kdTreeNode.getPoint2D())) {
                restPoint2D.add(kdTreeNode.getPoint2D());
            }

            if (kdTreeNode.getDir().equals("x")) {
                if (rect.xmin() <= kdTreeNode.getPoint2D().x()) {
                    range(kdTreeNode.getLeft(), restPoint2D,
                          new RectHV(rect.xmin(), rect.ymin(),
                                     Double.min(rect.xmax(), kdTreeNode.getPoint2D().x()),
                                     rect.ymax()));
                }

                if (kdTreeNode.getPoint2D().x() <= rect.xmax()) {
                    range(kdTreeNode.getRight(), restPoint2D,
                          new RectHV(Double.max(kdTreeNode.getPoint2D().x(), rect.xmin()),
                                     rect.ymin(), rect.xmax(), rect.ymax()));
                }
            }
            else {
                if (rect.ymin() <= kdTreeNode.getPoint2D().y()) {
                    range(kdTreeNode.getLeft(), restPoint2D,
                          new RectHV(rect.xmin(), rect.ymin(), rect.xmax(),
                                     Double.min(rect.ymax(), kdTreeNode.getPoint2D().y())));
                }
                if (rect.ymax() >= kdTreeNode.getPoint2D().y()) {
                    range(kdTreeNode.getRight(), restPoint2D,
                          new RectHV(rect.xmin(),
                                     Double.max(rect.ymin(), kdTreeNode.getPoint2D().y()),
                                     rect.xmax(), rect.ymax()));
                }

            }
        }

        public Point2D getPoint2D() {
            return point2D;
        }

        public String getDir() {
            return dir;
        }

        public KdTreeNode getLeft() {
            return left;
        }

        public void setLeft(KdTreeNode left) {
            this.left = left;
        }

        public KdTreeNode getRight() {
            return right;
        }

        public void setRight(KdTreeNode right) {
            this.right = right;
        }

        public void draw(double xMin, double xMax, double yMin, double yMax) {

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.circle(point2D.x(), point2D.y(), 0.01);
            if (dir.equals("x")) {
                StdDraw.setPenColor(StdDraw.BOOK_RED);
                StdDraw.line(point2D.x(), yMin, point2D.x(), yMax);
                if (left != null) left.draw(xMin, point2D.x(), yMin, yMax);
                if (right != null) right.draw(point2D.x(), xMax, yMin, yMax);
            }
            else {
                StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                StdDraw.line(xMin, point2D.y(), xMax, point2D.y());
                if (left != null) left.draw(xMin, xMax, yMin, point2D.y());
                if (right != null) right.draw(xMin, xMax, point2D.y(), yMax);
            }

        }
    }
}
