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
import com.google.firebase.firestore.FirebaseFirestore;
import com.vinayreddy.firangi.R;
import com.vinayreddy.firangi.models.UserModel;

public class SignUpActivity extends AppCompatActivity {

    TextInputLayout username_til;
    TextInputLayout email_til;
    TextInputLayout password_til;
    TextInputEditText username_tie;
    TextInputEditText email_tie;
    TextInputEditText password_tie;
    Button signup_btn;
    TextView signin_btn;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private UserModel instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        instance = UserModel.getInstance();

        username_til = findViewById(R.id.signup_username_til);
        email_til = findViewById(R.id.signup_email_til);
        password_til = findViewById(R.id.signup_password_til);
        username_tie = findViewById(R.id.signup_username_tie);
        email_tie = findViewById(R.id.signup_email_tie);
        password_tie = findViewById(R.id.signup_password_tie);
        signup_btn = findViewById(R.id.signup_btn);
        signin_btn = findViewById(R.id.signup_signin_btn);


        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username_til.setError(null);
                email_til.setError(null);
                password_til.setError(null);

                if(username_tie.getText().toString().isEmpty()){
                    username_til.setError("Username cannot be empty!");
                }
                else if(email_tie.getText().toString().isEmpty()){
                    email_til.setError("Please enter a valid Email Id!");
                }
                else if(password_tie.getText().toString().isEmpty() || password_tie.getText().toString().length() < 6){
                    email_til.setError(null);
                    password_til.setError("Password must be at least 6 characters long!");
                }
                else{
                    username_til.setError(null);
                    email_til.setError(null);
                    password_til.setError(null);
                    mAuth.createUserWithEmailAndPassword(email_tie.getText().toString(), password_tie.getText().toString())
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        instance.setUserId(user.getUid());
                                        instance.setUserName(username_tie.getText().toString());
                                        instance.setUserEmail(email_tie.getText().toString());
                                        instance.setCurrentLevel(UserModel.LEVEL_BEGINNER);
                                        instance.setCurrentLesson(1);
                                        instance.setBeginnerCompleted(false);
                                        instance.setIntermediateCompleted(false);
                                        instance.setExpertCompleted(false);
                                        instance.setImageUrl(null);

                                        db.collection("Users")
                                                .document(user.getUid())
                                                .set(new UserModel(
                                                        instance.getUserId(),
                                                        instance.getUserName(),
                                                        instance.getImageUrl(),
                                                        instance.getUserEmail(),
                                                        instance.getCurrentLevel(),
                                                        instance.getCurrentLesson(),
                                                        instance.getBeginnerCompleted(),
                                                        instance.getIntermediateCompleted(),
                                                        instance.getExpertCompleted())
                                                )
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Intent intent = new Intent(SignUpActivity.this, CourseSelectionActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                    }
                                    else {
                                        Log.e("Auth Exception", task.getException().toString());
                                        Toast.makeText(SignUpActivity.this, "Authentication Failed!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

    }
}