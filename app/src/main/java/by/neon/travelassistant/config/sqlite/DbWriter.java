package by.neon.travelassistant.config.sqlite;

import java.util.List;
import java.util.concurrent.ExecutionException;

import by.neon.travelassistant.activity.query.CategoryInsertAsyncTask;
import by.neon.travelassistant.activity.query.GenderInsertAsyncTask;
import by.neon.travelassistant.activity.query.ThingInsertAsyncTask;
import by.neon.travelassistant.activity.query.TransportInsertAsyncTask;
import by.neon.travelassistant.activity.query.TypeInsertAsyncTask;
import by.neon.travelassistant.activity.query.WeatherTypeInsertAsyncTask;
import by.neon.travelassistant.config.sqlite.model.CategoryDb;
import by.neon.travelassistant.config.sqlite.model.GenderDb;
import by.neon.travelassistant.config.sqlite.model.ThingDb;
import by.neon.travelassistant.config.sqlite.model.TransportDb;
import by.neon.travelassistant.config.sqlite.model.TypeDb;
import by.neon.travelassistant.config.sqlite.model.WeatherTypeDb;

public class DbWriter {
    private static final DbWriter ourInstance = new DbWriter();
    private List<GenderDb> genderDbs;
    private List<CategoryDb> categoryDbs;
    private List<TypeDb> typeDbs;
    private List<WeatherTypeDb> weatherTypeDbs;
    private List<TransportDb> transportDbs;
    private List<ThingDb> thingDbs;

    private DbWriter() {
    }

    public static DbWriter getInstance() {
        return ourInstance;
    }

    public void write() throws ExecutionException, InterruptedException {
        writeTransport(transportDbs);
        writeGenders(genderDbs);
        writeCategories(categoryDbs);
        writeTypes(typeDbs);
        writeWeatherTypes(weatherTypeDbs);
        writeThings(thingDbs);
    }

    private void writeGenders(List<GenderDb> genderDbs) throws ExecutionException, InterruptedException {
        GenderInsertAsyncTask task = new GenderInsertAsyncTask();
        task.execute(genderDbs.toArray(new GenderDb[0])).get();
    }

    private void writeCategories(List<CategoryDb> categoryDbs) throws ExecutionException, InterruptedException {
        CategoryInsertAsyncTask task = new CategoryInsertAsyncTask();
        task.execute(categoryDbs.toArray(new CategoryDb[0])).get();
    }

    private void writeTypes(List<TypeDb> typeDbs) throws ExecutionException, InterruptedException {
        TypeInsertAsyncTask task = new TypeInsertAsyncTask();
        task.execute(typeDbs.toArray(new TypeDb[0])).get();
    }

    private void writeWeatherTypes(List<WeatherTypeDb> weatherTypeDbs) throws ExecutionException, InterruptedException {
        WeatherTypeInsertAsyncTask task = new WeatherTypeInsertAsyncTask();
        task.execute(weatherTypeDbs.toArray(new WeatherTypeDb[0])).get();
    }

    private void writeThings(List<ThingDb> thingDbs) throws ExecutionException, InterruptedException {
        ThingInsertAsyncTask task = new ThingInsertAsyncTask();
        task.execute(thingDbs.toArray(new ThingDb[0])).get();
    }

    private void writeTransport(List<TransportDb> transportDbs) throws ExecutionException, InterruptedException {
        TransportInsertAsyncTask task = new TransportInsertAsyncTask();
        task.execute(transportDbs.toArray(new TransportDb[0])).get();
    }

    public void setGenderDbs(List<GenderDb> genderDbs) {
        this.genderDbs = genderDbs;
    }

    public void setCategoryDbs(List<CategoryDb> categoryDbs) {
        this.categoryDbs = categoryDbs;
    }

    public void setTypeDbs(List<TypeDb> typeDbs) {
        this.typeDbs = typeDbs;
    }

    public void setWeatherTypeDbs(List<WeatherTypeDb> weatherTypeDbs) {
        this.weatherTypeDbs = weatherTypeDbs;
    }

    public void setThingDbs(List<ThingDb> thingDbs) {
        this.thingDbs = thingDbs;
    }

    public void setTransportDbs(List<TransportDb> transportDbs) {
        this.transportDbs = transportDbs;
    }
}
