package io.spaceurgent.order.service.ai.infrastructure.ai.tools;

import dev.langchain4j.agent.tool.Tool;

import java.util.List;
import java.util.Map;

public interface DatabaseQueryService {

    List<String> listTables();

    List<Map<String, Object>> describeTable(String tableName);

    List<Map<String, Object>> executeSelectQuery(String sql);
}
