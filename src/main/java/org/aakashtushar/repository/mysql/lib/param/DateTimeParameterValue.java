package org.aakashtushar.repository.mysql.lib.param;

import java.time.LocalDateTime;

public class DateTimeParameterValue implements ParameterValue<LocalDateTime> {
    private LocalDateTime value;

    public DateTimeParameterValue(LocalDateTime value) {
        this.value = value;
    }

    @Override
    public ParameterValueType getType() {
        return ParameterValueType.DATETIME;
    }

    @Override
    public LocalDateTime getValue() {
        return value;
    }
}
