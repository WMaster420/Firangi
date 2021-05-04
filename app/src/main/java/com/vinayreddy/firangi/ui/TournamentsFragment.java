package com.vinayreddy.firangi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vinayreddy.firangi.R;
import com.vinayreddy.firangi.models.TournamentModel;
import com.vinayreddy.firangi.models.UserModel;

import java.util.Calendar;
import java.util.Objects;

//import com.vinayreddy.firangi.screens.R;

public class TournamentsFragment extends Fragment {

    TextView myScore;
    TextView status;
    TextView timeLeft;
    CardView participateBtn;
    CardView scoreBtn;

    long milliSecondsLeft;
    Boolean isActive = false;

    TournamentModel tournament;

    private UserModel instance;
    FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tournaments, container, false);

        db = FirebaseFirestore.getInstance();
        instance = UserModel.getInstance();

        myScore = root.findViewById(R.id.tournaments_rank_tv);
        status = root.findViewById(R.id.tournaments_status_tv);
        timeLeft = root.findViewById(R.id.tournaments_time_tv);
        participateBtn = root.findViewById(R.id.tournaments_participate_btn);
        scoreBtn = root.findViewById(R.id.tournaments_rank_cv);

        String score = "Your Score: " + instance.getTournamentScore();
        myScore.setText(score);

        getData();

        scoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), LeaderboardsActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        participateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isActive && milliSecondsLeft > 0){
                    Intent intent = new Intent(getActivity().getApplicationContext(), TestActivity.class);
                    intent.putExtra("isTournament", true);
                    startActivity(intent);
                    getActivity().finish();
                }
                else {
                    Toast.makeText(getContext(), "Tournament Unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    void getData() {
        db.collection("Tournaments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                tournament = document.toObject(TournamentModel.class);
                                status.setText((tournament.getStatus()));
                                if(tournament.getStatus().equalsIgnoreCase("Active")) {
                                    isActive = true;
                                    milliSecondsLeft = getMilliseconds(tournament.getEndDate(), tournament.getEndTime());

                                    if (milliSecondsLeft < 0)
                                        milliSecondsLeft = 0;
                                    new CountDownTimer(milliSecondsLeft, 1000) {

                                        @Override
                                        public void onTick(long millis) {
                                            int seconds = (int) (millis / 1000) % 60;
                                            int minutes = (int) ((millis / (1000 * 60)) % 60);
                                            int hours = (int) ((millis / (1000 * 60 * 60)) % 24);
                                            String text = String.format("%02d : %02d : %02d", hours, minutes, seconds);
                                            timeLeft.setText(text);
                                        }

                                        @Override
                                        public void onFinish() {
                                            timeLeft.setText("00 : 00 : 00");
                                        }
                                    }.start();
                                }
                            }
                        }
                        if(task.isCanceled())
                            Log.e("onCanceled", task.getException().toString());

                    }
                });

    }

    long getMilliseconds(String date, String time) {
        int day = Integer.parseInt(date.split("/")[1]);
        int month = Integer.parseInt(date.split("/")[0]);
        int year = Integer.parseInt(date.split("/")[2]);
        int hour = Integer.parseInt(time.split(":")[0]);
        int min = Integer.parseInt(time.split(":")[1]);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, min);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        Log.e("Time", String.valueOf(c));

        return (c.getTimeInMillis() - System.currentTimeMillis());
    }
}