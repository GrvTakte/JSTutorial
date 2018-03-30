package com.gaurav.javascripttutorial.subactivities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.model.Question;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 05/10/17.
 */

public class QuizMainActivity extends AppCompatActivity {


    private RewardedVideoAd rewardAd;
    private Dialog dialog;
    private ProgressDialog progressDialog;

    private List<Question> list;
    private ListView listView;

    private AdView banner_ad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_main_activity);
        list = new ArrayList<>();
        listView = (ListView) findViewById(R.id.quiz_list_view);
        getQuestions();

        banner_ad = (AdView) findViewById(R.id.banner_ad);
        final AdRequest request = new AdRequest.Builder().build();
        banner_ad.loadAd(request);

        banner_ad.setAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(int i) {
                banner_ad.loadAd(request);
            }
        });
    }

    private void getQuestions(){
        File rootPath = new File(Environment.getExternalStorageDirectory(),"questions");

        final File localFile = new File(rootPath,"questions.json");

                try {
                    String jsonStr = null;
                    FileInputStream inputStream = new FileInputStream(localFile);
                    try {
                        FileChannel fc = inputStream.getChannel();
                        MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                        jsonStr = Charset.defaultCharset().decode(bb).toString();
                       // Toast.makeText(QuizMainActivity.this, "Local File successfully created", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        inputStream.close();
                    }
                    JSONObject object = new JSONObject(jsonStr);
                    JSONArray array = object.getJSONArray("array");
                    for (int i=0; i<array.length();i++){
                        JSONObject obj = array.getJSONObject(i);
                        String question = obj.getString("questions");
                        String answer = obj.getString("answer");
                        String opta = obj.getString("opta");
                        String optb = obj.getString("optb");
                        String optc = obj.getString("optc");
                        String optd = obj.getString("optd");
                        list.add(new Question(question,answer,opta,optb,optc,optd));
                    }
                    CustomQuizAdapter adapter = new CustomQuizAdapter(getApplicationContext(),R.layout.quiz_list_layout,list);
                    listView.setAdapter(adapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
    }

    private void loadRewardVideoAd(){
        rewardAd.loadAd(getString(R.string.admob_reward_video_ad), new AdRequest.Builder().build());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    RewardedVideoAdListener listener = new RewardedVideoAdListener() {
        @Override
        public void onRewardedVideoAdLoaded() {
            progressDialog.dismiss();
            rewardAd.show();
            dialog.dismiss();
        }

        @Override
        public void onRewardedVideoAdOpened() {
        }

        @Override
        public void onRewardedVideoStarted() {
            Toast.makeText(QuizMainActivity.this, "Video started", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdClosed() {
            rewardAd.destroy(getApplicationContext());
        }

        @Override
        public void onRewarded(RewardItem rewardItem) {
        }

        @Override
        public void onRewardedVideoAdLeftApplication() {
            loadRewardVideoAd();
        }

        @Override
        public void onRewardedVideoAdFailedToLoad(int i) {
            loadRewardVideoAd();
        }
    };


    /**
     * Adapter class for Questions
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
        SharedPreferences preferences;
        SharedPreferences.Editor editor;

        //TextVIew Handler
        TextView wrong_answer, correct_answer, remaining_questions;

        public CustomQuizAdapter(Context context, int resourceId, List<Question> arrayList){
            super(context,resourceId,arrayList);
            this.context=context;
            this.resourceId=resourceId;
            this.arrayList=arrayList;
            animation = AnimationUtils.loadAnimation(context,R.anim.bounce);

            wrong_answer = (TextView) findViewById(R.id.wrong_answer);
            correct_answer = (TextView) findViewById(R.id.correct_answer);
            remaining_questions = (TextView) findViewById(R.id.ques_remaining);

            preferences = context.getSharedPreferences("Score",0);
            editor = preferences.edit();
            editor.clear();
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

            remaining_questions.setText(String.valueOf(remaining_number));

            opta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listPosition < arrayList.size()-1) {
                        if (opta.getText().equals(arrayList.get(listPosition).getANSWER())){
                            score=score+4;
                            correct_number++;
                            correct_answer.setText(String.valueOf(correct_number));
                            editor.putInt("id"+listPosition,listPosition);
                            System.out.println("id"+listPosition);
                            editor.commit();
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
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
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
                            editor.putInt("id"+listPosition,listPosition);
                            System.out.println("id"+listPosition);
                            editor.commit();
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
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
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
                            editor.putInt("id"+listPosition,listPosition);
                            System.out.println("id"+listPosition);
                            editor.commit();
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
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
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
                            editor.putInt("id"+listPosition,listPosition);
                            System.out.println("id"+listPosition);
                            editor.commit();
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
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            });
            return view;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Exit Quiz?");
        dialogBuilder.setMessage("Are You want to Exit The Quiz?");
        dialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}