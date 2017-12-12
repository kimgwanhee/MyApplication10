package cookmap.cookandroid.com.myapplication10;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class location extends AppCompatActivity  {

    protected double latitude, longitude, altitude;
    protected Button btCheck, btPlace;
    protected TextView tvLatitude, tvLongitude, tvAltitude;
    protected LocationManager locationManager;
    private MyLocationListener myLocationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);

        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) findViewById(R.id.tvLongitude);
        tvAltitude = (TextView) findViewById(R.id.tvAltitude);

        btCheck = (Button) findViewById(R.id.btCheck);

        btCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                latitude = myLocationListener.latitude;
                longitude = myLocationListener.longitude;
                altitude = myLocationListener.altitude;

                tvLatitude.setText(String.format("%g", latitude));
                tvLongitude.setText(String.format("%g", longitude));
                tvAltitude.setText(String.format("%g", altitude));

                String str = String.format("geo:%g, %g?z=15", latitude, longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(str));
                startActivity(intent);

            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        long nMinTime = 1000;
        float minDistance = 0;
        myLocationListener = new MyLocationListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, nMinTime, minDistance, myLocationListener);


        btPlace = (Button) findViewById(R.id.btPlace);
        btPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Geocoder geocoder;
                geocoder = new Geocoder(getApplicationContext(), Locale.KOREAN);
                try {
                    List<Address> isAddress;
                    isAddress = geocoder.getFromLocation(latitude, longitude, 1);
                    Address address = isAddress.get(0);
                    String placeName = address.getFeatureName();
                    Toast.makeText(getApplicationContext(), placeName, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}
