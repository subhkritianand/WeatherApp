package com.forecast.api.controller;

import com.forecast.api.model.Weather;
import com.forecast.api.model.WeatherView;
import com.forecast.api.owm.OpenWeatherMapClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.websocket.server.PathParam;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Main site controller to handle user request while browsing the site.
 *
 * Note: the home (or index) page is served statically, check resources/static folder.
 */
@Controller
public class HomeController {

    private final OpenWeatherMapClient openWeatherMapClient;

    public HomeController(OpenWeatherMapClient openWeatherMapClient) {
        this.openWeatherMapClient = openWeatherMapClient;
    }

    /**
     * This endpoint will simply redirect to a page that contains an input box to make it easy to introduce
     * a city Id.
     */
    @GetMapping(value = "/debug")
    public String index() {
        return "debugHome";
    }

    @GetMapping(value = "/weather")
    public String weather(@PathParam("cityId") String cityId, Model model) {
        Weather weather = this.openWeatherMapClient.fetchWeatherByCityId(cityId);
        model.addAttribute("weatherView", buildFromWeather(weather));
        return "forecast";
    }

    // The use of timeZone as it is works partially, for Hong Kong country code (HK) it does not get the proper timezone
    private WeatherView buildFromWeather(Weather weather) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm (z)");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a (z)");
        timeFormat.setTimeZone(TimeZone.getTimeZone(weather.getCountryCode()));
        WeatherView view = WeatherView.WeatherViewBuilder.aWeatherView()
                .withDate(dateFormat.format(new Date(weather.getDate() * 1000L)))
                .withCityName(weather.getCityName())
                .withOverallDescription(weather.getDescription())
                .withTempC(Double.toString(weather.getTempCelsius()))
                .withTempF(Double.toString(weather.getTempFahrenheit()))
                .withSunrise(timeFormat.format(new Date(weather.getSunrise() * 1000L)))
                .withSunset(timeFormat.format(new Date(weather.getSunset() * 1000L)))
                .build();
        return view;
    }
}
