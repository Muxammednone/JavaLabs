package functions;

import functions.basic.Exp;
import functions.factories.*;
import functions.io.*;
import functions.points.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class TabulatedFunctionsTest {
    private <T extends FunctionPoint> void roundTrip(T[] points, PointIO<T> io, boolean binary) throws IOException {
        var original = new LinkedListTabulatedFunction<>(points, io.pointFactory());
        TabulatedFunction<?> restored;
        if (binary) {
            var out = new ByteArrayOutputStream();
            TabulatedFunctions.outputTabulatedFunction(original, out, io);
            restored = TabulatedFunctions.inputTabulatedFunction(new ByteArrayInputStream(out.toByteArray()));
        } else {
            var out = new StringWriter();
            TabulatedFunctions.writeTabulatedFunction(original, out, io);
            restored = TabulatedFunctions.readTabulatedFunction(new StringReader(out.toString()));
        }
        assertEquals(points.length, restored.getPointsCount());
        for (int i = 0; i < points.length; i++) {
            var actual = restored.getPoint(i);
            assertEquals(points[i].getClass(), actual.getClass());
            assertEquals(points[i].getX(), actual.getX());
            assertEquals(points[i].getY(), actual.getY());
            if (points[i] instanceof FunctionPointString expected) {
                assertEquals(expected.getS(), ((FunctionPointString) actual).getS());
            }
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void roundTripsBothPointTypes(boolean binary) throws IOException {
        roundTrip(new FunctionPoint[]{new FunctionPoint(-1, 2), new FunctionPoint(3, 4)},
            new FunctionPointIO(), binary);
        roundTrip(new FunctionPointString[]{new FunctionPointString(-1, 2, ""),
            new FunctionPointString(3, 4, "  Привет \"мир\"  ")}, new FunctionPointStringIO(), binary);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "unknown\n2\n", "p1\n", "p1\nabc\n", "p1\n1\n", "p1\n2\n0 0\n"})
    void rejectsInvalidText(String text) {
        assertThrows(IOException.class, () -> TabulatedFunctions.readTabulatedFunction(new StringReader(text)));
    }

    @Test
    void rejectsTruncatedBinary() throws IOException {
        var out = new ByteArrayOutputStream();
        var data = new DataOutputStream(out);
        data.writeUTF("p1");
        data.writeInt(2);
        data.writeDouble(0);
        assertThrows(EOFException.class, () -> TabulatedFunctions.inputTabulatedFunction(
            new ByteArrayInputStream(out.toByteArray())));
    }

    @Test
    void tabulatesOnUniformGrid() {
        var f = TabulatedFunctions.tabulate(new Exp(), 0, 2, 3, new FunctionPointFactory());
        assertEquals(3, f.getPointsCount());
        for (int i = 0; i < 3; i++) {
            assertEquals(i, f.getPointX(i));
            assertEquals(Math.exp(i), f.getPointY(i), 1e-12);
        }
    }
}
