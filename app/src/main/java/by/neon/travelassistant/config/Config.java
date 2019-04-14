package by.neon.travelassistant.config;

import java.util.ArrayList;

public abstract class Config {
    private ArrayList<ThingModel> things;

    public ArrayList<ThingModel> getThings() {
        return new ArrayList<>(things);
    }

    void setThings(ArrayList<ThingModel> things) {
        this.things = things;
    }
}
