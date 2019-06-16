package by.neon.travelassistant.config.sqlite;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import by.neon.travelassistant.config.sqlite.mapper.CategoryMapper;
import by.neon.travelassistant.config.sqlite.mapper.GenderMapper;
import by.neon.travelassistant.config.sqlite.mapper.TypeMapper;
import by.neon.travelassistant.config.sqlite.model.CategoryDb;
import by.neon.travelassistant.config.sqlite.model.GenderDb;
import by.neon.travelassistant.config.sqlite.model.TypeDb;
import by.neon.travelassistant.config.sqlite.model.WeatherTypeDb;
import by.neon.travelassistant.model.Thing;
import by.neon.travelassistant.model.Transport;

/**
 * Reads the JSON database and returns the parsed lists.
 */
public class JsonReader {
    /**
     * The thing type values.
     */
    private List<String> types;
    /**
     * The category values.
     */
    private List<String> categories;
    /**
     * The weather type values.
     */
    private List<String> weatherTypes;
    /**
     * The gender values.
     */
    private List<String> genders;

    /**
     * Reads the list of things from JSON.
     *
     * @param json the JSON string.
     * @return the list of parsed thing instances.
     * @throws IOException when the JSON database was not read.
     */
    public List<Thing> read(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Thing> things = mapper.readValue(json, new TypeReference<List<Thing>>() {
        });
        List<String> types = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        List<String> weatherTypes = new ArrayList<>();
        List<String> genders = new ArrayList<>();

        for (Thing thing : things) {
            types.add(thing.getType());
            categories.addAll(thing.getCategory());
            weatherTypes.addAll(thing.getWeatherType());
            genders.add(thing.getGender());
        }

        this.types = new ArrayList<>(new HashSet<>(types));
        this.categories = new ArrayList<>(new HashSet<>(categories));
        this.genders = new ArrayList<>(new HashSet<>(genders));
        this.weatherTypes = new ArrayList<>(new HashSet<>(weatherTypes));
        return things;
    }

    /**
     * Reads the transports from JSON.
     *
     * @param json the JSON string.
     * @return the list of transports.
     * @throws IOException when the JSON database was not read.
     */
    public List<Transport> readTransports(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<List<Transport>>() {});
    }

    /**
     * Creates and returns the reference types for each thing type.
     *
     * @return the thing types.
     */
    public List<TypeDb> getTypes() {
        List<TypeDb> typeDbs = new ArrayList<>();
        TypeMapper mapper = new TypeMapper();
        for (String type : types) {
            TypeDb typeDb = new TypeDb();
            typeDb.setTypeNameEn(type);
            typeDb.setTypeNameRu(mapper.toRu(type));
            typeDbs.add(typeDb);
        }
        return typeDbs;
    }

    /**
     * Creates and returns the reference types for each category.
     *
     * @return the categories.
     */
    public List<CategoryDb> getCategories() {
        List<CategoryDb> list = new ArrayList<>();
        CategoryMapper mapper = new CategoryMapper();
        for (String cat : categories) {
            CategoryDb categoryDb = new CategoryDb();
            categoryDb.setCategoryNameEn(cat);
            categoryDb.setCategoryNameRu(mapper.toRu(cat));
            list.add(categoryDb);
        }
        return list;
    }

    /**
     * Creates and returns the reference types for each weather type.
     *
     * @return the weather types.
     */
    public List<WeatherTypeDb> getWeatherTypes() {
        List<WeatherTypeDb> list = new ArrayList<>();
        for (String type : weatherTypes) {
            WeatherTypeDb weatherTypeDb = new WeatherTypeDb();
            weatherTypeDb.setType(type);
            list.add(weatherTypeDb);
        }
        return list;
    }

    /**
     * Creates and returns the reference types for each gender.
     *
     * @return the genders.
     */
    public List<GenderDb> getGenders() {
        List<GenderDb> list = new ArrayList<>();
        GenderMapper mapper = new GenderMapper();
        for (String type : genders) {
            GenderDb genderDb = new GenderDb();
            genderDb.setTypeEn(type);
            genderDb.setTypeRu(mapper.toRu(type));
            list.add(genderDb);
        }
        return list;
    }
}
