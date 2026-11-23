package functions;

import java.io.*;


import functions.factories.PointFactory;
import functions.factories.PointIOFactory;
import functions.io.PointIO;
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
        OutputStream out,
        PointIO<T> pointIO
    ) throws IOException{

        DataOutputStream dos = new DataOutputStream(out);
        dos.writeUTF(pointIO.format());
        dos.writeInt(function.getPointsCount());

        for (int i = 0; i < function.getPointsCount(); i++){
            pointIO.output(function.getPoint(i), dos);
        }
    }

    public static TabulatedFunction<?> inputTabulatedFunction(
        InputStream in
    ) throws IOException{
        DataInputStream dis = new DataInputStream(in);

        String format = dis.readUTF();
       
        PointIO<?> pointIO = PointIOFactory.getByFormat(format);

        return inputPoints(dis, pointIO);
    }

    private static <T extends FunctionPoint> TabulatedFunction<T> inputPoints(
        DataInputStream in,
        PointIO<T> pointIO
    ) throws IOException{
        int pointsCount = in.readInt();

        if (pointsCount < 2){
            throw new IOException("Ожидалось минимум две точки");
        }

        PointFactory<T> factory = pointIO.pointFactory();
        T[] points = factory.createArray(pointsCount);

        for (int i = 0; i < pointsCount; i++){
            points[i] = pointIO.input(in);
        }

        return new LinkedListTabulatedFunction<>(points, factory);
    }

    public static <T extends FunctionPoint> void writeTabulatedFunction(
        TabulatedFunction<T> function,
        Writer out,
        PointIO<T> pointIO
    )throws IOException{
        PrintWriter pw = new PrintWriter(out);
        pw.println(pointIO.format());
        pw.println(function.getPointsCount());
        
        for (int i = 0; i < function.getPointsCount(); i++){
            pointIO.write(function.getPoint(i), pw);
        }

        if (pw.checkError()){
            throw new IOException("print writer checkError");
        }
    }

    public static TabulatedFunction<?> readTabulatedFunction(
        Reader in
    ) throws IOException{
        BufferedReader br = new BufferedReader(in);

        String format = br.readLine();
        if (format == null) {
            throw new EOFException("Не указан формат точек: файл пуст");
        }

        PointIO<?> pointIO = PointIOFactory.getByFormat(format);

        return readPoints(br, pointIO);
    }

    private static <T extends FunctionPoint> TabulatedFunction<T> readPoints(
        BufferedReader br, 
        PointIO<T> pointIO
    ) throws IOException{
        String line = br.readLine();

        if (line == null) {
            throw new EOFException("Не указано количество точек");
        }

        int pointsCount;
        try {
            pointsCount = Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            throw new IOException("Некорректное количество точек: " + line, e);
        }

        if (pointsCount < 2) {
            throw new IOException("Количество точек должно быть больше 1");
        }
        PointFactory<T> factory = pointIO.pointFactory();
        T[] points = factory.createArray(pointsCount);

        for (int i = 0; i < pointsCount; i++){
            points[i] = pointIO.read(br);
        }

        return new LinkedListTabulatedFunction<>(points, factory);
    }
    



    
}
