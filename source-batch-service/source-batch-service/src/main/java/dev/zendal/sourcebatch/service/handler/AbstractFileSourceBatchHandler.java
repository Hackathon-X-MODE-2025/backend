package dev.zendal.sourcebatch.service.handler;

import dev.zendal.sourcebatch.dto.source.SourceSettings;
import dev.zendal.sourcebatch.dto.source.SourceSettingsType;
import dev.zendal.sourcebatch.dto.source.hdfs.HDFSSourceSettings;

import java.util.Optional;
import java.util.stream.Stream;

public abstract class AbstractFileSourceBatchHandler implements SourceBatchHandler {
    @Override
    public String process(SourceSettings sourceSettings) {
        return this.process(this.generatePaths(sourceSettings).toArray(String[]::new), sourceSettings);
    }

    private Stream<String> generatePaths(SourceSettings sourceSettings) {
        if (sourceSettings instanceof HDFSSourceSettings hdfsSourceSettings) {
            final var hadoopBase = Optional.ofNullable(System.getenv("HADOOP_URL")).orElseThrow(
                    () -> new IllegalStateException("HADOOP_URL is not set")
            );

            return hdfsSourceSettings.getPaths().stream().map(path -> hadoopBase + "/" + path);
        }

        throw new IllegalStateException("Unsupported SourceSettings type: " + sourceSettings.type() + " " + sourceSettings.getClass());
    }

    protected abstract String process(String[] fileLocations, SourceSettings sourceSettings);

    @Override
    public SourceSettingsType type() {
        return null;
    }
}
