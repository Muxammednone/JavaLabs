package functions.meta;

import functions.Function;

public class Shift implements Function{
    private final Function a;
    private double xOffset;
    private double yOffset;

    public Shift(Function a, double xOffset, double yOffset){
        this.a = a;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public double getLeftDomainBorder(){
        return a.getLeftDomainBorder();
    }

    public double getRightDomainBorder(){
        return a.getRightDomainBorder();
    }

    public double getFunctionValue(double x){
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) throw new IllegalArgumentException("not in definition space");
        return a.getFunctionValue(x + xOffset) + yOffset;
    }
}
