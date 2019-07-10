package kz.nurda.locationdemo.extra;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.location.LocationResult;

public class LocationService extends BroadcastReceiver {

    public static final String ACTION_PROCESS_UPDATE = "kz.nurda.locationdemo.UPDATE_LOCATION";


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null){
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATE.equals(action)){
                LocationResult result = LocationResult.extractResult(intent);
                if (result != null){
                    Location location = result.getLastLocation();
                    String coordinates = new StringBuilder(""+location.getLatitude())
                            .append("/")
                            .append(location.getLongitude())
                            .toString();
                    try {
                        LocActivity.getInstance().updateTextView(coordinates);
                    }catch (Exception ex){
                        Toast.makeText(context, coordinates, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
