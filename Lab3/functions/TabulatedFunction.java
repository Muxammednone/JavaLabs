package functions;

public class TabulatedFunction {
    private FunctionPoint[] points;
    private int len;
    private int capacity;

    public int getLen(){
        return len;
    }
    public int getCapacity(){
        return capacity;
    }

    public TabulatedFunction(double leftX, double rightX, int pointsCount){
        this(leftX, rightX,  new double[pointsCount]);
    }

    public TabulatedFunction(double leftX, double rightX, double[] values){
        int n = values.length;
        double step = (rightX - leftX) / (n - 1);

        FunctionPoint[] points = new FunctionPoint[n * 2];

        this.len = n;
        this.capacity = n * 2;

        for (int i = 0; i < n; i++){
            double x = leftX + step * i;
            points[i] = new FunctionPoint(x, values[i]);
        }

        this.points = points;
    }

    public double getLeftDomainBorder(){
        return points[0].getX();
    }

    public double getRightDomainBorder(){
        return points[len - 1].getX();
    }

    public double getFunctionValue(double x){
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) return Double.NaN;

        int di = 0;
        while (x > points[di + 1].getX()){
            di++;
        }

        FunctionPoint leftPoint = points[di];
        FunctionPoint rightPoint = points[di + 1];

        return (x - leftPoint.getX()) * (rightPoint.getY() - leftPoint.getY()) / (rightPoint.getX() - leftPoint.getX()) + leftPoint.getY();
    }


    public int getPointsCount(){
        return len;
    }

    public FunctionPoint getPoint(int index){
        return points[index];
    }

    public void setPoint(int index, FunctionPoint point){
        if (index == 0 && point.getX() >= points[1].getX()) return;
        else if (index == getPointsCount() - 1 && point.getX() <= getPoint(getPointsCount() - 2).getX()) return;
        else if (point.getX() <= getPoint(index - 1).getX() || point.getX() >= getPoint(index + 1).getX()) return;

        points[index] = point;
    }   

    public double getPointX(int index){
        return getPoint(index).getX();
    }

    public void setPointX(int index, double x){
        FunctionPoint point = new FunctionPoint(x, getPoint(index).getY());
        setPoint(index, point);
    }

    public double getPointY(int index){
        return getPoint(index).getY();
    }

    public void setPoint(int index, double y){
        getPoint(index).setY(y);
    }

    public void deletePoint(int index){
        for (int i = index; i < len - 1; i++){
            points[i] = points[i + 1];
        }
        points[len - 1] = null;
        len--;
    }

    public void addPoint(FunctionPoint point){
        if (len == capacity){
            FunctionPoint[] newPoints = new FunctionPoint[capacity * 2];
            System.arraycopy(points, 0, newPoints, 0, len);
            this.points = newPoints;
            capacity = capacity * 2;
        }

        int di = 0;
        while (di != len && point.getX()  > getPointX(di)){
            di++;
        }

        
        for (int i = len; i > di; i--){
            points[i] = points[i - 1];
        }
        points[di] = point;
        len++;
    }

}