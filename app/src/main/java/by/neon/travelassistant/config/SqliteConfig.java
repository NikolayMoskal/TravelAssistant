package by.neon.travelassistant.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import by.neon.travelassistant.activity.query.CountryWithCitiesAsyncTask;
import by.neon.travelassistant.activity.query.QuerySet;
import by.neon.travelassistant.config.sqlite.model.Airport;
import by.neon.travelassistant.config.sqlite.select_model.CityWithAirports;
import by.neon.travelassistant.config.sqlite.select_model.CountryWithCities;

public final class SqliteConfig extends Config {
    public SqliteConfig() throws ExecutionException, InterruptedException {
        setAirportsInfo(getAirports());
    }

    private ArrayList<AirportInfo> getAirports() throws ExecutionException, InterruptedException {
        CountryWithCitiesAsyncTask task = new CountryWithCitiesAsyncTask(new QuerySet(null));
        List<CountryWithCities> countriesWithCities = task.execute().get();
        ArrayList<AirportInfo> list = new ArrayList<>(0);
        for (CountryWithCities countryWithCities : countriesWithCities) {
            for (CityWithAirports cityWithAirports : countryWithCities.getCities()) {
                for (Airport airport : cityWithAirports.getAirports()) {
                    list.add(new AirportInfo()
                            .setLatitude(airport.getLocation().getLatitude())
                            .setLongitude(airport.getLocation().getLongitude())
                            .setAirportName(airport.getName())
                            .setCityName(cityWithAirports.getCity().getCityName())
                            .setCityCode(cityWithAirports.getCity().getCityCode())
                            .setCountryName(countryWithCities.getCountry().getCountryName())
                            .setCountryCode(countryWithCities.getCountry().getCountryCode())
                            .setIataCode(airport.getIataCode())
                            .setIcaoCode(airport.getIcaoCode()));
                }
            }
        }
        return list;
    }
}
