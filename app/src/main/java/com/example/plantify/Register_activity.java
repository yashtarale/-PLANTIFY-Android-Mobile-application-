package com.example.plantify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Register_activity extends AppCompatActivity {


    private EditText editText_register_full_name, editText_register_email,
            editText_register_dob, editText_register_mobile,
            editText_register_password,edittext_register_confirm_password;

    private ProgressBar progressbar;
    private RadioGroup radio_group_register_gender;
    private RadioButton radio_group_register_gender_selected;
    public static final String TAG="Register_activity";
    private DatePickerDialog picker;

    Button button_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        getSupportActionBar().setTitle("Register");

        Toast.makeText(Register_activity.this, "You can register now", Toast.LENGTH_LONG).show();

        progressbar=findViewById(R.id.progressbar);
        editText_register_full_name =findViewById(R.id.editText_register_full_name);
        editText_register_email =findViewById(R.id.editText_register_email);
        editText_register_mobile =findViewById(R.id.editText_register_mobile);
        editText_register_dob =findViewById(R.id.editText_register_dob);
        edittext_register_confirm_password=findViewById(R.id.edittext_register_confirm_password);
        editText_register_password =findViewById(R.id.editText_register_password);

        //radio button for gender

        radio_group_register_gender =findViewById(R.id.radio_group_register_gender);
        radio_group_register_gender.clearCheck();//clear already selected button when newly opened

        //setting up date picker
        //on edit text
        editText_register_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar= Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);

                //date picker dialog
                picker=new DatePickerDialog(Register_activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int xmonth, int dayOfMonth) {
                        editText_register_dob.setText(dayOfMonth+"/"+(1+xmonth)+"/"+year);
                    }
                } ,year,month,day);
                picker.show();
            }
        });

        button_Register=findViewById(R.id.button_Register);
        button_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedGenderId= radio_group_register_gender.getCheckedRadioButtonId();
                radio_group_register_gender_selected=findViewById(selectedGenderId);

                //obtain data entered
                String textFullName= editText_register_full_name.getText().toString();
                String textEmail= editText_register_email.getText().toString();
                String textDob= editText_register_dob.getText().toString();
                String textMobile= editText_register_mobile.getText().toString();
                String textPwd= editText_register_password.getText().toString();
                String textConfirmPwd=edittext_register_confirm_password.getText().toString();
                String textGender; //first verifyt that button is selected or not

                //validating the mobile number
                String mobileRegex="[6-9][0-9]{9}";//1st no 6 to 9 and rest 9 can be any
                Matcher mobileMatcher;
                Pattern mobilePattern=Pattern.compile(mobileRegex);
                mobileMatcher=mobilePattern.matcher(textMobile);


                if(TextUtils.isEmpty(textFullName)){
                    Toast.makeText(Register_activity.this, "Please enter your full name", Toast.LENGTH_SHORT).show();
                    editText_register_full_name.setError("Full name is required");
                    editText_register_full_name.requestFocus();

                } 
                else if (TextUtils.isEmpty((textEmail))) {
                    Toast.makeText(Register_activity.this, "Email is required", Toast.LENGTH_SHORT).show();
                    editText_register_email.setError("Email.is required");
                    editText_register_email.requestFocus();


                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(Register_activity.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    editText_register_email.setError("Valid Email is required");
                    editText_register_full_name.requestFocus();
                } else if (TextUtils.isEmpty(textDob)) {
                    Toast.makeText(Register_activity.this, "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
                    editText_register_dob.setError("Date is required");
                    editText_register_full_name.requestFocus();
                } else if (radio_group_register_gender.getCheckedRadioButtonId()==-1) {
                    Toast.makeText(Register_activity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                    radio_group_register_gender_selected.setError("gender is required");
                    radio_group_register_gender_selected.requestFocus();

                } else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(Register_activity.this, "Please enter your Mobile Number", Toast.LENGTH_SHORT).show();
                    editText_register_mobile.setError("Mobile number is required");
                    editText_register_mobile.requestFocus();
                } else if (textMobile.length()!=10) {
                    Toast.makeText(Register_activity.this, "Please re-enter your mobile number", Toast.LENGTH_SHORT).show();
                    editText_register_mobile.setError("Length should be 10 digits");
                    editText_register_mobile.requestFocus();

                }
                else if(!mobileMatcher.find()){
                    Toast.makeText(Register_activity.this, "Enter valid mobile number", Toast.LENGTH_SHORT).show();
                    editText_register_mobile.setError("Mobile No. is not vaid");
                    editText_register_mobile.requestFocus();
                }
                else if(TextUtils.isEmpty(textPwd)){
                    Toast.makeText(Register_activity.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                    editText_register_password.setError("Password is required");
                    editText_register_password.requestFocus();
                } else if (textPwd.length()<6) {
                    Toast.makeText(Register_activity.this, "Password length should be more than 6 digits", Toast.LENGTH_SHORT).show();
                    editText_register_password.setError("Password too weak");
                    editText_register_password.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPwd)) {
                    Toast.makeText(Register_activity.this, "Please confirm your Password", Toast.LENGTH_SHORT).show();
                    edittext_register_confirm_password.setError("Password confirmation is required");
                    edittext_register_confirm_password.requestFocus();
                }
                else if (!textPwd.equals(textConfirmPwd)) {
                    Toast.makeText(Register_activity.this, "Both Passwords are not same", Toast.LENGTH_SHORT).show();
                    editText_register_password.setError("Check if both passwords are same");
                    editText_register_password.requestFocus();
                    //clear entered password
                    editText_register_password.clearComposingText();
                    edittext_register_confirm_password.clearComposingText();

                }
                else {
                    textGender=radio_group_register_gender_selected.getText().toString();
                    progressbar.setVisibility(View.VISIBLE);
                    registeruser(textFullName,textEmail,textDob,textGender,textMobile,textPwd);
                }


            }
            //require firebase now
            private void registeruser(String textFullName, String textEmail, String textDob, String textGender, String textMobile, String textPwd) {
                 FirebaseAuth auth =FirebaseAuth.getInstance();
                 auth.createUserWithEmailAndPassword(textEmail,textPwd).addOnCompleteListener(Register_activity.this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()){
                             //send email of registration
                             FirebaseUser firebaseUser=auth.getCurrentUser();

                             //update display name of user
                             UserProfileChangeRequest profileChangeRequest=new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                             firebaseUser.updateProfile(profileChangeRequest);

                             //ENTER USER DATA TO FIREBASE
                             ReadwriteUserDetails writeUserDetails=new ReadwriteUserDetails (textDob,textGender,textMobile);

                             //extract user refrence from database for registered users
                             DatabaseReference referenceProfile= FirebaseDatabase.getInstance().getReference("Registered Users");
                             referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){
                                            
                                            //send verify mail
                                            firebaseUser.sendEmailVerification();
                                            Toast.makeText(Register_activity.this, "User registered successfully. Please verify your email", Toast.LENGTH_LONG).show();

                                            //open profile after register
                                            Intent intent=new Intent(Register_activity.this,UserProfileActivity.class);
                                            //to pprevent return back to register page after registration
                                            intent.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                                    Intent.FLAG_ACTIVITY_NEW_TASK));
                                            startActivity(intent);
                                            finish();//to close register activity
                                        }
                                        
                                        else{
                                            Toast.makeText(Register_activity.this, "Registration Failed try again", Toast.LENGTH_SHORT).show();

                                        }
                                     progressbar.setVisibility(View.GONE);
                                 }
                             });


                         }
                         else {
                             try {
                                 throw task.getException();

                         }catch (FirebaseAuthWeakPasswordException E){
                                 editText_register_password.setError("password too weak, use number, alphabets and digits");
                             }
                             catch (FirebaseAuthInvalidCredentialsException e){
                                 editText_register_email.setError("kindly enter valid email");
                             }
                             catch (FirebaseAuthUserCollisionException e){
                                 editText_register_email.setError("User already registered with this email, use another email");
                                 editText_register_email.requestFocus();
                             }
                             catch (Exception e){
                                 Log.e(TAG,e.getMessage());
                                 Toast.makeText(Register_activity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                             }
                             }progressbar.setVisibility(View.GONE);
                     }
                 });

            }
        });
    }
}
