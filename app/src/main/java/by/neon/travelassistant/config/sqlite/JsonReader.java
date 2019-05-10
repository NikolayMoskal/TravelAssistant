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

public class JsonReader {
    private List<String> types;
    private List<String> categories;
    private List<String> weatherTypes;
    private List<String> genders;

    public List<Thing> read(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Thing> things = mapper.readValue(json, new TypeReference<List<Thing>>(){});
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

    public List<Transport> readTransports(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<List<Transport>>(){});
    }

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

    public List<WeatherTypeDb> getWeatherTypes() {
        List<WeatherTypeDb> list = new ArrayList<>();
        for (String type : weatherTypes) {
            WeatherTypeDb weatherTypeDb = new WeatherTypeDb();
            weatherTypeDb.setType(type);
            list.add(weatherTypeDb);
        }
        return list;
    }

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
