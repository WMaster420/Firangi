/*
package com.vinayreddy.firangi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import static com.vinayreddy.firangi.TestActivity.QuestionList;

public class TestdashboardActivity extends AppCompatActivity {

    List<QModel> Qlist;
    int index=0;
    QModel qModel;
    TextView cardQues, option1, option2, option3,option4;
    CardView  cardViewO1, cardViewO2, cardViewO3, cardViewO4;
    int cCount=0; //for correct answers
    int wCount=0; //for wrong answers
    LinearLayout nxtbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
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
        cardViewO1=findViewById(R.id.cardViewO1);
        cardViewO2=findViewById(R.id.cardViewO2);
        cardViewO3=findViewById(R.id.cardViewO3);
        cardViewO4=findViewById(R.id.cardViewO4);
        nxtbtn=findViewById(R.id.nxtbtn);
    }

    public void correctAns(CardView cardView){
        cardView.setCardBackgroundColor(getResources().getColor(R.color.correctans));

        nxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cCount++;
                index++;
                qModel= Qlist.get(index);
                setdata();
            }
        });
    }

    public void wrongAns(CardView cardViewO1){
        cardViewO1.setCardBackgroundColor(getResources().getColor(R.color.wrongans));
        nxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wCount++;
                if(index<Qlist.size()-1){
                    index++;
                    qModel= Qlist.get(index);
                    setdata();
                    btnColorDflt();
                }
                else{
                    GameEnd();
                }
            }
        });
    }

    private void GameEnd() {
        Intent intent = new Intent(TestdashboardActivity.this, endAct.class);
        startActivity(intent);

        //takes you to endAct to show the end screen after test completion
    }

    public void buttonControlE(){  //To enable the buttons
        cardViewO1.setClickable(true);
        cardViewO2.setClickable(true);
        cardViewO3.setClickable(true);
        cardViewO4.setClickable(true);
    }

    public void buttonControlD(){  //To Disable the buttons
        cardViewO1.setClickable(false);
        cardViewO2.setClickable(false);
        cardViewO3.setClickable(false);
        cardViewO4.setClickable(false);
    }

    public void btnColorDflt(){  //to reset the color of buttons when next question comes
        cardViewO1.setBackgroundColor(getResources().getColor(R.color.white));
        cardViewO2.setBackgroundColor(getResources().getColor(R.color.white));
        cardViewO3.setBackgroundColor(getResources().getColor(R.color.white));
        cardViewO4.setBackgroundColor(getResources().getColor(R.color.white));
    }

    public void opt1click(View view) {
        buttonControlD();                       //to prevent users from clicking multiple options
        nxtbtn.setClickable(true);
        if(qModel.getOption1().equals(qModel.getAnswer())){
            cardViewO1.setCardBackgroundColor(getResources().getColor(R.color.correctans));

            if(index<Qlist.size()-1){
                correctAns(cardViewO1);
            }
            else {
                GameEnd();
            }

        }
        else{
            wrongAns(cardViewO1);
        }
    }

    public void opt2click(View view) {
        buttonControlD();          //to prevent users from clicking multiple options
        nxtbtn.setClickable(true);
        if(qModel.getOption2().equals(qModel.getAnswer())){
            cardViewO2.setCardBackgroundColor(getResources().getColor(R.color.correctans));

            if(index<Qlist.size()-1){
                correctAns(cardViewO2);
            }
            else {
                GameEnd();
            }

        }
        else{
            wrongAns(cardViewO2);
        }
    }

    public void opt3click(View view) {
        buttonControlD();
        nxtbtn.setClickable(true);
        if(qModel.getOption3().equals(qModel.getAnswer())){
            cardViewO3.setCardBackgroundColor(getResources().getColor(R.color.correctans));

            if(index<Qlist.size()-1){
                correctAns(cardViewO3);
            }
            else {
                GameEnd();
            }

        }
        else{
            wrongAns(cardViewO3);
        }
    }

    public void opt4click(View view) {
        buttonControlD();
        nxtbtn.setClickable(true);
        if(qModel.getOption4().equals(qModel.getAnswer())){
            cardViewO4.setCardBackgroundColor(getResources().getColor(R.color.correctans));

            if(index<Qlist.size()-1){
                correctAns(cardViewO4);
            }
            else {
                GameEnd();
            }

        }
        else{
            wrongAns(cardViewO4);
        }
    }
}*/
