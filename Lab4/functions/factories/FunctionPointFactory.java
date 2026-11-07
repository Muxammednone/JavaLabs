package functions.factories;

import java.io.Serializable;

import functions.points.FunctionPoint;

public class FunctionPointFactory implements PointFactory<FunctionPoint>, Serializable {
    private final static long serialVersionUID = 1L;
    
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
