package by.neon.travelassistant.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import by.neon.travelassistant.R;
import by.neon.travelassistant.activity.query.impl.CategorySelectAsyncTask;
import by.neon.travelassistant.activity.query.impl.GenderSelectAsyncTask;
import by.neon.travelassistant.activity.query.impl.ThingSelectAsyncTask;
import by.neon.travelassistant.activity.query.impl.TransportSelectAsyncTask;
import by.neon.travelassistant.config.sqlite.mapper.CategoryMapper;
import by.neon.travelassistant.config.sqlite.mapper.GenderMapper;
import by.neon.travelassistant.config.sqlite.mapper.ThingMapper;
import by.neon.travelassistant.config.sqlite.mapper.TransportMapper;
import by.neon.travelassistant.config.sqlite.mapper.WeatherTypeMapper;
import by.neon.travelassistant.constant.CommonConstants;
import by.neon.travelassistant.listener.ForecastListener;
import by.neon.travelassistant.listener.ThingSelectListener;
import by.neon.travelassistant.model.Category;
import by.neon.travelassistant.model.Gender;
import by.neon.travelassistant.model.Settings;
import by.neon.travelassistant.model.Thing;
import by.neon.travelassistant.model.Transport;
import by.neon.travelassistant.model.WeatherType;
import by.neon.travelassistant.utility.TravelRequestQueue;

/**
 * The activity for show the list of recommendations.
 */
