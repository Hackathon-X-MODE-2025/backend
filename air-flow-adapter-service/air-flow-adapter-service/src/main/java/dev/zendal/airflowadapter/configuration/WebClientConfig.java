package dev.zendal.airflowadapter.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
public class WebClientConfig {

    @Value("${auth.base-url}")
    private String baseUrl;

    @Value("${auth.token-uri}")
    private String tokenUri;

    @Value("${auth.username}")
    private String username;

    @Value("${auth.password}")
    private String password;

    private final AtomicReference<TokenHolder> tokenCache = new AtomicReference<>();

    @Bean
    public WebClient airflowWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(baseUrl)
                .filter(authorizationFilter())
                .build();
    }

    private ExchangeFilterFunction authorizationFilter() {
        return (request, next) -> getAccessToken()
                .flatMap(token -> next.exchange(
                        ClientRequest.from(request)
                                .headers(headers -> headers.setBearerAuth(token))
                                .build()
                ));
    }

    private Mono<String> getAccessToken() {
        TokenHolder cached = tokenCache.get();
        if (cached != null && cached.expiresAt().isAfter(Instant.now().plusSeconds(30))) {
            return Mono.just(cached.token());
        }

        return WebClient.create(baseUrl)
                .post()
                .uri(tokenUri)
                .bodyValue(new AuthRequest(username, password))
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .map(resp -> {
                    TokenHolder holder = new TokenHolder(
                            resp.access_token(),
                            Instant.now().plusSeconds(resp.expires_in())
                    );
                    tokenCache.set(holder);
                    return holder.token();
                });
    }

    private record AuthRequest(String username, String password) {}
    private record AuthResponse(String access_token, long expires_in) {}
    private record TokenHolder(String token, Instant expiresAt) {}
}
