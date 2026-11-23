package functions.meta;

import functions.Function;

public class Scale implements Function{
    private final Function a;
    private double h; // horizontal
    private double v; // vertical

    public Scale(Function a, double h, double v){
        this.a = a;
        this.h = h;
        this.v = v;
    }

    public double getLeftDomainBorder(){
        return a.getLeftDomainBorder() * h;
    }

    public double getRightDomainBorder(){
        return a.getRightDomainBorder() * h;
    }

    public double getFunctionValue(double x){
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) throw new IllegalArgumentException("not in definition space");
        return a.getFunctionValue(x) * v;
    }
}
