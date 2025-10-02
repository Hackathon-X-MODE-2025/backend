package dev.zendal.sourcebatch.service.handler;

import dev.zendal.sourcebatch.dto.source.SourceSettings;
import dev.zendal.sourcebatch.dto.source.SourceSettingsType;
import dev.zendal.sourcebatch.dto.source.database.PostgreSQLSourceSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostgresSourceBatchHandler implements SourceBatchHandler {

    @Override
    public String process(SourceSettings sourceSettings) {
        final var postgres = (PostgreSQLSourceSettings) sourceSettings;

        String[] command = {
                "pg_dump",
                "--host=" + postgres.getHost(),
                "--port=" + postgres.getPort(),
                "--username=" + postgres.getUsername(),
                "--schema-only",
                "--schema=" + postgres.getSchema(),
                postgres.getDatabase()
        };


        log.info("Executing command: {}", String.join(" ", command));

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.environment().put("PGPASSWORD", postgres.getPassword());

        Process process;

        try {
            process = pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final var builder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getErrorStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!builder.isEmpty()) {
            throw new RuntimeException("Error executing pg_dump: " + builder);
        }

        final var result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.info("{}", line);
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result.toString();
    }

    @Override
    public SourceSettingsType type() {
        return SourceSettingsType.POSTGRES;
    }
}
