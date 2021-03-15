package com.vinayreddy.firangi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.vinayreddy.firangi.knowledgetst.QuestionList;

public class TestdashboardActivity extends AppCompatActivity {

    List<QModel> Qlist;
    int index=0;
    QModel qModel;
    TextView cardQues, option1, option2, option3,option4;
    CardView  cardView, cardView2, cardView5, cardView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);
        links();
        Qlist=QuestionList;
        Collections.shuffle(QuestionList);
        qModel=QuestionList.get(index);
        
        setdata();
    }

    private void setdata() {
        cardQues.setText(qModel.getQuestion());
        option1.setText(qModel.getOption1());
        option2.setText(qModel.getOption2());
        option3.setText(qModel.getOption3());
        option4.setText(qModel.getOption4());
    }

    private void links() {
        cardQues=findViewById(R.id.cardQues);
        option1=findViewById(R.id.option1);
        option2=findViewById(R.id.option2);
        option3=findViewById(R.id.option3);
        option4=findViewById(R.id.option4);
        cardView=findViewById(R.id.cardView);
        cardView2=findViewById(R.id.cardView2);
        cardView5=findViewById(R.id.cardView5);
        cardView4=findViewById(R.id.cardView4);

    }
}