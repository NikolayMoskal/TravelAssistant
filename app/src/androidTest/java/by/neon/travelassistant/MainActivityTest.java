package by.neon.travelassistant;

import android.Manifest;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule public GrantPermissionRule rule = GrantPermissionRule.grant(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE);

    private MainActivity mainActivity;
    private LocationManager locationManager;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mainActivity = new MainActivity();
        locationManager = (LocationManager) mainActivity.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void onLocationClick_ProviderOrListenerNotExists() {
        LocationListener locationListener = mock(LocationListener.class);
        locationManager.requestLocationUpdates(null, 5000, 0, locationListener);
    }

    @Test
    public void obLocationClick_PermissionsGranted() {
        LocationListener locationListener = mock(LocationListener.class);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
    }

    @Test
    public void checkLocationProvider_ProviderExistsOnDevice() {
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkLocationProvider_ProviderNorExistsOnDeviceOrNull() {
        locationManager.isProviderEnabled(null);
    }
}
