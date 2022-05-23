package ru.smak.gpstestjava.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ru.smak.gpstestjava.MainActivity;
import ru.smak.gpstestjava.PermissionManager;
import ru.smak.gpstestjava.R;

public class LocationHelper implements LocationListener {

    static Location imHere = null;
    private MainActivity activity;
    public static MutableLiveData<String> mutableLocation = new MutableLiveData<>();
    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    @SuppressLint("MissingPermission")
    public static void startLocationListening(Context context, LocationHelper locHelper) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // Проверить наличие разрешения!
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        if (PermissionManager.hasPermissions(context, permissions)) {// Проверить наличие разрешения!
            lm.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    500,
                    1f,
                    locHelper
            );
        } else {
            Toast toast = Toast.makeText(context, context.getResources().getString(R.string.error), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        imHere = location;
        String tmp = String.valueOf(activity.getResources().getString(R.string.latitude) + ": " +
                String.format("%.5f", imHere.getLatitude()) + "\n" +
                activity.getResources().getString(R.string.longitude) + ": " +
                String.format("%.5f", imHere.getLongitude()));
        mutableLocation.postValue(tmp);
    }

}
