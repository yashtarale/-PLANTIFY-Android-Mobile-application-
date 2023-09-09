package com.example.plantify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateProfileActivity extends AppCompatActivity {
private EditText edittext_update_profile_full_name,edittext_update_profile_dob,edittext_update_profile_mobile;
private RadioGroup radio_group_update_gender;
private RadioButton radioButtonUpdateGenderSelected;
private String textFullName,textDob,textGender,textMobile;
private FirebaseAuth authProfile;
private ProgressBar progressBar;

private Button button_update_profile;
private TextView textView_profile_upload_pic,textView_profile_update_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        progressBar=findViewById(R.id.progressbar);
        edittext_update_profile_full_name=findViewById(R.id.edittext_update_profile_full_name);
        edittext_update_profile_dob=findViewById(R.id.edittext_update_profile_dob);
        edittext_update_profile_mobile=findViewById(R.id.edittext_update_profile_mobile);

        radio_group_update_gender=findViewById(R.id.radio_group_update_gender);

        authProfile=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=authProfile.getCurrentUser();


        //show Profile data
        showProfile(firebaseUser);

        //Button upload profile picture
        textView_profile_upload_pic=findViewById(R.id.textView_profile_upload_pic);
        textView_profile_upload_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UpdateProfileActivity.this,profilePictureActivity.class);
                startActivity(intent);
                finish();
            }
        });

        textView_profile_update_email=findViewById(R.id.textView_profile_update_email);
        textView_profile_update_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UpdateProfileActivity.this,updateEmail.class);
                startActivity(intent);
                finish();
            }
        });


        //setting up date picker
        //on edit text
        edittext_update_profile_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Extracting saved
                final Calendar calendar= Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);

                DatePickerDialog picker;
                //date picker dialog
                picker=new DatePickerDialog(UpdateProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int xmonth, int dayOfMonth) {
                        edittext_update_profile_dob.setText(dayOfMonth+"/"+(1+xmonth)+"/"+year);
                    }
                } ,year,month,day);
                picker.show();
            }
        });


        button_update_profile=findViewById(R.id.button_update_profile);
        button_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(firebaseUser);
            }
        });
    }

    private void updateProfile(FirebaseUser firebaseUser) {
        int selectedGenderID=radio_group_update_gender.getCheckedRadioButtonId();
        radioButtonUpdateGenderSelected=findViewById(selectedGenderID);

        //validating the mobile number
        String mobileRegex="[6-9][0-9]{9}";//1st no 6 to 9 and rest 9 can be any
        Matcher mobileMatcher;
        Pattern mobilePattern=Pattern.compile(mobileRegex);
        mobileMatcher=mobilePattern.matcher(textMobile);


        if(TextUtils.isEmpty(textFullName)){
            Toast.makeText(UpdateProfileActivity.this, "Please enter your full name", Toast.LENGTH_SHORT).show();
            edittext_update_profile_full_name.setError("Full name is required");
            edittext_update_profile_full_name.requestFocus();

        }
       else if (TextUtils.isEmpty(textDob)) {
            Toast.makeText(UpdateProfileActivity.this, "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
            edittext_update_profile_dob.setError("Date is required");
            edittext_update_profile_dob.requestFocus();
        } else if (radio_group_update_gender.getCheckedRadioButtonId()==-1) {
            Toast.makeText(UpdateProfileActivity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
            radioButtonUpdateGenderSelected.setError("gender is required");
            radioButtonUpdateGenderSelected.requestFocus();

        } else if (TextUtils.isEmpty(textMobile)) {
            Toast.makeText(UpdateProfileActivity.this, "Please enter your Mobile Number", Toast.LENGTH_SHORT).show();
            edittext_update_profile_mobile.setError("Mobile number is required");
            edittext_update_profile_mobile.requestFocus();
        } else if (textMobile.length()!=10) {
            Toast.makeText(UpdateProfileActivity.this, "Please re-enter your mobile number", Toast.LENGTH_SHORT).show();
            edittext_update_profile_mobile.setError("Length should be 10 digits");
            edittext_update_profile_mobile.requestFocus();

        }
        else if(!mobileMatcher.find()){
            Toast.makeText(UpdateProfileActivity.this, "Enter valid mobile number", Toast.LENGTH_SHORT).show();
            edittext_update_profile_mobile.setError("Mobile No. is not vaid");
            edittext_update_profile_mobile.requestFocus();
        }


        else {

            //obtain data entered by user
            textGender=radioButtonUpdateGenderSelected.getText().toString();
            textFullName=edittext_update_profile_full_name.getText().toString();
            textDob=edittext_update_profile_dob.getText().toString();
            textMobile=edittext_update_profile_mobile.getText().toString();

            //enter data to firebase
            ReadwriteUserDetails writeUserDetails=new ReadwriteUserDetails(textDob,textGender,textMobile);
            //extract user refrence  form database registered users
            DatabaseReference refrenceprofile=FirebaseDatabase.getInstance().getReference("Registered Users");
            String userId=firebaseUser.getUid();

            progressBar.setVisibility(View.VISIBLE);

            refrenceprofile.child(userId).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        //setting new display name
                        UserProfileChangeRequest profileUpdates=new UserProfileChangeRequest.Builder().
                                setDisplayName(textFullName).build();
                        firebaseUser.updateProfile(profileUpdates);

                        Toast.makeText(UpdateProfileActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(UpdateProfileActivity.this,MainActivity.class);

                        //to pprevent return back to same page
                        intent.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK));
                        startActivity(intent);
                        finish();//to close register activity
                    }
                    else{
                        try {
                            throw task.getException();

                        }
                        catch (Exception e){

                            Toast.makeText(UpdateProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }


    private void showProfile(FirebaseUser firebaseUser) {
        String userIDofRegistered=firebaseUser.getUid();
        //Extracting user Refrencefrom Database for registerred user
        DatabaseReference referenceProfile= FirebaseDatabase.getInstance().getReference("Registered Users");
        progressBar.setVisibility(View.VISIBLE);

        referenceProfile.child(userIDofRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadwriteUserDetails readUserDetails=snapshot.getValue(ReadwriteUserDetails.class);
                if(readUserDetails!=null){
                    textFullName=firebaseUser.getDisplayName();
                    textDob=readUserDetails.dob;
                    textGender=readUserDetails.gender;
                    textMobile=readUserDetails.mobile;

                    edittext_update_profile_full_name.setText(textFullName);
                    edittext_update_profile_dob.setText(textDob);
                    edittext_update_profile_mobile.setText(textMobile);


                    //show gender by radio button
                    if(textGender=="Male"){
                        radioButtonUpdateGenderSelected=findViewById(R.id.radio_male);

                    }
                    else{
                        radioButtonUpdateGenderSelected=findViewById(R.id.radio_male);

                    }
                    radioButtonUpdateGenderSelected.setChecked(true);

                }
                else {
                    Toast.makeText(UpdateProfileActivity.this, "something went wrong!", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateProfileActivity.this, "something went wrong!", Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflat emnu options
        getMenuInflater().inflate(R.menu.profile_menu, menu);//menu is var of this "oncreate option ment"
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
            Intent intent=new Intent(UpdateProfileActivity.this,UpdateProfileActivity.class);
            startActivity(intent);
            //finish();
        }
         else if (id==R.id.menu_update_email) {
         Intent intent=new Intent(UpdateProfileActivity.this,updateEmail.class);
         startActivity(intent);
       //  finish();
         }
         else if (id==R.id.menu_settings) {
         Toast.makeText(UpdateProfileActivity.this, "Clicked on Settings", Toast.LENGTH_SHORT).show();
         }
         else if (id==R.id.menu_change_password) {
         Intent intent=new Intent(UpdateProfileActivity.this,updatePassword.class);
         startActivity(intent);
       //  finish();
         }
         else if (id==R.id.menu_delete_profile) {
         Intent intent=new Intent(UpdateProfileActivity.this,deleteProile.class);
         startActivity(intent);
       //  finish();

         }
        else if (id==R.id.menu_logout) {
            authProfile.signOut();
            Toast.makeText(UpdateProfileActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(UpdateProfileActivity.this,MainActivity.class);

            //clear stack to prevent coming back tosame page
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();//close user profile activity

        }
        else{
            Toast.makeText(UpdateProfileActivity.this, "something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}