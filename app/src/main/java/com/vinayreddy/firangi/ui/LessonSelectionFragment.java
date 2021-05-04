package com.vinayreddy.firangi.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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


public class LessonSelectionFragment extends Fragment {

    ListView lessonListView;
    ProgressBar progressBar;
    TextView toolbarTitle;
    static List<LessonModel> lessonList = new ArrayList<LessonModel>();
    static List<String> lessonIds = new ArrayList<>();
    private UserModel instance;
    FirebaseFirestore db;
    LessonAdapter lessonAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_lesson_selection, container, false);
        lessonListView = root.findViewById(R.id.lesson_selection_listview);
        progressBar = root.findViewById(R.id.lesson_selection_progressbar);
        toolbarTitle = root.findViewById(R.id.lesson_selection_toolbar_title);

        db = FirebaseFirestore.getInstance();
        instance = UserModel.getInstance();

        //Set the title of the Toolbar
        toolbarTitle.setText(instance.getCurrentLevel());

        //new GetData().execute();
        getData();


        lessonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                LessonModel lesson = (LessonModel) adapterView.getItemAtPosition(i);

                if(lesson.getsNo() <= instance.getCurrentLesson()){
                    if(lesson.getLessonType().equals(LessonModel.LESSON_TYPE_TEST)){
                        Intent intent = new Intent(getActivity().getApplicationContext(), TestActivity.class);
                        intent.putExtra("lessonName", lesson.getLessonName());
                        intent.putExtra("lessonNumber", lesson.getsNo());
                        intent.putExtra("lessonId", lessonIds.get(i));
                        startActivity(intent);
                        getActivity().finish();
                    }
                    else if(lesson.getLessonType().equals(LessonModel.LESSON_TYPE_FINAL_TEST)){
                        Intent intent = new Intent(getActivity().getApplicationContext(), TestActivity.class);
                        intent.putExtra("lessonName", lesson.getLessonName());
                        intent.putExtra("lessonNumber", lesson.getsNo());
                        intent.putExtra("lessonId", lessonIds.get(i));
                        startActivity(intent);
                        getActivity().finish();
                    }
                    else {
                        //Open the lesson which the user clicked
                        //Pass lessonName as a reference to get lesson content from DB
                        Intent intent = new Intent(getActivity().getApplicationContext(), LessonActivity.class);
                        intent.putExtra("lessonName", lesson.getLessonName());
                        intent.putExtra("lessonNumber", lesson.getsNo());
                        intent.putExtra("lessonId", lessonIds.get(i));
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
            }
        });


        return root;
    }

    void getData() {
        lessonList.clear();
        db.collection("Courses")
                .document(instance.getCurrentLevel())
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

                            lessonAdapter = new LessonAdapter(getContext(), lessonList, false);
                            lessonListView.setAdapter(lessonAdapter);
                            lessonAdapter.notifyDataSetChanged();
                        }
                        if(task.isCanceled())
                            Log.e("onCanceled", task.getException().toString());

                    }
                });

    }

}