package com.gaurav.javascripttutorial.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.model.Question;
import com.gaurav.javascripttutorial.model.QuizAnswer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 09/10/17.
 */

public class QuizReportAdapter extends ArrayAdapter<Question> {

    Context context;
    List<Question> arrayList;
    int resourceId;
    ArrayList<QuizAnswer> idArray;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public QuizReportAdapter(Context context, int resourceId, List<Question> arrayList){
        super(context,resourceId,arrayList);
        this.context = context;
        this.resourceId = resourceId;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.quiz_report_list_layout,null);

        preferences = view.getContext().getSharedPreferences("Score",0);
        int score = preferences.getInt("MainScore",0);

        TextView question = (TextView) view.findViewById(R.id.quiz_report_question);
        TextView answer = (TextView) view.findViewById(R.id.quiz_report_answer);

        question.setText(arrayList.get(position).getQUESTION());
        answer.setText(arrayList.get(position).getANSWER());

        return view;
    }
}