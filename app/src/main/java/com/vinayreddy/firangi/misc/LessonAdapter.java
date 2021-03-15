package com.vinayreddy.firangi.misc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vinayreddy.firangi.R;
import com.vinayreddy.firangi.models.LessonModel;

import java.util.List;

public class LessonAdapter extends BaseAdapter {
    Context context;
    List<LessonModel> lessonList;
    LayoutInflater inflater;

    public LessonAdapter(Context applicationContext, List<LessonModel> lessonList) {
        this.context = applicationContext;
        this.lessonList = lessonList;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return lessonList.size();
    }

    @Override
    public LessonModel getItem(int i) {
        return lessonList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.lesson_container, null);
        TextView lessonName = view.findViewById(R.id.lesson_container_lesson_name);
        ImageView lessonIcon = view.findViewById(R.id.lesson_container_lesson_icon);
        lessonName.setText(lessonList.get(i).getLessonName());
        if(lessonList.get(i).getLocked() != null && lessonList.get(i).getLocked())
            lessonIcon.setImageResource(R.drawable.ic_baseline_lock_24);
        else if(lessonList.get(i).getCompleted() != null && lessonList.get(i).getCompleted())
            lessonIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
        else
            lessonIcon.setImageResource(R.drawable.ic_baseline_navigate_next_24);

        return view;
    }
}
