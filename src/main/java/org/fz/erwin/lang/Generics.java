package org.fz.erwin.lang;

import lombok.experimental.UtilityClass;
import org.springframework.core.ResolvableType;

/**
 * @author fengbinbin
 * @version 1.0
 * @since 2026/2/6 22:35
 */

@UtilityClass
public class Generics
{

    @SuppressWarnings("unchecked")
    public <C> Class<C> getGenericSuperType(Class<?> currentClass, Class<?> superClass, int genericIndex)
    {
        ResolvableType currentType = ResolvableType.forClass(currentClass);

        while (true) {
            ResolvableType superType = currentType.getSuperType();
            Class<?> rawClass = superType.getRawClass();

            if (rawClass == null || rawClass == superClass) {
                if (rawClass == null) return null;

                ResolvableType entityType = superType.getGeneric(genericIndex);

                return (Class<C>) entityType.resolve();
            }

            currentType = superType;
        }
    }
}
