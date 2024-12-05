package com.deepak.auth.app.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.token.jwt")
public class TokenProperty {

    private long expires;
    private String privateKey;
    private String publicKey;

}
