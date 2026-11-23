package functions.meta;

import functions.*;
import functions.basic.*;
import functions.factories.FunctionPointFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MetaFunctionsTest {
    private Function interval(double left, double right) {
        return new ArrayTabulatedFunction<>(left, right, new double[]{left, right},
            new FunctionPointFactory());
    }

    @Test
    void sumAndProductUseIntersectionOfDomains() {
        Function a = interval(0, 5);
        Function b = interval(2, 8);
        for (Function result : new Function[]{Functions.sum(a, b), Functions.mult(a, b)}) {
            assertEquals(2, result.getLeftDomainBorder());
            assertEquals(5, result.getRightDomainBorder());
        }
    }

    @Test
    void evaluatesSumProductPowerAndComposition() {
        assertEquals(2, Functions.sum(new Exp(), new Cos()).getFunctionValue(0), 1e-12);
        assertEquals(0, Functions.mult(new Exp(), new Sin()).getFunctionValue(0), 1e-12);
        assertEquals(1, Functions.power(new Sin(), 2).getFunctionValue(Math.PI / 2), 1e-12);
        assertEquals(2, Functions.composition(new Log(Math.E), new Exp()).getFunctionValue(2), 1e-12);
    }

    @Test
    void horizontalScaleChangesArgument() {
        Function scaled = Functions.scale(interval(0, 10), 2, 3);
        // Stretching x by 2 and y by 3 gives g(4) = 3 * f(2).
        assertEquals(6, scaled.getFunctionValue(4), 1e-12);
        assertEquals(20, scaled.getRightDomainBorder());
    }

    @Test
    void verticalShiftAddsOffset() {
        assertEquals(6, Functions.shift(new Exp(), 0, 5).getFunctionValue(0), 1e-12);
    }
}
