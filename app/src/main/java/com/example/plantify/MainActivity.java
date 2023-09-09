package com.example.plantify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Homefragment homefragment=new Homefragment();
    Profilefragment profilefragment =new Profilefragment();
    Postsfragment postsfragment=new Postsfragment();

    Apmcfragment apmcfragment=new Apmcfragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bottomNavigationView=findViewById(R.id.bottom_navigation);

        //  to amke navigate framelyout  //replace container with home fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homefragment).commit();



        BadgeDrawable badgeDrawable=bottomNavigationView.getOrCreateBadge(R.id.posts);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(2);
        //setting on item selected listener with bottom view
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homefragment).commit();
                        return true;

                    case R.id.posts:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,postsfragment).commit();
                        return true;

                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,profilefragment).commit();
                        return true;

                }

                return false;
            }
        });


    }

}