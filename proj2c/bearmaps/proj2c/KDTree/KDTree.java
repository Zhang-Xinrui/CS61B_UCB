package bearmaps.proj2c.KDTree;

import java.util.Comparator;
import java.util.List;

public class KDTree {
    private KDTreeNode root;
    //Attributes below are used when finding the nearest point.
    private double nearestDistance;
    private Point nearestPoint;
    private Point target;
/**
 * Create a balanced KDTree and choose the dimension having the max variance to be used by root.
 * @author ZXR 03-17-20
 */
    public KDTree(List<Point> points) {
        double meanX = 0, meanY = 0, varianceX = 0, varianceY = 0;
        for (Point point: points) {
            meanX += point.getX();
            meanY += point.getY();
        }
        meanX /= points.size(); meanY /= points.size();
        for (Point point: points) {
            varianceX += Math.pow(point.getX() - meanX, 2);
            varianceY += Math.pow(point.getY() - meanY, 2);
        }
        boolean dimension;
        if (varianceX > varianceY) {
            dimension = true;
        } else {
            dimension = false;
        }
        root = help(points, dimension);
    }
    //Need to sort many times, any improved algorithms??
    private KDTreeNode help(List<Point> points, boolean dimension) {
        if (points == null || points.size() == 0) {
            return null;
        }
        if (dimension) {
            points.sort(new Comparex());
        } else {
            points.sort(new Comparey());
        }
        int rootIndex = points.size() / 2;
        KDTreeNode root = new KDTreeNode(points.get(rootIndex), dimension);
        root.setLeft(help(points.subList(0, rootIndex), !dimension));
        root.setRight(help(points.subList(rootIndex + 1, points.size()), !dimension));
        return root;
    }

    private class Comparex implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            return Double.compare(p1.getX(), p2.getX());
        }
    }
    private class Comparey implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            return Double.compare(p1.getY(), p2.getY());
        }
    }

    /**
     *  The KDTree has at least one Node.
     */
    public Point nearest(double x, double y) {
        target = new Point(x, y);
        nearestDistance = Point.distance(root.point, target);
        nearestPoint = root.getPoint();
        nearestHelp(root);
        return nearestPoint;
    }
    private void nearestHelp(KDTreeNode root) {
        double rootX, rootY, leftDistance, rightDistance;
        rootX = root.getPoint().getX();
        rootY = root.getPoint().getY();
        double minDistance;         //Used to estimate minimum Distance of the bad side of the root.
        boolean leftFirst = false;
        if (root.getDimension()) {
            minDistance = Math.abs(rootX - target.getX());
            if (target.getX() < rootX) {
                leftFirst = true;
            }
        } else {
            minDistance = Math.abs(rootY - target.getY());
            if (target.getY() < rootY) {
                leftFirst = true;
            }
        }
        if (leftFirst) {
            if (root.getLeft() != null) {
                leftDistance = Point.distance(target, root.getLeft().getPoint());
                if (leftDistance < nearestDistance) {
                    nearestDistance = leftDistance;
                    nearestPoint = root.getLeft().getPoint();
                }
                nearestHelp(root.getLeft());
            }
            if (root.getRight() != null && minDistance < nearestDistance) {
                rightDistance = Point.distance(target, root.getRight().getPoint());
                if (rightDistance < nearestDistance) {
                    nearestDistance = rightDistance;
                    nearestPoint = root.getRight().getPoint();
                }
                nearestHelp(root.getRight());
            }
        } else {    //right node first
            if (root.getRight() != null) {
                rightDistance = Point.distance(target, root.getRight().getPoint());
                if (rightDistance < nearestDistance) {
                    nearestDistance = rightDistance;
                    nearestPoint = root.getRight().getPoint();
                }
                nearestHelp(root.getRight());
            }
            if (root.getLeft() != null && minDistance < nearestDistance) {
                leftDistance = Point.distance(target, root.getLeft().getPoint());
                if (leftDistance < nearestDistance) {
                    nearestDistance = leftDistance;
                    nearestPoint = root.getLeft().getPoint();
                }
                nearestHelp(root.getLeft());
            }
        }
    }
    private class KDTreeNode {
        private Point point;
        private KDTreeNode left, right;
        private boolean dimension; // true for x and false for y

        public KDTreeNode(Point point, boolean dimension) {
            this.point = point; this.dimension = dimension;
            left = null; right = null;
        }
        public Point getPoint() { return point;}
        public KDTreeNode getLeft() { return left;}
        public KDTreeNode getRight() { return right;}
        public boolean getDimension() { return dimension;}
        public void setLeft(KDTreeNode left) {
            this.left = left;
        }
        public void setRight(KDTreeNode right) {
            this.right = right;
        }
    }
}
