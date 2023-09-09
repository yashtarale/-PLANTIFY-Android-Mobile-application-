package com.example.plantify;

import android.animation.LayoutTransition;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profilefragment extends Fragment{
    private Button button_prof_login;
    private Button button_prof_register;
    private Button button_join_community;
    private TextView textview_share_app,textview_givefeedback;
    private ImageView _imageView_profile_dp;
    private ProgressBar _progressbar;
    RelativeLayout _layout;
    LinearLayout LoginRegisterButtons;

    CardView _profile_cardviewExpad;
    private TextView textview_show_welcome,textView_show_full_name,textView_show_email,textView_show_dob,textView_show_gender,textView_show_mobile;

    private String fullname,email,dob,gender,mobile;

    private FirebaseAuth authProfile;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profilefragment, container, false);

        button_join_community=(Button)view.findViewById(R.id.button_join_community);
        textview_givefeedback=(TextView)view.findViewById(R.id.textview_givefeedback);
        textview_share_app=(TextView)view.findViewById(R.id.textview_share_app);

        Button button_prof_register=(Button)view.findViewById(R.id.button_prof_register);
        Button button_prof_login = (Button) view.findViewById(R.id.button_prof_login);
        _progressbar=(ProgressBar) view.findViewById(R.id.progressbar);

        textview_show_welcome=(TextView)view.findViewById(R.id.textview_show_welcome);
        textView_show_full_name=(TextView)view.findViewById(R.id.textView_show_full_name);
        textView_show_email=(TextView)view.findViewById(R.id.textView_show_email);
        textView_show_dob=(TextView)view.findViewById(R.id.textView_show_dob);
        textView_show_gender=(TextView)view.findViewById(R.id.textView_show_gender);
        textView_show_mobile=(TextView)view.findViewById(R.id.textView_show_mobile);

        _layout=(RelativeLayout) view.findViewById(R.id._layout);
        _layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        LoginRegisterButtons=(LinearLayout)view.findViewById(R.id.LoginRegisterButtons);

        _profile_cardviewExpad=(CardView)view.findViewById(R.id._profile_cardviewexpand);
        _imageView_profile_dp=(ImageView)view.findViewById(R.id._imageView_profile_dp);
        _imageView_profile_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profilefragment.this.getActivity(),profilePictureActivity.class);
                startActivity(intent);
            }
        });

        textview_share_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profilefragment.this.getActivity(),available_soon.class);
                startActivity(intent);
            }
        });
        textview_givefeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profilefragment.this.getActivity(),available_soon.class);
                startActivity(intent);
            }
        });

        button_join_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profilefragment.this.getActivity(),available_soon.class);
                startActivity(intent);
            }
        });


        authProfile=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=authProfile.getCurrentUser();

        if(firebaseUser!=null){

            checkIfEmailisVerified(firebaseUser);
            _progressbar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }

        //for show and hide data on login or logged out user

        if(firebaseUser !=null) {
            LoginRegisterButtons.setVisibility(View.GONE);
            setHasOptionsMenu(true);//to call three dot menu

        } else {


            button_prof_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Profilefragment.this.getActivity(), Login_activity.class);
                    startActivity(intent);
                }
            });
            button_prof_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Profilefragment.this.getActivity(), Register_activity.class);
                    startActivity(intent);
                }
            });

            _profile_cardviewExpad.setVisibility(View.GONE);




        }

        return view;

    }

    /**  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profilefragment, container, false);
    }**/


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

                    Picasso.with(Profilefragment.this.getActivity()).load(uri).into(_imageView_profile_dp);
                }
                else{
                    _imageView_profile_dp.setImageResource(R.drawable.profilepicture);
                    //Toast.makeText(UserProfileActivity.this, "no Profile picture", Toast.LENGTH_SHORT).show();
                }
                _progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profilefragment.this.getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                _progressbar.setVisibility(View.GONE);
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
        AlertDialog.Builder builder=new AlertDialog.Builder(Profilefragment.this.getActivity());
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


    //three dot menu top bar


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu,menu);//menu is var of this "oncreate option ment"
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.menu_refresh){
            startActivity(getActivity().getIntent());
            getActivity().finish();
            getActivity().overridePendingTransition(0,0);
            Toast.makeText(Profilefragment.this.getActivity(), "Refreshing...", Toast.LENGTH_SHORT).show();
        }
        else if (id==R.id.menu_update_profile) {
            Intent intent=new Intent(Profilefragment.this.getActivity(),UpdateProfileActivity.class);
            startActivity(intent);
            //finish();
        }
        else if (id==R.id.menu_update_email) {
            Intent intent=new Intent(Profilefragment.this.getActivity(),updateEmail.class);
            startActivity(intent);
            // finish();
        }
        else if (id==R.id.menu_settings) {
            Toast.makeText(Profilefragment.this.getActivity(), "Clicked on Settings", Toast.LENGTH_SHORT).show();
        }
        else if (id==R.id.menu_change_password) {
            Intent intent=new Intent(Profilefragment.this.getActivity(),updatePassword.class);
            startActivity(intent);
            //finish();
        }
        else if (id==R.id.menu_delete_profile) {
            Intent intent=new Intent(Profilefragment.this.getActivity(),deleteProile.class);
            startActivity(intent);
            // finish();
        }
        else if (id==R.id.menu_logout) {
            authProfile.signOut();
            Toast.makeText(Profilefragment.this.getActivity(), "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Profilefragment.this.getActivity(),MainActivity.class);

            //clear stack to prevent coming back tosame page
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //getActivity().finish();//close user profile activity

        }
        else{
            Toast.makeText(Profilefragment.this.getActivity(), "something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void expand_now(View view) {
        switch (view.getId()) {
            case R.id._profile_cardviewexpand:
                int v = (_layout.getVisibility() == view.GONE) ? View.VISIBLE : View.GONE;
                TransitionManager.beginDelayedTransition(_layout, new AutoTransition());

                _layout.setVisibility(v);
        }
    }


}
