package functions.basic;

import functions.TrigonometricFunction;

public class Tan extends TrigonometricFunction{
    public double getFunctionValue(double x){
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) throw new IllegalArgumentException("not in def area");
        return Math.tan(x);
    }
}
