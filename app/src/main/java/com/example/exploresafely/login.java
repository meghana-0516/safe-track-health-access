package com.example.exploresafely;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class login extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://resqpath-90dbf-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize your views here
        final EditText phone = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final Button loginBtn = findViewById(R.id.login_button);
        final TextView registerNowBtn = findViewById(R.id.registerNowBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneTxt = phone.getText().toString();
                final String passwordTxt = password.getText().toString();
                if(phoneTxt.isEmpty() || passwordTxt.isEmpty())
                {
                    Toast.makeText(login.this, "Please Enter Your Mobile No or Password", Toast.LENGTH_SHORT).show();

                }
                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if mobile is existing
                            if(snapshot.hasChild(phoneTxt))
                            {
                                //now get password

                                final String getPassword = snapshot.child(phoneTxt).child("password").getValue(String.class);

                                if(getPassword.equals(passwordTxt)){
                                    // Store user phone number in SharedPreferences
                                    SharedPreferences sharedPreferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("phone", phoneTxt); // Store the user's phone number
                                    editor.apply();

                                    //open main activity on login
                                    Toast.makeText(login.this,"Successfully Logged in",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(login.this, HomeActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(login.this,"Wrong password",Toast.LENGTH_SHORT).show();

                                }
                            }
                            else {
                                Toast.makeText(login.this,"Wrong Password",Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }
        });

        registerNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, Register.class));

            }
        });
        // Apply window insets listener to the main layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), this::onApplyWindowInsets);
    }

    private WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
        // Get system bar insets
        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

        // Set padding for the view
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

        return insets; // Return the insets to allow further handling
    }
}