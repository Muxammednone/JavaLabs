package functions.meta;

import functions.Function;

public class Composition implements Function{
    private final Function a;
    private final Function b;

    public Composition(Function a, Function b){
        this.a = a;
        this.b = b;
    }

    public double getLeftDomainBorder(){
        return a.getLeftDomainBorder();
    }

    public double getRightDomainBorder(){
        return b.getRightDomainBorder();
    }

    public double getFunctionValue(double x){
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) throw new IllegalArgumentException("not in definition space");
        return a.getFunctionValue(b.getFunctionValue(x));
    }
}
