package functions.factories;

import functions.points.FunctionPoint;

public interface PointFactory<T extends FunctionPoint> {
    T create(double x, double y);
    T copy(T point);
    T[] createArray(int len);
}