package org.aakashtushar.repository.mysql.lib.param;

public class StringParameterValue implements ParameterValue<String> {
    private String value;

    public StringParameterValue(String value) {
        this.value = value;
    }

    @Override
    public ParameterValueType getType() {
        return ParameterValueType.STRING;
    }

    @Override
    public String getValue() {
        return value;
    }
}
