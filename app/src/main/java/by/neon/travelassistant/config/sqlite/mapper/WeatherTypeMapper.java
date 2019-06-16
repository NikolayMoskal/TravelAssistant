package by.neon.travelassistant.config.sqlite.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import by.neon.travelassistant.model.Weather;
import by.neon.travelassistant.model.WeatherType;

/**
 * Makes the reference type for weather type title.
 */
public final class WeatherTypeMapper {
    /**
     * Gets the weather type instances based on weather data from server.
     *
     * @param weather the weather data.
     * @return the list of weather type instances.
     */
    public List<WeatherType> from(Weather weather) {
        List<WeatherType> weatherTypes = new ArrayList<>(0);
        for (String s : weather.getWeatherType()) {
            WeatherType weatherType = new WeatherType();
            weatherType.setType(s);
            weatherTypes.add(weatherType);
        }
        return weatherTypes;
    }

    /**
     * Gets the weather type instances based on many weather data from server.
     *
     * @param weatherList the list of weather data.
     * @return the list of weather type instances.
     */
    public List<WeatherType> from(List<Weather> weatherList) {
        Set<String> items = new HashSet<>(0);
        for (Weather weather : weatherList) {
            items.addAll(weather.getWeatherType());
        }
        List<WeatherType> weatherTypes = new ArrayList<>(0);
        for (String s : items) {
            WeatherType weatherType = new WeatherType();
            weatherType.setType(s);
            weatherTypes.add(weatherType);
        }
        return weatherTypes;
    }
}
