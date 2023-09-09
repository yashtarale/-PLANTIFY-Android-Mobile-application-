package com.example.plantify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView txt = findViewById(R.id.txt);
        ImageView img = findViewById(R.id.img);
        Animation scale = AnimationUtils.loadAnimation(this,R.anim.scale);
        txt.setAnimation(scale);
        img.setAnimation(scale);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               Intent i = new Intent(splash.this,MainActivity.class);
                startActivity(i);
                getSupportActionBar().hide();
                finish();

            }
        },3000);
    }
}