package dev.zendal.etlsetup.service.source.status.processor;

import dev.zendal.etlsetup.domain.source.EtlSessionSourceEntity;

public interface EtlSessionSourceStatusPostProcessor {

    void postProcess(EtlSessionSourceEntity entity);
}
