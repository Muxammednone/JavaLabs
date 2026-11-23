package functions.io;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;

import functions.factories.FunctionPointFactory;
import functions.factories.PointFactory;
import functions.points.FunctionPoint;

public class FunctionPointIO implements PointIO<FunctionPoint> {
    public String format(){
        return "p1";
    }

    public PointFactory<FunctionPoint> pointFactory(){
        return new FunctionPointFactory();
    }

    public void output(FunctionPoint point, DataOutputStream out) throws IOException{
        out.writeDouble(point.getX());
        out.writeDouble(point.getY());
    }

    public FunctionPoint input(DataInputStream in) throws IOException{
        double x = in.readDouble();
        double y = in.readDouble();
        return new FunctionPoint(x, y);
    }

    public void write(FunctionPoint point, PrintWriter out){
        out.print(point.getX());
        out.print(" ");
        out.println(point.getY());
    } 

    public FunctionPoint read(BufferedReader in) throws IOException{
        String coordinates = in.readLine();

        if (coordinates == null){
            throw new EOFException("Не хватает данных точки");
        }

        String[] parts = coordinates.trim().split("\\s+");

        if (parts.length != 2){
            throw new IOException("ожидалось две координаты");
        }

        try{
            double x = Double.parseDouble(parts[0]);
            double y = Double.parseDouble(parts[1]);

            return new FunctionPoint(x, y);
        }catch(NumberFormatException e){
            throw new IOException("Некорректные координаты", e);
        }
    }
}
