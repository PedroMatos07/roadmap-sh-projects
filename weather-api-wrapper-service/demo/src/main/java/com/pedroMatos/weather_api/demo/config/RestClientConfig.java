package com.pedroMatos.weather_api.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient viaWeatherAPI(@Value("${openweather.api.key}") String apiKey){

    return RestClient.builder().baseUrl("https://api.openweathermap.org/data/2.5?appid={chave}")
            .defaultUriVariables(Map.of("chave", apiKey))
            .defaultHeader("Accept", "application/json")
            .build();
    }

}
