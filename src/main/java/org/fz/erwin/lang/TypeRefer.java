package org.fz.erwin.lang;

import cn.hutool.core.util.TypeUtil;
import lombok.Getter;

import java.lang.reflect.Type;

/**
 * type parameter at the time of serialization
 *
 * @param <T> the type parameter
 * @author fengbinbin
 * @version 1.0
 * @since 2024 /1/15 11:24
 */
@Getter
public abstract class TypeRefer<T> implements Type {

    private final Type typeValue = TypeUtil.getTypeArgument(this.getClass());

    @Override
    public String toString() {
        return "TypeRefer(typeValue=" + this.getTypeValue() + ")";
    }
}
