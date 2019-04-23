package by.neon.travelassistant.model;

public class Thing extends Entity {
    private String thingNameEn;
    private String thingNameRu;
    private double weight;
    private String type;
    private String category;

    public String getThingNameEn() {
        return thingNameEn;
    }

    public void setThingNameEn(String thingNameEn) {
        this.thingNameEn = thingNameEn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getThingNameRu() {
        return thingNameRu;
    }

    public void setThingNameRu(String thingNameRu) {
        this.thingNameRu = thingNameRu;
    }
}
