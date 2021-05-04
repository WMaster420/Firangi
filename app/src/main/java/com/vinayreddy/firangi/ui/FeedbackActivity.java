package com.vinayreddy.firangi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;
import com.vinayreddy.firangi.R;
import com.vinayreddy.firangi.models.FeedbackModel;
import com.vinayreddy.firangi.models.UserModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FeedbackActivity extends AppCompatActivity {

    TextView feedback_tv;
    CardView send_btn;
    UserModel instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedback_tv = findViewById(R.id.feedback_tv);
        send_btn = findViewById(R.id.feedback_send_btn);

        instance = UserModel.getInstance();

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!feedback_tv.getText().toString().isEmpty()){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    String currentDateTime = sdf.format(new Date());
                    FeedbackModel feedback = new FeedbackModel(
                            feedback_tv.getText().toString(),
                            instance.getUserId(),
                            instance.getUserName(),
                            currentDateTime);

                    FirebaseFirestore.getInstance().collection("Feedbacks")
                            .add(feedback)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Intent intent = new Intent(FeedbackActivity.this, HomeScreenActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FeedbackActivity.this, HomeScreenActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}