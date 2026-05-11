package org.fz.erwin.lang;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.fz.erwin.TestAssertions.assertThrowsWithMessage;
import static org.fz.erwin.TestAssertions.failOnNotice;
import static org.junit.jupiter.api.Assertions.*;

class VarsTest {

    @Test
    void requireBooleanAndNullPredicates() {
        assertDoesNotThrow(() -> Vars.requireTrue(true));
        assertDoesNotThrow(() -> Vars.requireFalse(false));

        assertThrowsWithMessage(IllegalArgumentException.class, "require true but still false",
                                () -> Vars.requireTrue(false));
        assertThrowsWithMessage(IllegalArgumentException.class, "require false but still true",
                                () -> Vars.requireFalse(true));

        Object value = new Object();
        assertSame(value, Vars.requireNotNull(value));
        assertNull(Vars.requireNull(null));
        assertThrowsWithMessage(IllegalArgumentException.class, "require not null but still null",
                                () -> Vars.requireNotNull(null));
        assertThrowsWithMessage(IllegalArgumentException.class, "require null but still not null",
                                () -> Vars.requireNull(value));
    }

    @Test
    void requireNotEmptyReturnsOriginalContainers() {
        String[] array = {"x"};
        List<String> list = List.of("x");
        Map<String, String> map = Map.of("k", "v");

        assertSame(array, Vars.requireNotEmpty(array));
        assertSame(list, Vars.requireNotEmpty(list));
        assertSame(map, Vars.requireNotEmpty(map));
        assertSame("x", Vars.requireNotBlank("x"));
    }

    @Test
    void requireNotEmptyThrowsForEmptyValues() {
        assertThrowsWithMessage(IllegalArgumentException.class, "require array not empty but still empty",
                                () -> Vars.requireNotEmpty(new String[0]));
        assertThrowsWithMessage(IllegalArgumentException.class, "require collection not empty but still empty",
                                () -> Vars.requireNotEmpty(List.of()));
        assertThrowsWithMessage(IllegalArgumentException.class, "require map not empty but still empty",
                                () -> Vars.requireNotEmpty(Map.of()));
        assertThrowsWithMessage(IllegalArgumentException.class, "require not blank but still blank",
                                () -> Vars.requireNotBlank("  "));
    }

    @Test
    void equalityAndContainsRequirements() {
        assertDoesNotThrow(() -> Vars.requireEquals("a", "a"));
        assertDoesNotThrow(() -> Vars.requireNotEquals("a", "b"));
        assertDoesNotThrow(() -> Vars.requireContains(List.of("a", "b"), "a"));
        assertDoesNotThrow(() -> Vars.requireNotContains(List.of("a", "b"), "z"));

        assertThrowsWithMessage(IllegalArgumentException.class, "require equals but still not equals",
                                () -> Vars.requireEquals("a", "b"));
        assertThrowsWithMessage(IllegalArgumentException.class, "require not equals but still equals",
                                () -> Vars.requireNotEquals("a", "a"));
        assertThrowsWithMessage(IllegalArgumentException.class, "require contains but still not contains",
                                () -> Vars.requireContains(List.of("a", "b"), "z"));
        assertThrowsWithMessage(IllegalArgumentException.class, "require not contains but still contains",
                                () -> Vars.requireNotContains(List.of("a", "b"), "a"));
    }

    @Test
    void formattedMessagesAreUsedWhenProvided() {
        assertThrowsWithMessage(IllegalArgumentException.class, "need true 1",
                                () -> Vars.requireTrue(false, "need true {}", 1));
        assertThrowsWithMessage(IllegalArgumentException.class, "need not null 2",
                                () -> Vars.requireNotNull(null, "need not null {}", 2));
        assertThrowsWithMessage(IllegalArgumentException.class, "need not empty 3",
                                () -> Vars.requireNotEmpty(new String[0], "need not empty {}", 3));
        assertThrowsWithMessage(IllegalArgumentException.class, "need contains 4",
                                () -> Vars.requireContains(List.of("a"), "z", "need contains {}", 4));
    }

    @Test
    void supplierNoticesAreLazyWhenRequirementsPass() {
        assertDoesNotThrow(() -> Vars.requireTrue(true, failOnNotice()));
        assertDoesNotThrow(() -> Vars.requireFalse(false, failOnNotice()));
        assertDoesNotThrow(() -> Vars.requireNotNull("x", failOnNotice()));
        assertDoesNotThrow(() -> Vars.requireNull(null, failOnNotice()));
        assertDoesNotThrow(() -> Vars.requireNotEmpty(new String[]{"x"}, failOnNotice()));
        assertDoesNotThrow(() -> Vars.requireNotEmpty(List.of("x"), failOnNotice()));
        assertDoesNotThrow(() -> Vars.requireNotEmpty(Map.of("k", "v"), failOnNotice()));
        assertDoesNotThrow(() -> Vars.requireNotBlank("x", failOnNotice()));
        assertDoesNotThrow(() -> Vars.requireEquals("x", "x", failOnNotice()));
        assertDoesNotThrow(() -> Vars.requireNotEquals("x", "y", failOnNotice()));
        assertDoesNotThrow(() -> Vars.requireContains(List.of("x"), "x", failOnNotice()));
        assertDoesNotThrow(() -> Vars.requireNotContains(List.of("x"), "y", failOnNotice()));
    }
}
