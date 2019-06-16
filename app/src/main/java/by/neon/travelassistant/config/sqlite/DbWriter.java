package by.neon.travelassistant.config.sqlite;

import java.util.List;
import java.util.concurrent.ExecutionException;

import by.neon.travelassistant.activity.query.impl.CategoryInsertAsyncTask;
import by.neon.travelassistant.activity.query.impl.GenderInsertAsyncTask;
import by.neon.travelassistant.activity.query.impl.ThingInsertAsyncTask;
import by.neon.travelassistant.activity.query.impl.TransportInsertAsyncTask;
import by.neon.travelassistant.activity.query.impl.TypeInsertAsyncTask;
import by.neon.travelassistant.activity.query.impl.WeatherTypeInsertAsyncTask;
import by.neon.travelassistant.config.sqlite.model.CategoryDb;
import by.neon.travelassistant.config.sqlite.model.GenderDb;
import by.neon.travelassistant.config.sqlite.model.ThingDb;
import by.neon.travelassistant.config.sqlite.model.TransportDb;
import by.neon.travelassistant.config.sqlite.model.TypeDb;
import by.neon.travelassistant.config.sqlite.model.WeatherTypeDb;

/**
 * Writes values to database.
 */
public class DbWriter {
    /**
     * The instance of this writer.
     */
    private static final DbWriter ourInstance = new DbWriter();
    /**
     * The list of {@link GenderDb}.
     */
    private List<GenderDb> genderDbs;
    /**
     * The list of {@link CategoryDb}.
     */
    private List<CategoryDb> categoryDbs;
    /**
     * The list of {@link TypeDb}.
     */
    private List<TypeDb> typeDbs;
    /**
     * The list of {@link WeatherTypeDb}.
     */
    private List<WeatherTypeDb> weatherTypeDbs;
    /**
     * The list of {@link TransportDb}.
     */
    private List<TransportDb> transportDbs;
    /**
     * The list of {@link ThingDb}.
     */
    private List<ThingDb> thingDbs;

    /**
     * Builds a new instance of {@link DbWriter}.
     */
    private DbWriter() {
    }

    /**
     * Gets the instance of {@link DbWriter}.
     *
     * @return the instance.
     */
    public static DbWriter getInstance() {
        return ourInstance;
    }

    /**
     * Executes the writing in database.
     *
     * @throws ExecutionException   when the writing is not completed.
     * @throws InterruptedException when the writing was interrupted.
     */
    public void write() throws ExecutionException, InterruptedException {
        writeTransport(transportDbs);
        writeGenders(genderDbs);
        writeCategories(categoryDbs);
        writeTypes(typeDbs);
        writeWeatherTypes(weatherTypeDbs);
        writeThings(thingDbs);
    }

    /**
     * Writes the list of genders in database.
     *
     * @param genderDbs the list of genders.
     * @throws ExecutionException   when the writing is not completed.
     * @throws InterruptedException when the writing was interrupted.
     */
    private void writeGenders(List<GenderDb> genderDbs) throws ExecutionException, InterruptedException {
        GenderInsertAsyncTask task = new GenderInsertAsyncTask();
        task.execute(genderDbs.toArray(new GenderDb[0])).get();
    }

    /**
     * Writes the list of categories in database.
     *
     * @param categoryDbs the list of categories.
     * @throws ExecutionException   when the writing is not completed.
     * @throws InterruptedException when the writing was interrupted.
     */
    private void writeCategories(List<CategoryDb> categoryDbs) throws ExecutionException, InterruptedException {
        CategoryInsertAsyncTask task = new CategoryInsertAsyncTask();
        task.execute(categoryDbs.toArray(new CategoryDb[0])).get();
    }

    /**
     * Writes the list of thing types in database.
     *
     * @param typeDbs the list of thing types in database.
     * @throws ExecutionException   when the writing is not completed.
     * @throws InterruptedException when the writing was interrupted.
     */
    private void writeTypes(List<TypeDb> typeDbs) throws ExecutionException, InterruptedException {
        TypeInsertAsyncTask task = new TypeInsertAsyncTask();
        task.execute(typeDbs.toArray(new TypeDb[0])).get();
    }

    /**
     * Writes the list of weather types in database.
     *
     * @param weatherTypeDbs the list of weather types.
     * @throws ExecutionException   when the writing is not completed.
     * @throws InterruptedException when the writing was interrupted.
     */
    private void writeWeatherTypes(List<WeatherTypeDb> weatherTypeDbs) throws ExecutionException, InterruptedException {
        WeatherTypeInsertAsyncTask task = new WeatherTypeInsertAsyncTask();
        task.execute(weatherTypeDbs.toArray(new WeatherTypeDb[0])).get();
    }

    /**
     * Writes things in database.
     *
     * @param thingDbs the list of things.
     * @throws ExecutionException   when the writing is not completed.
     * @throws InterruptedException when the writing was interrupted.
     */
    private void writeThings(List<ThingDb> thingDbs) throws ExecutionException, InterruptedException {
        ThingInsertAsyncTask task = new ThingInsertAsyncTask();
        task.execute(thingDbs.toArray(new ThingDb[0])).get();
    }

    /**
     * Writes list of transport in database.
     *
     * @param transportDbs the list of transports.
     * @throws ExecutionException   when the writing is not completed.
     * @throws InterruptedException when the writing was interrupted.
     */
    private void writeTransport(List<TransportDb> transportDbs) throws ExecutionException, InterruptedException {
        TransportInsertAsyncTask task = new TransportInsertAsyncTask();
        task.execute(transportDbs.toArray(new TransportDb[0])).get();
    }

    /**
     * Sets the list of genders to writing.
     *
     * @param genderDbs the genders to set.
     */
    public void setGenderDbs(List<GenderDb> genderDbs) {
        this.genderDbs = genderDbs;
    }

    /**
     * Sets the categories to writing.
     *
     * @param categoryDbs the categories to set.
     */
    public void setCategoryDbs(List<CategoryDb> categoryDbs) {
        this.categoryDbs = categoryDbs;
    }

    /**
     * Sets the thing types to writing.
     *
     * @param typeDbs the thing types to set.
     */
    public void setTypeDbs(List<TypeDb> typeDbs) {
        this.typeDbs = typeDbs;
    }

    /**
     * Sets the weather types to writing.
     *
     * @param weatherTypeDbs the weather type to set.
     */
    public void setWeatherTypeDbs(List<WeatherTypeDb> weatherTypeDbs) {
        this.weatherTypeDbs = weatherTypeDbs;
    }

    /**
     * Sets the things to writing.
     *
     * @param thingDbs the things to set.
     */
    public void setThingDbs(List<ThingDb> thingDbs) {
        this.thingDbs = thingDbs;
    }

    /**
     * Sets transports to writing.
     *
     * @param transportDbs the transports to set.
     */
    public void setTransportDbs(List<TransportDb> transportDbs) {
        this.transportDbs = transportDbs;
    }
}
