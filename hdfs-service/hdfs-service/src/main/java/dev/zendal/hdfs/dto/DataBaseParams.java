package dev.zendal.hdfs.dto;

import lombok.Data;

@Data
public class DataBaseParams {

    private DataBaseType type;

    private String host;

    private String username;
    private String password;

    private String name;

    private String schema;


}
