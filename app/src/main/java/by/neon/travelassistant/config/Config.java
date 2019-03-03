package by.neon.travelassistant.config;

import java.util.ArrayList;

public abstract class Config {
    private ArrayList<AirportInfo> airportsInfo;

    public ArrayList<AirportInfo> getAirportsInfo() {
        return new ArrayList<>(airportsInfo);
    }

    void setAirportsInfo(ArrayList<AirportInfo> airportsInfo) {
        this.airportsInfo = airportsInfo;
    }
}
