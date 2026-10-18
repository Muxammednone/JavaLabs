package functions;

public class ArrayTabulatedFunction<T extends FunctionPoint> implements TabulatedFunction<T>{
    private T[] points;
    private int len;
    private int capacity;

    public int getCapacity(){
        return capacity;
    }

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount){
        this(leftX, rightX,  new double[pointsCount]);
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values){
        int n = values.length;
        if (rightX <= leftX || n < 2) throw new IllegalArgumentException();

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

    @Override
    public double getLeftDomainBorder(){
        return points[0].getX();
    }

    @Override
    public double getRightDomainBorder(){
        return points[len - 1].getX();
    }

    @Override
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


    @Override
    public int getPointsCount(){
        return len;
    }

    @Override
    public FunctionPoint getPoint(int index){
        if (index < 0 || index >= len) throw new FunctionPointIndexOutOfBoundsException("function point index out of bounds");
        // Инкапсуляцию поправил, getPoint возвращает копию 
        // чтобы мы не могли изменить эту точку в массиве
        // для этого у меня есть сеттеры
        return new FunctionPoint(points[index]);
    }

    @Override
    public void setPoint(int index, FunctionPoint point)
        throws InappropriateFunctionPointException{
            if (index < 0 || index >= len) throw new FunctionPointIndexOutOfBoundsException("function point index out of bounds");
            // улучшил проверку интервала
            if (
                (index > 0 && point.getX() <= getPointX(index - 1)) || 
                (index < len - 1 && point.getX() >= getPointX(index  + 1))
            ) throw new InappropriateFunctionPointException("inappropriate exception");

            points[index] = new FunctionPoint(point);
    }   

    @Override
    public double getPointX(int index){
        if (index < 0 || index >= len) throw new FunctionPointIndexOutOfBoundsException("function point index out of bounds");
        return points[index].getX();
    }

    @Override
    public void setPointX(int index, double x)
        throws InappropriateFunctionPointException{
            if (index < 0 || index >= len) throw new FunctionPointIndexOutOfBoundsException("function point index out of bounds");
            if (
                (index > 0 && x <= getPointX(index - 1)) || 
                (index < len - 1 && x >= getPointX(index  + 1))
            ) throw new InappropriateFunctionPointException("inappropriate exception");

            points[index].setX(x);
    }

    @Override
    public double getPointY(int index){
        if (index < 0 || index >= len) throw new FunctionPointIndexOutOfBoundsException("function point index out of bounds");
        return points[index].getY();
    }

    @Override
    public void setPointY(int index, double y){
        if (index < 0 || index >= len) throw new FunctionPointIndexOutOfBoundsException("function point index out of bounds");
        points[index].setY(y);
    }

    @Override
    public void deletePoint(int index){
        if (len < 3) throw new IllegalStateException("removing from array thats en less than 3");
        if (index < 0 || index >= len) throw new FunctionPointIndexOutOfBoundsException("function point index out of bounds");
        for (int i = index; i < len - 1; i++){
            points[i] = points[i + 1];
        }
        points[len - 1] = null;
        len--;
    }

    @Override
    public void addPoint(FunctionPoint point)
        throws InappropriateFunctionPointException{
        // нахождение интервала и проверка на равенство точек
        // потом проверка на выделение памяти
        // сдвиг елементов

        int di = 0;
        while (di != len && point.getX()  > getPointX(di)){
            di++;
        }

        if (di < len && point.getX() == getPointX(di)) {
            throw new InappropriateFunctionPointException("inappropriate exception"); 
        }


        if (len == capacity){
            FunctionPoint[] newPoints = new FunctionPoint[capacity * 2];
            System.arraycopy(points, 0, newPoints, 0, len);
            this.points = newPoints;
            capacity = capacity * 2;
        }

        
        for (int i = len; i > di; i--){
            points[i] = points[i - 1];
        }
        points[di] = new FunctionPoint(point);
        len++;
    }

}
