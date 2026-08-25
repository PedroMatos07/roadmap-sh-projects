package com.pedroMatos.weather_api.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenWeatherResponse(String name, List<WeatherDetail> weather, Main main){

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record WeatherDetail(String description){}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Main(Double temp){}
}


