package com.vinayreddy.firangi.ui.lesson_selection;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vinayreddy.firangi.R;
import com.vinayreddy.firangi.misc.LessonAdapter;
import com.vinayreddy.firangi.models.LessonModel;
import com.vinayreddy.firangi.ui.LessonActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class LessonSelectionFragment extends Fragment {

    ListView lessonListView;
    ProgressBar progressBar;
    static List<LessonModel> lessonList = new ArrayList<LessonModel>();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_lesson_selection, container, false);
        lessonListView = root.findViewById(R.id.lesson_selection_listview);
        progressBar = root.findViewById(R.id.lesson_selection_progressbar);

        //Get ActionBar to change the title of the ActionBar
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Beginner");

        new GetData().execute();


        lessonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                LessonModel lesson = (LessonModel) adapterView.getItemAtPosition(i);

                //Open the lesson which the user clicked
                //Pass lessonName as a reference to get lesson content from DB
                Intent intent = new Intent(getActivity().getApplicationContext(), LessonActivity.class);
                intent.putExtra("lessonName", lesson.getLessonName());
                intent.putExtra("lessonNumber", lesson.getsNo());
                startActivity(intent);
            }
        });


        return root;
    }

    private class GetData extends AsyncTask<Void, Void, Void>{
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        LessonAdapter lessonAdapter;


        @Override
        protected Void doInBackground(Void... voids) {
            lessonList.clear();
            db.collection("Courses")
                    .document("Beginner")
                    .collection("Lessons")
                    .orderBy("sNo")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                                    lessonList.add(document.toObject(LessonModel.class));
                                lessonAdapter.notifyDataSetChanged();
                            }
                            if(task.isCanceled())
                                Log.e("onCanceled", task.getException().toString());

                        }
                    });
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            lessonListView.setVisibility(View.GONE);
            progressBar.setEnabled(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            lessonListView.setVisibility(View.VISIBLE);
            progressBar.setEnabled(false);

            lessonAdapter = new LessonAdapter(getContext(), lessonList);
            lessonListView.setAdapter(lessonAdapter);
        }
    }
}