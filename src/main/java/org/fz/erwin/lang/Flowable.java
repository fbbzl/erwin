package org.fz.erwin.lang;

import org.fz.erwin.exception.Throws;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author fengbinbin
 * @version 1.0
 * @since 2025/9/7 13:51
 */
public interface Flowable {

    @SuppressWarnings("unchecked")
    default <E extends Enum<E>> Optional<E> next(Predicate<E> nextCondition) {
        Throws.ifNull(this, () -> new IllegalArgumentException("current enum can not be null"));
        Throws.ifFalse(this.getClass().isEnum(),
                       () -> new IllegalArgumentException(Flowable.class + "only support enum type enum type"));

        E        current   = (E) this;
        Class<E> enumClass = (Class<E>) current.getClass();
        E[]      enums     = enumClass.getEnumConstants();

        // return empty if current is last
        if (enums[enums.length - 1] == current) return Optional.empty();
        if (nextCondition.test(current))        return Optional.of(enums[current.ordinal() + 1]);
        else                                    return Optional.empty();
    }

}
