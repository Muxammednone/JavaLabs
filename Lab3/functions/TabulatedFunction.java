package functions;

public interface TabulatedFunction<T extends FunctionPoint> {
    int getPointsCount();
    double getLeftDomainBorder();
    double getRightDomainBorder();
    double getFunctionValue(double x);
    FunctionPoint getPoint(int index);
    void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;
    double getPointX(int index);
    void setPointX(int index, double y) throws InappropriateFunctionPointException;
    double getPointY(int index);
    void setPointY(int index, double y);
    void deletePoint(int index);
    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;
}
