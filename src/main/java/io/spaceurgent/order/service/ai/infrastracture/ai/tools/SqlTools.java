package io.spaceurgent.order.service.ai.infrastracture.ai.tools;

import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SqlTools {
    private final JdbcTemplate jdbcTemplate;

    @Tool(name = "listTables", value = "Returns list of tables in the database")
    public List<String> listTables() {
        return jdbcTemplate.queryForList("SELECT table_name FROM information_schema.tables WHERE table_schema = 'order_service'", String.class);
    }

    @Tool(name = "describeTable", value = "Returns columns with datatypes for the specified table.")
    public List<Map<String, Object>> describeTable(String tableName) {
        return jdbcTemplate.queryForList("SELECT column_name, data_type FROM information_schema.columns WHERE table_name = ?", tableName);
    }

    @Tool(name = "executeSelectQuery", value = "Executed valid select query")
    public List<Map<String, Object>> executeSelectQuery(String sql) {
        log.debug("Sql: {}", sql);
        sql = sql.toLowerCase();
        if (!sql.startsWith("select")) {
            throw new IllegalArgumentException("Only select query is allowed");
        }
        return jdbcTemplate.queryForList(sql);
    }
}
