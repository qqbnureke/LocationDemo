package kz.nurda.locationdemo

import android.Manifest
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest

    companion object {
        var instance: MainActivity? = null

        fun getMainInstance(): MainActivity {
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        instance = this

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        updateLocation()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                            permission: PermissionRequest?,
                            token: PermissionToken?
                    ) {
                        AlertDialog.Builder(this@MainActivity)
                                .setTitle(R.string.permission_title)
                                .setMessage(R.string.permission_message)
                                .setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->
                                    dialogInterface.dismiss()
                                    token?.cancelPermissionRequest()
                                })
                                .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialogInterface, i ->
                                    dialogInterface.dismiss()
                                    token?.continuePermissionRequest()
                                })
                                .show()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Toast.makeText(this@MainActivity, R.string.permission_denied, Toast.LENGTH_SHORT).show()
                    }

                }).check()
    }

    fun updateLocation() {
        createLocationRequest()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED)
            return

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.requestLocationUpdates(locationRequest, getPendingIntent());

    }

    private fun getPendingIntent(): PendingIntent? {
        val intent = Intent(this@MainActivity, MyLocationService::class.java)
        intent.setAction(MyLocationService.ACTION_PROCESS_UPDATE)
        return PendingIntent.getBroadcast(this@MainActivity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.create()?.apply {
            interval = 5000
            fastestInterval = 3000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            smallestDisplacement = 10f
        }!!
    }

    fun updateTextView(value: String) {
        this@MainActivity.runOnUiThread {
            tvCoordinates.text = value
        }
    }
}
