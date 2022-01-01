package org.aakashtushar.repository.mysql.lib.param;

import java.util.List;

public class IntListParameterValue implements ParameterValue<List<Integer>> {
    private List<Integer> value;

    public IntListParameterValue(List<Integer> value) {
        this.value = value;
    }

    @Override
    public ParameterValueType getType() {
        return ParameterValueType.INT_LIST;
    }

    @Override
    public List<Integer> getValue() {
        return value;
    }
}
