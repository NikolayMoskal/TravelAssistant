package by.neon.travelassistant.constant;

public final class DbConstants {
    public static final String DATABASE = "TravelDb";
    public static final String ID = "_id";

    public static final String TABLE_THINGS = "THINGS";
    public static final String THINGS_COLUMN_THING_NAME_EN_US = "THING_NAME_EN_US";
    public static final String THINGS_COLUMN_THING_NAME_RU_RU = "THING_NAME_RU_RU";
    public static final String THINGS_COLUMN_WEIGHT = "WEIGHT";
    public static final String THINGS_TYPES_FK_COLUMN = "TYPE_ID";
    public static final String THINGS_TYPES_FK_NAME = "THINGS_TYPES_FK";
    public static final String THINGS_GENDERS_FK_COLUMN = "GENDER_ID";
    public static final String THINGS_GENDERS_FK_NAME = "THINGS_GENDERS_FK";

    public static final String TABLE_TYPES = "TYPES";
    public static final String TYPES_COLUMN_TYPE_NAME = "TYPE_NAME";

    public static final String TABLE_CATEGORIES = "CATEGORIES";
    public static final String CATEGORIES_COLUMN_CATEGORY_NAME = "CATEGORY_NAME";

    public static final String TABLE_GENDERS = "GENDERS";
    public static final String GENDERS_COLUMN_GENDER_TYPE = "GENDER_TYPE";

    public static final String TABLE_WEATHER_TYPES = "WEATHER_TYPES";
    public static final String WEATHER_TYPES_COLUMN_TYPE_NAME = "TYPE_NAME";

    public static final String TABLE_TRANSPORTS = "TRANSPORTS";
    public static final String TRANSPORTS_COLUMN_TRANSPORT_NAME = "TRANSPORT_NAME";
    public static final String TRANSPORTS_COLUMN_PACK_WEIGHT = "PACK_WEIGHT";

    public static final String TABLE_THINGS_CATEGORIES = "THINGS_CATEGORIES";
    public static final String THINGS_CATEGORIES_COLUMN_THING_ID = "THING_ID";
    public static final String THINGS_CATEGORIES_COLUMN_CATEGORY_ID = "CATEGORY_ID";

    public static final String TABLE_THINGS_WEATHER_TYPES = "THINGS_WEATHER_TYPES";
    public static final String THINGS_WEATHER_TYPES_COLUMN_THING_ID = "THING_ID";
    public static final String THINGS_WEATHER_TYPES_COLUMN_WEATHER_TYPE_ID = "WEATHER_TYPE_ID";
}
