package com.vinayreddy.firangi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vinayreddy.firangi.R;
import com.vinayreddy.firangi.misc.LessonAdapter;
import com.vinayreddy.firangi.models.LessonModel;
import com.vinayreddy.firangi.models.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BasicsActivity extends AppCompatActivity {

    ListView lessonListView;
    static List<LessonModel> lessonList = new ArrayList<LessonModel>();
    static List<String> lessonIds = new ArrayList<>();
    private UserModel instance;
    FirebaseFirestore db;
    LessonAdapter lessonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basics);

        lessonListView = findViewById(R.id.basics_lesson_selection_listview);

        lessonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                LessonModel lesson = (LessonModel) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(getApplicationContext(), LessonActivity.class);
                intent.putExtra("lessonName", lesson.getLessonName());
                intent.putExtra("lessonNumber", lesson.getsNo());
                intent.putExtra("lessonId", lessonIds.get(i));
                intent.putExtra("isBasics", true);
                startActivity(intent);
                finish();

            }

        });

        getBasicsData();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    void getBasicsData() {
        lessonList.clear();
        FirebaseFirestore.getInstance()
                .collection("Courses")
                .document(UserModel.LEVEL_BASICS)
                .collection("Lessons")
                .orderBy("sNo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                lessonList.add(document.toObject(LessonModel.class));
                                lessonIds.add(document.getId());
                            }

                            lessonAdapter = new LessonAdapter(getApplicationContext(), lessonList, true);
                            lessonListView.setAdapter(lessonAdapter);
                            lessonAdapter.notifyDataSetChanged();
                        }
                        if(task.isCanceled())
                            Log.e("onCanceled", task.getException().toString());

                    }
                });

    }
}