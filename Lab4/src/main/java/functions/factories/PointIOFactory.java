package functions.factories;

import java.io.IOException;

import functions.io.*;

public final class PointIOFactory {
    private PointIOFactory() {}

    public static PointIO<?> getByFormat(String format)
            throws IOException {
        return switch (format) {
            case "p1" -> new FunctionPointIO();
            case "p2" -> new FunctionPointStringIO();
            default -> throw new IOException(
                "Неизвестный формат точки: " + format
            );
        };
    }
}