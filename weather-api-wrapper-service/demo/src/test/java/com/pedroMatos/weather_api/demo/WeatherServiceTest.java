package com.pedroMatos.weather_api.demo;

import com.pedroMatos.weather_api.demo.model.Weather;
import com.pedroMatos.weather_api.demo.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;


import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withNoContent;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class WeatherServiceTest {

    private WeatherService service;
    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder().baseUrl("https://api.openweathermap.org/data/2.5");
        mockServer = MockRestServiceServer.bindTo(builder).build();
        service = new WeatherService(builder.build());
    }

    @Test
    public void shouldReturnWeatherCity(){
      mockServer.expect(requestTo(containsString("/weather")))
              .andExpect(method(HttpMethod.GET))
              .andRespond(withSuccess("""
    { "name": "Recife", "main": { "temp": 29.5 }, "weather": [{ "description": "clear sky" }] }
""", MediaType.APPLICATION_JSON));
        Weather resultado = service.getWeather("Recife");

        mockServer.verify();

        assertEquals("Recife", resultado.getCity());
        assertEquals(29.5, resultado.getTemp());
        assertEquals("clear sky", resultado.getWeather());
    }

    @Test
    public void shouldReturnNull(){
        mockServer.expect(requestTo(containsString("/weather")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withNoContent());

        Weather resultado = service.getWeather("Sa");

        mockServer.verify();

        assertNull(resultado);
    }

}
