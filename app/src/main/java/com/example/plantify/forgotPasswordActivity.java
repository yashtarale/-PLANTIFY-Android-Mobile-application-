package com.example.plantify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class forgotPasswordActivity extends AppCompatActivity {
    private Button button_password_reset;
    private EditText edittext_password_reset_email;
    private ProgressBar progressbar;
    private FirebaseAuth authProfile;
    private static final String TAG="forgotPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().setTitle("Forgot Password");
        edittext_password_reset_email=findViewById(R.id.edittext_password_reset_email);
        progressbar =findViewById(R.id.progressbar);
        button_password_reset=findViewById(R.id.button_password_reset);

        button_password_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=edittext_password_reset_email.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(forgotPasswordActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    edittext_password_reset_email.setError("Email is required");
                    edittext_password_reset_email.requestFocus();
                    
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(forgotPasswordActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                    edittext_password_reset_email.setError("Email is not valid");
                    edittext_password_reset_email.requestFocus();
                    
                }
                else{
                    progressbar.setVisibility(View.VISIBLE);
                    resetPassword(email);
                }
            }
        });
    }

    private void resetPassword(String email) {
        authProfile=FirebaseAuth.getInstance();
        authProfile.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forgotPasswordActivity.this, "Please check your inbox for password reset link", Toast.LENGTH_LONG).show();


                    Intent intent=new Intent(forgotPasswordActivity.this,MainActivity.class);

                    //clear stack to prevent coming back tosame page
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    finish();//close user profile activity
                }else {

                    try{
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidUserException e){
                        edittext_password_reset_email.setError("User does not exist or no longer valid!, Please register again");
                    }
                    catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(forgotPasswordActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(forgotPasswordActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                progressbar.setVisibility(View.GONE);
            }
        });

    }

}