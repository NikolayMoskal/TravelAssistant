package by.neon.travelassistant.config;

import java.util.ArrayList;

import by.neon.travelassistant.model.Airport;

public abstract class Config {
    private ArrayList<Airport> airportsInfo;
    private ArrayList<ThingModel> things;

    public ArrayList<Airport> getAirportsInfo() {
        return new ArrayList<>(airportsInfo);
    }

    void setAirportsInfo(ArrayList<Airport> airportsInfo) {
        this.airportsInfo = airportsInfo;
    }

    public ArrayList<ThingModel> getThings() {
        return new ArrayList<>(things);
    }

    void setThings(ArrayList<ThingModel> things) {
        this.things = things;
    }
}
