package com.example.plantify;

import android.animation.LayoutTransition;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity {

    private TextView textview_show_welcome,textView_show_full_name,textView_show_email,textView_show_dob,textView_show_gender,textView_show_mobile;

    private ProgressBar progressBar;

    private String fullname,email,dob,gender,mobile;

    private FirebaseAuth authProfile;

    ImageView imageView_profile_dp;

RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        layout=findViewById(R.id.layout);
        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        textview_show_welcome=findViewById(R.id.textview_show_welcome);
        textView_show_full_name=findViewById(R.id.textView_show_full_name);
        textView_show_email=findViewById(R.id.textView_show_email);
        textView_show_dob=findViewById(R.id.textView_show_dob);
        textView_show_gender=findViewById(R.id.textView_show_gender);
        textView_show_mobile=findViewById(R.id.textView_show_mobile);
        progressBar=findViewById(R.id.progressbar);

        //set on click listener on imageview toopen profilepicture activity
        imageView_profile_dp=findViewById(R.id.imageView_profile_dp);
        imageView_profile_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserProfileActivity.this,profilePictureActivity.class);
                startActivity(intent);

            }
        });


        authProfile=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=authProfile.getCurrentUser();

        if(firebaseUser==null){
            Toast.makeText(UserProfileActivity.this, "Something went wrong, user data not available.", Toast.LENGTH_LONG).show();

        }
        else{

            checkIfEmailisVerified(firebaseUser);
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }

    }

    private void showUserProfile(FirebaseUser firebaseUser) {

        String UserId = firebaseUser.getUid();
        //Extracting uer refrence from database  for registered users
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        referenceProfile.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadwriteUserDetails readUserDetails = snapshot.getValue(ReadwriteUserDetails.class);
                if (readUserDetails != null) {
                    fullname = firebaseUser.getDisplayName();
                    email = firebaseUser.getEmail();
                    dob = readUserDetails.dob;
                    gender = readUserDetails.gender;
                    mobile = readUserDetails.mobile;

                    //textview_show_welcome.setText("Welcome, " + fullname + " !");----previous code
                    textview_show_welcome.setText(getString(R.string.welcome_head_profile,fullname));
                    textView_show_full_name.setText(fullname);
                    textView_show_email.setText(email);
                    textView_show_dob.setText(dob);
                    textView_show_gender.setText(gender);
                    textView_show_mobile.setText(mobile);

                    //set user dp(after user has uploaded

                    Uri uri=firebaseUser.getPhotoUrl();

                    //imageviewer setImageURI()should not be used with regular uris.so use picasso

                    Picasso.with(UserProfileActivity.this).load(uri).into(imageView_profile_dp);
                }
                else{
                    imageView_profile_dp.setImageResource(R.drawable.profilepicture);
                    //Toast.makeText(UserProfileActivity.this, "no Profile picture", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfileActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    private void checkIfEmailisVerified(FirebaseUser firebaseUser) {
        if(!firebaseUser.isEmailVerified()){
            showAlertDialog();

        }
    }

    private void showAlertDialog() {
        //setup alert builer
        AlertDialog.Builder builder=new AlertDialog.Builder(UserProfileActivity.this);
        builder.setTitle("email is not Verified");
        builder.setMessage("please verify your email. You cannot login without email verification next time.");

        //open email if user taps continue
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//open email in new window not in our app
                startActivity(intent);
            }

        });
    }
    public void expand(View view) {
        int v = (layout.getVisibility() == view.GONE) ? View.VISIBLE : View.GONE;
        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
        layout.setVisibility(v);
    }

    //acion bar menu


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
            Intent intent=new Intent(UserProfileActivity.this,UpdateProfileActivity.class);
            startActivity(intent);
            //finish();
        }
        else if (id==R.id.menu_update_email) {
            Intent intent=new Intent(UserProfileActivity.this,updateEmail.class);
            startActivity(intent);
           // finish();
        }
        else if (id==R.id.menu_settings) {
            Toast.makeText(UserProfileActivity.this, "Clicked on Settings", Toast.LENGTH_SHORT).show();
        }
        else if (id==R.id.menu_change_password) {
            Intent intent=new Intent(UserProfileActivity.this,updatePassword.class);
            startActivity(intent);
            //finish();
        }
        else if (id==R.id.menu_delete_profile) {
            Intent intent=new Intent(UserProfileActivity.this,deleteProile.class);
            startActivity(intent);
           // finish();
        }
        else if (id==R.id.menu_logout) {
            authProfile.signOut();
            Toast.makeText(UserProfileActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(UserProfileActivity.this,MainActivity.class);

            //clear stack to prevent coming back tosame page
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();//close user profile activity

        }
        else{
            Toast.makeText(UserProfileActivity.this, "something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}