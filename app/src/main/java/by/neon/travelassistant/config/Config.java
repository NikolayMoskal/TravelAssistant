package by.neon.travelassistant.config;

import java.util.ArrayList;
import java.util.List;

import by.neon.travelassistant.model.Airport;
import by.neon.travelassistant.model.OwmCity;
import by.neon.travelassistant.model.Thing;

public abstract class Config {
    private ArrayList<Airport> airportsInfo;
    private ArrayList<Thing> things;
    private List<OwmCity> cities;

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

    public List<OwmCity> getCities() {
        return cities;
    }

    void setCities(List<OwmCity> cities) {
        this.cities = cities;
    }
}
