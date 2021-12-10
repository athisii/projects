package com.athisii.authorization.config;

import com.athisii.authorization.model.IdentityContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

/**
 * @author athisii
 * @version 1.0
 * @since 13/10/21
 */

@Configuration
public class AuthorizationBeanConfig {
    @Bean
//    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    @RequestScope //same save @Scope("request")
    public IdentityContext identityContext() {
        return new IdentityContext();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
