package by.neon.travelassistant.listener;

import java.util.List;

import by.neon.travelassistant.model.Weather;

public interface ForecastCallback {
    void onSuccess(List<Weather> weatherList);
}
