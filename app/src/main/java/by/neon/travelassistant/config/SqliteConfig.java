package by.neon.travelassistant.config;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.Startup;
import by.neon.travelassistant.config.sqlite.TravelDbContext;
import by.neon.travelassistant.config.sqlite.model.Airport;
import by.neon.travelassistant.config.sqlite.model.City;
import by.neon.travelassistant.config.sqlite.model.Country;

public final class SqliteConfig extends Config {
    private final TravelDbContext context;

    public SqliteConfig() {
        this.context = Startup.getStartup().getDbContext();
        setAirportsInfo(getAirports());
    }

    private ArrayList<AirportInfo> getAirports() {
        List<Airport> airports = context.airportDao().getAll();
        ArrayList<AirportInfo> list = new ArrayList<>(0);
        for (Airport airport : airports) {
            City city = context.cityDao().getById(airport.getCityId());
            Country country = context.countryDao().getById(city.getCountryId());

            list.add(new AirportInfo()
                    .setLatitude(airport.getLocation().getLatitude())
                    .setLongitude(airport.getLocation().getLongitude())
                    .setAirportName(airport.getName())
                    .setCityName(city.getCityName())
                    .setCountryName(country.getCountryName())
                    .setIataCode(airport.getIataCode())
                    .setIcaoCode(airport.getIcaoCode()));
        }
        return list;
    }
}
