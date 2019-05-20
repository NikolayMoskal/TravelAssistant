package by.neon.travelassistant.config.sqlite;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import by.neon.travelassistant.config.sqlite.dao.CategoryDao;
import by.neon.travelassistant.config.sqlite.dao.GenderDao;
import by.neon.travelassistant.config.sqlite.dao.ThingCategoryDao;
import by.neon.travelassistant.config.sqlite.dao.ThingDao;
import by.neon.travelassistant.config.sqlite.dao.ThingWeatherTypeDao;
import by.neon.travelassistant.config.sqlite.dao.TransportDao;
import by.neon.travelassistant.config.sqlite.dao.TypeDao;
import by.neon.travelassistant.config.sqlite.dao.WeatherTypeDao;
import by.neon.travelassistant.config.sqlite.model.CategoryDb;
import by.neon.travelassistant.config.sqlite.model.GenderDb;
import by.neon.travelassistant.config.sqlite.model.ThingCategoryDb;
import by.neon.travelassistant.config.sqlite.model.ThingDb;
import by.neon.travelassistant.config.sqlite.model.ThingWeatherTypeDb;
import by.neon.travelassistant.config.sqlite.model.TransportDb;
import by.neon.travelassistant.config.sqlite.model.TypeDb;
import by.neon.travelassistant.config.sqlite.model.WeatherTypeDb;

@Database(entities = {ThingDb.class, TypeDb.class, CategoryDb.class, GenderDb.class, WeatherTypeDb.class,
        ThingCategoryDb.class, ThingWeatherTypeDb.class, TransportDb.class}, version = 1, exportSchema = false)
public abstract class TravelDbContext extends RoomDatabase {
    public abstract ThingDao getThingDao();

    public abstract TypeDao getTypeDao();

    public abstract CategoryDao getCategoryDao();

    public abstract GenderDao getGenderDao();

    public abstract WeatherTypeDao getWeatherTypeDao();

    public abstract ThingCategoryDao getThingCategoryDao();

    public abstract ThingWeatherTypeDao getThingWeatherTypeDao();

    public abstract TransportDao getTransportDao();
}
