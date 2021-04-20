package com.vinayreddy.firangi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vinayreddy.firangi.R;
import com.vinayreddy.firangi.models.TestContentModel;
import com.vinayreddy.firangi.models.UserModel;
import com.vinayreddy.firangi.ui.HomeScreenActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class TestActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static List<TestContentModel> questionList = new ArrayList<TestContentModel>();

    private UserModel instance;

    private int lessonNumber;
    String lessonName;
    String lessonId;
    private int testLength;
    private int currentIndex = 0;
    private String pageIndex;
    private int numOfCorrectQuestions = 0;
    private Boolean isFinalTest = false;

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

    RelativeLayout questionsLayout;
    RelativeLayout resultsLayout;

    TextView questionsAttempted;
    TextView questionsCorrect;
    TextView percentage;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        instance = UserModel.getInstance();

        Intent intentThatStartedThisActivity = getIntent();
        if(intentThatStartedThisActivity.hasExtra("lessonName"))
            lessonName = intentThatStartedThisActivity.getStringExtra("lessonName");
        if(intentThatStartedThisActivity.hasExtra("lessonNumber"))
            lessonNumber = intentThatStartedThisActivity.getIntExtra("lessonNumber", 6);
        if(intentThatStartedThisActivity.hasExtra("lessonId"))
            lessonId = intentThatStartedThisActivity.getStringExtra("lessonId");

        if(lessonName.contains("Course Evaluation Test")){
            isFinalTest = true;
        }

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

        questionsLayout = findViewById(R.id.test_activity_layout);
        resultsLayout = findViewById(R.id.test_activity_result_layout);
        questionsAttempted = findViewById(R.id.ques_attempted_tv);
        questionsCorrect = findViewById(R.id.questions_correct_tv);
        percentage = findViewById(R.id.percentage_tv);
        backBtn = findViewById(R.id.result_back_btn);

        toolbarTitle.setText(lessonName);

        questionsLayout.setVisibility(View.VISIBLE);
        resultsLayout.setVisibility(View.GONE);

        //String testNumber = lessonName.split("Test")[1].trim();

        questionList.clear();
        db.collection("Courses")
                .document(instance.getCurrentLevel())
                .collection("Lessons")
                .document(lessonId)
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
                            Collections.shuffle(questionList);
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

        option1_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EvaluateQuestion(option1_tv, option1_cv);
            }
        });
        option2_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EvaluateQuestion(option2_tv, option2_cv);
            }
        });
        option3_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EvaluateQuestion(option3_tv, option3_cv);
            }
        });
        option4_cv.setOnClickListener(new View.OnClickListener() {
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
                else if(currentIndex == questionList.size() - 1){
                    ShowResults();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(instance.getCurrentLesson() < lessonNumber + 1) {
                    instance.setCurrentLesson(lessonNumber + 1);
                    if(isFinalTest) {
                        if(instance.getCurrentLevel().equals(UserModel.LEVEL_BEGINNER)){
                            instance.setBeginnerCompleted(true);
                        }else if(instance.getCurrentLevel().equals(UserModel.LEVEL_INTERMEDIATE)) {
                            instance.setIntermediateCompleted(true);
                        }else if(instance.getCurrentLevel().equals(UserModel.LEVEL_EXPERT)) {
                            instance.setExpertCompleted(true);
                        }
                        db.collection("Users")
                                .document(instance.getUserId())
                                .update("currentLesson", instance.getCurrentLesson(),
                                        "beginnerCompleted", instance.getBeginnerCompleted(),
                                        "intermediateCompleted", instance.getIntermediateCompleted(),
                                        "expertCompleted", instance.getExpertCompleted())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                    } else {
                        db.collection("Users")
                                .document(instance.getUserId())
                                .update("currentLesson", instance.getCurrentLesson())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                    }
                }
                Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
        startActivity(intent);
        finish();
    }

    private void ShowResults() {
        questionsLayout.setVisibility(View.GONE);
        resultsLayout.setVisibility(View.VISIBLE);

        double percent = (double) numOfCorrectQuestions / testLength;
        percent *= 100;
        DecimalFormat precision = new DecimalFormat("0.00");
        String perc = (precision.format(percent)) + "%";

        questionsAttempted.setText(String.valueOf(testLength));
        questionsCorrect.setText(String.valueOf(numOfCorrectQuestions));
        percentage.setText(perc);
    }

    private void DisplayData(){
        Random r = new Random();
        int random = r.nextInt(4);

        question_tv.setText(questionList.get(currentIndex).getQuestion());
        pageIndex = currentIndex + 1 + "/" + testLength;
        pageNumber.setText(pageIndex);

        switch(random){
            case 1:
                option1_tv.setText(questionList.get(currentIndex).getCorrectAnswer());
                option2_tv.setText(questionList.get(currentIndex).getWrongAnswer1());
                option3_tv.setText(questionList.get(currentIndex).getWrongAnswer2());
                option4_tv.setText(questionList.get(currentIndex).getWrongAnswer3());
                break;
            case 2:
                option1_tv.setText(questionList.get(currentIndex).getWrongAnswer1());
                option2_tv.setText(questionList.get(currentIndex).getCorrectAnswer());
                option3_tv.setText(questionList.get(currentIndex).getWrongAnswer2());
                option4_tv.setText(questionList.get(currentIndex).getWrongAnswer3());
                break;
            case 3:
                option1_tv.setText(questionList.get(currentIndex).getWrongAnswer2());
                option2_tv.setText(questionList.get(currentIndex).getWrongAnswer1());
                option3_tv.setText(questionList.get(currentIndex).getCorrectAnswer());
                option4_tv.setText(questionList.get(currentIndex).getWrongAnswer3());
                break;
            case 4:
                option1_tv.setText(questionList.get(currentIndex).getWrongAnswer3());
                option2_tv.setText(questionList.get(currentIndex).getWrongAnswer1());
                option3_tv.setText(questionList.get(currentIndex).getWrongAnswer2());
                option4_tv.setText(questionList.get(currentIndex).getCorrectAnswer());
                break;
            default:
                option1_tv.setText(questionList.get(currentIndex).getCorrectAnswer());
                option2_tv.setText(questionList.get(currentIndex).getWrongAnswer1());
                option3_tv.setText(questionList.get(currentIndex).getWrongAnswer2());
                option4_tv.setText(questionList.get(currentIndex).getWrongAnswer3());
                break;
        }
    }

    private void EvaluateQuestion(TextView tv, CardView cv){
        if(tv.getText().toString().equals(questionList.get(currentIndex).getCorrectAnswer())){
            cv.setCardBackgroundColor(getResources().getColor(R.color.correctans));
            numOfCorrectQuestions++;
        }
        else{
            cv.setCardBackgroundColor(getResources().getColor(R.color.wrongans));
        }

        option1_cv.setClickable(false);
        option1_cv.setFocusable(false);
        option2_cv.setClickable(false);
        option2_cv.setFocusable(false);
        option3_cv.setClickable(false);
        option3_cv.setFocusable(false);
        option4_cv.setClickable(false);
        option4_cv.setFocusable(false);
    }

    private void NextQuestion(){
        option1_cv.setCardBackgroundColor(getResources().getColor(R.color.white));
        option2_cv.setCardBackgroundColor(getResources().getColor(R.color.white));
        option3_cv.setCardBackgroundColor(getResources().getColor(R.color.white));
        option4_cv.setCardBackgroundColor(getResources().getColor(R.color.white));

        option1_cv.setClickable(true);
        option1_cv.setFocusable(true);
        option2_cv.setClickable(true);
        option2_cv.setFocusable(true);
        option3_cv.setClickable(true);
        option3_cv.setFocusable(true);
        option4_cv.setClickable(true);
        option4_cv.setFocusable(true);
        DisplayData();
    }
}