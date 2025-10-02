package dev.zendal.sourcebatch.service.database;

import dev.zendal.sourcebatch.dto.datasource.ClickHouseDataSourceSettings;
import dev.zendal.sourcebatch.dto.datasource.DataSourceType;
import dev.zendal.sourcebatch.dto.request.DataBaseInitRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Component
public class ClickHouseDataSourceInitializerHandler implements DataSourceInitializerHandler {
    @Override
    public void initSchema(DataBaseInitRequest request) {
        final var ch = (ClickHouseDataSourceSettings) request.getDataSourceSettings();

        try {
            this.executeInConnection(ch, "default", connection -> this.createDb(connection, request, ch));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDb(Connection connection, DataBaseInitRequest request, ClickHouseDataSourceSettings ch) throws SQLException {
        try {
            log.info("Creating database {}", ch.getDatabase());
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + ch.getDatabase());
        } catch (SQLException e) {
            throw new RuntimeException("Error creating database " + ch.getDatabase(), e);
        }

        this.executeInConnection(ch, ch.getDatabase(), newConnection -> this.executeDdl(newConnection, request));
    }

    private void executeDdl(Connection connection, DataBaseInitRequest request) throws SQLException {
        log.info("Executing DDL in ClickHouse");
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(request.getDdl());
        }
    }

    private void executeInConnection(ClickHouseDataSourceSettings ch, String database, PostgreSqlDataSourceInitializerHandler.ConnectionConsumer connectionConsumer) throws SQLException {
        final var url = "jdbc:clickhouse://%s:%s/%s".formatted(ch.getHost(), ch.getPort(), database);

        try (Connection dbConn = DriverManager.getConnection(url, ch.getUsername(), ch.getPassword())) {
            connectionConsumer.accept(dbConn);
        }
    }

    @Override
    public DataSourceType supportedDataSourceSettings() {
        return DataSourceType.CLICK_HOUSE;
    }
}
