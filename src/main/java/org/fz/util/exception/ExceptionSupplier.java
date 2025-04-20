package org.fz.util.exception;

/**
 * @author fengbinbin
 * @since 2017/4/2/038 11:52
 */
@FunctionalInterface
public interface ExceptionSupplier {
    /**
     * get one exception
     */
    RuntimeException get();
}
