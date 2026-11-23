package functions;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.Writer;

import functions.factories.PointFactory;
import functions.points.FunctionPoint;


public class TabulatedFunctions{
    private TabulatedFunctions(){}

    public static <T extends FunctionPoint> TabulatedFunction<T> tabulate(
        Function function,
        double leftX,
        double rightX,
        int pointsCount,
        PointFactory<T> factory
    ){
        double[] arr = new double[pointsCount];
        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++){
            arr[i] = function.getFunctionValue(leftX + i * step);
        }

        return new LinkedListTabulatedFunction<T>(leftX, rightX, arr, factory);
    }

    public static <T extends FunctionPoint> void outputTabulatedFunction(
        TabulatedFunction<T> function,
        OutputStream out
    ) throws IOException{

        DataOutputStream dos = new DataOutputStream(out);

        dos.writeInt(function.getPointsCount());

        for (int i = 0; i < function.getPointsCount(); i++){
            dos.writeDouble(function.getPointX(i));
            dos.writeDouble(function.getPointY(i));
        }
    }

    public static <T extends FunctionPoint> TabulatedFunction<T> inputTabulatedFunction(
        InputStream in, 
        PointFactory<T> factory
    ) throws IOException{
        DataInputStream dis = new DataInputStream(in);

        int pointsCount = dis.readInt();
        T[] points = factory.createArray(pointsCount);
        for (int i = 0; i < pointsCount; i++){
            double x = dis.readDouble();
            double y = dis.readDouble();
            points[i] = factory.create(x, y);
        }

        return new LinkedListTabulatedFunction<>(points, factory);
    }

    public static <T extends FunctionPoint> void writeTabulatedFunction(
        TabulatedFunction<T> function,
        Writer out
    )throws IOException{
        PrintWriter pw = new PrintWriter(out);
        pw.print(function.getPointsCount());

        for (int i = 0; i < function.getPointsCount(); i++){
            pw.print(" ");
            pw.print(function.getPointX(i));
            pw.print(" ");
            pw.print(function.getPointY(i));
        }

        pw.println();

        if (pw.checkError()){
            throw new IOException("some error");
        }
    }

    public static <T extends FunctionPoint> TabulatedFunction<T> readTabulatedFunction(
        Reader in,
        PointFactory<T> factory
    ) throws IOException{
        StreamTokenizer tokenizer = new StreamTokenizer(in);

        tokenizer.resetSyntax();
        tokenizer.whitespaceChars(0, ' ');
        tokenizer.wordChars('!', '\uFFFF');

        tokenizer.nextToken();
        int pointsCount = Integer.parseInt(tokenizer.sval);

        T[] points = factory.createArray(pointsCount);
        for (int i = 0; i < pointsCount; i++){
            tokenizer.nextToken();
            double x = Double.parseDouble(tokenizer.sval);
            tokenizer.nextToken();
            double y = Double.parseDouble(tokenizer.sval);

            points[i] = factory.create(x, y);
        }

        return new LinkedListTabulatedFunction<>(points, factory);
    }
    



    
}
