package by.neon.travelassistant.config;

public class ThingModel {
    private String thingName;
    private String type;

    public String getThingName() {
        return thingName;
    }

    ThingModel setThingName(String thingName) {
        this.thingName = thingName;
        return this;
    }

    public String getType() {
        return type;
    }

    ThingModel setType(String type) {
        this.type = type;
        return this;
    }
}
