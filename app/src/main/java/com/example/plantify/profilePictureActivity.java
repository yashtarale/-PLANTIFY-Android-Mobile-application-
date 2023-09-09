package com.example.plantify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class profilePictureActivity extends AppCompatActivity {
private ProgressBar progressBar;
private ImageView imageview_profile_picture;
private FirebaseAuth authProfile;
private StorageReference storageRefrence;
private FirebaseUser firebaseuser;
private static final int PICK_IMAGE_REQUEST=1;
private Uri uriImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);

        getSupportActionBar().setTitle("User profile Activity");

        Button upload_pic_choose_button=findViewById(R.id.upload_pic_choose_button);
        Button upload_pic_button=findViewById(R.id.upload_pic_button);
        progressBar=findViewById(R.id.progressbar);
        imageview_profile_picture=findViewById(R.id.imageview_profile_picture);

        authProfile=FirebaseAuth.getInstance();
        firebaseuser=authProfile.getCurrentUser();

        storageRefrence= FirebaseStorage.getInstance().getReference("DisplayPics");
        Uri uri=firebaseuser.getPhotoUrl();
        //set users current dp in iageview(if uploaded already)
        //we will use  picasso library
        Picasso.with(profilePictureActivity.this).load(uri).into(imageview_profile_picture);


        //choose the image from gallery
        upload_pic_choose_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileManager();
            }
        });

        //upload the picture now
        upload_pic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                UploadPic();
            }
        });

    }

    private void UploadPic() {
        if(uriImage!=null){
            //save image with usder uid
            StorageReference fileRefrence=storageRefrence.child(authProfile.getCurrentUser().getUid()+"."
            +getFileExtension(uriImage));

            //upload img to storage
            fileRefrence.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRefrence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUri=uri;
                            firebaseuser=authProfile.getCurrentUser();

                            //finallyset the display image of user after upload
                            UserProfileChangeRequest profileUpdates=new UserProfileChangeRequest.Builder().setPhotoUri(downloadUri).build();
                            firebaseuser.updateProfile(profileUpdates);

                        }
                    });progressBar.setVisibility(View.GONE);
                    Toast.makeText(profilePictureActivity.this, "Upload Succesful", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(profilePictureActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(profilePictureActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }
        else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(profilePictureActivity.this, "No file selected", Toast.LENGTH_SHORT).show();

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }

    private void openFileManager() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!= null && data.getData()!=null ){
            uriImage=data.getData();
            imageview_profile_picture.setImageURI(uriImage);
        }

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
         Intent intent=new Intent(profilePictureActivity.this,UpdateProfileActivity.class);
         startActivity(intent);
        // finish();
         }
         else if (id==R.id.menu_update_email) {
         Intent intent=new Intent(profilePictureActivity.this,updateEmail.class);
         startActivity(intent);
       //  finish();
         }
         else if (id==R.id.menu_settings) {
         Toast.makeText(profilePictureActivity.this, "Clicked on Settings", Toast.LENGTH_SHORT).show();
         }
         else if (id==R.id.menu_change_password) {
         Intent intent=new Intent(profilePictureActivity.this,updatePassword.class);
         startActivity(intent);
        // finish();
         }
         else if (id==R.id.menu_delete_profile) {
         Intent intent=new Intent(profilePictureActivity.this,deleteProile.class);
         startActivity(intent);
          //  finish();
         }
        else if (id==R.id.menu_logout) {
            authProfile.signOut();
            Toast.makeText(profilePictureActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(profilePictureActivity.this,MainActivity.class);

            //clear stack to prevent coming back tosame page
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();//close user profile activity

        }
        else{
            Toast.makeText(profilePictureActivity.this, "something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}