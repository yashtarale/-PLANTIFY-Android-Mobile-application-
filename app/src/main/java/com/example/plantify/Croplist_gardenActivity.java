package com.example.plantify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Croplist_gardenActivity extends AppCompatActivity implements View.OnClickListener {
    public CardView rose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_croplist_garden);


        rose=(CardView) findViewById(R.id.rose);


        rose.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.rose:
                intent = new Intent(this, x_rose.class);
                startActivity(intent);
                break;

        }


    }
}