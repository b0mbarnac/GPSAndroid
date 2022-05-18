package ru.smak.gpstestjava.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;

public class LocationHelper implements LocationListener {

    static Location imHere = null;

    @SuppressLint("MissingPermission")
    public static void startLocationListening(Context context){
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // Проверить наличие разрешения!
        lm.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                500,
                10f,
                new LocationHelper()
        );
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        imHere = location;
    }


}