public class PreviewActivity extends AppCompatActivity {
    /**
     * The unique log tag constant for this class.
     */
    private static final String TAG = "PreviewActivity";
    /**
     * The list settings.
     */
    private Map<String, Object> input;
    /**
     * The listener for each thing. Calculates the weight of all selected things in the list.
     */
    private ThingSelectListener thingSelectListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        input = parseExtras(getIntent().getExtras());
        requestWeatherTypes();
    }

    @Override
    public void onBackPressed() {
        Intent intent = prepareIntentForResult();
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    /**
     * Collects the list settings data in the intent.
     *
     * @return the intent.
     */
    private Intent prepareIntentForResult() {
        Intent intent = new Intent(this, PackActivity.class);
        Settings settings = new Settings();
        Settings.City city = new Settings.City();
        city.setCityCode((long) input.get(CommonConstants.ARRIVAL_CITY_ID));
        city.setName(String.valueOf(input.get(CommonConstants.ARRIVAL_CITY_INFO)));
        city.setLocation((Location) input.get(CommonConstants.ARRIVAL_CITY_LOCATION));
        settings.setCity(city);
        settings.setCategories(getNames("category"));
        settings.setTransportTypes(getNames("transport"));
        settings.setGenders(getNames("gender"));
        settings.setWeight(thingSelectListener.getAllWeight());
        try {
            DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
            settings.setTravelStartDate(format.parse(String.valueOf(input.get(CommonConstants.TRAVEL_START_DATE))));
            settings.setTravelEndDate(format.parse(String.valueOf(input.get(CommonConstants.TRAVEL_END_DATE))));
        } catch (ParseException e) {
            Log.e(TAG, "onPreviewCloseClick: " + e.getMessage(), e);
        }
        settings.setSelections(getSelections());

        ObjectMapper mapper = new ObjectMapper();
        try {
            intent.putExtra(CommonConstants.NEW_LIST_TAG, mapper.writeValueAsString(settings));
        } catch (JsonProcessingException e) {
            Log.e(TAG, "onPreviewCloseClick: " + e.getMessage(), e);
        }
        return intent;
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     *
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     *
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     *
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     *
     * <p>When you add items to the menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.preview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     *
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_list:
                Intent intent = prepareIntentForResult();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.action_show_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Parses the list settings received from the {@link InputActivity}.
     *
     * @param extras the extra data.
     * @return the map with parsed data.
     */
    private Map<String, Object> parseExtras(Bundle extras) {
        Map<String, Object> map = new HashMap<>();
        if (extras == null) {
            return map;
        }

        map.put(CommonConstants.ARRIVAL_CITY_ID, extras.getLong(CommonConstants.ARRIVAL_CITY_ID, 0));
        map.put(CommonConstants.ARRIVAL_CITY_INFO, extras.getString(CommonConstants.ARRIVAL_CITY_INFO, ""));
        map.put(CommonConstants.ARRIVAL_CITY_LOCATION, Objects.requireNonNull(extras.get(CommonConstants.ARRIVAL_CITY_LOCATION)));
        map.put(CommonConstants.TRAVEL_START_DATE, extras.getString(CommonConstants.TRAVEL_START_DATE, ""));
        map.put(CommonConstants.TRAVEL_END_DATE, extras.getString(CommonConstants.TRAVEL_END_DATE, ""));
        extractData(extras, "category", CommonConstants.COUNT_CATEGORIES, map);
        extractData(extras, "transport", CommonConstants.COUNT_TRANSPORT_TYPES, map);
        extractData(extras, "gender", CommonConstants.COUNT_GENDERS, map);
        if (extras.containsKey(CommonConstants.SELECTIONS)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                List<Settings.Selection> selections = mapper.readValue(extras.getString(CommonConstants.SELECTIONS),
                        new TypeReference<List<Settings.Selection>>() {
                        });
                map.put(CommonConstants.SELECTIONS, selections);
            } catch (IOException e) {
                Log.e(TAG, "parseExtras: " + e.getMessage(), e);
            }
        }
        return map;
    }

    /**
     * Extracts the data by given {@code tagItem} and collects it in the map. The count of this data
     * given by the {@code tagCount} tag.
     *
     * @param extras   the extra data.
     * @param tagItem  the tag for target data.
     * @param tagCount the tag for count of the target data.
     * @param map      the map to collect the target data.
     */
    private void extractData(Bundle extras, String tagItem, String tagCount, Map<String, Object> map) {
        int count = extras.getInt(tagCount, 0);
        for (int index = 0; index < count; index++) {
            map.put(tagItem + index, extras.getString(tagItem + index, ""));
        }
    }

    /**
     * Extracts the categories from the database by given list of names.
     *
     * @return the list of categories.
     */
    private List<Category> extractTargets() {
        List<String> names = getNames("category");
        CategorySelectAsyncTask task = new CategorySelectAsyncTask();
        CategoryMapper mapper = new CategoryMapper();
        task.setNames(names);
        List<Category> list = new ArrayList<>();
        try {
            list.addAll(mapper.to(task.execute().get()));
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "extractTargets: " + e.getMessage(), e);
        }
        return list;
    }

    /**
     * Extracts the transport from the database by given name.
     *
     * @return the transport.
     */
    private Transport extractTransport() {
        List<String> names = getNames("transport");
        TransportSelectAsyncTask task = new TransportSelectAsyncTask();
        TransportMapper mapper = new TransportMapper();
        task.setNames(names);
        try {
            return mapper.to(task.execute().get().get(0));
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "extractTransports: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Extracts the genders from the database by given names.
     *
     * @return the list of genders.
     */
    private List<Gender> extractGenders() {
        List<String> names = getNames("gender");
        names.add("neutral");
        GenderSelectAsyncTask task = new GenderSelectAsyncTask();
        GenderMapper mapper = new GenderMapper();
        task.setTypes(names);
        List<Gender> genders = new ArrayList<>(0);
        try {
            genders.addAll(mapper.to(task.execute().get()));
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "extractGenders: " + e.getMessage(), e);
        }
        return genders;
    }

    /**
     * Makes the weather request to the server. When the data is received, then makes the list of recommendations
     * using the collected filters.
     */
    private void requestWeatherTypes() {
        SharedPreferences preferences = getSharedPreferences(CommonConstants.APP_SETTINGS, MODE_PRIVATE);
        String url = "https://api.openweathermap.org/data/2.5/forecast?" +
                "id=" + input.get(CommonConstants.ARRIVAL_CITY_ID) +
                "&appid=" + CommonConstants.OWM_APP_ID +
                "&lang=" + Locale.getDefault().getLanguage() +
                "&units=" + preferences.getString(CommonConstants.TEMPERATURE_UNIT, "Standard");
        ForecastListener listener = new ForecastListener(weatherList -> {
            try {
                WeatherTypeMapper mapper = new WeatherTypeMapper();
                fillPreview(mapper.from(weatherList));
                if (input.containsKey(CommonConstants.SELECTIONS)) {
                    //noinspection unchecked
                    setSelections((List<Settings.Selection>) input.get(CommonConstants.SELECTIONS));
                }
            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "onSuccess: " + e.getMessage(), e);
            }
        });
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, null);
        TravelRequestQueue.getInstance(this).addRequest(request);
    }

    /**
     * Extracts the names using given tag. For example, for extract the categories the application
     * uses the tag "category". It means that all categories have the names: category0,
     * category1 etc.
     *
     * @param tag the tag to extract the names collection.
     * @return the list of extracted names.
     */
    private List<String> getNames(String tag) {
        List<String> names = new ArrayList<>();
        for (Map.Entry<String, Object> item : input.entrySet()) {
            if (item.getKey().startsWith(tag)) {
                names.add((String) item.getValue());
            }
        }
        return names;
    }

    /**
     * Makes the list of recommendations.
     *
     * @param weatherTypes the weather types list based of current weather.
     * @throws ExecutionException   when the writing is not completed.
     * @throws InterruptedException when the writing was interrupted.
     */
    private void fillPreview(List<WeatherType> weatherTypes) throws ExecutionException, InterruptedException {
        List<Gender> genders = extractGenders();
        Transport transport = extractTransport();
        thingSelectListener = new ThingSelectListener(this, transport);
        LinearLayout parent = findViewById(R.id.layout_preview);
        addGroup(parent, getResources().getString(R.string.need), "need", genders, weatherTypes);

        for (Category entry : extractTargets()) {
            addGroup(parent, entry.getCategoryName(), entry.getCategoryNameEn(), genders, weatherTypes);
        }
    }

    /**
     * Adds a group of views to parent layout. This group includes the name of category and the list of things
     *
     * @param parent       the parent layout.
     * @param title        the name of category in current locale.
     * @param category     the name of category in database.
     * @param genders      the list of genders. Uses as filter to select the things.
     * @param weatherTypes the list of weather types. Uses as filter to select the things.
     * @throws ExecutionException   when the writing is not completed.
     * @throws InterruptedException when the writing was interrupted.
     */
    private void addGroup(LinearLayout parent, String title, String category, List<Gender> genders, List<WeatherType> weatherTypes) throws ExecutionException, InterruptedException {
        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(createViewByTitle(title, category));
        addThingsToParent(layout, category, genders, weatherTypes);
        parent.addView(layout);
    }

    /**
     * Creates the view for the given category.
     *
     * @param title the name of category in current locale.
     * @param name  the name of category in database.
     * @return the created view.
     */
    private TextView createViewByTitle(String title, String name) {
        TextView targetTitleView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = (int) (10 / getResources().getDisplayMetrics().density);
        targetTitleView.setLayoutParams(params);
        targetTitleView.setText(capitalize(title));
        targetTitleView.setHint(name);
        targetTitleView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        return targetTitleView;
    }

    /**
     * Adds the things to parent layout using the given filters.
     *
     * @param parent       the parent layout.
     * @param target       the category name in database.
     * @param genders      the list of genders. Uses as filter to select the things.
     * @param weatherTypes the list of weather types. Uses as filter to select the things.
     * @throws ExecutionException   when the writing is not completed.
     * @throws InterruptedException when the writing was interrupted.
     */
    private void addThingsToParent(LinearLayout parent, String target, List<Gender> genders, List<WeatherType> weatherTypes) throws ExecutionException, InterruptedException {
        ThingSelectAsyncTask task = new ThingSelectAsyncTask();
        task.setCategory(target);
        ThingMapper mapper = new ThingMapper();
        List<Thing> things = filter(mapper.to(task.execute().get()), weatherTypes, genders);
        for (Thing thing : things) {
            parent.addView(createThingView(thing));
        }
    }

    /**
     * Creates the view for given thing.
     *
     * @param thing the thing.
     * @return the created view.
     */
    private CheckBox createThingView(Thing thing) {
        CheckBox checkBox = new CheckBox(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = (int) (10 / getResources().getDisplayMetrics().density);
        checkBox.setLayoutParams(params);
        checkBox.setText(capitalize(thing.getThingName()));
        checkBox.setTag(thing);
        checkBox.setOnCheckedChangeListener(thingSelectListener);
        return checkBox;
    }

    /**
     * Makes the capital letter in given title.
     *
     * @param title the title.
     * @return the title with capital letter.
     */
    private String capitalize(String title) {
        return title.substring(0, 1).toUpperCase() + title.substring(1);
    }

    /**
     * Selects the thing set using the given filters.
     *
     * @param source       the source list of things.
     * @param weatherTypes the list of weather types. Uses as filter to select the things.
     * @param genders      the list of genders. Uses as filter to select the things.
     * @return the filtered list of things.
     */
    private List<Thing> filter(List<Thing> source, List<WeatherType> weatherTypes, List<Gender> genders) {
        List<Thing> filtered = new ArrayList<>();
        for (Thing thing : source) {
            if (hasAnyWeatherType(thing, weatherTypes) && belongsTo(thing, genders)) {
                filtered.add(thing);
            }
        }
        return filtered;
    }

    /**
     * Verifies that the thing belong to any of given weather type.
     *
     * @param thing        the thing to test.
     * @param weatherTypes the list of weather types to test.
     * @return true - if the thing has any of given weather type.
     */
    private boolean hasAnyWeatherType(Thing thing, List<WeatherType> weatherTypes) {
        if (weatherTypes == null || weatherTypes.size() == 0) {
            return true;
        }

        for (String type : thing.getWeatherType()) {
            for (WeatherType weatherType : weatherTypes) {
                if (weatherType.getType().equals(type)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifies that the thing belong to one or more gender types.
     *
     * @param thing   the thing to test.
     * @param genders the list of genders to test.
     * @return true - if the thing belong to one or more gender types.
     */
    private boolean belongsTo(Thing thing, List<Gender> genders) {
        if (genders == null || genders.size() == 0) {
            return true;
        }

        for (Gender gender : genders) {
            if (thing.getGender().equals(gender.getGenderEn())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Collects the selected things from the list.
     *
     * @return the {@link by.neon.travelassistant.model.Settings.Selection}.
     */
    private List<Settings.Selection> getSelections() {
        LinearLayout content = findViewById(R.id.layout_preview);
        List<Settings.Selection> selections = new ArrayList<>(0);
        for (int index = 0; index < content.getChildCount(); index++) {
            LinearLayout inner = (LinearLayout) content.getChildAt(index);
            Settings.Selection selection = new Settings.Selection();
            selection.setCategory(((TextView) inner.getChildAt(0)).getHint().toString());
            Map<String, Boolean> flags = new LinkedHashMap<>(0);
            for (int viewIndex = 1; viewIndex < inner.getChildCount(); viewIndex++) {
                CheckBox checkBox = (CheckBox) inner.getChildAt(viewIndex);
                flags.put(checkBox.getText().toString(), checkBox.isChecked());
            }
            selection.setAsBoolean(flags);
            selections.add(selection);
        }
        return selections;
    }

    /**
     * Restores the selected things to the list using given {@link by.neon.travelassistant.model.Settings.Selection}.
     *
     * @param selections the {@link by.neon.travelassistant.model.Settings.Selection}.
     */
    private void setSelections(List<Settings.Selection> selections) {
        LinearLayout content = findViewById(R.id.layout_preview);
        for (int index = 0; index < content.getChildCount(); index++) {
            Settings.Selection selection = selections.get(index);
            LinearLayout inner = (LinearLayout) content.getChildAt(index);
            for (int viewIndex = 1; viewIndex < inner.getChildCount(); viewIndex++) {
                CheckBox checkBox = (CheckBox) inner.getChildAt(viewIndex);
                if (selection.getAsBoolean().containsKey(checkBox.getText().toString())) {
                    Boolean flag = selection.getAsBoolean().get(checkBox.getText().toString());
                    assert flag != null;
                    checkBox.setChecked(flag);
                }
            }
        }
    }
}
