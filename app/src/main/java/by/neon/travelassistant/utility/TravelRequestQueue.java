package by.neon.travelassistant.utility;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * The Volley request queue singleton. More info on https://developer.android.com/training/volley/requestqueue#singleton.
 *
 * @see RequestQueue
 */
public class TravelRequestQueue {
    /**
     * The instance of {@link TravelRequestQueue}
     */
    private static TravelRequestQueue ourInstance;
    /**
     * The application context.
     */
    private static Context appContext;
    /**
     * The Volley request queue.
     */
    private RequestQueue requestQueue;

    /**
     * Builds a new instance of {@link TravelRequestQueue} using application context.
     *
     * @param context the app context.
     */
    private TravelRequestQueue(Context context) {
        appContext = context;
        this.requestQueue = getRequestQueue();
    }

    /**
     * Gets the instance of {@link TravelRequestQueue}.
     *
     * @param context the app context.
     * @return the instance.
     */
    public static TravelRequestQueue getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new TravelRequestQueue(context);
        }
        return ourInstance;
    }

    /**
     * Gets the request queue from this instance.
     *
     * @return the request queue.
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(appContext.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Adds a request to existing request queue.
     *
     * @param request the request.
     * @param <T>     the type of requested data.
     */
    public <T> void addRequest(Request<T> request) {
        getRequestQueue().add(request);
    }
}
