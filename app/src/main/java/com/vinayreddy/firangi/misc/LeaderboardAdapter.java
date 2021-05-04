package com.vinayreddy.firangi.misc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vinayreddy.firangi.R;
import com.vinayreddy.firangi.models.LessonModel;
import com.vinayreddy.firangi.models.UserModel;

import java.util.List;

public class LeaderboardAdapter extends BaseAdapter {
    Context context;
    List<UserModel> participantList;
    LayoutInflater inflater;
    private UserModel instance;

    public LeaderboardAdapter(Context applicationContext, List<UserModel> participantList) {
        this.context = applicationContext;
        this.participantList = participantList;
        inflater = (LayoutInflater.from(applicationContext));
        instance = UserModel.getInstance();
    }

    @Override
    public int getCount() {
        return participantList.size();
    }

    @Override
    public UserModel getItem(int i) {
        return participantList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.leaderboard_container, null);
        TextView name = view.findViewById(R.id.leaderboard_container_name);
        String info = (i+1) + ": " + participantList.get(i).getUserName() + " (" + participantList.get(i).getTournamentScore() + ")";
        name.setText(info);
        return view;
    }
}
