package functions.points;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FunctionPointStringTest {
    @Test
    void storesCoordinatesAndText() {
        var point = new FunctionPointString(1, -2, "hello");
        assertEquals(1, point.getX());
        assertEquals(-2, point.getY());
        assertEquals("hello", point.getS());
    }

    @Test
    void copyIsIndependent() {
        var original = new FunctionPointString(1, 2, "original");
        var copy = new FunctionPointString(original);
        assertEquals("original", copy.getS());
        copy.setX(9);
        copy.setY(8);
        copy.setS("changed");
        assertEquals(1, original.getX());
        assertEquals(2, original.getY());
        assertEquals("original", original.getS());
    }
}
