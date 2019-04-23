package by.neon.travelassistant.config.sqlite;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import by.neon.travelassistant.config.sqlite.dao.AirportDao;
import by.neon.travelassistant.config.sqlite.dao.CityDao;
import by.neon.travelassistant.config.sqlite.dao.CountryDao;
import by.neon.travelassistant.config.sqlite.dao.ThingDao;
import by.neon.travelassistant.config.sqlite.model.AirportDb;
import by.neon.travelassistant.config.sqlite.model.CityDb;
import by.neon.travelassistant.config.sqlite.model.CountryDb;
import by.neon.travelassistant.config.sqlite.model.ThingDb;

@Database(entities = {CountryDb.class, CityDb.class, AirportDb.class, ThingDb.class}, version = 1, exportSchema = false)
public abstract class TravelDbContext extends RoomDatabase {
    public abstract CountryDao countryDao();
    public abstract CityDao cityDao();
    public abstract AirportDao airportDao();
    public abstract ThingDao thingDao();
}
