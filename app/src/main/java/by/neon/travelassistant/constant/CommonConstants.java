package by.neon.travelassistant.constant;

/**
 * Contains the tag constants that is commonly used in all application.
 */
public final class CommonConstants {
    /**
     * The file name for preferences with all lists of recommendations.
     */
    public static final String APP_LISTS = "LISTS";
    /**
     * The file name for preferences with all app settings.
     */
    public static final String APP_SETTINGS = "SETTINGS";
    /**
     * The tag for preference that contains all city codes from all lists.
     */
    public static final String APP_SETTINGS_KEYS = "KEYS";
    /**
     * The tag for new list.
     */
    public static final String NEW_LIST_TAG = "NEW_LIST";
    /**
     * The unique account ID in OpenWeatherMap service.
     */
    public static final String OWM_APP_ID = "1a3d439f77be3c3a7fe13b4ee1d0840c";

    /**
     * The tag for unique city code.
     */
    public static final String ARRIVAL_CITY_ID = "CITY_ID";
    /**
     * The tag for city name and country code.
     */
    public static final String ARRIVAL_CITY_INFO = "CITY_INFO";
    /**
     * The tag for location of the city.
     */
    public static final String ARRIVAL_CITY_LOCATION = "CITY_LOCATION";
    /**
     * The tag for count of categories.
     */
    public static final String COUNT_CATEGORIES = "COUNT_CATEGORIES";
    /**
     * The tag for count of the transport types.
     */
    public static final String COUNT_TRANSPORT_TYPES = "COUNT_TRANSPORT_TYPES";
    /**
     * The tag for count of gender types.
     */
    public static final String COUNT_GENDERS = "COUNT_GENDERS";
    /**
     * The tag for start date of the travel.
     */
    public static final String TRAVEL_START_DATE = "TRAVEL_START_DATE";
    /**
     * The tag for end date of the travel.
     */
    public static final String TRAVEL_END_DATE = "TRAVEL_END_DATE";
    /**
     * The tag for list of the selected things after list preview.
     */
    public static final String SELECTIONS = "SELECTIONS";

    /**
     * The tag for temperature unit (Kelvin, Celsius or Fahrenheit).
     */
    public static final String TEMPERATURE_UNIT = "TEMPERATURE_UNIT";
    /**
     * The tag for the designation of a temperature unit (ºK, ºC or ºF).
     */
    public static final String TEMPERATURE_UNIT_SIGN = "TEMPERATURE_UNIT_SIGN";
    /**
     * The tag for disable warnings option in settings.
     */
    public static final String DISABLE_WARN = "DISABLE_WARN";
    /**
     * The tag for disable error messages option in settings.
     */
    public static final String DISABLE_ERR = "DISABLE_ERR";

    /**
     * The constant number that allows to receive the list data from preview activity.
     */
    public static final int REQUEST_PREVIEW = 20;
}
