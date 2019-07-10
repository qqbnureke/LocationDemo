package kz.nurda.locationdemo.extra

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.location.LocationResult

class MyLocationService : BroadcastReceiver() {

    companion object {
        val ACTION_PROCESS_UPDATE = "kz.nurda.locationdemo.UPDATE_LOCATION"
        var coors = String()
    }



    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (action != null) {
            if (action.equals(ACTION_PROCESS_UPDATE)) {

                val result = LocationResult.extractResult(intent)
                if (result != null) {
                    val location = result.lastLocation
                    val coordinates = StringBuilder("${location.longitude}")
                        .append("/")
                        .append(location.latitude)
                        .toString()

                    coors = coordinates
                    try {
                        MainActivity.getMainInstance().updateTextView(coordinates)
                    } catch (e: Exception) {
                        Toast.makeText(context, coordinates, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}