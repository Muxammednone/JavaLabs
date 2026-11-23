package functions.io;

import functions.points.FunctionPoint;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class FunctionPointIOTest {

    @Test
    void readsCoordinates() throws IOException {
        var io = new FunctionPointIO();
        var reader = new BufferedReader(
                new StringReader("1.5 2.5\n")
        );

        FunctionPoint point = io.read(reader);

        assertEquals(1.5, point.getX());
        assertEquals(2.5, point.getY());
    }

    @Test
    void rejectsInvalidCoordinates() {
        var io = new FunctionPointIO();
        var reader = new BufferedReader(
                new StringReader("abc 2.5\n")
        );

        assertThrows(IOException.class, () -> io.read(reader));
    }
}