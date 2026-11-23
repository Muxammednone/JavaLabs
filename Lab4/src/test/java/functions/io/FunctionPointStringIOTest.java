package functions.io;

import functions.points.FunctionPointString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class FunctionPointStringIOTest {
    @ParameterizedTest
    @ValueSource(strings = {"", "  spaces  ", "Привет \"мир\" \\ text"})
    void textRoundTripPreservesText(String text) throws IOException {
        var io = new FunctionPointStringIO();
        var buffer = new StringWriter();
        io.write(new FunctionPointString(-1.5, 2.5, text), new PrintWriter(buffer));
        var result = io.read(new BufferedReader(new StringReader(buffer.toString())));
        assertEquals(-1.5, result.getX());
        assertEquals(2.5, result.getY());
        assertEquals(text, result.getS());
    }

    @Test
    void binaryRoundTripPreservesMultilineText() throws IOException {
        var io = new FunctionPointStringIO();
        var bytes = new ByteArrayOutputStream();
        io.output(new FunctionPointString(3, 4, "first\nsecond"), new DataOutputStream(bytes));
        var result = io.input(new DataInputStream(new ByteArrayInputStream(bytes.toByteArray())));
        assertEquals(3, result.getX());
        assertEquals(4, result.getY());
        assertEquals("first\nsecond", result.getS());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "1 2\n", "abc 2\ntext\n", "1 2 3\ntext\n"})
    void rejectsIncompleteOrInvalidText(String input) {
        var io = new FunctionPointStringIO();
        assertThrows(IOException.class,
            () -> io.read(new BufferedReader(new StringReader(input))));
    }
}
