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

/**
 * Represents the activity to show all lists of recommendations. This is a main window of the application.
 */
public class PackActivity extends AppCompatActivity {
    /**
     * The unique log tag constant for this class.
     */
    private static final String TAG = "PackActivity";
    /**
     * The collection of lists. The information for each list contains in {@link Settings} object.
     */
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
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getMessage(), e);
        }

        setLists(findViewById(R.id.layout_lists));
        addNewList(getIntent().getExtras());
    }

    /**
     * Adds a new list or replaces existing if it exists.
     *
     * @param extras the extra data that can contain the list settings.
     */
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

    /**
     * Finds a view that contains the info about a list by given unique city code.
     *
     * @param cityCode the unique numeric city code in OpenWeatherMap city database.
     * @return the view on layout that contains the target list with given city code or null if not found.
     */
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

    /**
     * Updates the view that contains the list info by given unique city code. NOTE: if you want to create
     * two or more lists of recommendations for one city/region then each new list will replace the
     * each previous one.
     *
     * @param cityCode the unique numeric city code in OpenWeatherMap city database.
     * @param newView  the replacement view with updated list data.
     */
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
        getMenuInflater().inflate(R.menu.pack, menu);
        return true;
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

    /**
     * Shows the activity to input the data for a new list of recommendations.
     *
     * @param view the parent view.
     */
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

    /**
     * Shows the context menu after long click by view that contains a list info.
     *
     * @param cityCode the unique numeric city code in OpenWeatherMap city database.
     * @return the boolean value. It's not important here.
     */
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

    /**
     * Shows the chooser for select the application to send the list data.
     *
     * @param settings the list data.
     * @param title    the title of a chooser.
     */
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

    /**
     * Sends the list data to edit activity.
     *
     * @param settings the list data to send.
     */
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

    /**
     * Removes the view with list data from application.
     *
     * @param settings the list data to remove.
     */
    private void onDeleteListSelect(Settings settings) {
        CardView cardView = findByCityCode(settings.getCity().getCityCode());
        assert cardView != null;
        LinearLayout layout = (LinearLayout) cardView.getChildAt(0);
        CheckBox checkBox = (CheckBox) layout.getChildAt(0);
        checkBox.setChecked(true);
        onDeleteConfirm(null);
    }

    /**
     * Finds the list data by given city code.
     *
     * @param cityCode the unique numeric city code in OpenWeatherMap city database.
     * @return the found list data or empty object if not found.
     */
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

    /**
     * Puts the items to intent by given {@code tagItem} tag and their count by {@code tagCount} tag.
     *
     * @param intent   the target intent.
     * @param items    the collections of string items.
     * @param tagItem  the tag constant for each item.
     * @param tagCount the tag constant for a count of items.
     */
    private void putData(Intent intent, List<String> items, String tagItem, String tagCount) {
        int index = 0;
        for (; index < items.size(); index++) {
            intent.putExtra(tagItem + index, items.get(index));
        }
        intent.putExtra(tagCount, index);
    }

    /**
     * Restores all lists from preferences.
     *
     * @param parent the layout that contains all views. Each view related with one list.
     */
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

    /**
     * Creates one view for given list data.
     *
     * @param settings the list data.
     * @return the view that represents the given list data.
     */
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

    /**
     * Sends the list data to preview activity.
     *
     * @param cityCode the unique numeric city code in OpenWeatherMap city database.
     */
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

    /**
     * Creates a title for a list.
     *
     * @param title the title.
     * @return the created view.
     */
    private TextView setPackTitle(String title) {
        TextView view = new TextView(this);
        view.setText(title);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        view.setTextSize(getResources().getDimension(R.dimen.pack_title_text_size) / getResources().getDisplayMetrics().scaledDensity);
        return view;
    }

    /**
     * Creates a view that shows the dates of a travel.
     *
     * @param start the start date of a travel.
     * @param end   the end date of a travel.
     * @return the created view.
     */
    private TextView setDatesTitle(Date start, Date end) {
        TextView view = new TextView(this);
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        view.setText(String.format(Locale.getDefault(), "%s - %s", format.format(start), format.format(end)));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        view.setTextSize(getResources().getDimension(R.dimen.pack_title_text_size) / getResources().getDisplayMetrics().scaledDensity);
        return view;
    }

    /**
     * Creates a view that shows the current weight of the luggage.
     *
     * @param weight the weight of a luggage to show.
     * @return the created view.
     */
    private TextView setWeightTitle(double weight) {
        TextView view = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        view.setText(getResources().getString(R.string.weight_title, weight));
        view.setTextSize(getResources().getDimension(R.dimen.pack_title_text_size) / getResources().getDisplayMetrics().scaledDensity);
        return view;
    }

    /**
     * Creates a check box that designed to mark the list(s) to delete.
     *
     * @return the created check box.
     */
    private CheckBox setCheckBox() {
        CheckBox checkBox = new CheckBox(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginEnd((int) (10 / getResources().getDisplayMetrics().density + 0.5f));
        params.gravity = Gravity.CENTER_VERTICAL;
        checkBox.setLayoutParams(params);
        checkBox.setVisibility(View.GONE);
        return checkBox;
    }

    /**
     * Creates a view that contains a progress of collecting the list of things in percentage.
     *
     * @param fillPercentage the current percentage value of collecting.
     * @return the created view.
     */
    private TextView setFillProgress(double fillPercentage) {
        TextView view = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        view.setLayoutParams(params);
        view.setText(String.format(Locale.getDefault(), "%.2f%%", fillPercentage * 100.0f));
        return view;
    }

    /**
     * Sets the color of a view that contains the list data based on percentage of collecting.
     * This color changes from red (0%) to green (100%).
     *
     * @param percentage the current percentage value of collecting.
     * @return the integer value of a color.
     */
    private int setBackgroundColor(double percentage) {
        return Color.HSVToColor(80, new float[]{(float) (120 * percentage), 100.0f, 100.0f});
    }

    /**
     * Calculates a count of collected things in the list data.
     *
     * @param settings the list data.
     * @return the count of collected things.
     */
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

    /**
     * Calculates a count of all things in the list of recommendations.
     *
     * @param settings the list data.
     * @return the count of all things in the list of recommendations.
     */
    private int getAllCount(Settings settings) {
        int count = 0;
        for (Settings.Selection selection : settings.getSelections()) {
            count += selection.getThings().size();
        }
        return count;
    }

    /**
     * Runs a delete mode in this activity.
     */
    public void onDeleteClick() {
        switchDeleteMode(true);
    }

    /**
     * Switches a delete mode and normal mode.
     *
     * @param isModeActivate if true then activates the delete mode in this activity.
     */
    private void switchDeleteMode(boolean isModeActivate) {
        switchCheckBoxVisibility(isModeActivate, false);
        switchDeleteButtonsVisibility(isModeActivate);
        switchButtonsVisibility(!isModeActivate);
    }

    /**
     * Shows or hides all check boxes in all views that contains the list data.
     *
     * @param isVisible if true then makes the check box visible.
     * @param isChecked if true then makes the check box checked.
     */
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

    /**
     * Shows or hides the buttons to manage in delete mode.
     *
     * @param isVisible if true then makes the buttons of delete mode visible.
     */
    @SuppressLint("RestrictedApi")
    private void switchDeleteButtonsVisibility(boolean isVisible) {
        FloatingActionButton buttonConfirm = findViewById(R.id.delete_confirm);
        buttonConfirm.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        FloatingActionButton buttonCancel = findViewById(R.id.delete_cancel);
        buttonCancel.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * Shows or hides the buttons to manage in normal mode.
     *
     * @param isVisible if true then makes the buttons of normal mode visible.
     */
    @SuppressLint("RestrictedApi")
    private void switchButtonsVisibility(boolean isVisible) {
        FloatingActionButton buttonConfirm = findViewById(R.id.add_pack);
        buttonConfirm.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        FloatingActionButton buttonCancel = findViewById(R.id.report);
        buttonCancel.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * Shows dialog to confirm remove the all lists of recommendations.
     */
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

    /**
     * Removes all lists of recommendations from preferences.
     *
     * @param view the parent view.
     */
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

    /**
     * Closes the delete mode in this activity.
     *
     * @param view the parent view.
     */
    public void onDeleteCancel(View view) {
        switchDeleteMode(false);
    }
}
