package functions;

public class FunctionPointString extends FunctionPoint{
    private String s;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public FunctionPointString(double x, double y, String s) {
        super(x, y);
        this.s = s;
    }

    public FunctionPointString(FunctionPointString point) {
        super(point.getX(), point.getY());
        this.s = s;
    }

    public FunctionPointString(){
        super(0, 0);
        s = "no data";
    }
}
