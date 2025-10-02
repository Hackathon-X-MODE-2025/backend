package dev.zendal.etlsetup.dto.source.s3;

import dev.zendal.etlsetup.dto.source.SourceSettings;

import java.util.List;

public interface S3SourceSettings extends SourceSettings {

    List<String> getS3Paths();
}
