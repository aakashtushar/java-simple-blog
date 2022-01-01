package org.aakashtushar.repository.mysql.lib.param;

public interface ParameterValue<T> {
    ParameterValueType getType();

    T getValue();
}
