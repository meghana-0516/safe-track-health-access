package com.example.exploresafely;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Register extends AppCompatActivity {
    //create object of database references
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://resqpath-90dbf-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        final EditText fullname = findViewById(R.id.fullname);
        final EditText email = findViewById(R.id.email);
        final EditText phone = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final EditText confirmpassword = findViewById(R.id.conpassword);

        final Button registerBtn = findViewById(R.id.register_button);
        final TextView loginNowBtn = findViewById(R.id.loginNowBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get data from
                final String fullnameTxt = fullname.getText().toString();
                final String emailTxt = email.getText().toString();
                final String phoneTxt = phone.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String conformPasswordTxt = confirmpassword.getText().toString();

                //check if user fill all the data

                if(fullnameTxt.isEmpty() || emailTxt.isEmpty() || phoneTxt.isEmpty() || passwordTxt.isEmpty() || conformPasswordTxt.isEmpty())
                {
                    Toast.makeText(Register.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                }
                //checking passwords with each other
                else if (!passwordTxt.equals(conformPasswordTxt)) {
                    Toast.makeText(Register.this, "passwords are not matching", Toast.LENGTH_SHORT).show();

                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if phone is not registered
                            if(snapshot.hasChild(phoneTxt)){
                                Toast.makeText(Register.this, "phone is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                databaseReference.child("users").child(phoneTxt).child("fullname").setValue(fullnameTxt);
                                databaseReference.child("users").child(phoneTxt).child("email").setValue(emailTxt);
                                databaseReference.child("users").child(phoneTxt).child("password").setValue(passwordTxt);

                                Toast.makeText(Register.this, "User registered Successfully", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });
        loginNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });

    }
}