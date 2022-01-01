package org.aakashtushar.repository.mysql.lib.param;

import java.time.LocalDateTime;
import java.util.List;

public class DateTimeListParameterValue implements ParameterValue<List<LocalDateTime>> {
    private List<LocalDateTime> value;

    public DateTimeListParameterValue(List<LocalDateTime> value) {
        this.value = value;
    }

    @Override
    public ParameterValueType getType() {
        return ParameterValueType.DATETIME_LIST;
    }

    @Override
    public List<LocalDateTime> getValue() {
        return value;
    }
}
