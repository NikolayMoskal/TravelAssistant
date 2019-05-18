package by.neon.travelassistant.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import by.neon.travelassistant.BuildConfig;
import by.neon.travelassistant.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setVersion();
    }

    private void setVersion() {
        TextView view = findViewById(R.id.app_version_title);
        view.setText(BuildConfig.VERSION_NAME);
    }
}
