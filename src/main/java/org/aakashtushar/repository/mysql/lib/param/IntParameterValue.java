package org.aakashtushar.repository.mysql.lib.param;

public class IntParameterValue implements ParameterValue<Integer> {
    private Integer value;

    public IntParameterValue(Integer value) {
        this.value = value;
    }

    @Override
    public ParameterValueType getType() {
        return ParameterValueType.INT;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
