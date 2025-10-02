package dev.zendal.etlsetup.clinet;

import reactor.core.publisher.Mono;

public interface TranslatorClient {

    Mono<String> toRussian(String text);

    Mono<String> toEnglish(String text);
}
