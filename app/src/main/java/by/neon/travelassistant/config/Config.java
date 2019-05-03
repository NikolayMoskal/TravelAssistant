package by.neon.travelassistant.config;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.model.Airport;
import by.neon.travelassistant.model.Thing;

public abstract class Config {
    private List<Airport> airportsInfo;
    private List<Thing> things;

    public List<Airport> getAirportsInfo() {
        return new ArrayList<>(airportsInfo);
    }

    void setAirportsInfo(List<Airport> airportsInfo) {
        this.airportsInfo = airportsInfo;
    }

    public List<Thing> getThings() {
        return new ArrayList<>(things);
    }

    void setThings(List<Thing> things) {
        this.things = things;
    }
}
