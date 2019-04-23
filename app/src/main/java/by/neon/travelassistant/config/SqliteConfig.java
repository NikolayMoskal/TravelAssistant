package by.neon.travelassistant.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import by.neon.travelassistant.activity.query.CountryWithCitiesSelectAsyncTask;
import by.neon.travelassistant.activity.query.ThingSelectAsyncTask;
import by.neon.travelassistant.config.sqlite.mapper.AirportMapper;
import by.neon.travelassistant.config.sqlite.mapper.ThingMapper;
import by.neon.travelassistant.config.sqlite.model.AirportDb;
import by.neon.travelassistant.config.sqlite.model.CityDb;
import by.neon.travelassistant.config.sqlite.model.CountryDb;
import by.neon.travelassistant.config.sqlite.model.ThingDb;
import by.neon.travelassistant.model.Airport;
import by.neon.travelassistant.model.Thing;

public final class SqliteConfig extends Config {
    public SqliteConfig() throws ExecutionException, InterruptedException {
        setAirportsInfo(getAirports());
        setThings(getThingsInfo());
    }

    private ArrayList<Airport> getAirports() throws ExecutionException, InterruptedException {
        CountryWithCitiesSelectAsyncTask task = new CountryWithCitiesSelectAsyncTask();
        List<CountryDb> countriesWithCities = task.execute().get();
        ArrayList<Airport> list = new ArrayList<>(0);

        AirportMapper mapper = new AirportMapper();
        for (CountryDb countryDb : countriesWithCities) {
            for (CityDb cityDb : countryDb.getCities()) {
                cityDb.setCountryDb(countryDb);
                for (AirportDb airportDb : cityDb.getAirportDbs()) {
                    airportDb.setCityDb(cityDb);
                    list.add(mapper.to(airportDb));
                }
            }
        }
        return list;
    }

    private ArrayList<Thing> getThingsInfo() throws ExecutionException, InterruptedException {
        ThingSelectAsyncTask task = new ThingSelectAsyncTask();
        List<ThingDb> thingDbs = task.execute().get();
        ArrayList<Thing> things = new ArrayList<>(0);

        ThingMapper mapper = new ThingMapper();
        for (ThingDb thingDb : thingDbs) {
            things.add(mapper.to(thingDb));
        }
        return things;
    }
}
