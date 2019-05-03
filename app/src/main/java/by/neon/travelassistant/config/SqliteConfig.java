package by.neon.travelassistant.config;

import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import by.neon.travelassistant.activity.query.CountryWithCitiesSelectAsyncTask;
import by.neon.travelassistant.activity.query.ThingInsertAsyncTask;
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
    private Context context;

    public SqliteConfig(Context context) throws ExecutionException, InterruptedException, IOException {
        this.context = context;
        setAirportsInfo(getAirports());
        setThings(getThingsInfo());
    }

    private List<Airport> getAirports() throws ExecutionException, InterruptedException {
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

    private List<Thing> getThingsInfo() throws ExecutionException, InterruptedException, IOException {
        ThingSelectAsyncTask task = new ThingSelectAsyncTask();
        List<ThingDb> thingDbs = task.execute().get();
        if (thingDbs.size() == 0) {
            return saveThingsToDatabase();
        }

        ArrayList<Thing> things = new ArrayList<>(0);
        ThingMapper mapper = new ThingMapper();
        things.addAll(mapper.to(thingDbs));
        return things;
    }

    private List<Thing> saveThingsToDatabase() throws IOException, ExecutionException, InterruptedException {
        String array = readJsonFromFile();
        ObjectMapper mapper = new ObjectMapper();
        List<Thing> things = mapper.readValue(array, new TypeReference<List<Thing>>(){});
        ThingInsertAsyncTask task = new ThingInsertAsyncTask();
        ThingMapper thingMapper = new ThingMapper();
        task.execute(thingMapper.from(things).toArray(new ThingDb[things.size()])).get();
        return things;
    }

    private String readJsonFromFile() throws IOException {
        Writer writer = new StringWriter();
        try (InputStream stream = context.getResources().openRawResource(R.raw.all_things);
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
            }
        }

        return writer.toString();
    }
}
