package dev.zendal.etlsetup.domain.source;

import java.util.Set;

public enum EtlSessionSourceStatus {
    PREPARING,
    PROCESSING,
    PROCESSED,
    ERROR;

    private static final Set<EtlSessionSourceStatus> TERMINATED = Set.of(
            EtlSessionSourceStatus.ERROR,
            EtlSessionSourceStatus.PROCESSED
    );

    public static Set<EtlSessionSourceStatus> terminated() {
        return TERMINATED;
    }
}
