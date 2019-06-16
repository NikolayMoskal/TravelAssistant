package by.neon.travelassistant.config;

import java.util.List;

import by.neon.travelassistant.model.Thing;

/**
 * Contains the read-only methods to get the common-used objects.
 */
public abstract class Config {
    /**
     * Gets the list of things.
     *
     * @return the list of things.
     * @throws Exception when error is occurred while extracting the things.
     * @see Thing
     */
    public abstract List<Thing> getThings() throws Exception;
}
