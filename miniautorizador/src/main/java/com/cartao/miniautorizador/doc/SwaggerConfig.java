package com.cartao.miniautorizador.doc;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi cartoesApi() {
        return GroupedOpenApi.builder()
                .group("cartoes-api")
                .pathsToMatch("/cartoes/**", "/transacoes/**")
                .build();
    }
}