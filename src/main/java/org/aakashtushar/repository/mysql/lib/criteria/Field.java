package org.aakashtushar.repository.mysql.lib.criteria;

import org.aakashtushar.repository.mysql.lib.param.ParameterValue;
import org.aakashtushar.repository.mysql.lib.param.StringParameterValue;

public class Field {
    private String column;
    private Operator operator;
    private ParameterValue value;

    public Field(String column, Operator operator, ParameterValue value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }

    public Field(String column, Operator operator) {
        this(column, operator, new StringParameterValue(""));
    }

    public Field(String column, ParameterValue value) {
        this(column, Operator.EQUALS, value);
    }

    public String getColumn() {
        return column;
    }

    public Operator getOperator() {
        return operator;
    }

    public ParameterValue getValue() {
        return value;
    }

    public boolean isValueType(String type) {
        return this.getValueTypeName().toLowerCase().contains(type.toLowerCase());
    }

    private String getValueTypeName() {
        return this.getClass().getGenericSuperclass().getTypeName();
    }
}
