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

/**
 * The main entry point for access to database. Contains all DAOs for each table in database.
 */
@Database(entities = {ThingDb.class, TypeDb.class, CategoryDb.class, GenderDb.class, WeatherTypeDb.class,
        ThingCategoryDb.class, ThingWeatherTypeDb.class, TransportDb.class}, version = 1, exportSchema = false)
public abstract class TravelDbContext extends RoomDatabase {
    /**
     * Gets the {@link ThingDao}.
     *
     * @return {@link ThingDao}.
     */
    public abstract ThingDao getThingDao();

    /**
     * Gets the {@link TypeDao}.
     *
     * @return {@link TypeDao}.
     */
    public abstract TypeDao getTypeDao();

    /**
     * Gets the {@link CategoryDao}.
     *
     * @return {@link CategoryDao}.
     */
    public abstract CategoryDao getCategoryDao();

    /**
     * Gets the {@link GenderDao}.
     *
     * @return {@link GenderDao}.
     */
    public abstract GenderDao getGenderDao();

    /**
     * Gets the {@link WeatherTypeDao}.
     *
     * @return {@link WeatherTypeDao}.
     */
    public abstract WeatherTypeDao getWeatherTypeDao();

    /**
     * Gets the {@link ThingCategoryDao}.
     *
     * @return {@link ThingCategoryDao}.
     */
    public abstract ThingCategoryDao getThingCategoryDao();

    /**
     * Gets the {@link ThingWeatherTypeDao}.
     *
     * @return {@link ThingWeatherTypeDao}.
     */
    public abstract ThingWeatherTypeDao getThingWeatherTypeDao();

    /**
     * Gets the {@link TransportDao}.
     *
     * @return {@link TransportDao}.
     */
    public abstract TransportDao getTransportDao();
}
