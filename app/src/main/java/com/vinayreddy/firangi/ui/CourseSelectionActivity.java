package com.vinayreddy.firangi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vinayreddy.firangi.R;
import com.vinayreddy.firangi.models.UserModel;

public class CourseSelectionActivity extends AppCompatActivity {

    ImageButton saveBtn;
    ImageButton cancelBtn;
    CardView beginnerCV;
    CardView intermediateCV;
    CardView expertCV;

    private UserModel instance;
    String newLevel = "";
    Boolean beginnerCompleted = false;
    Boolean intermediateCompleted = false;
    Boolean expertCompleted = false;
    int optionSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selection);

        saveBtn = findViewById(R.id.course_selection_save_btn);
        cancelBtn = findViewById(R.id.course_selection_cancel_btn);
        beginnerCV = findViewById(R.id.course_selection_beginner_cv);
        intermediateCV = findViewById(R.id.course_selection_intermediate_cv);
        expertCV = findViewById(R.id.course_selection_expert_cv);

        instance = UserModel.getInstance();

        beginnerCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newLevel = UserModel.LEVEL_BEGINNER;
                beginnerCV.setCardBackgroundColor(getResources().getColor(R.color.correctans));
                intermediateCV.setCardBackgroundColor(getResources().getColor(R.color.white));
                expertCV.setCardBackgroundColor(getResources().getColor(R.color.white));
                beginnerCompleted = false;
                optionSelected = 1;
            }
        });

        intermediateCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newLevel = UserModel.LEVEL_INTERMEDIATE;
                intermediateCV.setCardBackgroundColor(getResources().getColor(R.color.correctans));
                beginnerCV.setCardBackgroundColor(getResources().getColor(R.color.white));
                expertCV.setCardBackgroundColor(getResources().getColor(R.color.white));
                intermediateCompleted = false;
                optionSelected = 2;
            }
        });

        expertCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newLevel = UserModel.LEVEL_EXPERT;
                expertCV.setCardBackgroundColor(getResources().getColor(R.color.correctans));
                beginnerCV.setCardBackgroundColor(getResources().getColor(R.color.white));
                intermediateCV.setCardBackgroundColor(getResources().getColor(R.color.white));
                expertCompleted = false;
                optionSelected = 3;
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instance.setCurrentLevel(newLevel);
                instance.setCurrentLesson(1);
                if(optionSelected == 1)
                    instance.setBeginnerCompleted(beginnerCompleted);
                else if(optionSelected == 2)
                    instance.setIntermediateCompleted(intermediateCompleted);
                else
                    instance.setExpertCompleted(expertCompleted);

                FirebaseFirestore.getInstance().collection("Users")
                        .document(instance.getUserId())
                        .update("currentLevel", instance.getCurrentLevel(),
                                "currentLesson", 1,
                                "beginnerCompleted", instance.getBeginnerCompleted(),
                                "intermediateCompleted", instance.getIntermediateCompleted(),
                                "expertCompleted", instance.getExpertCompleted())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}