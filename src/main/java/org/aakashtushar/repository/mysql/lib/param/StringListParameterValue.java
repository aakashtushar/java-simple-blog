package org.aakashtushar.repository.mysql.lib.param;

import java.util.List;

public class StringListParameterValue implements ParameterValue<List<String>> {
    private List<String> value;

    public StringListParameterValue(List<String> value) {
        this.value = value;
    }

    @Override
    public ParameterValueType getType() {
        return ParameterValueType.STRING_LIST;
    }

    @Override
    public List<String> getValue() {
        return value;
    }
}
