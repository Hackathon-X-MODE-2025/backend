package dev.zendal.sourcebatch.service.database;

import dev.zendal.sourcebatch.dto.datasource.DataSourceType;
import dev.zendal.sourcebatch.dto.datasource.PostgreSQLDataSourceSettings;
import dev.zendal.sourcebatch.dto.request.DataBaseInitRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Component
public class PostgreSqlDataSourceInitializerHandler implements DataSourceInitializerHandler {
    @Override
    public void initSchema(DataBaseInitRequest request) {
        final var psql = (PostgreSQLDataSourceSettings) request.getDataSourceSettings();

        try {
            this.executeInConnection(psql, "postgres", connection -> this.createDb(connection, request, psql));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void createDb(Connection connection, DataBaseInitRequest request, PostgreSQLDataSourceSettings psql) throws SQLException {
        try {
            log.info("Creating database {}", psql.getDatabase());
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE DATABASE \"" + psql.getDatabase() + "\"");
        }catch (SQLException e){
            if (!e.getMessage().contains("already exists")) {
                throw e;
            }
            log.warn("Database {} already exists", psql.getDatabase());
        }
        this.executeInConnection(psql, psql.getDatabase(), newConnection -> this.createShema(newConnection, request, psql));

    }

    private void createShema(Connection connection, DataBaseInitRequest request, PostgreSQLDataSourceSettings psql) throws SQLException {

        log.info("Creating schema {}", psql.getSchema());
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE SCHEMA IF NOT EXISTS " + psql.getSchema());

        this.executeInConnection(psql, psql.getDatabase() + "?currentSchema=" + psql.getSchema(), newConnection -> {
            log.info("Executing DDL");
            newConnection.createStatement().executeUpdate(request.getDdl());
        });
    }


    private void executeInConnection(PostgreSQLDataSourceSettings psql, String database, ConnectionConsumer connectionConsumer) throws SQLException {

        final var url = "jdbc:postgresql://%s:%s/".formatted(psql.getHost(), psql.getPort());

        try (Connection dbConn = DriverManager.getConnection(url + database, psql.getUsername(), psql.getPassword())) {
            connectionConsumer.accept(dbConn);
        }
    }


    @Override
    public DataSourceType supportedDataSourceSettings() {
        return DataSourceType.POSTGRES;
    }


    interface ConnectionConsumer {
        void accept(Connection connection) throws SQLException;
    }
}
