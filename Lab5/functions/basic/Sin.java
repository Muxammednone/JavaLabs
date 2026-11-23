package functions.basic;

import functions.TrigonometricFunction;

public class Sin extends TrigonometricFunction{
    public double getFunctionValue(double x){
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) throw new IllegalArgumentException("not in def area");
        return Math.sin(x);
    }
}
