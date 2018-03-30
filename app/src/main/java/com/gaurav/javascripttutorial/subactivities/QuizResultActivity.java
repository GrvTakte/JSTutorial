package com.gaurav.javascripttutorial.subactivities;

/**
 * Created by gaurav on 05/10/17.
 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gaurav.javascripttutorial.MainActivity;
import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.model.LeaderBoard;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

public class QuizResultActivity extends AppCompatActivity {

    TextView textScore;
    TextView scoreStatus;
    CardView finishQuiz;
    DatabaseReference reference;
    int newScore;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    FirebaseAuth auth;
    FirebaseUser user;

    private InterstitialAd adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_result_activity);
        //get rating bar object
        textScore = (TextView) findViewById(R.id.quiz_score);
        finishQuiz = (CardView) findViewById(R.id.finish_quiz);

        reference = FirebaseDatabase.getInstance().getReference("score");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        SmileRating rating = (SmileRating) findViewById(R.id.smile_rating);

        //get text view
        scoreStatus=(TextView)findViewById(R.id.textResult);
        //get score
        Bundle b = getIntent().getExtras();
        newScore= b.getInt("score");
        Log.d("Score",""+newScore);

        //display score
        textScore.setText(""+newScore);

        if (newScore <= 80 && newScore > 64){
            scoreStatus.setText("Great! Seems to be a professional javascript programmer.");
            rating.setSelectedSmile(BaseRating.GREAT);
        }else if (newScore <= 64 && newScore > 48){
            scoreStatus.setText("Good! Now you are ready to code.");
            rating.setSelectedSmile(BaseRating.GOOD);
        }else if (newScore <= 48 && newScore > 32){
            scoreStatus.setText("Okay! Need some more effort.");
            rating.setSelectedSmile(BaseRating.OKAY);
        }else if (newScore <= 32 && newScore > 16){
            scoreStatus.setText("Bad! Take it seriously.");
            rating.setSelectedSmile(BaseRating.BAD);
        }else if (newScore <= 16){
            scoreStatus.setText("Terrible! Please read all concepts carefully and try again");
            rating.setSelectedSmile(BaseRating.TERRIBLE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_quiz_result);
        setSupportActionBar(toolbar);

        finishQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

       /* reportQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizResultActivity.this, QuizReportActivity.class);
                startActivity(intent);
            }
        }); */

        configSharedPref();
        saveScoreToDatabase(newScore);

        adView = new InterstitialAd(getApplicationContext());
        adView.setAdUnitId(getString(R.string.admob_intertial_ad));
        final AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);
        adView.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                adView.show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                adView.loadAd(request);
            }
        });
    }

    private void configSharedPref(){
        preferences = getApplicationContext().getSharedPreferences("addScore",0);
        editor = preferences.edit();
    }

    private void saveScoreToDatabase(int score){
        int oldScore = preferences.getInt("oldScore",0);
        String id = user.getUid();
        String name = user.getDisplayName();
        String image = user.getPhotoUrl().toString();
        score = oldScore+score;
        LeaderBoard leaderBoard = new LeaderBoard(id,name,score,image);
        reference.child(id).setValue(leaderBoard);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String id = user.getUid();
        reference.child(id).child("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                editor.clear();
                editor.putInt("oldScore", Integer.parseInt(dataSnapshot.getValue().toString()));
                editor.commit();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(QuizResultActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}