package dev.zendal.hdfs.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileItem {

    private String name;

    private long size;

    private boolean isDirectory;

    private long lastModified;
}
