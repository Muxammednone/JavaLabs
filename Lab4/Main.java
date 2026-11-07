import functions.basic.Cos;
import functions.basic.Exp;
import functions.basic.Log;
import functions.basic.Sin;
import functions.factories.FunctionPointStringFactory;
import functions.factories.PointFactory;
import functions.points.FunctionPointString;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import functions.*;

public class Main {
    public static void main(String[] args) {
        test3();
    }

    @SuppressWarnings("unchecked")
    private static void serialization_test(){
        PointFactory<FunctionPointString> factory = new FunctionPointStringFactory();
        TabulatedFunction<FunctionPointString> log = TabulatedFunctions.tabulate(new Log(Math.E), 1, 10, 10, factory);
        for (int i = 0; i < log.getPointsCount(); i++){
            System.out.println(log.getPoint(i).getS());
        }
        try (var fileOutput = new FileOutputStream("ser_obj");
             var objectOutput = new ObjectOutputStream(fileOutput)) {
            objectOutput.writeObject(log);
        } catch (IOException e) {
            System.err.println("Ошибка сохранения функции: " + e.getMessage());
            return;
        }

        try (var fileInput = new FileInputStream("ser_obj");
             var objectInput = new ObjectInputStream(fileInput)) {
            TabulatedFunction<FunctionPointString> deser_func =
                    (TabulatedFunction<FunctionPointString>) objectInput.readObject();
            for (int i = 0; i < deser_func.getPointsCount(); i++) {
                System.out.println(deser_func.getPoint(i).getS());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка восстановления функции: " + e.getMessage());
        }
    }

    private static void test1(){
        Function sin = new Sin();
        Function cos = new Cos();

        double last = 2 * Math.PI;
        for (int i = 0; 0.1 * i < 2 * Math.PI; i++){
            double x = 0.1 * i;
            System.out.printf("sin %.3f: %.3f; cos %.3f: %.3f;\n", x, sin.getFunctionValue(x), x, cos.getFunctionValue(x));
        }
        System.out.printf("sin %.3f: %.3f; cos %.3f: %.3f;\n", last, sin.getFunctionValue(last), last, cos.getFunctionValue(last));

        PointFactory<FunctionPointString> factory = new FunctionPointStringFactory();
        TabulatedFunction<FunctionPointString> tabSin = TabulatedFunctions.tabulate(sin, 0, last, 100, factory);
        TabulatedFunction<FunctionPointString> tabCos = TabulatedFunctions.tabulate(cos, 0, last, 100, factory);

        for (int i = 0; 0.1 * i < 2 * Math.PI; i++){
            double x = 0.1 * i;
            System.out.printf("sin %.3f: %.3f; cos %.3f: %.3f;\n", x, tabSin.getFunctionValue(x), x, tabCos.getFunctionValue(x));
        }
        System.out.printf("sin %.3f: %.3f; cos %.3f: %.3f;\n", last, tabSin.getFunctionValue(last), last, tabCos.getFunctionValue(last));

        Function f1 = Functions.sum(Functions.power(tabSin, 2), Functions.power(tabCos, 2));
        for (int i = 0; 0.1 * i < 2 * Math.PI; i++){
            double x = 0.1 * i;
            System.out.printf("sin %.3f: %.3f;\n", x, f1.getFunctionValue(x));
        }
        System.out.printf("sin %.3f: %.3f;\n", last, f1.getFunctionValue(last));
    }

    private static void test2(){
        PointFactory<FunctionPointString> factory = new FunctionPointStringFactory();
        TabulatedFunction<FunctionPointString> tabExp = TabulatedFunctions.tabulate(new Exp(), 0, 10, 11, factory);
        try(
            FileOutputStream out = new FileOutputStream("test2");
        ){
            TabulatedFunctions.outputTabulatedFunction(tabExp, out);
        }catch(IOException e){
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            return;
        }

        TabulatedFunction<FunctionPointString> tabExpFromFile;
        try(
            FileInputStream in = new FileInputStream("test2");
        ){
            tabExpFromFile = TabulatedFunctions.inputTabulatedFunction(in, factory);
        }catch(IOException e){
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            return;
        }

        for (int i = 0; i <= 10; i++){
            double x = 1 * i;
            System.out.printf("%.3f: func1 %.3f; func2 %.3f;\n", x, tabExp.getFunctionValue(x), tabExpFromFile.getFunctionValue(x));
        }
    }

    private static void test3(){
        PointFactory<FunctionPointString> factory = new FunctionPointStringFactory();
        TabulatedFunction<FunctionPointString> tabLog = TabulatedFunctions.tabulate(new Log(2), 1, 10, 10, factory);
        try(
            FileWriter out = new FileWriter("test3");
        ){
            TabulatedFunctions.writeTabulatedFunction(tabLog, out);
        }catch(IOException e){
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            return;
        }

        TabulatedFunction<FunctionPointString> tabLogFromFile;
        try(
            FileReader in = new FileReader("test3");
        ){
            tabLogFromFile = TabulatedFunctions.readTabulatedFunction(in, factory);
        }catch(IOException e){
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            return;
        }

        for (int i = 1; i <= 10; i++){
            double x = 1 * i;
            System.out.printf("%.3f: func1 %.3f; func2 %.3f;\n", x, tabLog.getFunctionValue(x), tabLogFromFile.getFunctionValue(x));
        }
    }




}
