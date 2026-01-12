package com.example.exploresafely;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    // Firebase database reference
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://resqpath-90dbf-default-rtdb.firebaseio.com/");

    // UI elements
    private TextView tvFullName, tvEmail, tvPhone;
    private TextView tvHospitalCount, tvPoliceCount, tvCombinedCount, tvSOSCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        // Initialize TextViews
        tvFullName = findViewById(R.id.tvFullName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvHospitalCount = findViewById(R.id.tvHospitalCount);
        tvPoliceCount = findViewById(R.id.tvPoliceCount);
        tvCombinedCount = findViewById(R.id.tvCombinedCount);
        tvSOSCount = findViewById(R.id.tvSOSCount);

        // Get current user's phone number from shared preferences
        String phoneNumber = getSharedPreferences("USER_DATA", MODE_PRIVATE).getString("phone", "");

        if (!phoneNumber.isEmpty()) {
            // Load user data from Firebase
            loadUserData(phoneNumber);
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadUserData(String phoneNumber) {
        databaseReference.child("users").child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get user profile data
                    String fullName = snapshot.child("fullname").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);

                    // Set user profile data to TextViews
                    tvFullName.setText(fullName);
                    tvEmail.setText(email);
                    tvPhone.setText(phoneNumber);

                    // Get emergency counts
                    DataSnapshot countSnapshot = snapshot.child("emergency_counts");

                    // Set counts (default to 0 if not found)
                    int hospitalCount = 0;
                    int policeCount = 0;
                    int combinedCount = 0;
                    int sosCount = 0;

                    if (countSnapshot.exists()) {
                        if (countSnapshot.child("hospital").exists()) {
                            hospitalCount = countSnapshot.child("hospital").getValue(Integer.class);
                        }
                        if (countSnapshot.child("police").exists()) {
                            policeCount = countSnapshot.child("police").getValue(Integer.class);
                        }
                        if (countSnapshot.child("combined").exists()) {
                            combinedCount = countSnapshot.child("combined").getValue(Integer.class);
                        }
                        if (countSnapshot.child("sos").exists()) {
                            sosCount = countSnapshot.child("sos").getValue(Integer.class);
                        }
                    }

                    // Update count displays
                    tvHospitalCount.setText(String.valueOf(hospitalCount));
                    tvPoliceCount.setText(String.valueOf(policeCount));
                    tvCombinedCount.setText(String.valueOf(combinedCount));
                    tvSOSCount.setText(String.valueOf(sosCount));
                } else {
                    Toast.makeText(Profile.this, "User profile not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}