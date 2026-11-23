package functions.io;

import java.io.*;

import functions.factories.*;


import functions.points.FunctionPointString;

public class FunctionPointStringIO implements PointIO<FunctionPointString> {
    public String format(){
        return "p2";
    }

    public PointFactory<FunctionPointString> pointFactory(){
        return FunctionPointStringFactory.INSTANCE;
    }

    public void output(FunctionPointString point, DataOutputStream out) throws IOException{
        out.writeDouble(point.getX());
        out.writeDouble(point.getY());
        out.writeUTF(point.getS());
    }

    public FunctionPointString input(DataInputStream in) throws IOException{
        double x = in.readDouble();
        double y = in.readDouble();
        String s = in.readUTF();
        return new FunctionPointString(x, y, s);
    }

    public void write(FunctionPointString point, PrintWriter out){
        out.print(point.getX());
        out.print(" ");
        out.println(point.getY());
        out.println(point.getS());
    }


    public FunctionPointString read(BufferedReader in) throws IOException{
        String coordinates = in.readLine();
        String s = in.readLine();

        if (s == null || coordinates == null){
            throw new EOFException("Не хватает данных стринговой точки");
        }

        String[] parts = coordinates.trim().split("\\s+");
    
        if (parts.length != 2) {
            throw new IOException("Ожидались две координаты");
        }

        try {
            double x = Double.parseDouble(parts[0]);
            double y = Double.parseDouble(parts[1]);

            return new FunctionPointString(x, y, s);
        } catch (NumberFormatException e) {
            throw new IOException("Некорректные координаты", e);
        }
    }
}
