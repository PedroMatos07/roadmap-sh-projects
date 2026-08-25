package com.pedroMatos.weather_api.demo.controller;

import com.pedroMatos.weather_api.demo.model.Weather;
import com.pedroMatos.weather_api.demo.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public Weather getWeather(@RequestParam String city){
        return weatherService.getWeather(city);
    }
}
