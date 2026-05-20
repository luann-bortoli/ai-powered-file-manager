package io.github.JarvisFs.ai.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class AppConfig
{
    @Bean
    public ObjectMapper om(){
        return new ObjectMapper();
    }

    @Bean
    public HttpClient httpClient(){
        return HttpClient.newHttpClient();
    }
}
