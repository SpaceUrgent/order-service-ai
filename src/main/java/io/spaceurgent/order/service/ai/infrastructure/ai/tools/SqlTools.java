package io.spaceurgent.order.service.ai.infrastructure.ai.tools;

import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SqlTools {
    private final DatabaseQueryService databaseQueryService;

    @Tool(name = "listTables", value = "Returns list of tables in the database")
    public List<String> listTables() {
        log.debug("List tables called");
        return databaseQueryService.listTables();
    }

    @Tool(name = "describeTable", value = "Returns columns with datatypes for the specified table.")
    public List<Map<String, Object>> describeTable(String tableName) {
        log.debug("Describe {} table", tableName);
        return databaseQueryService.describeTable(tableName);
    }

    @Tool(name = "executeSelectQuery", value = "Executed valid select query")
    public List<Map<String, Object>> executeSelectQuery(String sql) {
        log.debug("Sql: {}", sql);
        return databaseQueryService.executeSelectQuery(sql);
    }
}
