package com.forecast.api.controller;

import com.forecast.api.model.Weather;
import com.forecast.api.owm.OpenWeatherMapClient;
import org.springframework.web.bind.annotation.*;

/**
 * Simple REST style controller to return Weather data in JSON format for
 * clients that need the "raw" data instead of a pre formatted view object.
 */
@RestController
@RequestMapping(value = "v1/weather")
public class WeatherController {

    private final OpenWeatherMapClient openWeatherMapClient;

    public WeatherController(OpenWeatherMapClient apiClient) {
        this.openWeatherMapClient = apiClient;
    }

    @GetMapping(value = "/city/{cityId}")
    public Weather getWeatherById(@PathVariable String cityId) {
        return openWeatherMapClient.fetchWeatherByCityId(cityId);
    }
}
