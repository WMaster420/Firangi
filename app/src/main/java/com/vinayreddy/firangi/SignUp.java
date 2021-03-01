package com.vinayreddy.firangi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private EditText name,phoneNumber,eMail,password;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        name = (EditText)findViewById(R.id.etName);
        phoneNumber = (EditText)findViewById(R.id.etPhoneNo);
        eMail = (EditText)findViewById(R.id.etEmail);
        password = (EditText)findViewById(R.id.etPassword);
        signUp = (Button)findViewById(R.id.btSubmit);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ModelUser user = new ModelUser();
                user.Name = name.getText().toString();
                user.Email = eMail.getText().toString();
                user.Password = password.getText().toString();
                user.PhoneNumber = phoneNumber.getText().toString();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference users = databaseReference.child("Users");
                String newUserKey = users.push().getKey();
                user.id = newUserKey;
                databaseReference.child("Users").child(newUserKey).setValue(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SignUp.this, "Account created successfully", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUp.this, "Failed to create new account", Toast.LENGTH_SHORT).show();
                            }


                        });
                 }
            });
        }



    }