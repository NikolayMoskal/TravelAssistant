package by.neon.travelassistant.config.sqlite.mapper;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.config.sqlite.model.CategoryDb;
import by.neon.travelassistant.config.sqlite.model.GenderDb;
import by.neon.travelassistant.config.sqlite.model.ThingDb;
import by.neon.travelassistant.config.sqlite.model.TypeDb;
import by.neon.travelassistant.config.sqlite.model.WeatherTypeDb;
import by.neon.travelassistant.model.Thing;

public final class ThingMapper extends BaseMapper<Thing, ThingDb> {
    @Override
    public ThingDb from(Thing source) {
        ThingDb thingDb = new ThingDb();
        thingDb.setId(source.getId());
        thingDb.setThingNameEn(source.getThingNameEn());
        thingDb.setThingNameRu(source.getThingNameRu());
        thingDb.setTypeId(source.getTypeId());
        thingDb.setTypeDb(createTypeInstance(source.getTypeId(), source.getType()));
        thingDb.setCategoryDbs(createCategoryInstances(source.getCategory()));
        thingDb.setWeight(source.getWeight());
        thingDb.setGenderId(source.getGenderId());
        thingDb.setGenderDb(createGenderInstance(source.getGenderId(), source.getGender()));
        thingDb.setWeatherTypeDbs(createWeatherTypeInstances(source.getWeatherType()));
        return thingDb;
    }

    private List<CategoryDb> createCategoryInstances(List<String> names) {
        List<CategoryDb> list = new ArrayList<>(0);
        for (String name : names) {
            CategoryDb categoryDb = new CategoryDb();
            categoryDb.setCategoryNameEn(name);
            categoryDb.setCategoryNameRu(new CategoryMapper().toRu(name));
            list.add(categoryDb);
        }
        return list;
    }

    private List<WeatherTypeDb> createWeatherTypeInstances(List<String> names) {
        List<WeatherTypeDb> list = new ArrayList<>(0);
        for (String name : names) {
            WeatherTypeDb weatherTypeDb = new WeatherTypeDb();
            weatherTypeDb.setType(name);
            list.add(weatherTypeDb);
        }
        return list;
    }

    private TypeDb createTypeInstance(long id, String nameEn) {
        TypeDb typeDb = new TypeDb();
        typeDb.setId(id);
        typeDb.setTypeNameEn(nameEn);
        typeDb.setTypeNameRu(new TypeMapper().toRu(nameEn));
        return typeDb;
    }

    private GenderDb createGenderInstance(long id, String type) {
        GenderDb genderDb = new GenderDb();
        genderDb.setId(id);
        genderDb.setTypeEn(type);
        genderDb.setTypeRu(new GenderMapper().toRu(type));
        return genderDb;
    }

    @Override
    public Thing to(ThingDb source) {
        Thing thing = new Thing();
        thing.setId(source.getId());
        thing.setThingNameEn(source.getThingNameEn());
        thing.setThingNameRu(source.getThingNameRu());
        thing.setType(source.getTypeDb().getTypeNameEn());
        thing.setTypeId(source.getTypeId());
        thing.setCategory(createCategoryNames(source.getCategoryDbs()));
        thing.setWeight(source.getWeight());
        thing.setGender(source.getGenderDb().getTypeEn());
        thing.setGenderId(source.getGenderId());
        thing.setWeatherType(createWeatherTypeNames(source.getWeatherTypeDbs()));
        return thing;
    }

    private List<String> createCategoryNames(List<CategoryDb> instances) {
        List<String> list = new ArrayList<>(0);
        for (CategoryDb categoryDb : instances) {
            list.add(categoryDb.getCategoryNameEn());
        }
        return list;
    }

    private List<String> createWeatherTypeNames(List<WeatherTypeDb> instances) {
        List<String> list = new ArrayList<>(0);
        for (WeatherTypeDb weatherTypeDb : instances) {
            list.add(weatherTypeDb.getType());
        }
        return list;
    }
}
