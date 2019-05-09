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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import by.neon.travelassistant.R;
import by.neon.travelassistant.activity.query.ThingSelectAsyncTask;
import by.neon.travelassistant.config.SqliteConfig;
import by.neon.travelassistant.config.sqlite.mapper.ThingMapper;
import by.neon.travelassistant.constant.CommonConstants;
import by.neon.travelassistant.model.Thing;

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
        new SqliteConfig(this);
        try {
            fillPreview();
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "onCreate: " + e.getMessage(), e);
        }
    }

    private Map<String, Object> parseExtras(Bundle extras) {
        Map<String, Object> map = new HashMap<>();
        if (extras == null) {
            return map;
        }

        map.put(CommonConstants.ARRIVAL_CITY_ID, extras.getLong(CommonConstants.ARRIVAL_CITY_ID, 0));
        map.put(CommonConstants.TYPE_MALE, extras.getBoolean(CommonConstants.TYPE_MALE, false));
        map.put(CommonConstants.TYPE_FEMALE, extras.getBoolean(CommonConstants.TYPE_FEMALE, false));
        map.put(CommonConstants.TRANSPORT_TYPE_AIRPLANE, extras.getBoolean(CommonConstants.TRANSPORT_TYPE_AIRPLANE, false));
        map.put(CommonConstants.TRANSPORT_TYPE_AUTO, extras.getBoolean(CommonConstants.TRANSPORT_TYPE_AUTO, false));
        map.put(CommonConstants.TRANSPORT_TYPE_BUS, extras.getBoolean(CommonConstants.TRANSPORT_TYPE_BUS, false));
        map.put(CommonConstants.TRANSPORT_TYPE_CYCLE, extras.getBoolean(CommonConstants.TRANSPORT_TYPE_CYCLE, false));
        map.put(CommonConstants.TRANSPORT_TYPE_SHIP, extras.getBoolean(CommonConstants.TRANSPORT_TYPE_SHIP, false));
        map.put(CommonConstants.TRANSPORT_TYPE_TRAIN, extras.getBoolean(CommonConstants.TRANSPORT_TYPE_TRAIN, false));
        int count = extras.getInt(CommonConstants.COUNT_TARGETS, 0);
        for (int index = 0; index < count; index++) {
            map.put("type" + index, extras.getString("type" + index, ""));
        }
        return map;
    }

    private Map<String, String> extractTargets() {
        Map<String, String> targets = new HashMap<>(0);
        for (Map.Entry<String, Object> item : input.entrySet()) {
            if (item.getKey().startsWith("type")) {
                String target = (String)item.getValue();
                targets.put(target, Objects.requireNonNull(getLocalizedTarget(target)));
            }
        }
        return targets;
    }

    private String getLocalizedTarget(String defaultTarget) {
        String[] localizedTargets = getResources().getStringArray(R.array.targets);
        String[] defaultTargets = getResources().getStringArray(R.array.targetsDefault);
        for (int index = 0; index < defaultTargets.length; index++) {
            if (defaultTargets[index].equals(defaultTarget)) {
                return localizedTargets[index];
            }
        }
        return null;
    }

    private void fillPreview() throws ExecutionException, InterruptedException {
        LinearLayout parent = findViewById(R.id.layout_preview);
        parent.addView(createViewByTitle(getResources().getString(R.string.need)));
        addThingsToParent(parent, "need");

        for (Map.Entry<String, String> entry : extractTargets().entrySet()) {
            parent.addView(createViewByTitle(entry.getValue()));
            addThingsToParent(parent, entry.getKey());
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

    private void addThingsToParent(LinearLayout parent, String target) throws ExecutionException, InterruptedException {
        ThingSelectAsyncTask task = new ThingSelectAsyncTask();
        task.setCategory(target);
        ThingMapper mapper = new ThingMapper();
        List<Thing> things = mapper.to(task.execute().get());
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
        return title.substring(0,1).toUpperCase() + title.substring(1);
    }
}
