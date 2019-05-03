package by.neon.travelassistant.config.sqlite.mapper;

import by.neon.travelassistant.model.Thing;
import by.neon.travelassistant.config.sqlite.model.ThingDb;

public final class ThingMapper extends BaseMapper<Thing, ThingDb> {
    @Override
    public ThingDb from(Thing source) {
        ThingDb thingDb = new ThingDb();
        thingDb.setThingNameEn(source.getThingNameEn());
        thingDb.setThingNameRu(source.getThingNameRu());
        thingDb.setType(source.getType());
        thingDb.setCategory(source.getCategory());
        thingDb.setWeight(source.getWeight());
        thingDb.setGender(source.getGender());
        thingDb.setWeatherType(source.getWeatherType());
        return thingDb;
    }

    @Override
    public Thing to(ThingDb source) {
        Thing thing = new Thing();
        thing.setThingNameEn(source.getThingNameEn());
        thing.setThingNameRu(source.getThingNameRu());
        thing.setType(source.getType());
        thing.setCategory(source.getCategory());
        thing.setWeight(source.getWeight());
        thing.setGender(source.getGender());
        thing.setWeatherType(source.getWeatherType());
        return thing;
    }
}
