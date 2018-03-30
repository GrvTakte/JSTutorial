package com.gaurav.javascripttutorial.subactivities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.adapter.CustomLeaderBoardAdapter;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

/**
 * Created by gaurav on 13/11/17.
 */

public class QuizLeaderBoard extends AppCompatActivity {

    //CircleImageView firstLeaderImage;
    //TextView firstLeaderRank;
    //TextView firstLeaderName;

    ListView listViewLeaderBoard;
    //Firebase database
    DatabaseReference reference;
    DatabaseReference scoreRef;

    InterstitialAd interstitialAd;

    android.app.AlertDialog progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } */

        if (!isNetworkAvailable()){
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setMessage("Please Be Online To see Latest Ranking")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setIcon(R.drawable.offline)
                    .setTitle("Online?")
                    .create();
            dialog.show();
        }else {
            progress = new SpotsDialog(QuizLeaderBoard.this,R.style.Custom);
            progress.show();

        }

        //get Views
        //firstLeaderName  = (TextView) findViewById(R.id.leader_name);
        //firstLeaderRank = (TextView) findViewById(R.id.leader_rank);
        //firstLeaderImage = (CircleImageView) findViewById(R.id.leader_first);
        listViewLeaderBoard = (ListView) findViewById(R.id.listView_leader_board);

        reference = FirebaseDatabase.getInstance().getReference();
        scoreRef = reference.child("score");
    }

    private void loadAd(){
        interstitialAd = new InterstitialAd(getApplicationContext());
        interstitialAd.setAdUnitId(getString(R.string.admob_intertial_ad));
        final AdRequest request = new AdRequest.Builder().build();
        interstitialAd.loadAd(request);
        interstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                interstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                interstitialAd.loadAd(request);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
                scoreRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<LeaderBoard> leaderBoards = new ArrayList<>();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        leaderBoards.clear();

                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            LeaderBoard leader = ds.getValue(LeaderBoard.class);
                            leaderBoards.add(leader);
                        }

                        Collections.sort(leaderBoards);

                        SharedPreferences preferences = getApplicationContext().getSharedPreferences("addScore",0);
                        SharedPreferences.Editor editor = preferences.edit();
                        /* firstLeaderName.setText(leaderBoards.get(0).getName());
                        Glide.with(getApplicationContext())
                                .load(leaderBoards.get(0).getImageUrl())
                                .into(firstLeaderImage);
                        firstLeaderRank.setText("WINNER"); */

                        CustomLeaderBoardAdapter adapter = new CustomLeaderBoardAdapter(getApplicationContext(),R.layout.leader_board_list_layout,leaderBoards);
                        listViewLeaderBoard.setAdapter(adapter);
                        setOldScore();
                        progress.dismiss();
                        loadAd();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private void setOldScore(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        scoreRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot != null) {
                        int score = Integer.parseInt(dataSnapshot.child(user.getUid()).child("score").getValue().toString());

                        SharedPreferences preferences = getApplicationContext().getSharedPreferences("addScore", 0);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.putInt("oldScore", score);
                        editor.commit();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}