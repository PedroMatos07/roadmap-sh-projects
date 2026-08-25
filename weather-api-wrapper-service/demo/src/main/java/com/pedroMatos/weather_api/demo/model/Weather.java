package com.pedroMatos.weather_api.demo.model;

import java.time.LocalDateTime;

public class Weather {
    private String city;
    private Double temp;
    private String weather;

    public Weather(){}


    public Weather(String city, Double temp, String weather, LocalDateTime time) {
        this.city = city;
        this.temp = temp;
        this.weather = weather;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

}
