package by.neon.travelassistant.model;

import android.location.Location;
import android.location.LocationManager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Settings {
    private City city;
    @JsonProperty("start")
    private Date travelStartDate;
    @JsonProperty("end")
    private Date travelEndDate;
    private double weight;
    private List<String> genders;
    private List<String> transportTypes;
    private List<String> categories;
    private List<Selection> selections;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Date getTravelStartDate() {
        return travelStartDate;
    }

    public void setTravelStartDate(Date travelStartDate) {
        this.travelStartDate = travelStartDate;
    }

    public Date getTravelEndDate() {
        return travelEndDate;
    }

    public void setTravelEndDate(Date travelEndDate) {
        this.travelEndDate = travelEndDate;
    }

    public List<String> getGenders() {
        return genders;
    }

    public void setGenders(List<String> genders) {
        this.genders = genders;
    }

    public List<String> getTransportTypes() {
        return transportTypes;
    }

    public void setTransportTypes(List<String> transportTypes) {
        this.transportTypes = transportTypes;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Selection> getSelections() {
        return selections;
    }

    public void setSelections(List<Selection> selections) {
        this.selections = selections;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public static class Selection {
        private String category;
        private Map<String, Integer> things;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Map<String, Integer> getThings() {
            return things;
        }

        public void setThings(Map<String, Integer> things) {
            this.things = things;
        }

        @JsonIgnore
        public Map<String, Boolean> getAsBoolean() {
            Map<String, Boolean> list = new LinkedHashMap<>(0);
            for (Map.Entry<String, Integer> flag : this.things.entrySet()) {
                list.put(flag.getKey(), flag.getValue() == 1);
            }
            return list;
        }

        @JsonIgnore
        public void setAsBoolean(Map<String, Boolean> things) {
            this.things = new LinkedHashMap<>(0);
            for (Map.Entry<String, Boolean> flag : things.entrySet()) {
                this.things.put(flag.getKey(), flag.getValue() ? 1 : 0);
            }
        }
    }

    public static class City {
        private long cityCode;
        private String name;
        private double latitude;
        private double longitude;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public long getCityCode() {
            return cityCode;
        }

        public void setCityCode(long cityCode) {
            this.cityCode = cityCode;
        }

        @JsonIgnore
        public Location getLocation() {
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(latitude);
            location.setLongitude(longitude);

            return location;
        }

        @JsonIgnore
        public void setLocation(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }
}
