package by.neon.travelassistant.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import by.neon.travelassistant.BuildConfig;
import by.neon.travelassistant.R;
import by.neon.travelassistant.config.SqliteConfig;
import by.neon.travelassistant.constant.CommonConstants;
import by.neon.travelassistant.model.Settings;
import by.neon.travelassistant.utility.ReportManager;
import by.neon.travelassistant.utility.SettingsManager;

public class PackActivity extends AppCompatActivity {
    private static final String TAG = "PackActivity";
    private List<Settings> settingsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack);
        setTitle(R.string.title_activity_pack);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            new SqliteConfig(this).getThings();
        } catch (IOException | ExecutionException | InterruptedException e) {
            Log.e(TAG, "onCreate: " + e.getMessage(), e);
        }

        setLists(findViewById(R.id.layout_lists));
        addNewList(getIntent().getExtras());
    }

    private void addNewList(Bundle extras) {
        if (extras == null || !extras.containsKey(CommonConstants.NEW_LIST_TAG)) {
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            Settings newSettings = mapper.readValue(extras.getString(CommonConstants.NEW_LIST_TAG), Settings.class);
            boolean exists = false;
            for (int index = 0; index < settingsList.size(); index++) {
                if (settingsList.get(index).getCity().getCityCode() == newSettings.getCity().getCityCode()) {
                    settingsList.set(index, newSettings);
                    findAndReplace(newSettings.getCity().getCityCode(), createCardForList(newSettings));
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                settingsList.add(newSettings);
                LinearLayout layout = findViewById(R.id.layout_lists);
                layout.addView(createCardForList(newSettings));
            }
        } catch (IOException e) {
            Log.e(TAG, "onActivityResult: " + e.getMessage(), e);
        }
    }

    private CardView findByCityCode(long cityCode) {
        LinearLayout layout = findViewById(R.id.layout_lists);
        for (int index = 0; index < layout.getChildCount(); index++) {
            CardView cardView = (CardView) layout.getChildAt(index);
            if (cityCode == (long) cardView.getTag()) {
                return cardView;
            }
        }
        return null;
    }

    private void findAndReplace(long cityCode, CardView newView) {
        LinearLayout layout = findViewById(R.id.layout_lists);
        for (int index = 0; index < layout.getChildCount(); index++) {
            CardView cardView = (CardView) layout.getChildAt(index);
            if (cityCode == (long) cardView.getTag()) {
                layout.removeViewAt(index);
                layout.addView(newView, index);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pack, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                onDeleteClick();
                return true;
            case R.id.action_delete_all:
                onDeleteAllClick();
                return true;
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            case R.id.action_show_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data == null) {
            return;
        }

        if (resultCode == RESULT_OK && requestCode == CommonConstants.REQUEST_PREVIEW) {
            addNewList(data.getExtras());
        }
    }

    public void onAddPackClick(View view) {
        startActivity(new Intent(this, InputActivity.class));
    }

    /**
     * Call this when your activity is done and should be closed.  The
     * ActivityResult is propagated back to whoever launched you via
     * onActivityResult().
     */
    @Override
    public void finish() {
        try {
            SettingsManager.writeSettings(this, settingsList);
        } catch (JsonProcessingException e) {
            Log.e(TAG, "finish: " + e.getMessage(), e);
        }
        super.finish();
    }

    private boolean showContextMenuDialog(long cityCode) {
        final String[] menuItems = getResources().getStringArray(R.array.context_menu);
        final Settings settings = find(cityCode);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setItems(menuItems, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            onSendSelect(settings, menuItems[which]);
                            break;
                        case 1:
                            onEditListSelect(settings);
                            break;
                        case 2:
                            onDeleteListSelect(settings);
                            break;
                    }
                }).show();
        return false;
    }

    private void onSendSelect(Settings settings, String title) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        File file = ReportManager.writeListAsText(getFilesDir(), settings);
        if (file != null) {
            intent
                    .putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, file))
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent
                .putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.send_subject,
                        settings.getCity().getName(),
                        "https://maps:google.com/maps?q=" + Uri.encode(settings.getCity().getName())))
                .setType("text/plain");
        startActivity(Intent.createChooser(intent, title));
    }

    private void onEditListSelect(Settings settings) {
        Intent intent = new Intent(this, InputActivity.class);
        intent.putExtra(CommonConstants.ARRIVAL_CITY_ID, settings.getCity().getCityCode());
        intent.putExtra(CommonConstants.ARRIVAL_CITY_INFO, settings.getCity().getName());
        intent.putExtra(CommonConstants.ARRIVAL_CITY_LOCATION, settings.getCity().getLocation());
        putData(intent, settings.getCategories(), "category", CommonConstants.COUNT_CATEGORIES);
        putData(intent, settings.getGenders(), "gender", CommonConstants.COUNT_GENDERS);
        putData(intent, settings.getTransportTypes(), "transport", CommonConstants.COUNT_TRANSPORT_TYPES);
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        intent.putExtra(CommonConstants.TRAVEL_START_DATE, format.format(settings.getTravelStartDate()));
        intent.putExtra(CommonConstants.TRAVEL_END_DATE, format.format(settings.getTravelEndDate()));
        startActivity(intent);
    }

    private void onDeleteListSelect(Settings settings) {
        CardView cardView = findByCityCode(settings.getCity().getCityCode());
        assert cardView != null;
        LinearLayout layout = (LinearLayout) cardView.getChildAt(0);
        CheckBox checkBox = (CheckBox) layout.getChildAt(0);
        checkBox.setChecked(true);
        onDeleteConfirm(null);
    }

    private Settings find(long cityCode) {
        Settings settings = new Settings();
        for (Settings set : settingsList) {
            if (set.getCity().getCityCode() == cityCode) {
                settings = set;
                break;
            }
        }
        return settings;
    }

    private void putData(Intent intent, List<String> items, String tagItem, String tagCount) {
        int index = 0;
        for (; index < items.size(); index++) {
            intent.putExtra(tagItem + index, items.get(index));
        }
        intent.putExtra(tagCount, index);
    }

    private void setLists(LinearLayout parent) {
        try {
            settingsList = SettingsManager.readSettings(this);
        } catch (IOException e) {
            Log.e(TAG, "setLists: " + e.getMessage(), e);
        }

        for (Settings settings : settingsList) {
            parent.addView(createCardForList(settings));
        }
    }

    private CardView createCardForList(Settings settings) {
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = (int) (10 / getResources().getDisplayMetrics().density + 0.5f);
        params.bottomMargin = (int) (10 / getResources().getDisplayMetrics().density + 0.5f);
        cardView.setLayoutParams(params);
        cardView.setTag(settings.getCity().getCityCode());
        double fillPercentage = getSelectedCount(settings) * 1.0f / getAllCount(settings);
        cardView.setCardBackgroundColor(setBackgroundColor(fillPercentage));
        int padding = (int) (5 * getResources().getDisplayMetrics().density);
        cardView.setContentPadding(padding, padding, padding, padding);
        cardView.setOnLongClickListener(v -> showContextMenuDialog(settings.getCity().getCityCode()));
        cardView.setOnClickListener(v -> openPreview(settings.getCity().getCityCode()));

        LinearLayout contentWithCheckBox = new LinearLayout(this);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        contentWithCheckBox.setLayoutParams(params);

        LinearLayout content = new LinearLayout(this);
        params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        content.setLayoutParams(params);
        content.setOrientation(LinearLayout.VERTICAL);
        content.addView(setPackTitle(settings.getCity().getName()));
        content.addView(setDatesTitle(settings.getTravelStartDate(), settings.getTravelEndDate()));
        content.addView(setWeightTitle(settings.getWeight()));

        contentWithCheckBox.addView(setCheckBox());
        contentWithCheckBox.addView(content);
        contentWithCheckBox.addView(setFillProgress(fillPercentage));
        cardView.addView(contentWithCheckBox);

        return cardView;
    }

    private void openPreview(long cityCode) {
        Intent intent = new Intent(this, PreviewActivity.class);
        Settings settings = find(cityCode);
        intent.putExtra(CommonConstants.ARRIVAL_CITY_ID, settings.getCity().getCityCode());
        intent.putExtra(CommonConstants.ARRIVAL_CITY_INFO, settings.getCity().getName());
        putData(intent, settings.getCategories(), "category", CommonConstants.COUNT_CATEGORIES);
        putData(intent, settings.getGenders(), "gender", CommonConstants.COUNT_GENDERS);
        putData(intent, settings.getTransportTypes(), "transport", CommonConstants.COUNT_TRANSPORT_TYPES);
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        intent.putExtra(CommonConstants.TRAVEL_START_DATE, format.format(settings.getTravelStartDate()));
        intent.putExtra(CommonConstants.TRAVEL_END_DATE, format.format(settings.getTravelEndDate()));
        try {
            ObjectMapper mapper = new ObjectMapper();
            intent.putExtra(CommonConstants.SELECTIONS, mapper.writeValueAsString(settings.getSelections()));
        } catch (JsonProcessingException e) {
            Log.e(TAG, "openPreview: " + e.getMessage(), e);
        }
        startActivityForResult(intent, CommonConstants.REQUEST_PREVIEW);
    }

    private TextView setPackTitle(String title) {
        TextView view = new TextView(this);
        view.setText(title);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        view.setTextSize(getResources().getDimension(R.dimen.pack_title_text_size) / getResources().getDisplayMetrics().scaledDensity);
        return view;
    }

    private TextView setDatesTitle(Date start, Date end) {
        TextView view = new TextView(this);
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        view.setText(String.format(Locale.getDefault(), "%s - %s", format.format(start), format.format(end)));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        view.setTextSize(getResources().getDimension(R.dimen.pack_title_text_size) / getResources().getDisplayMetrics().scaledDensity);
        return view;
    }

    private TextView setWeightTitle(double weight) {
        TextView view = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        view.setText(getResources().getString(R.string.weight_title, weight));
        view.setTextSize(getResources().getDimension(R.dimen.pack_title_text_size) / getResources().getDisplayMetrics().scaledDensity);
        return view;
    }

    private CheckBox setCheckBox() {
        CheckBox checkBox = new CheckBox(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginEnd((int) (10 / getResources().getDisplayMetrics().density + 0.5f));
        params.gravity = Gravity.CENTER_VERTICAL;
        checkBox.setLayoutParams(params);
        checkBox.setVisibility(View.GONE);
        return checkBox;
    }

    private TextView setFillProgress(double fillPercentage) {
        TextView view = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        view.setLayoutParams(params);
        view.setText(String.format(Locale.getDefault(), "%.2f%%", fillPercentage * 100.0f));
        return view;
    }

    private int setBackgroundColor(double percentage) {
        return Color.HSVToColor(80, new float[]{(float) (120 * percentage), 100.0f, 100.0f});
    }

    private int getSelectedCount(Settings settings) {
        int count = 0;
        for (Settings.Selection selection : settings.getSelections()) {
            for (Map.Entry<String, Boolean> flag : selection.getAsBoolean().entrySet()) {
                if (flag.getValue()) {
                    count++;
                }
            }
        }
        return count;
    }

    private int getAllCount(Settings settings) {
        int count = 0;
        for (Settings.Selection selection : settings.getSelections()) {
            count += selection.getThings().size();
        }
        return count;
    }


    public void onDeleteClick() {
        switchDeleteMode(true);
    }

    private void switchDeleteMode(boolean isModeActivate) {
        switchCheckBoxVisibility(isModeActivate, false);
        switchDeleteButtonsVisibility(isModeActivate);
        switchButtonsVisibility(!isModeActivate);
    }

    private void switchCheckBoxVisibility(boolean isVisible, boolean isChecked) {
        LinearLayout parent = findViewById(R.id.layout_lists);
        for (int index = 0; index < parent.getChildCount(); index++) {
            CardView cardView = (CardView) parent.getChildAt(index);
            LinearLayout layout = (LinearLayout) cardView.getChildAt(0);
            CheckBox checkBox = (CheckBox) layout.getChildAt(0);
            checkBox.setChecked(isChecked);
            checkBox.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    @SuppressLint("RestrictedApi")
    private void switchDeleteButtonsVisibility(boolean isVisible) {
        FloatingActionButton buttonConfirm = findViewById(R.id.delete_confirm);
        buttonConfirm.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        FloatingActionButton buttonCancel = findViewById(R.id.delete_cancel);
        buttonCancel.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @SuppressLint("RestrictedApi")
    private void switchButtonsVisibility(boolean isVisible) {
        FloatingActionButton buttonConfirm = findViewById(R.id.add_pack);
        buttonConfirm.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        FloatingActionButton buttonCancel = findViewById(R.id.report);
        buttonCancel.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void onDeleteAllClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle(R.string.action_delete_all)
                .setMessage(R.string.delete_confirm_title)
                .setCancelable(true)
                .setPositiveButton(R.string.action_yes, (dialog, which) -> {
                    switchCheckBoxVisibility(false, true);
                    onDeleteConfirm(null);
                })
                .setNegativeButton(R.string.action_no, null)
                .show();
    }

    public void onDeleteConfirm(View view) {
        LinearLayout parent = findViewById(R.id.layout_lists);
        List<Long> cityCodes = new ArrayList<>(0);
        for (int index = 0; index < parent.getChildCount(); index++) {
            CardView cardView = (CardView) parent.getChildAt(index);
            LinearLayout layout = (LinearLayout) cardView.getChildAt(0);
            CheckBox checkBox = (CheckBox) layout.getChildAt(0);
            if (checkBox.isChecked()) {
                cityCodes.add((long) cardView.getTag());
                parent.removeViewAt(index--);
            }
        }
        SharedPreferences.Editor editor = getSharedPreferences(CommonConstants.APP_LISTS, MODE_PRIVATE).edit();
        for (int index = 0; index < settingsList.size(); index++) {
            if (cityCodes.contains(settingsList.get(index).getCity().getCityCode())) {
                editor.remove(String.valueOf(settingsList.get(index).getCity().getCityCode()));
                settingsList.remove(index--);
            }
        }
        editor.apply();
        switchDeleteMode(false);
    }

    public void onDeleteCancel(View view) {
        switchDeleteMode(false);
    }
}
