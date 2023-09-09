package com.example.plantify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class deleteProile extends AppCompatActivity {
private FirebaseAuth authProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_proile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflat emnu options
        getMenuInflater().inflate(R.menu.profile_menu,menu);//menu is var of this "oncreate option ment"
        return super.onCreateOptionsMenu(menu);
    }

    //when any item selected

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.menu_refresh){
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        }

        else if (id==R.id.menu_update_profile) {
            Intent intent=new Intent(deleteProile.this,UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        }
        else if (id==R.id.menu_update_email) {
            Intent intent=new Intent(deleteProile.this,updateEmail.class);
            startActivity(intent);
            finish();
        }
        else if (id==R.id.menu_settings) {
            Toast.makeText(deleteProile.this, "Clicked on Settings", Toast.LENGTH_SHORT).show();
        }
        else if (id==R.id.menu_change_password) {
            Intent intent=new Intent(deleteProile.this,updatePassword.class);
            finish();
            startActivity(intent);
        }
        else if (id==R.id.menu_delete_profile) {
            Intent intent=new Intent(deleteProile.this,deleteProile.class);
            startActivity(intent);
            finish();

        }
        else if (id==R.id.menu_logout) {
            authProfile.signOut();
            Toast.makeText(deleteProile.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(deleteProile.this,MainActivity.class);

            //clear stack to prevent coming back tosame page
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();//close user profile activity

        }
        else{
            Toast.makeText(deleteProile.this, "something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}