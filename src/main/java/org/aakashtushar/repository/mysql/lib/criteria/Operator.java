package org.aakashtushar.repository.mysql.lib.criteria;

public enum Operator {
    EQUALS ("="),
    NOT_EQUALS ("<>"),
    IN ("IN"),
    BETWEEN ("BETWEEN"),
    GREATER_THAN ("<"),
    GREATER_THAN_EQUALS ("<="),
    LESS_THAN (">"),
    LESS_THAN_EQUALS (">="),
    IS_NULL ("IS NULL"),
    IS_NOT_NULL ("IS NOT NULL")
    ;

    private final String code;

    Operator(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
