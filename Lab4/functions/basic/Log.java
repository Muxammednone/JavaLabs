package functions.basic;

import functions.Function;

public class Log implements Function {
    private final double base;

    public Log(double base){
        if (!Double.isFinite(base) || base <= 0 || base == 1) {
            throw new IllegalArgumentException("Invalid logarithm base");
        }
        this.base = base;
    }

    public double getLeftDomainBorder() {
        return 0;
    }
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    public double getFunctionValue(double x){
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) throw new IllegalArgumentException("not in def area");
        return Math.log(x) / Math.log(base);
    }
}
