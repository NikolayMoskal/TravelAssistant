package by.neon.travelassistant.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import by.neon.travelassistant.R;
import by.neon.travelassistant.activity.query.CategorySelectAsyncTask;
import by.neon.travelassistant.activity.query.GenderSelectAsyncTask;
import by.neon.travelassistant.activity.query.ThingSelectAsyncTask;
import by.neon.travelassistant.activity.query.TransportSelectAsyncTask;
import by.neon.travelassistant.config.TravelRequestQueue;
import by.neon.travelassistant.config.sqlite.mapper.CategoryMapper;
import by.neon.travelassistant.config.sqlite.mapper.GenderMapper;
import by.neon.travelassistant.config.sqlite.mapper.ThingMapper;
import by.neon.travelassistant.config.sqlite.mapper.TransportMapper;
import by.neon.travelassistant.config.sqlite.mapper.WeatherTypeMapper;
import by.neon.travelassistant.constant.CommonConstants;
import by.neon.travelassistant.listener.ForecastListener;
import by.neon.travelassistant.model.Category;
import by.neon.travelassistant.model.Gender;
import by.neon.travelassistant.model.Thing;
import by.neon.travelassistant.model.Transport;
import by.neon.travelassistant.model.WeatherType;

public class PreviewActivity extends AppCompatActivity {
    private static final String TAG = "PreviewActivity";
    private Map<String, Object> input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        input = parseExtras(getIntent().getExtras());
        requestWeatherTypes();
    }

    private Map<String, Object> parseExtras(Bundle extras) {
        Map<String, Object> map = new HashMap<>();
        if (extras == null) {
            return map;
        }

        map.put(CommonConstants.ARRIVAL_CITY_ID, extras.getLong(CommonConstants.ARRIVAL_CITY_ID, 0));
        extractData(extras, "category", CommonConstants.COUNT_CATEGORIES, map);
        extractData(extras, "transport", CommonConstants.COUNT_TRANSPORT_TYPES, map);
        extractData(extras, "gender", CommonConstants.COUNT_GENDERS, map);
        return map;
    }

    private void extractData(Bundle extras, String tagItem, String tagCount, Map<String, Object> map) {
        int count = extras.getInt(tagCount, 0);
        for (int index = 0; index < count; index++) {
            map.put(tagItem + index, extras.getString(tagItem + index, ""));
        }
    }

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

    private void requestWeatherTypes() {
        String url = "https://api.openweathermap.org/data/2.5/forecast?" +
                "id=" + input.get(CommonConstants.ARRIVAL_CITY_ID) +
                "&appid=" + CommonConstants.OWM_APP_ID +
                "&lang=" + Locale.getDefault().getLanguage() +
                "&units=metric";
        ForecastListener listener = new ForecastListener(weatherList -> {
            try {
                WeatherTypeMapper mapper = new WeatherTypeMapper();
                fillPreview(mapper.from(weatherList));
            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "onSuccess: " + e.getMessage(), e);
            }
        });
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, null);
        TravelRequestQueue.getInstance(this).addRequest(request);
    }

    private List<String> getNames(String tag) {
        List<String> names = new ArrayList<>();
        for (Map.Entry<String, Object> item : input.entrySet()) {
            if (item.getKey().startsWith(tag)) {
                names.add((String) item.getValue());
            }
        }
        return names;
    }

    private void fillPreview(List<WeatherType> weatherTypes) throws ExecutionException, InterruptedException {
        List<Gender> genders = extractGenders();
        LinearLayout parent = findViewById(R.id.layout_preview);
        parent.addView(createViewByTitle(getResources().getString(R.string.need)));
        addThingsToParent(parent, "need", genders, weatherTypes);

        for (Category entry : extractTargets()) {
            parent.addView(createViewByTitle(entry.getCategoryName()));
            addThingsToParent(parent, entry.getCategoryNameEn(), genders, weatherTypes);
        }
    }

    private TextView createViewByTitle(String title) {
        TextView targetTitleView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 10;
        targetTitleView.setLayoutParams(params);
        targetTitleView.setText(capitalize(title));
        targetTitleView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        return targetTitleView;
    }

    private void addThingsToParent(LinearLayout parent, String target, List<Gender> genders, List<WeatherType> weatherTypes) throws ExecutionException, InterruptedException {
        ThingSelectAsyncTask task = new ThingSelectAsyncTask();
        task.setCategory(target);
        ThingMapper mapper = new ThingMapper();
        List<Thing> things = filter(mapper.to(task.execute().get()), weatherTypes, genders);
        for (Thing thing : things) {
            parent.addView(createThingView(thing.getThingName()));
        }
    }

    private CheckBox createThingView(String thingName) {
        CheckBox checkBox = new CheckBox(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 10;
        checkBox.setLayoutParams(params);
        checkBox.setText(capitalize(thingName));
        return checkBox;
    }

    private String capitalize(String title) {
        return title.substring(0, 1).toUpperCase() + title.substring(1);
    }

    private List<Thing> filter(List<Thing> source, List<WeatherType> weatherTypes, List<Gender> genders) {
        List<Thing> filtered = new ArrayList<>();
        for (Thing thing : source) {
            if (hasAnyWeatherType(thing, weatherTypes) && belongsTo(thing, genders)) {
                filtered.add(thing);
            }
        }
        return filtered;
    }

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
}
