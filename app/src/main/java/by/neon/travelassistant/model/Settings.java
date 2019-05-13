package by.neon.travelassistant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Settings {
    private String cityName;
    private long cityCode;
    @JsonProperty("start")
    private Date travelStartDate;
    @JsonProperty("end")
    private Date travelEndDate;
    private List<String> genders;
    private List<String> transportTypes;
    private List<String> categories;
    private List<Selection> selections;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getCityCode() {
        return cityCode;
    }

    public void setCityCode(long cityCode) {
        this.cityCode = cityCode;
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

    public static class Selection {
        private String category;
        private List<Integer> flags;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public List<Integer> getFlags() {
            return flags;
        }

        public void setFlags(List<Integer> flags) {
            this.flags = flags;
        }

        @JsonIgnore
        public void setFlagsAsBoolean(List<Boolean> flags) {
            this.flags = new ArrayList<>(0);
            for (Boolean flag : flags) {
                this.flags.add(flag ? 1 : 0);
            }
        }

        @JsonIgnore
        public List<Boolean> getFlagsAsBoolean() {
            List<Boolean> list = new ArrayList<>(0);
            for (Integer flag : this.flags) {
                list.add(flag == 1);
            }
            return list;
        }
    }
}
