package com.vinayreddy.firangi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vinayreddy.firangi.R;
import com.vinayreddy.firangi.models.UserModel;

public class SignInActivity extends AppCompatActivity {

    TextInputLayout email_til;
    TextInputLayout password_til;
    TextInputEditText email_tie;
    TextInputEditText password_tie;
    Button signin_btn;
    TextView signup_btn;
    TextView forgot_password_btn;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private UserModel instance;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        instance = UserModel.getInstance();

        if(currentUser != null){
            db.collection("Users")
                    .document(currentUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot doc = task.getResult();
                                if(doc.exists()){
                                    instance.setInstance(doc.toObject(UserModel.class));
                                    Intent intent = new Intent(SignInActivity.this, HomeScreenActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        email_til = findViewById(R.id.signin_email_til);
        password_til = findViewById(R.id.signin_password_til);
        email_tie = findViewById(R.id.signin_email_tie);
        password_tie = findViewById(R.id.signin_password_tie);
        signin_btn = findViewById(R.id.signin_btn);
        signup_btn = findViewById(R.id.signin_signup_btn);
        forgot_password_btn = findViewById(R.id.signin_forgot_password_btn);


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email_til.setError(null);
                password_til.setError(null);

                if(email_tie.getText().toString().isEmpty()){
                    email_til.setError("Please enter a valid Email Id!");
                }
                else if(password_tie.getText().toString().isEmpty() || password_tie.getText().toString().length() < 6){
                    email_til.setError(null);
                    password_til.setError("Password must be at least 6 characters long!");
                }
                else{
                    email_til.setError(null);
                    password_til.setError(null);
                    mAuth.signInWithEmailAndPassword(email_tie.getText().toString(), password_tie.getText().toString())
                            .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        db.collection("Users")
                                                .document(user.getUid())
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if(task.isSuccessful()){
                                                            DocumentSnapshot doc = task.getResult();
                                                            if(doc.exists()){
                                                                instance.setInstance(doc.toObject(UserModel.class));
                                                                Intent intent = new Intent(SignInActivity.this, HomeScreenActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        }
                                                    }
                                                });
                                    }
                                    else {
                                        Log.e("Auth Exception", task.getException().toString());
                                        Toast.makeText(SignInActivity.this, "Authentication Failed!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

        forgot_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email_tie.getText().toString().isEmpty()) {
                    email_til.setError("Enter your Email address!");
                }
                else {
                    mAuth.sendPasswordResetEmail(email_tie.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(SignInActivity.this, "Password Reset Email Sent!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }
}