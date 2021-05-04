package com.vinayreddy.firangi.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.vinayreddy.firangi.R;
import com.vinayreddy.firangi.models.UserModel;

//import com.vinayreddy.firangi.screens.R;

public class ProfileFragment extends Fragment {
    private UserModel instance;
    ImageView profileImage;
    TextView username;
    TextView email;
    ImageButton edit;
    ProgressBar frenchFluencyProgressBar;
    ImageView beginnerIcon;
    ImageView intermediateIcon;
    ImageView expertIcon;
    TextView currentCourse;
    TextView changeCourse;
    CardView logoutBtn;
    CardView feedbackBtn;
    CardView basicsBtn;
    TextView beginnerScore;
    TextView intermediateScore;
    TextView expertScore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        instance = UserModel.getInstance();
        int progress = 0;

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage = root.findViewById(R.id.profile_image);
        username = root.findViewById(R.id.profile_username);
        email = root.findViewById(R.id.profile_email);
        edit = root.findViewById(R.id.profile_edit_btn);
        frenchFluencyProgressBar = root.findViewById(R.id.french_fluency_progress_bar);
        beginnerIcon = root.findViewById(R.id.profile_beginner_check_icon);
        intermediateIcon = root.findViewById(R.id.profile_intermediate_check_icon);
        expertIcon = root.findViewById(R.id.profile_expert_check_icon);
        currentCourse = root.findViewById(R.id.profile_current_course_tv);
        changeCourse = root.findViewById(R.id.profile_change_course_tv);
        logoutBtn = root.findViewById(R.id.profile_logout_btn);
        basicsBtn = root.findViewById(R.id.profile_basics_cv);
        feedbackBtn = root.findViewById(R.id.profile_feedback_btn);
        beginnerScore = root.findViewById(R.id.profile_beginner_score_tv);
        intermediateScore = root.findViewById(R.id.profile_intermediate_score_tv);
        expertScore = root.findViewById(R.id.profile_expert_score_tv);

        username.setText(instance.getUserName());
        email.setText(instance.getUserEmail());
        currentCourse.setText(instance.getCurrentLevel());
        Glide.with(this)
                .load(instance.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.portrait_placeholder)
                .into(profileImage);

        beginnerScore.setText(instance.getBeginnerScore());
        intermediateScore.setText(instance.getIntermediateScore());
        expertScore.setText(instance.getExpertScore());

        if (instance.getBeginnerCompleted()) {
            beginnerIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            progress += 33;
        } else {
            beginnerIcon.setImageResource(R.drawable.ic_baseline_check_circle_24_grey);
        }

        if (instance.getIntermediateCompleted()) {
            intermediateIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            progress += 33;
        } else {
            intermediateIcon.setImageResource(R.drawable.ic_baseline_check_circle_24_grey);
        }

        if (instance.getExpertCompleted()) {
            expertIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            progress += 34;
        } else {
            expertIcon.setImageResource(R.drawable.ic_baseline_check_circle_24_grey);
        }

        frenchFluencyProgressBar.setProgress(progress);
        Log.e("Progress", String.valueOf(progress));

        changeCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CourseSelectionActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity().getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), FeedbackActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        basicsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), BasicsActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return root;
    }
}