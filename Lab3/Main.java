import functions.*;

public class Main{
    void main(String[] args){
        TabulatedFunction func = new TabulatedFunction(0, 10, 6);
        System.out.printf("previous len: %d, capacity: %d\n", func.getLen(), func.getCapacity());
        for (int i = 0; i < func.getLen(); i++){
            System.out.printf("point number %d: {%f, %f}\n", i, func.getPointX(i), func.getPointY(i));
        }

        for (int i = 12; i <= 30; i+= 2) func.addPoint(new FunctionPoint(i, i * 2));

        System.out.printf("last len: %d, capacity: %d\n", func.getLen(), func.getCapacity());
        for (int i = 0; i < func.getLen(); i++){
            System.out.printf("point number %d: {%f, %f}\n", i, func.getPointX(i), func.getPointY(i));
        }
    }
};