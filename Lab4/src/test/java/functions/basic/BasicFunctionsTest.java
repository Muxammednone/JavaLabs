package functions.basic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class BasicFunctionsTest {
    @Test
    void evaluatesKnownValues() {
        assertEquals(1, new Sin().getFunctionValue(Math.PI / 2), 1e-12);
        assertEquals(-1, new Cos().getFunctionValue(Math.PI), 1e-12);
        assertEquals(1, new Tan().getFunctionValue(Math.PI / 4), 1e-12);
        assertEquals(1, new Exp().getFunctionValue(0), 1e-12);
        assertEquals(3, new Log(2).getFunctionValue(8), 1e-12);
        assertEquals(-1, new Log(0.5).getFunctionValue(2), 1e-12);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-2, 0, 1, Double.NaN, Double.POSITIVE_INFINITY})
    void rejectsInvalidLogBase(double base) {
        assertThrows(IllegalArgumentException.class, () -> new Log(base));
    }

    @Test
    void rejectsNegativeLogArgument() {
        assertThrows(IllegalArgumentException.class, () -> new Log(2).getFunctionValue(-1));
    }
}
