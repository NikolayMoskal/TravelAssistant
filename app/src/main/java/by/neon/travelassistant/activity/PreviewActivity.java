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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import by.neon.travelassistant.R;
import by.neon.travelassistant.activity.query.ThingSelectAsyncTask;
import by.neon.travelassistant.config.sqlite.mapper.ThingMapper;
import by.neon.travelassistant.constant.CommonConstants;
import by.neon.travelassistant.model.Thing;

public class PreviewActivity extends AppCompatActivity {
    private static final String TAG = "PreviewActivity";
    private HashMap<String, Object> input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        input = parseExtras(getIntent().getExtras());
        try {
            fillPreview();
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "onCreate: " + e.getMessage(), e);
        }
    }

    private HashMap<String, Object> parseExtras(Bundle extras) {
        HashMap<String, Object> map = new HashMap<>();
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

    private List<String> extractTargets() {
        List<String> targets = new ArrayList<>();
        for (Map.Entry<String, Object> item : input.entrySet()) {
            if (item.getKey().startsWith("type")) {
                targets.add((String) item.getValue());
            }
        }
        return targets;
    }

    private void fillPreview() throws ExecutionException, InterruptedException {
        LinearLayout parent = findViewById(R.id.layout_preview);
        parent.addView(createViewByTitle(getResources().getString(R.string.need)));
        addThingsToParent(parent, "need");

        for (String target : extractTargets()) {
            parent.addView(createViewByTitle(target));
            addThingsToParent(parent, target);
        }
    }

    private TextView createViewByTitle(String title) {
        TextView targetTitleView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 10;
        targetTitleView.setLayoutParams(params);
        targetTitleView.setText(title);
        targetTitleView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        return targetTitleView;
    }

    private void addThingsToParent(LinearLayout parent, String target) throws ExecutionException, InterruptedException {
        ThingSelectAsyncTask task = new ThingSelectAsyncTask(new ArrayList<String>(){{add(target);}});
        ThingMapper mapper = new ThingMapper();
        for (Thing thing : mapper.to(task.execute().get())) {
            parent.addView(createThingView(thing.getThingName()));
        }
    }

    private CheckBox createThingView(String thingName) {
        CheckBox checkBox = new CheckBox(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 10;
        checkBox.setLayoutParams(params);
        checkBox.setText(thingName);
        return checkBox;
    }
}
