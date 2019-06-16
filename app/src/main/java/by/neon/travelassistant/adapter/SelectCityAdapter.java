package by.neon.travelassistant.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import by.neon.travelassistant.R;
import by.neon.travelassistant.activity.query.DownloadWeatherIconAsyncTask;

/**
 * Prepares the adapter for dialog of city selection.
 */
public final class SelectCityAdapter extends SimpleAdapter {
    /**
     * The unique log tag constant for this class.
     */
    private static final String TAG = "SelectCityAdapter";
    /**
     * The inflater for the layout. Used to fill the layout with XML markup.
     */
    private LayoutInflater inflater;

    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public SelectCityAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * @param position    current position in parent view
     * @param convertView a target view
     * @param parent      a parent view
     * @see Adapter#getView(int, View, ViewGroup)
     */
    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = inflater.inflate(R.layout.select_city_layout, null);
        }

        //noinspection unchecked
        HashMap<String, String> map = (HashMap<String, String>) getItem(position);

        try {
            ImageView imageView = view.findViewById(R.id.citySelectWeatherIcon);
            imageView.setImageDrawable(new DownloadWeatherIconAsyncTask(map.get("Icon")).execute().get());
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "getView: " + e.getMessage(), e);
        }

        TextView cityName = view.findViewById(R.id.citySelectCityNameAndCountry);
        cityName.setText(map.get("City"));
        TextView location = view.findViewById(R.id.citySelectCityCoordinates);
        location.setText(map.get("Location"));
        TextView currentTemp = view.findViewById(R.id.citySelectCurrentTemp);
        currentTemp.setText(map.get("Temp"));
        TextView selected = view.findViewById(R.id.selectedCityName);
        selected.setText(map.get("Selected"));
        return view;
    }
}
