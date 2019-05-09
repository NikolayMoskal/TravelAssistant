package by.neon.travelassistant.config.sqlite;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import by.neon.travelassistant.config.sqlite.dao.*;
import by.neon.travelassistant.config.sqlite.model.*;

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
