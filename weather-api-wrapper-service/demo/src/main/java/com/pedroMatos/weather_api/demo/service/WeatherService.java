package com.pedroMatos.weather_api.demo.service;

import com.pedroMatos.weather_api.demo.model.OpenWeatherResponse;
import com.pedroMatos.weather_api.demo.model.Weather;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;


@Service
public class WeatherService {

    private final RestClient viaWeatherAPI;

    public WeatherService(RestClient viaWeatherAPI) {
        this.viaWeatherAPI = viaWeatherAPI;
    }

    @Cacheable(value = "weathers" , key = "#city", unless = "#result == null")
    public Weather getWeather(String city){
        OpenWeatherResponse response = viaWeatherAPI.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/weather")
                            .queryParam("q", city)
                            .queryParam("units", "metric")
                            .build())
                    .retrieve()
                    .body(OpenWeatherResponse.class);
            if(response!=null){
                return new Weather(response.name(),response.main().temp(), response.weather().get(0).description(), LocalDateTime.now());
            }
            return null;

    }
}
