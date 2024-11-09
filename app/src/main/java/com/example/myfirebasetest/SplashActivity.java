package com.example.myfirebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);  // Enable edge-to-edge support
        setContentView(R.layout.activity_splash);  // Load splash layout

        // Apply window insets to handle system bars (e.g., status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;  // Return the insets to be applied
        });

        // Handler to move to HomeActivity after 2 seconds
        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(mainIntent);  // Start HomeActivity
            finish();  // Close SplashActivity
        }, SPLASH_DISPLAY_LENGTH);  // 2000ms = 2 seconds
    }
}
