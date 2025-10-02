package dev.zendal.etlsetup.domain;

public enum EtlSessionStatus {
    ANALYZING,
    AI_ANALYZING,
    ERROR,
    AI_DATABASE_ANALYZING,
    USER_CHOOSE_DATABASE,
    AI_ETL_ANALYZING,
    ETL_CREATION,
    USER_WAITING,
    FINISHED
}
