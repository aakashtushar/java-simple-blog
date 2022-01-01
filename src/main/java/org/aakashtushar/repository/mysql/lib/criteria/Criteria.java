package org.aakashtushar.repository.mysql.lib.criteria;

import org.aakashtushar.repository.mysql.lib.querybuilder.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class Criteria {
    private List<Field> fields;

    public Criteria() {
        this.fields = new ArrayList<>();
    }

    public Criteria add(Field field) {
        this.fields.add(field);

        return this;
    }

    public void apply(QueryBuilder queryBuilder) {
        for (Field field : fields) {
            queryBuilder.andWhere(field.getColumn(), field.getOperator(), field.getValue());
        }
    }
}
