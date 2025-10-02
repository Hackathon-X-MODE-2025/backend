package dev.zendal.etlsetup.dto.source.hdfs;

import dev.zendal.etlsetup.dto.source.SourceSettings;

import java.util.List;

public interface HDFSSourceSettings extends SourceSettings {

    List<String> getPaths();
}
