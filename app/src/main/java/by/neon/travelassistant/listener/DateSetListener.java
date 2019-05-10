package by.neon.travelassistant.listener;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateSetListener implements DatePickerDialog.OnDateSetListener {
    private final EditText view;

    public DateSetListener(final EditText view) {
        this.view = view;
    }

    /**
     * @param view       the picker associated with the dialog
     * @param year       the selected year
     * @param month      the selected month (0-11 for compatibility with
     *                   {@link Calendar#MONTH})
     * @param dayOfMonth th selected day of the month (1-31, depending on
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        Date date = new Date(calendar.getTimeInMillis());
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        this.view.setText(format.format(date));
    }
}
