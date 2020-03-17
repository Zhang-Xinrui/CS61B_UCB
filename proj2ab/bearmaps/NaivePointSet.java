package bearmaps;

import java.util.List;

public class NaivePointSet {
    private List<Point> points;
    public NaivePointSet(List<Point> points) {
        this.points = points;
    }
    public Point nearest(double x, double y) {
        Point target = new Point(x, y); 
        Point nearestOne = points.get(0);
        Double minDistance = Point.distance(target, nearestOne);
        for (Point point: points) {
            if (Point.distance(target, point) < minDistance) {
                minDistance = Point.distance(target, point);
                nearestOne = point;
            }
        }
        return nearestOne;
    } 
}