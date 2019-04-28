package by.neon.travelassistant.config;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import by.neon.travelassistant.R;
import by.neon.travelassistant.constant.CommonConstants;
import by.neon.travelassistant.model.OwmCity;
import by.neon.travelassistant.model.Weather;

public final class OwmConfig extends Config {
    private Activity activity;

    public OwmConfig(Activity activity) throws ExecutionException, InterruptedException {
        this.activity = activity;
        setCities(getKnownCities());
    }

    private List<OwmCity> getKnownCities() throws ExecutionException, InterruptedException {
        return new JsonAsyncTask(activity).execute(R.raw.city_list_1).get();
    }

    public Weather getWeatherInfo(long cityId) {
        String url = "https://api.openweathermap.org/data/2.5/weather?" +
                "id=" + cityId +
                "&appId=" + CommonConstants.OWM_APP_ID +
                "&lang=" + Locale.getDefault().getLanguage();
        Weather weather = new Weather();
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray weatherConditionStates = response.getJSONArray("weather");
                        JSONObject mainInfo = response.getJSONObject("main");
                        weather.setCurrentTemp(mainInfo.getDouble("temp") - 273.15);
                        weather.setCalculationDate(new Date(response.getLong("dt")));
                        weather.setMaxTemp(mainInfo.getDouble("temp_max") - 273.15);
                        weather.setMinTemp(mainInfo.getDouble("temp_min") - 273.15);
                        if (!response.isNull("rain") && !response.getJSONObject("rain").isNull("1h")) {
                            weather.setRainVolume(response.getJSONObject("rain").getDouble("1h"));
                        }
                        if (!response.isNull("snow") && !response.getJSONObject("snow").isNull("1h")) {
                            weather.setSnowVolume(response.getJSONObject("snow").getDouble("1h"));
                        }
                        for (int index = 0; index < weatherConditionStates.length(); index++) {
                            JSONObject object = weatherConditionStates.getJSONObject(index);
                            Weather.State state = new Weather.State();
                            state.setTitle(object.getString("description"));
                            state.setIconUrl("http://openweathermap.org/img/w/" + object.getString("icon") + ".png");
                        }
                    } catch (JSONException e) {
                        Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }, null);
        queue.add(request);
        return weather;
    }

    private static final class JsonAsyncTask extends AsyncTask<Integer, Integer, List<OwmCity>> {
        private static final String TAG = "JsonAsyncTask";
        private final Reference<Activity> activityReference;

        private JsonAsyncTask(Activity activity) {
            this.activityReference = new WeakReference<>(activity);
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param values The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected List<OwmCity> doInBackground(Integer... values) {
            ArrayList<OwmCity> list = new ArrayList<>(0);
            try (InputStream stream = this.activityReference.get().getResources().openRawResource(values[0])) {
                list.addAll(readStream(stream));
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: " + e.getMessage(), e);
            }

            return list;
        }

        /**
         * Runs on the UI thread after {@link #publishProgress} is invoked.
         * The specified values are the values passed to {@link #publishProgress}.
         *
         * @param values The values indicating progress.
         * @see #publishProgress
         * @see #doInBackground
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            TextView view = activityReference.get().findViewById(R.id.publishProgress);
            view.setText("Proceeded: " + values[0]);
        }

        private List<OwmCity> readStream(InputStream stream) throws IOException {
            try (JsonReader reader = new JsonReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                return readCityArray(reader);
            }
        }

        private List<OwmCity> readCityArray(JsonReader reader) throws IOException {
            List<OwmCity> cities = new ArrayList<>(0);
            reader.beginArray();
            while (reader.hasNext()) {
                cities.add(readCity(reader));
                publishProgress(cities.size());
            }
            reader.endArray();
            return cities;
        }

        private OwmCity readCity(JsonReader reader) throws IOException {
            OwmCity city = new OwmCity();

            reader.beginObject();
            while (reader.hasNext()) {
                String text = reader.nextName();
                switch (text) {
                    case "id": city.setOwmId(reader.nextLong()); break;
                    case "name": city.setName(reader.nextString()); break;
                    case "country": city.setCountryCode(reader.nextString()); break;
                    case "coord": city.setLocation(readLocation(reader)); break;
                    default: reader.skipValue(); break;
                }
            }
            reader.endObject();

            return city;
        }

        private Location readLocation(JsonReader reader) throws IOException {
            Location location = new Location(LocationManager.GPS_PROVIDER);

            reader.beginObject();
            while (reader.hasNext()) {
                String text = reader.nextName();
                switch (text) {
                    case "lat": location.setLatitude(reader.nextDouble()); break;
                    case "lon": location.setLongitude(reader.nextDouble()); break;
                    default: reader.skipValue(); break;
                }
            }
            reader.endObject();

            return location;
        }
    }
}
