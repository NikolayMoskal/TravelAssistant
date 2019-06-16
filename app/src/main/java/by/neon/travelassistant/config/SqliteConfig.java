package by.neon.travelassistant.config;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import by.neon.travelassistant.R;
import by.neon.travelassistant.activity.query.impl.ThingSelectAsyncTask;
import by.neon.travelassistant.config.sqlite.DbWriter;
import by.neon.travelassistant.config.sqlite.JsonReader;
import by.neon.travelassistant.config.sqlite.mapper.ThingMapper;
import by.neon.travelassistant.config.sqlite.mapper.TransportMapper;
import by.neon.travelassistant.config.sqlite.model.ThingDb;
import by.neon.travelassistant.model.Thing;
import by.neon.travelassistant.model.Transport;

/**
 * Extracts the common-used objects from the SQLite database.
 */
public final class SqliteConfig extends Config {
    /**
     * The application context.
     */
    private Context context;

    /**
     * Builds a new instance of {@link SqliteConfig} using the app context.
     *
     * @param context the application context.
     */
    public SqliteConfig(Context context) {
        this.context = context;
    }

    /**
     * Gets the list of things.
     *
     * @return the list of things.
     * @throws Exception when error is occurred while extracting the things.
     * @see Thing
     */
    @Override
    public List<Thing> getThings() throws Exception {
        ThingSelectAsyncTask task = new ThingSelectAsyncTask();
        task.setSelectAll(true);
        List<ThingDb> thingDbs = task.execute().get();
        if (thingDbs.size() == 0) {
            return saveThingsToDatabase();
        }

        ArrayList<Thing> things = new ArrayList<>(0);
        ThingMapper mapper = new ThingMapper();
        things.addAll(mapper.to(thingDbs));
        return things;
    }

    /**
     * Writes the things to database.
     *
     * @return the list of things.
     * @throws IOException          when the JSON database was not read.
     * @throws ExecutionException   when the writing is not completed.
     * @throws InterruptedException when the writing was interrupted.
     */
    private List<Thing> saveThingsToDatabase() throws IOException, ExecutionException, InterruptedException {
        String thingsArray = readJsonFromFile(R.raw.all_things);
        String transportArray = readJsonFromFile(R.raw.transports);
        JsonReader reader = new JsonReader();
        List<Thing> things = reader.read(thingsArray);
        List<Transport> transports = reader.readTransports(transportArray);
        DbWriter writer = DbWriter.getInstance();
        writer.setTypeDbs(reader.getTypes());
        writer.setCategoryDbs(reader.getCategories());
        writer.setGenderDbs(reader.getGenders());
        writer.setWeatherTypeDbs(reader.getWeatherTypes());
        ThingMapper mapper = new ThingMapper();
        writer.setThingDbs(mapper.from(things));
        TransportMapper transportMapper = new TransportMapper();
        writer.setTransportDbs(transportMapper.from(transports));
        writer.write();
        return things;
    }

    /**
     * Reads the things from JSON file.
     *
     * @param resId the ID of a raw JSON file.
     * @return the JSON string.
     * @throws IOException when the JSON database was not read.
     */
    private String readJsonFromFile(int resId) throws IOException {
        Writer writer = new StringWriter();
        try (InputStream stream = context.getResources().openRawResource(resId);
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
            }
        }

        return writer.toString();
    }
}
