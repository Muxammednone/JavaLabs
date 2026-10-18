package functions.factories;

import functions.points.FunctionPoint;

public class FunctionPointFactory implements PointFactory<FunctionPoint> {
    public FunctionPoint create(double x, double y){
        return new FunctionPoint(x, y);
    }

    public FunctionPoint copy(FunctionPoint point){
        return new FunctionPoint(point.getX(), point.getY());
    }

    public FunctionPoint[] createArray(int size){
        return new FunctionPoint[size];
    }
}
