package by.neon.travelassistant.config;

import java.util.List;

import by.neon.travelassistant.model.Thing;

public abstract class Config {
    public abstract List<Thing> getThings() throws Exception;
}
