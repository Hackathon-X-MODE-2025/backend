package dev.zendal.sourcebatch.service.handler;

import dev.zendal.sourcebatch.dto.source.SourceSettings;
import dev.zendal.sourcebatch.dto.source.SourceSettingsType;
import dev.zendal.sourcebatch.dto.source.database.ClickHouseSourceSettings;
import org.springframework.stereotype.Component;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class ClickHouseSourceBatchHandler implements SourceBatchHandler {
    @Override
    public String process(SourceSettings sourceSettings) {

        final var clickHouse = (ClickHouseSourceSettings) sourceSettings;

        final var url = "jdbc:ch://%s:%s"
                .formatted(clickHouse.getHost(), clickHouse.getPort());

        final var info = new Properties();
        info.put("user", clickHouse.getUsername());
        info.put("password", clickHouse.getPassword());
        info.put("database", "system");


        final var result = new StringBuilder();

        try (final var conn = DriverManager.getConnection(url, info)) {
            final var statement = conn.prepareStatement("""
                    SELECT create_table_query FROM system.tables WHERE database = ?
                    """);

            statement.setString(1, clickHouse.getDatabase());

            final var set = statement.executeQuery();
            while (set.next()) {
                result.append(set.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result.toString();
    }

    @Override
    public SourceSettingsType type() {
        return SourceSettingsType.CLICK_HOUSE;
    }
}
