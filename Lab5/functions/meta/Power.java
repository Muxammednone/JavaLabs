package functions.meta;

import functions.Function;

public class Power implements Function{
    private final Function a;
    private double power;

    public Power(Function a, double power){
        this.a = a;
        this.power = power;
    }

    @Override 
    public double getLeftDomainBorder(){
        return a.getLeftDomainBorder();
    }
    @Override 
    public double getRightDomainBorder(){
        return a.getRightDomainBorder();
    }

    @Override 
    public double getFunctionValue(double x){
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) throw new IllegalArgumentException("not in definition space");
        return Math.pow(a.getFunctionValue(x), power);
    }
}
