package functions.factories;

import java.io.Serializable;

import functions.points.FunctionPointString;

public class FunctionPointStringFactory implements PointFactory<FunctionPointString>, Serializable{
    private final static long serialVersionUID = 1L;

    public FunctionPointString create(double x, double y){
        return new FunctionPointString(x, y, String.format("coordinates: {%f, %f}", x, y));
    }

    public FunctionPointString copy(FunctionPointString point){
        return new FunctionPointString(point.getX(), point.getY(), point.getS());
    }

    public FunctionPointString[] createArray(int size){
        return new FunctionPointString[size];
    }
}
