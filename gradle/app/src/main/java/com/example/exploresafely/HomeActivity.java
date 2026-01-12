package com.example.exploresafely;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int SMS_PERMISSION_REQUEST_CODE = 2;
    private FusedLocationProviderClient fusedLocationClient;
    private double userLatitude, userLongitude;
    private DatabaseReference databaseReference;
    private String currentUserPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize Firebase and get current user
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://resqpath-90dbf-default-rtdb.firebaseio.com/");
        SharedPreferences sharedPreferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        currentUserPhone = sharedPreferences.getString("phone", "");

        // Initialize location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Find views by ID
        MaterialCardView hospitalCard = findViewById(R.id.hospitalCard);
        MaterialCardView policeCard = findViewById(R.id.policeCard);
        MaterialCardView combinedCard = findViewById(R.id.combinedCard);
        MaterialCardView chatbotCard = findViewById(R.id.chatbotCard);
        MaterialButton sosButton = findViewById(R.id.sosButton);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);

        // Set click listeners
        hospitalCard.setOnClickListener(v -> {
            handleEmergency("hospital");
            updateEmergencyCount("hospital");
        });

        policeCard.setOnClickListener(v -> {
            handleEmergency("police");
            updateEmergencyCount("police");
        });

        combinedCard.setOnClickListener(v -> {
            handleEmergency("hospital");
            handleEmergency("police");
            updateEmergencyCount("combined");
        });

        chatbotCard.setOnClickListener(v -> {
            // Launch chatbot activity
            Intent chatIntent = new Intent(HomeActivity.this, ChatbotActivity.class);
            startActivity(chatIntent);
        });

        sosButton.setOnClickListener(v -> {
            handleEmergency("hospital");
            handleEmergency("police");
            sendEmergencySMS();
            updateEmergencyCount("sos");
        });

        // Set up bottom navigation
        bottomNavigation.setOnItemSelectedListener(item -> {
            // Handle navigation item clicks
            int itemId = item.getItemId();
            // Example:
            if (itemId == R.id.nav_home) {
                // Already on home
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(HomeActivity.this, Profile.class));
                return true;
            } else if (itemId == R.id.nav_maps) {
                startActivity(new Intent(HomeActivity.this, offline_maps.class));
                return true;
            } else if (itemId == R.id.nav_aboutUs) {
                startActivity(new Intent(HomeActivity.this, about.class));
                return true;
            }

            return true;
        });

        // Check permissions and get user location
        checkPermissions();
    }

    // Method to update emergency counts in Firebase
    private void updateEmergencyCount(String emergencyType) {
        if (currentUserPhone.isEmpty()) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference userRef = databaseReference.child("users").child(currentUserPhone);
        userRef.child("emergency_counts").child(emergencyType).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int currentCount = 0;
                if (snapshot.exists()) {
                    currentCount = snapshot.getValue(Integer.class);
                }
                userRef.child("emergency_counts").child(emergencyType).setValue(currentCount + 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Failed to update emergency count", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            Toast.makeText(this, "Requesting Location Permission", Toast.LENGTH_SHORT).show();
        } else {
            getUserLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation();
                Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            userLatitude = location.getLatitude();
                            userLongitude = location.getLongitude();
                            Toast.makeText(HomeActivity.this, "Location fetched successfully: " + userLatitude + ", " + userLongitude, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(HomeActivity.this, "Unable to fetch location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void handleEmergency(String serviceType) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
        } else {
            String emergencyNumber = findNearestService(serviceType);
            if (emergencyNumber != null) {
                sendSms(emergencyNumber, "Emergency at location: https://maps.google.com/?q=" + userLatitude + "," + userLongitude);
            } else {
                Toast.makeText(this, "No nearby " + serviceType + " found within 10 km", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendEmergencySMS() {
        // Send emergency SMS to predefined emergency contacts
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
        } else {
            // Get emergency contacts from shared preferences or database
            String[] emergencyContacts = getEmergencyContacts();
            String message = "EMERGENCY! I need help at: https://maps.google.com/?q=" + userLatitude + "," + userLongitude;

            for (String contact : emergencyContacts) {
                sendSms(contact, message);
            }
        }
    }

    private String[] getEmergencyContacts() {
        // This should be implemented to retrieve actual emergency contacts from settings
        // For now, return a placeholder
        return new String[]{"9676087449"};
    }

    private String findNearestService(String serviceType) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(userLatitude, userLongitude, 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                String searchQuery = serviceType.equals("hospital") ? "nearest hospital" : "nearest police station";
                List<Address> results = geocoder.getFromLocationName(searchQuery + " near " + address.getAddressLine(0), 5);
                for (Address result : results) {
                    double distance = calculateDistance(userLatitude, userLongitude, result.getLatitude(), result.getLongitude());
                    if (distance <= 10) { // Within 10 km
                        Toast.makeText(this, "Found " + serviceType + " within 10 km", Toast.LENGTH_SHORT).show();
                        return getServicePhoneNumber(result); // Replace with actual phone number retrieval logic
                    }
                }
                Toast.makeText(this, "No " + serviceType + " found within 10 km", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error finding " + serviceType, Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // Radius of Earth in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    private String getServicePhoneNumber(Address address) {
        // In a real app, you would implement logic to get the actual phone number
        // For now, return emergency numbers based on service type
        return "9398879292"; // Replace with actual logic
    }

    private void sendSms(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Toast.makeText(this, "SMS sent to " + phoneNumber + ": " + message, Toast.LENGTH_SHORT).show();
    }
}