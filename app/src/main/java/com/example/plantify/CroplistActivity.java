package com.example.plantify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class CroplistActivity extends AppCompatActivity implements View.OnClickListener {

    public CardView potato,tomato,pepperBell,cherry,apple,grapes,peach,cotton,corn,strawberry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_croplist);

        potato=(CardView) findViewById(R.id.potato);
        tomato=(CardView) findViewById(R.id.tomato);
        pepperBell=(CardView) findViewById(R.id.pepperBell);
        corn=(CardView)findViewById(R.id.corn);
        cherry=(CardView) findViewById(R.id.cherry);
        grapes=(CardView)findViewById(R.id.grapes);
        peach=(CardView)findViewById(R.id.peach);
        apple=(CardView) findViewById(R.id.apple);
        strawberry=(CardView)findViewById(R.id.strawberry);
        cotton=(CardView)findViewById(R.id.cotton);


        potato.setOnClickListener(this);
        tomato.setOnClickListener(this);
        pepperBell.setOnClickListener(this);
        corn.setOnClickListener(this);
        cherry.setOnClickListener(this);
        grapes.setOnClickListener(this);
        peach.setOnClickListener(this);
        apple.setOnClickListener(this);
        strawberry.setOnClickListener(this);
        cotton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.strawberry:
                intent =new Intent(this,x_strawberry.class);
                startActivity(intent);
                break;

            case R.id.corn:
                intent =new Intent(this,x_corn.class);
                startActivity(intent);
                break;



            case R.id.grapes:
                intent = new Intent(this, x_grapes.class);
                startActivity(intent);
                break;


                case R.id.cherry:
                    intent = new Intent(this, x_cherry.class);
                    startActivity(intent);
                    break;



                    case R.id.apple:
                        intent = new Intent(this, x_apple.class);
                        startActivity(intent);
                        break;

                    case R.id.potato:
                        intent = new Intent(this, x_potato.class);
                        startActivity(intent);
                        break;

            case R.id.peach:
                intent = new Intent(this, x_peach.class);
                startActivity(intent);
                break;

            case R.id.cotton:
                intent = new Intent(this, x_cotton.class);
                startActivity(intent);
                break;

                    case R.id.tomato:
                        intent = new Intent(this, x_tomato.class);
                        startActivity(intent);
                        break;

                    case R.id.pepperBell:
                        intent = new Intent(this, x_pepperbell.class);
                        startActivity(intent);
                        break;
                }


            }
        }
