package com.vinayreddy.firangi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vinayreddy.firangi.R;
import com.vinayreddy.firangi.models.LessonContentModel;
import com.vinayreddy.firangi.models.LessonModel;
import com.vinayreddy.firangi.models.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LessonActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private UserModel instance;

    List<LessonContentModel> contentList = new ArrayList<>();
    private int lessonNumber;
    private String lessonId;
    private String lessonName;
    private int lessonLength;
    private int currentIndex = 0;
    private String pageIndex;

    RelativeLayout activityLayout;
    ProgressBar progressBar;
    TextView toolbarTitle;
    TextView frenchWord;
    TextView englishWord;
    TextView frenchSentence;
    TextView englishSentence;
    TextView pageNumber;
    ImageButton previousBtn;
    ImageButton nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        instance = UserModel.getInstance();

        activityLayout = findViewById(R.id.lesson_activity_layout);
        progressBar = findViewById(R.id.lesson_activity_progressbar);
        toolbarTitle = findViewById(R.id.lesson_activity_toolbar_title);
        frenchWord = findViewById(R.id.lesson_activity_french_word_tv);
        englishWord = findViewById(R.id.lesson_activity_english_word_tv);
        frenchSentence = findViewById(R.id.lesson_activity_french_sentence_tv);
        englishSentence = findViewById(R.id.lesson_activity_english_sentence_tv);
        pageNumber = findViewById(R.id.lesson_activity_page_number_tv);
        previousBtn = findViewById(R.id.lesson_activity_previous_button);
        nextBtn = findViewById(R.id.lesson_activity_next_button);

        Intent intentThatStartedThisActivity = getIntent();
        if(intentThatStartedThisActivity.hasExtra("lessonNumber"))
            lessonNumber = intentThatStartedThisActivity.getIntExtra("lessonNumber", 1);
        if(intentThatStartedThisActivity.hasExtra("lessonName"))
            lessonName = intentThatStartedThisActivity.getStringExtra("lessonName");
        if(intentThatStartedThisActivity.hasExtra("lessonId"))
            lessonId = intentThatStartedThisActivity.getStringExtra("lessonId");

        toolbarTitle.setText(lessonName);

        progressBar.setVisibility(View.VISIBLE);
        activityLayout.setVisibility(View.GONE);
        progressBar.setEnabled(true);

        contentList.clear();
        db.collection("Courses")
                .document(instance.getCurrentLevel())
                .collection("Lessons")
                .document(lessonId)
                .collection("Content")
                .orderBy("sNo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                contentList.add(document.toObject(LessonContentModel.class));
                            }
                            lessonLength = contentList.size();
                            progressBar.setVisibility(View.GONE);
                            activityLayout.setVisibility(View.VISIBLE);
                            progressBar.setEnabled(false);
                            DisplayData();
                            //LessonActivity.this.notifyAll();//
                        } else {
                            Log.e("Error: ", task.getException().toString());
                        }
                    }
                });
        /*db.collection("Users")
                .document(instance.getCurrentLevel())
                .collection("Lessons")
                .document(lessonId)
                .collection("Content")
                .orderBy("sNo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                contentList.add(document.toObject(LessonContentModel.class));
                            }
                            lessonLength = contentList.size();
                            progressBar.setVisibility(View.GONE);
                            activityLayout.setVisibility(View.VISIBLE);
                            progressBar.setEnabled(false);
                            DisplayData();
                            //LessonActivity.this.notifyAll();//
                        } else {
                            Log.e("Error: ", task.getException().toString());
                        }
                    }
                });*/





        /*db.collection("Courses")
                .document(instance.getCurrentLevel())
                .collection("Lessons")
                .document("Lesson_" + lessonNumber)
                .collection("Content")
                .orderBy("sNo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                contentList.add(document.toObject(LessonContentModel.class));
                            }
                            lessonLength = contentList.size();
                            progressBar.setVisibility(View.GONE);
                            activityLayout.setVisibility(View.VISIBLE);
                            progressBar.setEnabled(false);
                            DisplayData();
                            //LessonActivity.this.notifyAll();//
                        } else {
                            Log.e("Error: ", task.getException().toString());
                        }
                    }
                });*/


        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentIndex > 0){
                    currentIndex--;
                    DisplayData();
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentIndex < contentList.size() - 1){
                    currentIndex++;
                    DisplayData();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(instance.getCurrentLesson() < lessonNumber + 1) {
            instance.setCurrentLesson(lessonNumber + 1);
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

    private void DisplayData(){
        frenchWord.setText(contentList.get(currentIndex).getFrenchWord());
        englishWord.setText(contentList.get(currentIndex).getEnglishWord());
        frenchSentence.setText(contentList.get(currentIndex).getFrenchSentence());
        englishSentence.setText(contentList.get(currentIndex).getEnglishSentence());
        pageIndex = currentIndex + 1 + "/" + lessonLength;
        pageNumber.setText(pageIndex);
    }
}