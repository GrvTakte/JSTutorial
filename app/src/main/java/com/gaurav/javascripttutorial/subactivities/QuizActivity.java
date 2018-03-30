package com.gaurav.javascripttutorial.subactivities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.gaurav.javascripttutorial.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import dmax.dialog.SpotsDialog;

/**
 * Created by gaurav on 04/10/17.
 */

public class QuizActivity extends AppCompatActivity {

    private Button startButton;
    private Toolbar toolbar;
    private AlertDialog dialog;


    private static final int PERMISSIONS_MULTIPLE_REQUEST = 100;
    private InterstitialAd ad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);
        dialog = new SpotsDialog(QuizActivity.this, R.style.Custom);
        toolbar = (Toolbar) findViewById(R.id.toolbar_quiz);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        startButton = (Button) findViewById(R.id.start_quiz);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, QuizMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (isNetworkAvailable()){
            checkPermission();
        }else {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Online?");
            dialogBuilder.setMessage("Please be online...");

            dialogBuilder.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        }

        Button rewardRuleButton = (Button) findViewById(R.id.get_reward);
        rewardRuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRules();
            }
        });
    }

    private void loadAdvert(){
        ad = new InterstitialAd(getApplicationContext());
        ad.setAdUnitId(getString(R.string.interstitial_without_video));
        final AdRequest request = new AdRequest.Builder().build();
        ad.loadAd(request);
        ad.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                ad.show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                ad.loadAd(request);
            }
        });
    }

    private void getRules(){
        AlertDialog.Builder alertadd = new AlertDialog.Builder(
                QuizActivity.this);
        LayoutInflater factory = LayoutInflater.from(QuizActivity.this);
        final View view = factory.inflate(R.layout.bottom_sheet, null);
        alertadd.setView(view);
        alertadd.show();
    }

    private void checkPermission(){
        int permissionCheck = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(QuizActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setTitle("Storage Permission");
                builder.setMessage("This application require storage permission");
                builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(QuizActivity.this,  new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_MULTIPLE_REQUEST);
                    }
                });
                builder.show();
            } else {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setTitle("Storage Permission");
                builder.setMessage("This application require storage permission");
                builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(QuizActivity.this,  new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_MULTIPLE_REQUEST);
                    }
                });
                builder.show();
            }
        } else {
                // got permission use it
            //Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
            saveQuestionFile();
        }
    }

    private void saveQuestionFile(){
        dialog.show();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://js-tutorial-e4492.appspot.com/");
        StorageReference islandRef = storageRef.child("Questions/questions.json");

        File rootPath = new File(Environment.getExternalStorageDirectory(),"questions");
        if (!rootPath.exists()){
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath,"questions.json");

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                loadAdvert();
                Toast.makeText(QuizActivity.this, "New Questions Added", Toast.LENGTH_SHORT).show();
                startButton.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
                startButton.startAnimation(animation);
                dialog.cancel();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(QuizActivity.this, "Failed to add new Questions", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id== android.R.id.home){
            onBackPressed();
        }        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_MULTIPLE_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length >= 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, do your work....
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    saveQuestionFile();
                } else {
                    // permission denied
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    // Disable the functionality that depends on this permission.
                }
                return;
            }
            // other 'case' statements for other permissions
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
