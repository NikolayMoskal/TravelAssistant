package by.neon.travelassistant.utility;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class TravelRequestQueue {
    private static TravelRequestQueue ourInstance;
    private static Context appContext;
    private RequestQueue requestQueue;

    private TravelRequestQueue(Context context) {
        appContext = context;
        this.requestQueue = getRequestQueue();
    }

    public static TravelRequestQueue getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new TravelRequestQueue(context);
        }
        return ourInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(appContext.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addRequest(Request<T> request) {
        getRequestQueue().add(request);
    }
}
