package functions.factories;

import functions.points.FunctionPointString;

public class FunctionPointStringFactory implements PointFactory<FunctionPointString>{
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
