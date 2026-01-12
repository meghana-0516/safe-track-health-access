package com.example.exploresafely;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.util.GeoPoint;

public class offline_maps extends AppCompatActivity {

    private MapView mapView;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_maps);

        // Load OSMDroid configuration
        Configuration.getInstance().load(getApplicationContext(), getSharedPreferences("osmdroid", MODE_PRIVATE));

        // Initialize MapView
        mapView = findViewById(R.id.map);
        mapView.setMultiTouchControls(true);

        // Set a default location (New Delhi, India)
        GeoPoint indiaPoint = new GeoPoint(28.6139, 77.2090); // New Delhi's coordinates
        mapView.getController().setCenter(indiaPoint);
        mapView.getController().setZoom(5);  // Adjust zoom level as necessary

        // Add a marker on the map
        Marker indiaMarker = new Marker(mapView);
        indiaMarker.setPosition(indiaPoint);
        indiaMarker.setTitle("New Delhi, India");
        mapView.getOverlays().add(indiaMarker);

        // Request permissions for offline storage
        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION
        });
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission required for offline maps!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }
}
