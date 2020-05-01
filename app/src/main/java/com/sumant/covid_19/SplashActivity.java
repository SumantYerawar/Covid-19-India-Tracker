package com.sumant.covid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=3000;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);

        imageView = findViewById(R.id.imageView);

        String url = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ef/COVID-19_Outbreak_Cases_in_India.svg/800px-COVID-19_Outbreak_Cases_in_India.svg.png";

        Glide.with(getApplicationContext()).load(url).into(imageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent( SplashActivity.this , MainActivity.class );
                startActivity( intent );
            }
        },SPLASH_TIME_OUT);

    }

}
