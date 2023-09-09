package com.example.plantify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class Login_activity extends AppCompatActivity {
    private EditText edittext_login_email,edittext_login_password;
    private ProgressBar progressbar;
    private FirebaseAuth authProfile;
    public static final String TAG="Login_activity";
    ImageView show_hide_pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//activity name displayed in action bar
        getSupportActionBar().setTitle("Login");

        edittext_login_email=findViewById(R.id.edittext_login_email);
        edittext_login_password=findViewById(R.id.edittext_login_password);
        progressbar=findViewById(R.id.progressbar);


        authProfile=FirebaseAuth.getInstance();

        TextView Textview_forgot_password_link=findViewById(R.id.Textview_forgot_password_link);

        Textview_forgot_password_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login_activity.this, "You can reset your passwprd now!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login_activity.this,forgotPasswordActivity.class));
            }
        });

        //register textview
        TextView Textview_register_link=findViewById(R.id.Textview_register_link);
        Textview_register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_activity.this,Register_activity.class));
            }
        });



        //show and hide password eye
        show_hide_pwd=findViewById(R.id.show_hide_pwd);
        show_hide_pwd.setImageResource(R.drawable.eye_hide);
        show_hide_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edittext_login_password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance()))
                {
                    //if visible pass then hide it
                    edittext_login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //change eye icon
                    show_hide_pwd.setImageResource(R.drawable.eye_hide);
                }
                else{
                    edittext_login_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    show_hide_pwd.setImageResource(R.drawable.eye);
                }
            }
        });

        //Login user
        Button buttonLogin=findViewById(R.id.button_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail=edittext_login_email.getText().toString();
                String textPwd=edittext_login_password.getText().toString();

                if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(Login_activity.this, "please enter your Email", Toast.LENGTH_SHORT).show();
                    edittext_login_email.setError("Email is required");
                    edittext_login_email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(Login_activity.this, "please re-enter your Email", Toast.LENGTH_SHORT).show();
                    edittext_login_email.setError("Valid Email is required");
                    edittext_login_email.requestFocus();

                }
                else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(Login_activity.this, "Please enter account password", Toast.LENGTH_SHORT).show();
                    edittext_login_password.setError("Password is required");
                    edittext_login_password.requestFocus();

                }
                else {
                    progressbar.setVisibility(View.VISIBLE);
                    loginUser(textEmail,textPwd);
                }
            }
        });
}

    private void loginUser(String email, String pwd) {
        authProfile.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(Login_activity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //getting instance of current user for alert of verify mail
                    FirebaseUser firebaseUser=authProfile.getCurrentUser();

                    //check if email verified
                    if(firebaseUser.isEmailVerified()){
                        Toast.makeText(Login_activity.this, "You are Logged in now", Toast.LENGTH_SHORT).show();
                        //open user profiler
                        //start user profile activity
                        Intent intent=new Intent(Login_activity.this,MainActivity.class);
                        startActivity(intent);
                       // startActivity(new Intent(Login_activity.this,Profilefragment.class));
                        finish();//close login activity
                    }
                    else {
                        firebaseUser.sendEmailVerification();
                        authProfile.signOut();//signout user if not verified
                        showAlertDialog();
                    }

                }
                else{
                    try
                        {
                            throw task.getException();
                        }
                        catch(FirebaseAuthInvalidUserException e){
                            edittext_login_email.setError("User does not exist or is no longer valid.Please register again");
                            edittext_login_email.requestFocus();
                        }
                    catch (FirebaseAuthInvalidCredentialsException e){
                        edittext_login_email.setError("Invalid credentials. Kindly check and re-enter (email/password)");
                        edittext_login_email.requestFocus();
                    }
                    catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(Login_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    private void showAlertDialog() {
        //setup alert builer
        AlertDialog.Builder builder=new AlertDialog.Builder(Login_activity.this);
        builder.setTitle("email is not Verified");
        builder.setMessage("please verify your email. You cannot login without email verification");

        //open email if user taps continue
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//open email in new window not in our app
                startActivity(intent);


            }
        });

        //create aert dialog
        AlertDialog alertDialog=builder.create();
        //show alert dialog
        alertDialog.show();
    }
//check if already log in if then redirect directly to user profile

    @Override
    protected void onStart() {
        super.onStart();
        if(authProfile.getCurrentUser()!=null){
            Toast.makeText(Login_activity.this, "Already Logged In", Toast.LENGTH_SHORT).show();

            //start user profile activity
            startActivity(new Intent(Login_activity.this,Profilefragment.class));
            finish();//close login activity

        }

        else{
            Toast.makeText(Login_activity.this, "You can log In" , Toast.LENGTH_SHORT).show();
        }
    }
}