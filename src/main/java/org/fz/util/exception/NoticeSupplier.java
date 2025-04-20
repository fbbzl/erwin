package org.fz.util.exception;

/**
 * @author fengbinbin
 * @since 2017/4/2/038 11:52
 */
@FunctionalInterface
public interface NoticeSupplier {
    /**
     * get one exception notice
     */
    String get();
}
