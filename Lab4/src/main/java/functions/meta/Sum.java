package functions.meta;

import functions.Function;

public class Sum implements Function{
    private final Function a;
    private final Function b;

    public Sum(Function a, Function b){
        this.a = a;
        this.b = b;
    }

    public double getFunctionValue(double x){
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) throw new IllegalArgumentException("not in definition space");
        return a.getFunctionValue(x) + b.getFunctionValue(x);
    }

    public double getLeftDomainBorder(){
        return Math.max(a.getLeftDomainBorder(), b.getLeftDomainBorder());
    }

    public double getRightDomainBorder() {
        return Math.min(a.getRightDomainBorder(), b.getRightDomainBorder());
    }
}
