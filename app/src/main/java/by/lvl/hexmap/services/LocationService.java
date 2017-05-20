package by.lvl.hexmap.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

import by.lvl.hexmap.api.Api;
import by.lvl.hexmap.models.User.User;
import by.lvl.hexmap.models.User.repo.PrefUserRepository;
import by.lvl.hexmap.models.UserLocation.UserLocation;
import by.lvl.hexmap.tools.AppLog;


@SuppressWarnings("MissingPermission")
public class LocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static final int PERMISSION_ACCESS_FINE_LOCATION_REQUEST_CODE = 101;

    private static final long TIME_INTERVAL = 600 * 1000;
    private static final long FAST_TIME_INTERVAL = 60 * 1000;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;


    @Override
    public void onCreate() {

        super.onCreate();

        if (!isGPSenabled(getApplicationContext())) {
            stopSelf();
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(TIME_INTERVAL)
                .setFastestInterval(FAST_TIME_INTERVAL);

        mGoogleApiClient.connect();
    }


    @Override
    public void onDestroy() {

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }

        super.onDestroy();
    }


    @Override
    public void onConnected(Bundle bundle) {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }


    @Override
    public void onConnectionSuspended(int i) { }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (!connectionResult.hasResolution()) {
            AppLog.d("Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }


    private void handleNewLocation(Location location) {

        float lat = (float) location.getLatitude();
        float lng = (float) location.getLongitude();
        AppLog.d("Have got a new location: " + lat + "; " + lng);

        LatLng latLng = new LatLng(lat, lng);

        User user = new PrefUserRepository().get();
        if (!TextUtils.isEmpty(user.getId())) {
            UserLocation userLocation = new UserLocation();
            userLocation.setUser(user);
            userLocation.setDate(new Date());
            userLocation.setLocation(latLng);
            Api.getInstance().postLocation(userLocation);
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private static boolean isGPSenabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    public static double calculateDistance(LatLng from, LatLng to) {

        double distance = .0;
        float[] results = new float[3];

        if (from != null) {
            if (to == null) {
                distance = Double.MAX_VALUE;
            }
            else {
                Location.distanceBetween(from.latitude, from.longitude, to.latitude, to.longitude, results);
                distance = results[0];
            }
        }

        return distance;
    }
}