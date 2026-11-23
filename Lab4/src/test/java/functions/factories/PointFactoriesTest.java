package functions.factories;

import functions.io.*;
import functions.points.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class PointFactoriesTest {
    @Test
    void plainFactoryCreatesIndependentCopiesAndTypedArrays() {
        var factory = new FunctionPointFactory();
        var point = factory.create(1, 2);
        var copy = factory.copy(point);
        assertEquals(1, copy.getX());
        assertEquals(2, copy.getY());
        copy.setX(5);
        assertEquals(1, point.getX());
        assertEquals(FunctionPoint[].class, factory.createArray(3).getClass());
        assertEquals(3, factory.createArray(3).length);
    }

    @Test
    void stringFactoryPreservesTextWhenCopying() {
        var factory = new FunctionPointStringFactory();
        var point = factory.create(1, 2);
        point.setS("custom text");
        var copy = factory.copy(point);
        assertNotSame(point, copy);
        assertEquals(1, copy.getX());
        assertEquals(2, copy.getY());
        assertEquals("custom text", copy.getS());
        assertEquals(FunctionPointString[].class, factory.createArray(2).getClass());
    }

    @Test
    void selectsMatchingIOAndRejectsUnknownFormat() throws IOException {
        assertInstanceOf(FunctionPointIO.class, PointIOFactory.getByFormat("p1"));
        assertInstanceOf(FunctionPointStringIO.class, PointIOFactory.getByFormat("p2"));
        assertThrows(IOException.class, () -> PointIOFactory.getByFormat("unknown"));
    }
}
