package com.daall.howtoeat.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Value("${DOMAIN_URL}")
    private String domainUrl;
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("http://"+(domainUrl.replaceAll(":8080", ":3000")));
        config.addAllowedOriginPattern("https://howtoeat.ai.kr");
        config.addAllowedOriginPattern("https://www.howtoeat.ai.kr");

        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Authorization");
        config.addExposedHeader("RefreshToken");


        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}