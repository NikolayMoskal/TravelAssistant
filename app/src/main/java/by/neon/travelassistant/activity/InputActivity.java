package by.neon.travelassistant.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import by.neon.travelassistant.R;
import by.neon.travelassistant.activity.query.CategorySelectAsyncTask;
import by.neon.travelassistant.activity.query.GenderSelectAsyncTask;
import by.neon.travelassistant.activity.query.TransportSelectAsyncTask;
import by.neon.travelassistant.config.sqlite.mapper.CategoryMapper;
import by.neon.travelassistant.config.sqlite.mapper.GenderMapper;
import by.neon.travelassistant.config.sqlite.mapper.TransportMapper;
import by.neon.travelassistant.constant.CommonConstants;
import by.neon.travelassistant.listener.CitySelectListener;
import by.neon.travelassistant.listener.DateSetListener;
import by.neon.travelassistant.model.Category;
import by.neon.travelassistant.model.Gender;
import by.neon.travelassistant.model.Transport;
import by.neon.travelassistant.utility.TravelRequestQueue;

public class InputActivity extends AppCompatActivity {
    private static final String TAG = "InputActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setGenders();
        setTransport();
        setCategories();

        readSettings(getIntent().getExtras());
    }

    private void readSettings(Bundle extras) {
        if (extras == null) {
            return;
        }

        restoreData(extras, findViewById(R.id.layout_categories), CommonConstants.COUNT_CATEGORIES, "category");
        restoreData(extras, findViewById(R.id.layout_genders), CommonConstants.COUNT_GENDERS, "gender");
        restoreData(extras, findViewById(R.id.layout_transports), CommonConstants.COUNT_TRANSPORT_TYPES, "transport");
        ((EditText) findViewById(R.id.start_date)).setText(extras.getString(CommonConstants.TRAVEL_START_DATE));
        ((EditText) findViewById(R.id.end_date)).setText(extras.getString(CommonConstants.TRAVEL_END_DATE));
        EditText city = findViewById(R.id.arv_city);
        city.setEnabled(false);
        city.setText(extras.getString(CommonConstants.ARRIVAL_CITY_INFO));
        city.setTag(extras.getLong(CommonConstants.ARRIVAL_CITY_ID));
    }

    private void restoreData(Bundle extras, LinearLayout parent, String tagCount, String tagItem) {
        int count = extras.getInt(tagCount, 0);
        List<String> items = new ArrayList<>(0);
        for (int index = 0; index < count; index++) {
            items.add(extras.getString(tagItem + index, ""));
        }

        for (int layoutIndex = 0; layoutIndex < parent.getChildCount(); layoutIndex++) {
            LinearLayout inner = (LinearLayout) parent.getChildAt(layoutIndex);
            for (int viewIndex = 0; viewIndex < inner.getChildCount(); viewIndex++) {
                final ToggleButton button = (ToggleButton) inner.getChildAt(viewIndex);
                for (String item : items) {
                    if (button.getHint().toString().equals(item)) {
                        button.setChecked(true);
                    }
                }
            }
        }
    }

    public void onSelectStartDate(View view) {
        EditText text = findViewById(R.id.start_date);
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        new DatePickerDialog(this, new DateSetListener(text),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void onSelectEndDate(View view) {
        EditText text = findViewById(R.id.end_date);
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        new DatePickerDialog(this, new DateSetListener(text),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void onFindCity(View view) {
        EditText text = findViewById(R.id.arv_city);
        String cityName = text.getText().toString();
        createCitySelectDialog(cityName);
    }

    public void onSendClick(View view) {
        Intent intent = new Intent(this, PreviewActivity.class);
        EditText editText = findViewById(R.id.arv_city);
        intent.putExtra(CommonConstants.ARRIVAL_CITY_ID, (long) editText.getTag(R.id.cityId));
        intent.putExtra(CommonConstants.ARRIVAL_CITY_INFO, editText.getText().toString());
        intent.putExtra(CommonConstants.ARRIVAL_CITY_LOCATION, (Location) editText.getTag(R.id.location));
        putData(intent, findViewById(R.id.layout_genders), "gender", CommonConstants.COUNT_GENDERS);
        putData(intent, findViewById(R.id.layout_transports), "transport", CommonConstants.COUNT_TRANSPORT_TYPES);
        putData(intent, findViewById(R.id.layout_categories), "category", CommonConstants.COUNT_CATEGORIES);
        intent.putExtra(CommonConstants.TRAVEL_START_DATE, ((EditText) findViewById(R.id.start_date)).getText().toString());
        intent.putExtra(CommonConstants.TRAVEL_END_DATE, ((EditText) findViewById(R.id.end_date)).getText().toString());
        startActivity(intent);
    }

    private void putData(Intent intent, LinearLayout parent, String itemTag, String countTag) {
        int count = 0;
        for (int layoutIndex = 0; layoutIndex < parent.getChildCount(); layoutIndex++) {
            LinearLayout inner = (LinearLayout) parent.getChildAt(layoutIndex);
            for (int viewIndex = 0; viewIndex < inner.getChildCount(); viewIndex++) {
                final ToggleButton button = (ToggleButton) inner.getChildAt(viewIndex);
                if (button.isChecked()) {
                    intent.putExtra(itemTag + count++, button.getHint().toString());
                }
            }
        }
        intent.putExtra(countTag, count);
    }

    public void createCitySelectDialog(String cityName) {
        SharedPreferences preferences = getSharedPreferences(CommonConstants.APP_SETTINGS, MODE_PRIVATE);
        String url = "https://api.openweathermap.org/data/2.5/find?" +
                "q=" + cityName +
                "&appid=" + CommonConstants.OWM_APP_ID +
                "&lang=" + Locale.getDefault().getLanguage() +
                "&units=" + preferences.getString(CommonConstants.TEMPERATURE_UNIT, "Standard");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new CitySelectListener(this), null);
        TravelRequestQueue.getInstance(this).addRequest(request);
    }

    private void setCategories() {
        CategorySelectAsyncTask categorySelect = new CategorySelectAsyncTask();
        categorySelect.setSelectAll(true);
        CategoryMapper categoryMapper = new CategoryMapper();
        List<Category> categories = new ArrayList<>(0);
        try {
            categories = categoryMapper.to(categorySelect.execute().get());
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "setCategories: " + e.getMessage(), e);
        }

        int index = 0;
        for (; index < categories.size(); index++) {
            if (categories.get(index).getCategoryNameEn().equals("need")) {
                break;
            }
        }
        categories.remove(index);
        final List<Category> immutableCategories = categories;
        fillParentLayout(findViewById(R.id.layout_categories), categories.size(),
                x -> immutableCategories.get(x).getCategoryName(),
                x -> immutableCategories.get(x).getCategoryNameEn(),
                x -> null);
    }

    private void setTransport() {
        TransportSelectAsyncTask task = new TransportSelectAsyncTask();
        task.setSelectAll(true);
        TransportMapper mapper = new TransportMapper();
        List<Transport> transports = new ArrayList<>(0);
        try {
            transports = mapper.to(task.execute().get());
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "setTransport: " + e.getMessage(), e);
        }

        final List<Transport> immutableTransports = transports;
        fillParentLayout(findViewById(R.id.layout_transports), transports.size(),
                x -> immutableTransports.get(x).getName(),
                x -> immutableTransports.get(x).getNameEn(),
                x -> getTransportIcon(immutableTransports.get(x).getNameEn()));
    }

    private void setGenders() {
        GenderSelectAsyncTask task = new GenderSelectAsyncTask();
        task.setSelectAll(true);
        GenderMapper mapper = new GenderMapper();
        List<Gender> genders = new ArrayList<>(0);
        try {
            genders = mapper.to(task.execute().get());
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "setGenders: " + e.getMessage(), e);
        }

        int index = 0;
        for (; index < genders.size(); index++) {
            if (genders.get(index).getGenderEn().equals("neutral")) {
                break;
            }
        }
        genders.remove(index);
        final List<Gender> immutableGenders = genders;
        fillParentLayout(findViewById(R.id.layout_genders), genders.size(),
                x -> immutableGenders.get(x).getGender(),
                x -> immutableGenders.get(x).getGenderEn(),
                x -> getGenderIcon(immutableGenders.get(x).getGenderEn()));
    }

    private void fillParentLayout(LinearLayout parent, int countEntities, IFunc<Integer, String> titleFunc, IFunc<Integer, String> nameFunc, IFunc<Integer, Drawable> iconFunc) {
        for (int index = 0; index < countEntities; ) {
            LinearLayout inner = createInnerLayout();
            for (int item = 0; item < 2 && index < countEntities; item++, index++) {
                final int currentIndex = index;
                ToggleButton button = createSwitch(nameFunc.run(index), titleFunc.run(index), x -> iconFunc.run(currentIndex));
                inner.addView(button);
            }
            parent.addView(inner);
        }
    }

    private LinearLayout createInnerLayout() {
        LinearLayout inner = new LinearLayout(this);
        inner.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        int margin = (int) (10 / getResources().getDisplayMetrics().density + 0.5f);
        layoutParams.setMargins(0, margin, 0, 0);
        inner.setLayoutParams(layoutParams);
        return inner;
    }

    private ToggleButton createSwitch(String name, String title) {
        ToggleButton button = (ToggleButton) getLayoutInflater().inflate(R.layout.toggle_button_style_layout, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginEnd((int) (getResources().getDimension(R.dimen.toggle_button_margin_end) / getResources().getDisplayMetrics().density + 0.5f));
        int padding = (int) (5 / getResources().getDisplayMetrics().density + 0.5f);
        button.setPadding(padding, padding, padding, padding);
        button.setLayoutParams(params);
        button.setText(capitalize(title));
        button.setTextOn(capitalize(title));
        button.setTextOff(capitalize(title));
        button.setHint(name);
        return button;
    }

    private ToggleButton createSwitch(String name, String title, IFunc<ToggleButton, Drawable> func) {
        ToggleButton button = createSwitch(name, title);
        button.setCompoundDrawablesWithIntrinsicBounds(null, func.run(button), null, null);
        return button;
    }

    private Drawable getTransportIcon(String transportName) {
        int id;
        switch (transportName) {
            case "airplane":
                id = R.drawable.ic_airplane;
                break;
            case "bus":
                id = R.drawable.ic_bus;
                break;
            case "ship":
                id = R.drawable.ic_ship;
                break;
            case "cycle":
                id = R.drawable.ic_cycle;
                break;
            case "train":
                id = R.drawable.ic_train;
                break;
            case "auto":
                id = R.drawable.ic_auto;
                break;
            default:
                return null;
        }
        return ContextCompat.getDrawable(this, id);
    }

    private Drawable getGenderIcon(String genderType) {
        int id;
        switch (genderType) {
            case "man":
                id = R.drawable.ic_male;
                break;
            case "woman":
                id = R.drawable.ic_female;
                break;
            default:
                return null;
        }
        return ContextCompat.getDrawable(this, id);
    }

    private String capitalize(String title) {
        return title.substring(0, 1).toUpperCase() + title.substring(1);
    }
}
