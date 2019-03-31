package by.neon.travelassistant.listener;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import by.neon.travelassistant.R;

public class AutoCompleteTextViewItemClickListener implements AdapterView.OnItemClickListener {
    private final Activity activity;

    public AutoCompleteTextViewItemClickListener(final Activity activity) {
        this.activity = activity;
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!(view instanceof LinearLayout)) {
            throw new IllegalArgumentException("Missing LinearLayout item in AutoCompleteTextView");
        }
        TextView airportCode = (TextView) ((LinearLayout) view).getChildAt(1);
        AutoCompleteTextView target = activity.findViewById(R.id.arv_airport);
        target.setText(airportCode.getText());
    }
}
