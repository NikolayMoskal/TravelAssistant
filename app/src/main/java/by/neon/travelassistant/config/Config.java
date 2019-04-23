package by.neon.travelassistant.config;

import java.util.ArrayList;

import by.neon.travelassistant.model.Airport;
import by.neon.travelassistant.model.Thing;

public abstract class Config {
    private ArrayList<Airport> airportsInfo;
    private ArrayList<Thing> things;

    public ArrayList<Airport> getAirportsInfo() {
        return new ArrayList<>(airportsInfo);
    }

    void setAirportsInfo(ArrayList<Airport> airportsInfo) {
        this.airportsInfo = airportsInfo;
    }

    public ArrayList<Thing> getThings() {
        return new ArrayList<>(things);
    }

    void setThings(ArrayList<Thing> things) {
        this.things = things;
    }
}
