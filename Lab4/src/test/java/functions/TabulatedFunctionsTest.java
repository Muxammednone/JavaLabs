package functions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import functions.basic.Exp;
import functions.basic.Log;
import functions.basic.Sin;
import functions.factories.FunctionPointFactory;
import functions.factories.FunctionPointStringFactory;
import functions.io.FunctionPointStringIO;
import functions.io.PointIO;
import functions.points.FunctionPointString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;

public class TabulatedFunctionsTest {
    @Test
    void output_and_input(@TempDir Path tempDir) throws  IOException{
        var file = tempDir.resolve("output_and_input").toFile();
        TabulatedFunction<FunctionPointString> tabSin = TabulatedFunctions.tabulate(new Sin(), 1, 10, 11, FunctionPointStringFactory.INSTANCE);
        PointIO<FunctionPointString> pointIO = new FunctionPointStringIO();
        try(OutputStream out = new FileOutputStream(file)){
            TabulatedFunctions.outputTabulatedFunction(tabSin, out, pointIO);
        }

        try(InputStream in = new FileInputStream(file)){
            var tabFunction = TabulatedFunctions.inputTabulatedFunction(in);
            assertEquals(tabFunction.getPointsCount(), tabSin.getPointsCount());
            for (int i = 0; i < tabFunction.getPointsCount(); i++){
                assertEquals(tabSin.getPointX(i), tabFunction.getPointX(i));
                assertEquals(tabSin.getPointY(i), tabFunction.getPointY(i));

                var pointString = assertInstanceOf(
                        FunctionPointString.class,
                        tabFunction.getPoint(i)
                );

                assertEquals(tabSin.getPoint(i).getS(), pointString.getS());
            }
        }
    }
    @Test
    void write_and_read(@TempDir Path tempDir) throws IOException{
        var file = tempDir.resolve("write_and_read").toFile();
        TabulatedFunction<FunctionPointString> tabExp = TabulatedFunctions.tabulate(new Exp(), 1, 10, 11, FunctionPointStringFactory.INSTANCE);
        PointIO<FunctionPointString>  pointIO = new FunctionPointStringIO();

        try(Writer out = new FileWriter(file)){
            TabulatedFunctions.writeTabulatedFunction(tabExp, out, pointIO);
        }

        try(Reader in = new FileReader(file)){
            var tabFunction = TabulatedFunctions.readTabulatedFunction(in);
            assertEquals(tabExp.getPointsCount(), tabFunction.getPointsCount());

            for (int i= 0; i < tabFunction.getPointsCount(); i++){
                assertEquals(tabExp.getPointX(i), tabFunction.getPointX(i));
                assertEquals(tabExp.getPointY(i), tabFunction.getPointY(i));

                var pointString = assertInstanceOf(
                        FunctionPointString.class,
                        tabFunction.getPoint(i)
                );

                assertEquals(tabExp.getPoint(i).getS(), pointString.getS());
            }
        }
    }
    @Test
    void serialization_test(@TempDir Path tempDir) throws IOException, ClassNotFoundException {
        var tabSin = TabulatedFunctions.tabulate(new Sin(), 1, 10, 11, FunctionPointStringFactory.INSTANCE);
        var file = tempDir.resolve("function.bin").toFile();

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));){
            out.writeObject(tabSin);
        }

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
            var tabFunction = assertInstanceOf(
                    LinkedListTabulatedFunction.class,
                    in.readObject()
            );

            assertEquals(tabSin.getPointsCount(), tabFunction.getPointsCount());

            for (int i = 0; i < tabSin.getPointsCount(); i++){
                assertEquals(tabSin.getPointX(i), tabFunction.getPointX(i));
                assertEquals(tabSin.getPointY(i), tabFunction.getPointY(i));

                var pointString = assertInstanceOf(
                        FunctionPointString.class,
                        tabFunction.getPoint(i)
                );

                assertEquals(tabSin.getPoint(i).getS(), pointString.getS());
            }
        }
    }
}
