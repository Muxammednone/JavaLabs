package functions.points;

import java.io.Serializable;
import java.util.Objects;

public class FunctionPoint implements Serializable{
    private final static long serialVersionUID = 1L;
    
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
    @Override 
    public String toString(){
        return String.format("coordinates: {%f, %f}", x, y);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.x, this.y);
    }

    @Override 
    public boolean equals(Object object){
        if (this == object) return true;
        if (!(object instanceof FunctionPoint)) return false;
        FunctionPoint point = (FunctionPoint) object;
        return this.x == point.x && this.y == point.y;
    }

    @Override 
    public FunctionPoint clone(){
        return new FunctionPoint(this.x, this.y);
    }

    
}
