package dev.zendal.sourcebatch.dto.source.hdfs;

import dev.zendal.sourcebatch.dto.source.SourceSettings;

import java.util.List;

public interface HDFSSourceSettings extends SourceSettings {

    List<String> getPaths();
}
