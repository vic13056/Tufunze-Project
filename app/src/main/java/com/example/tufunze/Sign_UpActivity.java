package com.example.tufunze;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Sign_UpActivity extends AppCompatActivity {
    TextView dont;
    Button signup;
    EditText mfullname,memail,mpassword;
    FirebaseAuth fauth;
    CircleImageView imageView;
    private ProgressDialog dialog;
    static int PregCode = 1;
    static int REQUESCODE = 1;
    Uri pickedimg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        dont = findViewById(R.id.tvdonthave);
        signup = findViewById(R.id.btnsignup);
        mfullname = findViewById(R.id.edtfullname);
        memail = findViewById(R.id.etdemail);
        mpassword = findViewById(R.id.edtpassword);
        imageView = findViewById(R.id.user_photo);


        dialog = new ProgressDialog(this);
        dialog.setTitle("Saving");
        dialog.setMessage("Please Wait...");


        fauth = FirebaseAuth.getInstance();

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String email = memail.getText().toString().trim();
                    String password = mpassword.getText().toString().trim();
                    final String name = mfullname.getText().toString().trim();

                    if (email.isEmpty()){
                        memail.setError("Enter Email");
                        return;
                    }
                    if (password.isEmpty()){
                        mpassword.setError("Enter Password");
                        return;
                    }
                    if (name.isEmpty()){
                        mfullname.setError("Enter Fullnames");
                        return;
                    }

                    long time = System.currentTimeMillis();
                    String timeconv = String.valueOf(time);
                    fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Sign_UpActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),Home.class));
                                finish();

                                //after we create the user we need to update his profile picture and name
                                updateUserInfo(name,pickedimg,fauth.getCurrentUser());




                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Sign_UpActivity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            dont.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >22){

                        checkAndRequestForPermission ();
                    }
                    else {
                        openGallery();
                    }

                }
            });
    }
                //update user photo and name
    private void updateUserInfo(final String name, Uri pickedimg, final FirebaseUser currentUser) {

        //first we start by uploading user photo to firebase storage and ger url

        StorageReference mstorage = FirebaseStorage.getInstance().getReference().child("users_photo");
        final StorageReference imagefilepath = mstorage.child(pickedimg.getLastPathSegment());
        imagefilepath.putFile(pickedimg).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                //image added successfully
                //now we can get our image url

                imagefilepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                       //uri contain user image url
                        UserProfileChangeRequest profileupdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();


                        currentUser.updateProfile(profileupdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            //user info updated sucessfully
                                            Toast.makeText(Sign_UpActivity.this, "Register Complete", Toast.LENGTH_SHORT).show();
                                            updateUI();
                                        }
                                    }
                                });
                    }
                });
            }
        });

    }

    private void updateUI() {

        startActivity(new Intent(getApplicationContext(),Home.class));
        finish();
    }

    private void openGallery() {
        //TODO open gallery intent and wait for the user to pick an image

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    private void checkAndRequestForPermission() {
                if (ContextCompat.checkSelfPermission(Sign_UpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                !=PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Sign_UpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                        Toast.makeText(this, "Please accept for the required permission", Toast.LENGTH_SHORT).show();
                    }else {
                        ActivityCompat.requestPermissions(Sign_UpActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                                                                        PregCode);

                    }

                }
                else {
                    openGallery();
                }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null){
            //the user has sucessfully picked an image
            //we need its reference to a Uri Varaible

            pickedimg= data.getData();
            imageView.setImageURI(pickedimg);
        }
    }
}
