package org.fz.erwin;

import org.junit.jupiter.api.function.Executable;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

public final class TestAssertions {

    private TestAssertions() {
    }

    public static <T extends Throwable> T assertThrowsWithMessage(Class<T> type, String message,
                                                                  Executable executable) {
        T throwable = assertThrows(type, executable);
        assertEquals(message, throwable.getMessage());
        return throwable;
    }

    public static Supplier<String> failOnNotice() {
        return () -> fail("notice supplier should not be evaluated");
    }
}
