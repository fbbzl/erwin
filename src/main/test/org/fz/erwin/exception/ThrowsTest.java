package org.fz.erwin.exception;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.fz.erwin.TestAssertions.assertThrowsWithMessage;
import static org.fz.erwin.TestAssertions.failOnNotice;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ThrowsTest {

    @Test
    void booleanNullAndEqualityPredicatesThrowWithFormattedMessages() {
        assertThrowsWithMessage(IllegalArgumentException.class, "true 1",
                                () -> Throws.ifTrue(true, "true {}", 1));
        assertThrowsWithMessage(IllegalArgumentException.class, "false 2",
                                () -> Throws.ifFalse(false, "false {}", 2));
        assertThrowsWithMessage(IllegalArgumentException.class, "null 3",
                                () -> Throws.ifNull(null, "null {}", 3));
        assertThrowsWithMessage(IllegalArgumentException.class, "not null 4",
                                () -> Throws.ifNotNull("value", "not null {}", 4));
        assertThrowsWithMessage(IllegalArgumentException.class, "equals 5",
                                () -> Throws.ifEquals("a", "a", "equals {}", 5));
        assertThrowsWithMessage(IllegalArgumentException.class, "not equals 6",
                                () -> Throws.ifNotEquals("a", "b", "not equals {}", 6));
    }

    @Test
    void arrayCollectionMapAndTextPredicatesThrowForMatchingStates() {
        assertThrowsWithMessage(IllegalArgumentException.class, "array empty",
                                () -> Throws.ifEmpty(new Object[0], "array empty"));
        assertThrowsWithMessage(IllegalArgumentException.class, "array not empty",
                                () -> Throws.ifNotEmpty(new Object[]{"x"}, "array not empty"));
        assertThrowsWithMessage(IllegalArgumentException.class, "collection empty",
                                () -> Throws.ifEmpty(List.of(), "collection empty"));
        assertThrowsWithMessage(IllegalArgumentException.class, "collection not empty",
                                () -> Throws.ifNotEmpty(List.of("x"), "collection not empty"));
        assertThrowsWithMessage(IllegalArgumentException.class, "map empty",
                                () -> Throws.ifEmpty(Map.of(), "map empty"));
        assertThrowsWithMessage(IllegalArgumentException.class, "map not empty",
                                () -> Throws.ifNotEmpty(Map.of("k", "v"), "map not empty"));
        assertThrowsWithMessage(IllegalArgumentException.class, "text empty",
                                () -> Throws.ifEmpty("", "text empty"));
        assertThrowsWithMessage(IllegalArgumentException.class, "text not empty",
                                () -> Throws.ifNotEmpty("x", "text not empty"));
        assertThrowsWithMessage(IllegalArgumentException.class, "blank",
                                () -> Throws.ifBlank("   ", "blank"));
        assertThrowsWithMessage(IllegalArgumentException.class, "not blank",
                                () -> Throws.ifNotBlank("x", "not blank"));
    }

    @Test
    void containsPredicatesCoverCollectionsTextAndMaps() {
        List<String> values = List.of("a", "b");
        Map<String, Integer> map = Map.of("a", 1, "b", 2);

        assertThrowsWithMessage(IllegalArgumentException.class, "contains",
                                () -> Throws.ifContains(values, "a", "contains"));
        assertThrowsWithMessage(IllegalArgumentException.class, "not contains",
                                () -> Throws.ifNotContains(values, "z", "not contains"));
        assertThrowsWithMessage(IllegalArgumentException.class, "text contains",
                                () -> Throws.ifContains("abc", "b", "text contains"));
        assertThrowsWithMessage(IllegalArgumentException.class, "text not contains",
                                () -> Throws.ifNotContains("abc", "z", "text not contains"));
        assertThrowsWithMessage(IllegalArgumentException.class, "key contains",
                                () -> Throws.ifContainsKey(map, "a", "key contains"));
        assertThrowsWithMessage(IllegalArgumentException.class, "key not contains",
                                () -> Throws.ifNotContainsKey(map, "z", "key not contains"));
        assertThrowsWithMessage(IllegalArgumentException.class, "value contains",
                                () -> Throws.ifContainsValue(map, 1, "value contains"));
        assertThrowsWithMessage(IllegalArgumentException.class, "value not contains",
                                () -> Throws.ifNotContainsValue(map, 9, "value not contains"));
    }

    @Test
    void validationFailuresUseBuiltInMessagesBeforeMembershipChecks() {
        assertThrowsWithMessage(IllegalArgumentException.class, "collection is null",
                                () -> Throws.ifContains((List<String>) null, "a", "ignored"));
        assertThrowsWithMessage(IllegalArgumentException.class, "element is null",
                                () -> Throws.ifContains(List.of("a"), null, "ignored"));
        assertThrowsWithMessage(IllegalArgumentException.class, "map is null",
                                () -> Throws.ifContainsKey((Map<String, String>) null, "a", "ignored"));
        assertThrowsWithMessage(IllegalArgumentException.class, "key is null",
                                () -> Throws.ifContainsKey(Map.of("a", "b"), null, "ignored"));
        assertThrowsWithMessage(IllegalArgumentException.class, "value is null",
                                () -> Throws.ifContainsValue(Map.of("a", "b"), null, "ignored"));
        assertThrowsWithMessage(IllegalArgumentException.class, "array is null",
                                () -> Throws.ifHasNullElement((String[]) null, "ignored"));
    }

    @Test
    void typeElementAndAssignablePredicatesThrowForMatchingStates() {
        assertThrowsWithMessage(IllegalArgumentException.class, "instance",
                                () -> Throws.ifInstanceOf(Number.class, 1, "instance"));
        assertThrowsWithMessage(IllegalArgumentException.class, "not instance",
                                () -> Throws.ifNotInstanceOf(Number.class, "x", "not instance"));
        assertThrowsWithMessage(IllegalArgumentException.class, "null element",
                                () -> Throws.ifHasNullElement(new String[]{"a", null}, "null element"));
        assertThrowsWithMessage(IllegalArgumentException.class, "null collection element",
                                () -> Throws.ifHasNullElement(Arrays.asList("a", null), "null collection element"));
        assertThrowsWithMessage(IllegalArgumentException.class, "assignable",
                                () -> Throws.ifAssignable(Number.class, Integer.class, "assignable"));
        assertThrowsWithMessage(IllegalArgumentException.class, "not assignable",
                                () -> Throws.ifNotAssignable(Integer.class, Number.class, "not assignable"));
    }

    @Test
    void predicatesDoNotEvaluateSupplierNoticeWhenTheyPass() {
        assertDoesNotThrow(() -> Throws.ifTrue(false, failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifFalse(true, failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifNull("value", failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifNotNull(null, failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifEmpty(new Object[]{"x"}, failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifNotEmpty(new Object[0], failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifEmpty(List.of("x"), failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifNotEmpty(List.of(), failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifEmpty(Map.of("k", "v"), failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifNotEmpty(Map.of(), failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifEmpty("x", failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifNotEmpty("", failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifBlank("x", failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifNotBlank("   ", failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifEquals("a", "b", failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifNotEquals("a", "a", failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifContains(List.of("a"), "b", failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifNotContains(List.of("a"), "a", failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifContains("abc", "z", failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifNotContains("abc", "b", failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifContainsKey(Map.of("a", "b"), "z", failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifNotContainsKey(Map.of("a", "b"), "a", failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifContainsValue(Map.of("a", "b"), "z", failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifNotContainsValue(Map.of("a", "b"), "b", failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifInstanceOf(Number.class, "x", failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifNotInstanceOf(Number.class, 1, failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifHasNullElement(new String[]{"a"}, failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifHasNullElement(List.of("a"), failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifAssignable(Integer.class, Number.class, failOnNotice()));
        assertDoesNotThrow(() -> Throws.ifNotAssignable(Number.class, Integer.class, failOnNotice()));
    }
}
