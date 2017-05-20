package by.lvl.hexmap.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import by.lvl.hexmap.R;
import by.lvl.hexmap.services.LocationService;

import static by.lvl.hexmap.services.LocationService.PERMISSION_ACCESS_FINE_LOCATION_REQUEST_CODE;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    public void initView() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            resume();
        }
        else {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    LocationService.PERMISSION_ACCESS_FINE_LOCATION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_ACCESS_FINE_LOCATION_REQUEST_CODE
                && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                resume();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void resume() {

        startService(new Intent(this, LocationService.class));
        new Handler().postDelayed(() -> {
            Navigator.toSignUp(MainActivity.this);
        }, 150);
    }


    @Override
    public void onStop() {
        super.onStop();
        stopService(new Intent(this, LocationService.class));
    }
}
