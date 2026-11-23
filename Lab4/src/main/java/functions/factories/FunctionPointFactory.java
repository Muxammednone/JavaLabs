package functions.factories;

import java.io.Serial;
import java.io.Serializable;

import functions.points.FunctionPoint;

public enum FunctionPointFactory implements PointFactory<FunctionPoint>, Serializable {
    INSTANCE;
    @Serial
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
