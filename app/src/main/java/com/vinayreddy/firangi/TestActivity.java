package com.vinayreddy.firangi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vinayreddy.firangi.models.TestContentModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static List<TestContentModel> questionList = new ArrayList<TestContentModel>();

    String lessonName;
    private int testLength;
    private int currentIndex = 0;
    private String pageIndex;
    
    TextView question_tv;
    TextView option1_tv;
    TextView option2_tv;
    TextView option3_tv;
    TextView option4_tv;
    CardView option1_cv;
    CardView option2_cv;
    CardView option3_cv;
    CardView option4_cv;
    TextView pageNumber;
    ImageButton nextBtn;
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intentThatStartedThisActivity = getIntent();
        if(intentThatStartedThisActivity.hasExtra("lessonName"))
            lessonName = intentThatStartedThisActivity.getStringExtra("lessonName");
        
        question_tv = findViewById(R.id.test_activity_question_tv);
        option1_tv = findViewById(R.id.test_activity_option1_tv);
        option2_tv = findViewById(R.id.test_activity_option2_tv);
        option3_tv = findViewById(R.id.test_activity_option3_tv);
        option4_tv = findViewById(R.id.test_activity_option4_tv);
        option1_cv = findViewById(R.id.test_activity_option1_cv);
        option2_cv = findViewById(R.id.test_activity_option2_cv);
        option3_cv = findViewById(R.id.test_activity_option3_cv);
        option4_cv = findViewById(R.id.test_activity_option4_cv);
        pageNumber = findViewById(R.id.test_activity_page_number_tv);
        nextBtn = findViewById(R.id.test_activity_next_button);
        toolbarTitle = findViewById(R.id.test_activity_toolbar_title);

        toolbarTitle.setText(lessonName);

        String testNumber = lessonName.split("Test")[1].trim();

        questionList.clear();
        db.collection("Courses")
                .document("Beginner")
                .collection("Lessons")
                .document("KnowledgeTest_" + testNumber)
                .collection("Content")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                questionList.add(document.toObject(TestContentModel.class));
                            }
                            testLength = questionList.size();
                            DisplayData();
                            /*progressBar.setVisibility(View.GONE);
                            activityLayout.setVisibility(View.VISIBLE);
                            progressBar.setEnabled(false);*/
                            //LessonActivity.this.notifyAll();//
                        } else {
                            Log.e("Error: ", task.getException().toString());
                        }
                    }
                });
        
        option1_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EvaluateQuestion(option1_tv, option1_cv);
            }
        });
        option2_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EvaluateQuestion(option2_tv, option2_cv);
            }
        });
        option3_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EvaluateQuestion(option3_tv, option3_cv);
            }
        });
        option4_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EvaluateQuestion(option4_tv, option4_cv);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentIndex < questionList.size() - 1){
                    currentIndex++;
                    NextQuestion();
                }
            }
        });


    }

    private void DisplayData(){
        question_tv.setText(questionList.get(currentIndex).getQuestion());
        option1_tv.setText(questionList.get(currentIndex).getCorrectAnswer());
        option2_tv.setText(questionList.get(currentIndex).getWrongAnswer1());
        option3_tv.setText(questionList.get(currentIndex).getWrongAnswer2());
        option4_tv.setText(questionList.get(currentIndex).getWrongAnswer3());
        pageIndex = currentIndex + 1 + "/" + testLength;
        pageNumber.setText(pageIndex);
        
        /*option1_tv.setBackgroundColor(getResources().getColor(R.color.white));
        option2_tv.setBackgroundColor(getResources().getColor(R.color.white));
        option3_tv.setBackgroundColor(getResources().getColor(R.color.white));
        option4_tv.setBackgroundColor(getResources().getColor(R.color.white));;*/
    }
    
    private void EvaluateQuestion(TextView tv, CardView cv){
        if(tv.getText().toString().equals(questionList.get(currentIndex).getCorrectAnswer())){
            cv.setCardBackgroundColor(getResources().getColor(R.color.correctans));
        }
        else{
            cv.setCardBackgroundColor(getResources().getColor(R.color.wrongans));
        }
        
        option1_tv.setClickable(false);
        option1_tv.setFocusable(false);
        option2_tv.setClickable(false);
        option2_tv.setFocusable(false);
        option3_tv.setClickable(false);
        option3_tv.setFocusable(false);
        option4_tv.setClickable(false);
        option4_tv.setFocusable(false);
    }

    private void NextQuestion(){
        option1_cv.setCardBackgroundColor(getResources().getColor(R.color.white));
        option2_cv.setCardBackgroundColor(getResources().getColor(R.color.white));
        option3_cv.setCardBackgroundColor(getResources().getColor(R.color.white));
        option4_cv.setCardBackgroundColor(getResources().getColor(R.color.white));

        option1_tv.setClickable(true);
        option1_tv.setFocusable(true);
        option2_tv.setClickable(true);
        option2_tv.setFocusable(true);
        option3_tv.setClickable(true);
        option3_tv.setFocusable(true);
        option4_tv.setClickable(true);
        option4_tv.setFocusable(true);
        DisplayData();
    }
}