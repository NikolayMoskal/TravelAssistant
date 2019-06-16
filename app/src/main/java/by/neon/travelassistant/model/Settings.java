package by.neon.travelassistant.model;

import android.location.Location;
import android.location.LocationManager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The settings of the list of recommendations.
 */
public class Settings {
    /**
     * The information about city or region.
     */
    private City city;
    /**
     * The start date of a travel.
     */
    @JsonProperty("start")
    private Date travelStartDate;
    /**
     * The end date of a travel.
     */
    @JsonProperty("end")
    private Date travelEndDate;
    /**
     * The weight of all selected things.
     */
    private double weight;
    /**
     * The selected gender types for this list.
     */
    private List<String> genders;
    /**
     * The selected transport types for this list.
     */
    private List<String> transportTypes;
    /**
     * The selected categories for this list.
     */
    private List<String> categories;
    /**
     * The selected things in this list.
     */
    private List<Selection> selections;

    /**
     * Gets the city of region info.
     *
     * @return the info.
     * @see City
     */
    public City getCity() {
        return city;
    }

    /**
     * Sets the city or region info.
     *
     * @param city the info to set.
     * @see City
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * Gets the start date of a travel.
     *
     * @return the date.
     */
    public Date getTravelStartDate() {
        return travelStartDate;
    }

    /**
     * Sets the start date of a travel.
     *
     * @param travelStartDate the date to set.
     */
    public void setTravelStartDate(Date travelStartDate) {
        this.travelStartDate = travelStartDate;
    }

    /**
     * Gets the end date of a travel.
     *
     * @return the date.
     */
    public Date getTravelEndDate() {
        return travelEndDate;
    }

    /**
     * Sets the end date of a travel.
     *
     * @param travelEndDate the date to set.
     */
    public void setTravelEndDate(Date travelEndDate) {
        this.travelEndDate = travelEndDate;
    }

    /**
     * Gets the selected gender types for this list.
     *
     * @return the list of gender types.
     */
    public List<String> getGenders() {
        return genders;
    }

    /**
     * Sets the selected gender types for this list.
     *
     * @param genders the list to set.
     */
    public void setGenders(List<String> genders) {
        this.genders = genders;
    }

    /**
     * Gets the selected transport types for this list.
     *
     * @return the list of transport types.
     */
    public List<String> getTransportTypes() {
        return transportTypes;
    }

    /**
     * Sets the selected transport types for this list.
     *
     * @param transportTypes the list to set.
     */
    public void setTransportTypes(List<String> transportTypes) {
        this.transportTypes = transportTypes;
    }

    /**
     * Gets the selected categories for this list.
     *
     * @return the list of categories.
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     * Sets the selected categories for this list.
     *
     * @param categories the list to set.
     */
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    /**
     * Gets the selected things in this list.
     *
     * @return the list of selected things.
     * @see Selection
     */
    public List<Selection> getSelections() {
        return selections;
    }

    /**
     * Sets the selected things in this list.
     *
     * @param selections the list to set.
     * @see Selection
     */
    public void setSelections(List<Selection> selections) {
        this.selections = selections;
    }

    /**
     * Gets the weight of all selected things.
     *
     * @return the weight.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the weight of all selected things.
     *
     * @param weight the weight to set.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * The information about all selected things for one category.
     */
    public static class Selection {
        /**
         * The category of selected things.
         */
        private String category;
        /**
         * The collection of selected things for this category.
         */
        private Map<String, Integer> things;

        /**
         * Gets the category name.
         *
         * @return the name.
         */
        public String getCategory() {
            return category;
        }

        /**
         * Sets the category name.
         *
         * @param category the name to set.
         */
        public void setCategory(String category) {
            this.category = category;
        }

        /**
         * Gets the collection of selected things.
         *
         * @return the collection.
         */
        public Map<String, Integer> getThings() {
            return things;
        }

        /**
         * Sets the collection of selected things.
         *
         * @param things the collection to set.
         */
        public void setThings(Map<String, Integer> things) {
            this.things = things;
        }

        /**
         * Gets the collection of selected things as flags.
         *
         * @return the collection of boolean values.
         */
        @JsonIgnore
        public Map<String, Boolean> getAsBoolean() {
            Map<String, Boolean> list = new LinkedHashMap<>(0);
            for (Map.Entry<String, Integer> flag : this.things.entrySet()) {
                list.put(flag.getKey(), flag.getValue() == 1);
            }
            return list;
        }

        /**
         * Sets the collection of selected things as flags.
         *
         * @param things the collection to set.
         */
        @JsonIgnore
        public void setAsBoolean(Map<String, Boolean> things) {
            this.things = new LinkedHashMap<>(0);
            for (Map.Entry<String, Boolean> flag : things.entrySet()) {
                this.things.put(flag.getKey(), flag.getValue() ? 1 : 0);
            }
        }
    }

    /**
     * The information about the city or region.
     */
    public static class City {
        /**
         * The unique 6-digital city or region code in OpenWeatherMap region database.
         */
        private long cityCode;
        /**
         * The name of the city or region.
         */
        private String name;
        /**
         * The latitude coordinate of this city or region.
         */
        private double latitude;
        /**
         * The longitude coordinate of this city or region.
         */
        private double longitude;

        /**
         * Gets the name of the city or region.
         *
         * @return the region name.
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the name of the city or region.
         *
         * @param name the name to set.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Gets the latitude coordinate of this city or region.
         *
         * @return the latitude.
         */
        public double getLatitude() {
            return latitude;
        }

        /**
         * Sets the latitude coordinate of this city or region.
         *
         * @param latitude the latitude to set.
         */
        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        /**
         * Gets the longitude coordinate of this city or region.
         *
         * @return the longitude.
         */
        public double getLongitude() {
            return longitude;
        }

        /**
         * Sets the longitude coordinate of this city or region.
         *
         * @param longitude the longitude to set.
         */
        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        /**
         * Gets the unique city code.
         *
         * @return the city ID.
         */
        public long getCityCode() {
            return cityCode;
        }

        /**
         * Sets the unique city code.
         *
         * @param cityCode the city ID to set.
         */
        public void setCityCode(long cityCode) {
            this.cityCode = cityCode;
        }

        /**
         * Gets the location of this city or region.
         *
         * @return the location.
         */
        @JsonIgnore
        public Location getLocation() {
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(latitude);
            location.setLongitude(longitude);

            return location;
        }

        /**
         * Sets the location of this city or region.
         *
         * @param location the location to set.
         */
        @JsonIgnore
        public void setLocation(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }
}
