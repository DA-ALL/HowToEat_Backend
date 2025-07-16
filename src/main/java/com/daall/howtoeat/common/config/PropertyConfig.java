package com.daall.howtoeat.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:properties/env-${spring.profiles.active}.properties")
})
public class PropertyConfig {

}
