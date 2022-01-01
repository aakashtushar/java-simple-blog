package org.aakashtushar.repository.mysql;

import org.aakashtushar.repository.AbstractRepository;
import org.aakashtushar.repository.BaseRepositoryInterface;
import org.aakashtushar.repository.mysql.lib.criteria.Criteria;
import org.aakashtushar.repository.mysql.lib.exception.LogicException;
import org.aakashtushar.repository.mysql.lib.criteria.Field;
import org.aakashtushar.repository.mysql.lib.param.ParameterValue;
import org.aakashtushar.repository.mysql.lib.querybuilder.QueryBuilder;
import org.aakashtushar.util.ReflectionHelper;
import org.aakashtushar.util.StringCaseConvertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

abstract public class AbstractMysqlRepository<Entity> extends AbstractRepository<Entity> implements BaseRepositoryInterface<Entity> {
    protected final String TABLE = this.getTableName();
    protected int stmtParamIndex = 1;

    public AbstractMysqlRepository(Connection connection) {
        super(connection);
    }

    private String getTableName() {
        var type = ReflectionHelper.getGenericTypeName(this.getClass()).split("\\.");
        return StringCaseConvertor.camelToSnake(
                type[type.length -1]
        );
    }

    @Override
    public List<Entity> findBy(Criteria criteria) throws LogicException, SQLException {
        QueryBuilder qb = this.createQueryBuilder(this.TABLE, "t");
        criteria.apply(qb);

        return this.toEntityList(
                qb.getQuery().getResultSet()
        );
    }

    @Override
    public Entity findOneBy(Criteria criteria) throws LogicException, SQLException {
        QueryBuilder qb = this.createQueryBuilder(this.TABLE, "t");
        criteria.apply(qb);
        qb.limit(1);

        return this.toEntity(
                qb.getQuery().getResultSet()
        );
    }

    @Override
    public List<Entity> getAll() throws SQLException, LogicException {
        return this.toEntityList(
                this.createQueryBuilder(this.TABLE, "t").getQuery().getResultSet()
        );
    }

    public boolean create(Map<String, ParameterValue> columns) throws LogicException, SQLException {
        return this.createQueryBuilder(TABLE, "t").insert().setColumns(columns).getQuery().execute();
    }

    public boolean update(Map<String, ParameterValue> columns) throws LogicException, SQLException {
        return this.createQueryBuilder(TABLE, "t").update().setColumns(columns).getQuery().execute();
    }

    private QueryBuilder createQueryBuilder(String table, String t) {
        return new QueryBuilder(connection, table, t);
    }

    private LogicException unexpectedTypeException(Field field, String expectedType) {
        return new LogicException(String.format("Value of column %s with operator %s must be of type `%s`, `%s` given", field.getColumn(), field.getOperator().toString(), expectedType, field.getValue().getClass().getGenericSuperclass().getTypeName()));
    }

    protected void incrementStmtParamIndex() {
        this.stmtParamIndex++;
    }

    protected void resetStmtParamIndex() {
        this.stmtParamIndex = 1;
    }
}
