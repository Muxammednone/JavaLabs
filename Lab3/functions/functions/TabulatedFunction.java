package functions.functions;

import functions.exceptions.InappropriateFunctionPointException;
import functions.points.FunctionPoint;

public interface TabulatedFunction<T extends FunctionPoint> {
    int getPointsCount();
    double getLeftDomainBorder();
    double getRightDomainBorder();
    double getFunctionValue(double x);
    T getPoint(int index);
    void setPoint(int index, T point) throws InappropriateFunctionPointException;
    double getPointX(int index);
    void setPointX(int index, double y) throws InappropriateFunctionPointException;
    double getPointY(int index);
    void setPointY(int index, double y);
    void deletePoint(int index);
    void addPoint(T point) throws InappropriateFunctionPointException;
}
