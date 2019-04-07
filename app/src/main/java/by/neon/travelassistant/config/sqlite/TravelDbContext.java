package by.neon.travelassistant.config.sqlite;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import by.neon.travelassistant.config.sqlite.dao.AirportDao;
import by.neon.travelassistant.config.sqlite.dao.CityDao;
import by.neon.travelassistant.config.sqlite.dao.CountryDao;
import by.neon.travelassistant.config.sqlite.model.Airport;
import by.neon.travelassistant.config.sqlite.model.City;
import by.neon.travelassistant.config.sqlite.model.Country;

@Database(entities = {Country.class, City.class, Airport.class}, version = 1, exportSchema = false)
public abstract class TravelDbContext extends RoomDatabase {
    public abstract CountryDao countryDao();
    public abstract CityDao cityDao();
    public abstract AirportDao airportDao();
}
