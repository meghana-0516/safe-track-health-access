package com.example.exploresafely;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Handle edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the Explore button
        Button exploreButton = findViewById(R.id.exploreButton);

        // 💡 Bounce Animation (VISIBLE and SMOOTH)
        ObjectAnimator bounceX = ObjectAnimator.ofFloat(exploreButton, "scaleX", 1.0f, 1.2f);
        bounceX.setDuration(1200);
        bounceX.setInterpolator(new BounceInterpolator());
        bounceX.setRepeatCount(ValueAnimator.INFINITE);
        bounceX.setRepeatMode(ValueAnimator.REVERSE);
        bounceX.start();

        ObjectAnimator bounceY = ObjectAnimator.ofFloat(exploreButton, "scaleY", 1.0f, 1.2f);
        bounceY.setDuration(1200);
        bounceY.setInterpolator(new BounceInterpolator());
        bounceY.setRepeatCount(ValueAnimator.INFINITE);
        bounceY.setRepeatMode(ValueAnimator.REVERSE);
        bounceY.start();

        // 💡 Fade-in Effect for Smooth Visibility
        AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(1500);
        fadeIn.setFillAfter(true);
        exploreButton.startAnimation(fadeIn);

        // 🔘 Click Animation + Navigation
        exploreButton.setOnClickListener(v -> {
            // Quick shrink effect
            v.animate().scaleX(0.85f).scaleY(0.85f).setDuration(150).withEndAction(() -> {
                // Restore size
                v.animate().scaleX(1f).scaleY(1f).setDuration(150);
                // Navigate to LoginActivity
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
                // Slide transition animation
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            });
        });
    }
}
