package functions.basic;

import functions.TrigonometricFunction;

public class Cos extends TrigonometricFunction{
    public double getFunctionValue(double x){
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) throw new IllegalArgumentException("not in def area");
        return Math.cos(x);
    }
}
