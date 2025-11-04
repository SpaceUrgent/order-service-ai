package io.spaceurgent.order.service.ai.infrastructure.db.impl;

import io.spaceurgent.order.service.ai.infrastructure.ai.tools.DatabaseQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DatabaseQueryServiceImpl implements DatabaseQueryService {
    private static final String LIST_TABLES_SQL = """
            SELECT table_name
            FROM information_schema.tables WHERE table_schema = 'order_service';
            """;

    private static final String DESCRIBE_TABLE_SQL = """
            SELECT column_name, data_type
            FROM information_schema.columns WHERE table_name = ?
            """;

    private final JdbcTemplate jdbcTemplate;

    @Cacheable(value = "tableList")
    @Override
    public List<String> listTables() {
        return jdbcTemplate.queryForList(LIST_TABLES_SQL, String.class);
    }

    @Cacheable(value = "tableDescription", key = "#tableName")
    @Override
    public List<Map<String, Object>> describeTable(String tableName) {
        return jdbcTemplate.queryForList(DESCRIBE_TABLE_SQL, tableName);
    }

    @Override
    public List<Map<String, Object>> executeSelectQuery(String sql) {
        sql = sql.toLowerCase();
        if (!sql.startsWith("select")) {
            throw new IllegalArgumentException("Only select query is allowed");
        }
        return jdbcTemplate.queryForList(sql);
    }
}
