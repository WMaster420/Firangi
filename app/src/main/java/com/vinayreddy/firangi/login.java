package com.vinayreddy.firangi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    EditText email,password;
    Button signin;
    FirebaseAuth mfirebaseauth;
    private FirebaseAuth.AuthStateListener mauthstatelistner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mfirebaseauth=FirebaseAuth.getInstance();
        email=findViewById(R.id.mail);
        password=findViewById(R.id.pass);
        signin=findViewById(R.id.btn);

        mauthstatelistner=new FirebaseAuth.AuthStateListener() {

            final FirebaseUser mfirebaseuser = mfirebaseauth.getCurrentUser();


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mfirebaseuser != null) {
                    Toast.makeText(login.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(login.this,HomeScreenActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(login.this,"Please login",Toast.LENGTH_SHORT).show();
                }
            }
        };
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eml=email.getText().toString();
                String pwd=password.getText().toString();
                if(eml.isEmpty())
                {
                    email.setError("Please enter email ID");
                    email.requestFocus();
                }
                else if(pwd.isEmpty())
                {
                    password.setError("Enter valid password");
                    password.requestFocus();
                }
                else if(eml.isEmpty() && pwd.isEmpty())
                {
                    Toast.makeText(login.this,"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else if(!eml.isEmpty() && !pwd.isEmpty())
                {
                    mfirebaseauth.signInWithEmailAndPassword(eml,pwd).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(login.this,"Error occured,Please Login again",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Intent home=new Intent(login.this,HomeScreenActivity.class);
                                startActivity(home);
                            }
                        }

                    });
                }
                else
                {
                    Toast.makeText(login.this,"Error Occured",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mfirebaseauth.addAuthStateListener(mauthstatelistner);
    }
}