package functions.functions;

import functions.exceptions.FunctionPointIndexOutOfBoundsException;
import functions.exceptions.InappropriateFunctionPointException;
import functions.factories.PointFactory;
import functions.points.FunctionPoint;

public class LinkedListTabulatedFunction<T extends FunctionPoint> implements TabulatedFunction<T>{
    private final FunctionNode<T> head;
    private int len;

    private final PointFactory<T> factory;

    private FunctionNode<T> lastAccessed;
    private int lastAccessedIndex;

    private LinkedListTabulatedFunction(PointFactory<T> factory){
        head = new FunctionNode<T>(null, null, null);
        head.prev = head;
        head.next = head;
        this.factory = factory;
    }

    private FunctionNode<T> getNodeByIndex(int index){
        FunctionNode<T> current = head.next;
        int currentIndex = 0;
        int distance = index;

        if (len - index - 1 < distance){
            current = head.prev;
            currentIndex = len - 1;
            distance = len - index - 1;
        }

        if (lastAccessed != null && Math.abs(index - lastAccessedIndex) < distance){
            current = lastAccessed;
            currentIndex = lastAccessedIndex;
        }


        while (currentIndex != index){
            current = currentIndex > index ? current.prev : current.next;
            currentIndex += currentIndex > index ? -1 : 1;
        }


        lastAccessed = current;
        lastAccessedIndex = currentIndex;
        return current;
    }

    private FunctionNode<T> addNodeToTail(){
        FunctionNode<T> newTail = new FunctionNode<T>(null, head.prev, head);
        head.prev.next = newTail;
        head.prev = newTail;

        lastAccessed = newTail;
        lastAccessedIndex = len;

        len++;
        return newTail;
    }

    private FunctionNode<T> addNodeByIndex(int index){
        FunctionNode<T> newNode;

        if (index == len){
            newNode = addNodeToTail();
        }
        else{
             FunctionNode<T> right = getNodeByIndex(index);
             FunctionNode<T> left = right.prev;

            newNode = new FunctionNode<T>(null, left, right);
            left.next = newNode;
            right.prev = newNode;
            len++;
        }

        lastAccessed = newNode;
        lastAccessedIndex = index;
        
        return newNode;
    }

    private FunctionNode<T> deleteNodeByIndex(int index){
        FunctionNode<T> deleteNode = getNodeByIndex(index);

        FunctionNode<T> left = deleteNode.prev;
        FunctionNode<T> right = deleteNode.next;

        left.next = right;
        right.prev = left;

        lastAccessed = null;
        len--;
        return deleteNode;
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values, PointFactory<T> factory){
        this(factory);
        int n = values.length;
        if (rightX <= leftX || n < 2) throw new IllegalArgumentException();
        double step = (rightX - leftX) / (n - 1);

        for (int i = 0; i < n; i++){
            double x = leftX + step * i;
            addNodeToTail().point = this.factory.create(x, values[i]);
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount, PointFactory<T> factory){
        this(leftX, rightX, new double[pointsCount], factory);
    }


    @Override
    public int getPointsCount(){
        return len;
    }

    @Override
    public double getLeftDomainBorder(){
        return getNodeByIndex(0).point.getX();
    }

    @Override
    public double getRightDomainBorder(){
        return getNodeByIndex(len - 1).point.getX();
    }

    @Override
    public double getFunctionValue(double x){
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) return Double.NaN;

        int di = 0;
        while (x > getPointX(di + 1)){
            di++;
        }

        FunctionPoint leftPoint = getPoint(di);
        FunctionPoint rightPoint = getPoint(di + 1);

         return (x - leftPoint.getX()) * (rightPoint.getY() - leftPoint.getY()) / (rightPoint.getX() - leftPoint.getX()) + leftPoint.getY();
    }

    @Override
    public T getPoint(int index){
        if (index < 0 || index >= len) throw new FunctionPointIndexOutOfBoundsException("function point index out of bounds");
        return this.factory.copy(getNodeByIndex(index).point);
    }

    @Override
    public void setPoint(int index, T point)
        throws InappropriateFunctionPointException{
        if (index < 0 || index >= len) throw new FunctionPointIndexOutOfBoundsException("function point index out of bounds");    
        if (
            (index > 0 && point.getX() <= getPointX(index - 1)) || 
            (index < len - 1 && point.getX() >= getPointX(index  + 1))
        ) throw new InappropriateFunctionPointException("inappropriate exception");

        getNodeByIndex(index).point = this.factory.copy(point);
    }

    @Override
    public double getPointX(int index){
        if (index < 0 || index >= len) throw new FunctionPointIndexOutOfBoundsException("function point index out of bounds");
        return getNodeByIndex(index).point.getX();
    }

    @Override
    public void setPointX(int index, double x)
     throws InappropriateFunctionPointException{    
        if (index < 0 || index >= len) throw new FunctionPointIndexOutOfBoundsException("function point index out of bounds");
        if (
            (index > 0 && x <= getPointX(index - 1)) || 
            (index < len - 1 && x >= getPointX(index  + 1))
        ) throw new InappropriateFunctionPointException("inappropriate exception");

        getNodeByIndex(index).point.setX(x);
    }

    @Override
    public double getPointY(int index){
        if (index < 0 || index >= len) throw new FunctionPointIndexOutOfBoundsException("function point index out of bounds");
        return getNodeByIndex(index).point.getY();
    }

    @Override
    public void setPointY(int index, double y){
        if (index < 0 || index >= len) throw new FunctionPointIndexOutOfBoundsException("function point index out of bounds");
         getNodeByIndex(index).point.setY(y);
    }

    @Override
    public void deletePoint(int index){
        if (len < 3) throw new IllegalStateException("removing from array thats en less than 3");
        if (index < 0 || index >= len) throw new FunctionPointIndexOutOfBoundsException("function point index out of bounds");
        deleteNodeByIndex(index);
    }

    @Override
    public void addPoint(T point)
    throws InappropriateFunctionPointException{
        // Найдем интервал 
        
        int di = 0;
        while (di != len && point.getX() > getPointX(di)){
            di++;
        }

        if (di != len && point.getX() == getPointX(di)){
            throw new InappropriateFunctionPointException("inappropriate exception"); 
        }

        addNodeByIndex(di).point = this.factory.copy(point);
    }


    private static class FunctionNode<T extends FunctionPoint>{
        private T point;
        private FunctionNode<T> prev;
        private FunctionNode<T> next;


        private FunctionNode(T point, FunctionNode<T> prev, FunctionNode<T> next){
            this.point = point;
            this.prev = prev;
            this.next = next;
        }


    }
}
