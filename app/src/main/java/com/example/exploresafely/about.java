package com.example.exploresafely;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);

        // Set up window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get app version and set it to the TextView
        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            TextView versionTextView = findViewById(R.id.appVersion);
            versionTextView.setText("Version " + versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set up social media click listeners
        setupSocialMediaLinks();
    }

    private void setupSocialMediaLinks() {
        // Facebook click listener
        ImageView facebookIcon = findViewById(R.id.facebookIcon);
        facebookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl("https://www.facebook.com/exploresafely");
            }
        });

        // Twitter click listener
        ImageView twitterIcon = findViewById(R.id.twitterIcon);
        twitterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl("https://www.twitter.com/exploresafely");
            }
        });

        // Instagram click listener
        ImageView instagramIcon = findViewById(R.id.instagramIcon);
        instagramIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl("https://www.instagram.com/exploresafely");
            }
        });
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}