package by.neon.travelassistant.config;

import java.util.ArrayList;

public final class SqliteConfig extends Config {
    public SqliteConfig() {
        setThings(getThings());
    }

    public ArrayList<ThingModel> getThings() {
        ArrayList<ThingModel> things = new ArrayList<>(0);
        things.add(new ThingModel().setThingName("ABC").setType("Type A"));
        things.add(new ThingModel().setThingName("DEF").setType("Type B"));
        return things;
    }
}
