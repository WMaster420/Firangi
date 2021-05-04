package com.vinayreddy.firangi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vinayreddy.firangi.R;
import com.vinayreddy.firangi.misc.LeaderboardAdapter;
import com.vinayreddy.firangi.misc.LessonAdapter;
import com.vinayreddy.firangi.models.LessonModel;
import com.vinayreddy.firangi.models.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LeaderboardsActivity extends AppCompatActivity {

    ListView listview;
    UserModel instance;
    List<UserModel> participants = new ArrayList<UserModel>();
    LeaderboardAdapter leaderboardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        listview = findViewById(R.id.leaderboards_listview);

        instance = UserModel.getInstance();

        FirebaseFirestore.getInstance().collection("Tournaments")
                .document("Tournament 1")
                .collection("Participants")
                .orderBy("tournamentScore", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                participants.add(document.toObject(UserModel.class));
                            }

                            leaderboardAdapter = new LeaderboardAdapter(getApplicationContext(), participants);
                            listview.setAdapter(leaderboardAdapter);
                            leaderboardAdapter.notifyDataSetChanged();
                        }
                        if(task.isCanceled())
                            Log.e("onCanceled", task.getException().toString());

                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}