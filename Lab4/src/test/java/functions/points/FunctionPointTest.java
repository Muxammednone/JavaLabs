package functions.points;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionPointTest {

    @Test
    void defaultConstructorCreatesZeroCoordinates() {
        FunctionPoint point = new FunctionPoint();

        assertEquals(0.0, point.getX());
        assertEquals(0.0, point.getY());
    }

    @Test
    void constructorStoresCoordinates() {
        FunctionPoint point = new FunctionPoint(-1.5, 2.5);

        assertEquals(-1.5, point.getX());
        assertEquals(2.5, point.getY());
    }

    @Test
    void setXChangesOnlyX() {
        FunctionPoint point = new FunctionPoint(1.0, 2.0);

        point.setX(5.0);

        assertEquals(5.0, point.getX());
        assertEquals(2.0, point.getY());
    }

    @Test
    void setYChangesOnlyY() {
        FunctionPoint point = new FunctionPoint(1.0, 2.0);

        point.setY(-3.0);

        assertEquals(1.0, point.getX());
        assertEquals(-3.0, point.getY());
    }

    @Test
    void copyConstructorCopiesCoordinates() {
        FunctionPoint original = new FunctionPoint(1.5, -2.5);

        FunctionPoint copy = new FunctionPoint(original);

        assertNotSame(original, copy);
        assertEquals(original.getX(), copy.getX());
        assertEquals(original.getY(), copy.getY());
    }

    @Test
    void changingCopyDoesNotChangeOriginal() {
        FunctionPoint original = new FunctionPoint(1.0, 2.0);
        FunctionPoint copy = new FunctionPoint(original);

        copy.setX(10.0);
        copy.setY(20.0);

        assertEquals(1.0, original.getX());
        assertEquals(2.0, original.getY());
    }
}