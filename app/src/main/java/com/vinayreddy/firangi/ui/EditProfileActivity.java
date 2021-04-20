package com.vinayreddy.firangi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vinayreddy.firangi.R;
import com.vinayreddy.firangi.models.UserModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity {

    private static final int SELECT_IMAGE = 0;
    ImageView profileImage;
    ImageButton cameraBtn;
    TextInputLayout usernameTil;
    TextInputEditText usernameTie;
    TextInputEditText emailTie;
    Button saveBtn;

    private UserModel instance;
    Bitmap selectedImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        instance = UserModel.getInstance();

        profileImage = findViewById(R.id.edit_profile_image_view);
        cameraBtn = findViewById(R.id.edit_profile_camera_btn);
        usernameTil = findViewById(R.id.edit_profile_username_til);
        usernameTie = findViewById(R.id.edit_profile_username_tie);
        emailTie = findViewById(R.id.edit_profile_email_tie);
        saveBtn = findViewById(R.id.edit_profile_save_btn);

        usernameTie.setText(instance.getUserName());
        emailTie.setText(instance.getUserEmail());
        Glide.with(this)
                .load(instance.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.portrait_placeholder)
                .into(profileImage);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!usernameTie.getText().toString().isEmpty()){
                    if(!usernameTie.getText().toString().equalsIgnoreCase(instance.getUserName())){
                        instance.setUserName(usernameTie.getText().toString());
                        FirebaseFirestore.getInstance().collection("Users")
                                .document(instance.getUserId())
                                .update("userName", instance.getUserName())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                    }
                    else{
                        Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else {
                    usernameTil.setError("Username cannot be empty!");
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    //selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    //profileImage.setImageBitmap(selectedImageBitmap);

                    Uri file = data.getData();
                    String storagePath = "users/" + instance.getUserId() + "/" + file.getLastPathSegment() + ".jpg";
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(storagePath);

                    UploadTask uploadTask = storageRef.putFile(file);

                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    instance.setImageUrl(uri.toString());
                                    FirebaseFirestore.getInstance().collection("Users")
                                            .document(instance.getUserId())
                                            .update("imageUrl", instance.getImageUrl())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Glide.with(getApplicationContext())
                                                            .load(instance.getImageUrl())
                                                            .centerCrop()
                                                            .placeholder(R.drawable.portrait_placeholder)
                                                            .into(profileImage);
                                                }
                                            });
                                }
                            });
                        }
                    });
                        /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] byteData = baos.toByteArray();

                        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(storagePath);
                        UploadTask uploadTask = storageRef.putBytes(byteData);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> urlTask = storageRef.getDownloadUrl();
                                instance.setImageUrl(urlTask.getResult().getPath());
                            }
                        });
*/
                }
            } else if (resultCode == Activity.RESULT_CANCELED)  {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
        startActivity(intent);
        finish();
    }
}