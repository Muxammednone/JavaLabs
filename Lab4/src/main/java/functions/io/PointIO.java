package functions.io;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import functions.factories.PointFactory;
import functions.points.FunctionPoint;

public interface PointIO<T extends FunctionPoint> {
    String format();
    PointFactory<T> pointFactory();

    void output(T point, DataOutputStream out) throws IOException;
    T input(DataInputStream in) throws IOException;

    void write(T point, PrintWriter out) throws IOException;
    T read(BufferedReader in) throws IOException;
}
