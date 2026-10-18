package functions.points;

public class FunctionPoint {

    private double x, y;

    public double getX(){
        return this.x;
    }

    public void setX(double value){
        this.x = value;
    }

    public double getY(){
        return this.y;
    }

    public void setY(double value){
        this.y = value;
    }

    public FunctionPoint(double x, double y){
        this.x = x;
        this.y = y;
    }

    public FunctionPoint(FunctionPoint point){
        this.x = point.x;
        this.y = point.y;
    }

    public FunctionPoint(){
        this.x = 0;
        this.y = 0;
    }
}