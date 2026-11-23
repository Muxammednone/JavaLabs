package functions;

import functions.factories.FunctionPointFactory;
import functions.points.FunctionPoint;
import functions.exceptions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class TabulatedFunctionTest {
    private TabulatedFunction<FunctionPoint> create(boolean linked) {
        var factory = new FunctionPointFactory();
        FunctionPoint[] points = {new FunctionPoint(0, 0), new FunctionPoint(2, 4), new FunctionPoint(4, 8)};
        return linked ? new LinkedListTabulatedFunction<>(points, factory)
                      : new ArrayTabulatedFunction<>(points, factory);
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void interpolatesAndIncludesEndpoints(boolean linked) {
        var f = create(linked);
        assertEquals(0, f.getFunctionValue(0), 1e-12);
        assertEquals(2, f.getFunctionValue(1), 1e-12);
        assertEquals(8, f.getFunctionValue(4), 1e-12);
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void insertsAndDeletesKeepingOrder(boolean linked) throws Exception {
        var f = create(linked);
        f.addPoint(new FunctionPoint(1, 2));
        assertEquals(4, f.getPointsCount());
        assertEquals(1, f.getPointX(1));
        f.deletePoint(1);
        assertEquals(3, f.getPointsCount());
        assertEquals(2, f.getPointX(1));
        assertEquals(6, f.getFunctionValue(3), 1e-12);
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void rejectsDuplicateCoordinatesAndInvalidIndices(boolean linked) {
        var f = create(linked);
        assertThrows(InappropriateFunctionPointException.class, () -> f.addPoint(new FunctionPoint(2, 9)));
        assertThrows(InappropriateFunctionPointException.class, () -> f.setPointX(1, 4));
        assertThrows(FunctionPointIndexOutOfBoundsException.class, () -> f.getPoint(-1));
        assertThrows(FunctionPointIndexOutOfBoundsException.class, () -> f.getPoint(3));
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void cannotDeleteBelowTwoPoints(boolean linked) {
        var f = create(linked);
        f.deletePoint(1);
        assertThrows(IllegalStateException.class, () -> f.deletePoint(0));
    }
}
