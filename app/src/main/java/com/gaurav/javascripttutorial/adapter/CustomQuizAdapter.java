package com.gaurav.javascripttutorial.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.model.Question;
import com.gaurav.javascripttutorial.subactivities.QuizResultActivity;

import java.util.List;

/**
 * Created by gaurav on 06/12/17.
 */

public class CustomQuizAdapter extends ArrayAdapter<Question> {

    private Context context;
    private int resourceId;
    private List<Question> arrayList;
    private int listPosition = 0;
    private int score = 0;
    private int remaining_number = 19;
    private int wrong_number = 0;
    private int correct_number = 0;
    private Animation animation;

    //TextVIew Handler
    TextView wrong_answer, correct_answer, remaining_questions;

    public CustomQuizAdapter(Context context, int resourceId, List<Question> arrayList){
        super(context,resourceId,arrayList);
        this.context=context;
        this.resourceId=resourceId;
        this.arrayList=arrayList;
        animation = AnimationUtils.loadAnimation(context,R.anim.bounce);
    }

    @Override
    public int getCount() {
        int size = arrayList.size()-(arrayList.size()-1);
        return size;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(resourceId,null);

        final TextView question;
        final Button opta,optb,optc, optd;

        question = (TextView) view.findViewById(R.id.questions);
        opta = (Button) view.findViewById(R.id.opta);
        optb = (Button) view.findViewById(R.id.optb);
        optc = (Button) view.findViewById(R.id.optc);
        optd = (Button) view.findViewById(R.id.optd);

        question.setText(arrayList.get(position).getQUESTION());
        opta.setText(arrayList.get(position).getOPTA());
        opta.startAnimation(animation);
        optb.setText(arrayList.get(position).getOPTB());
        optb.startAnimation(animation);
        optc.setText(arrayList.get(position).getOPTC());
        optc.startAnimation(animation);
        optd.setText(arrayList.get(position).getOPTD());
        optd.startAnimation(animation);

        wrong_answer = (TextView) view.findViewById(R.id.wrong_answer);
        correct_answer = (TextView) view.findViewById(R.id.correct_answer);
        remaining_questions = (TextView) view.findViewById(R.id.ques_remaining);

        remaining_questions.setText(String.valueOf(remaining_number));
        correct_answer.setText(String.valueOf(correct_number));
        wrong_answer.setText(String.valueOf(wrong_number));

        opta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listPosition <arrayList.size()-1) {
                    if (opta.getText().equals(arrayList.get(listPosition).getANSWER())){
                        score=score+4;
                        correct_number++;
                        correct_answer.setText(String.valueOf(correct_number));
                    }else {
                        wrong_number++;
                        wrong_answer.setText(String.valueOf(wrong_number));
                    }
                    remaining_number--;
                    remaining_questions.setText(String.valueOf(remaining_number));
                    listPosition++;
                    arrayList.get(listPosition);
                    question.setText(arrayList.get(listPosition).getQUESTION());
                    opta.setText(arrayList.get(listPosition).getOPTA());
                    opta.startAnimation(animation);
                    optb.setText(arrayList.get(listPosition).getOPTB());
                    optb.startAnimation(animation);
                    optc.setText(arrayList.get(listPosition).getOPTC());
                    optc.startAnimation(animation);
                    optd.setText(arrayList.get(listPosition).getOPTD());
                    optd.startAnimation(animation);
                }else {
                    Intent intent = new Intent(context,QuizResultActivity.class);
                    intent.putExtra("score",score);
                    context.startActivity(intent);
                }
            }
        });

        optb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listPosition <arrayList.size()-1) {
                    if (optb.getText().equals(arrayList.get(listPosition).getANSWER())){
                        score=score+4;
                        correct_number++;
                        correct_answer.setText(String.valueOf(correct_number));
                    }else {
                        wrong_number++;
                        wrong_answer.setText(String.valueOf(wrong_number));
                    }
                    remaining_number--;
                    remaining_questions.setText(String.valueOf(remaining_number));
                    listPosition++;
                    arrayList.get(listPosition);
                    question.setText(arrayList.get(listPosition).getQUESTION());
                    opta.setText(arrayList.get(listPosition).getOPTA());
                    opta.startAnimation(animation);
                    optb.setText(arrayList.get(listPosition).getOPTB());
                    optb.startAnimation(animation);
                    optc.setText(arrayList.get(listPosition).getOPTC());
                    optc.startAnimation(animation);
                    optd.setText(arrayList.get(listPosition).getOPTD());
                    optd.startAnimation(animation);
                }else {
                    Intent intent = new Intent(context,QuizResultActivity.class);
                    intent.putExtra("score",score);
                    context.startActivity(intent);
                }
            }
        });

        optc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listPosition <arrayList.size()-1) {
                    if (optc.getText().equals(arrayList.get(listPosition).getANSWER())){
                        score=score+4;
                        correct_number++;
                        correct_answer.setText(String.valueOf(correct_number));
                    }else {
                        wrong_number++;
                        wrong_answer.setText(String.valueOf(wrong_number));
                    }
                    remaining_number--;
                    remaining_questions.setText(String.valueOf(remaining_number));
                    listPosition++;
                    arrayList.get(listPosition);
                    question.setText(arrayList.get(listPosition).getQUESTION());
                    opta.setText(arrayList.get(listPosition).getOPTA());
                    opta.startAnimation(animation);
                    optb.setText(arrayList.get(listPosition).getOPTB());
                    optb.startAnimation(animation);
                    optc.setText(arrayList.get(listPosition).getOPTC());
                    optc.startAnimation(animation);
                    optd.setText(arrayList.get(listPosition).getOPTD());
                    optd.startAnimation(animation);
                }else {
                    Intent intent = new Intent(context,QuizResultActivity.class);
                    intent.putExtra("score",score);
                    context.startActivity(intent);
                }
            }
        });

        optd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listPosition <arrayList.size()-1) {
                    if (optd.getText().equals(arrayList.get(listPosition).getANSWER())){
                        score=score+4;
                        correct_number++;
                        correct_answer.setText(String.valueOf(correct_number));
                    }else {
                        wrong_number++;
                        wrong_answer.setText(String.valueOf(wrong_number));
                    }
                    remaining_number--;
                    remaining_questions.setText(String.valueOf(remaining_number));
                    listPosition++;
                    arrayList.get(listPosition);
                    question.setText(arrayList.get(listPosition).getQUESTION());
                    opta.setText(arrayList.get(listPosition).getOPTA());
                    opta.startAnimation(animation);
                    optb.setText(arrayList.get(listPosition).getOPTB());
                    optb.startAnimation(animation);
                    optc.setText(arrayList.get(listPosition).getOPTC());
                    optc.startAnimation(animation);
                    optd.setText(arrayList.get(listPosition).getOPTD());
                    optd.startAnimation(animation);
                }else {
                    Intent intent = new Intent(context,QuizResultActivity.class);
                    intent.putExtra("score",score);
                    context.startActivity(intent);
                }
            }
        });
        return view;
    }
}
