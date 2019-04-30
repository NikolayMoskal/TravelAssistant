package by.neon.travelassistant.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.HashMap;

import by.neon.travelassistant.R;
import by.neon.travelassistant.constant.CommonConstants;

public class PreviewActivity extends AppCompatActivity {
    private HashMap<String, Object> input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        input = parseExtras(getIntent().getExtras());
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
            map.put("type" + index, extras.getBoolean("type" + index, false));
        }
        return map;
    }
}
