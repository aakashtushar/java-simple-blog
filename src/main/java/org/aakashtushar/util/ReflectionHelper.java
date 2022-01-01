package org.aakashtushar.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionHelper {
    public static String getGenericTypeName(Class reflClass) {
        Class<?> genericType = null;
        Type gnrcType = reflClass.getGenericSuperclass();
        if (gnrcType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) gnrcType;
            Type types[] = parameterizedType.getActualTypeArguments();

            if (types != null && types.length > 0) {
                Type type = types[0];
                if (type instanceof Class) {
                    genericType = (Class<?>) type;
                }
            }
        }

        return genericType.getName();
    }
}
