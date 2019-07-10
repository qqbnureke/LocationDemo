package kz.nurda.locationdemo.extra;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import com.google.android.gms.location.LocationResult;

public class LocationUpdateService extends IntentService {
    private static final String TAG = "LocationUpdateService";
    private Context mContext;

    public LocationUpdateService(String name) {
        super(name);
    }
    public LocationUpdateService() {
        super("Get location");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!LocationResult.hasResult(intent)) {
            Log.d(TAG, "onHandleIntent: LocationUpdateService fired, but a LocationResult is missing");
            return;
        }

        LocationResult locationResult = LocationResult.extractResult(intent);
        if (!(locationResult.getLocations().size() > 0)) {
            Log.d(TAG, "onHandleIntent: LocationUpdateService fired, but no location(s) available");
        }

        Location location = locationResult.getLastLocation();
        Log.d(TAG, "onHandleIntent: " + location.getLatitude() + "/" + location.getLongitude());

    }
}
