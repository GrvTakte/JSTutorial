package com.gaurav.javascripttutorial.subactivities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.fragments.BeginnerListFragment;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

/**
 * Created by gaurav on 18/09/17.
 */

public class BeginnerActivity extends AppCompatActivity {
    private Button indexButton;
    private FirebaseRemoteConfig mRemoteConfig;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_beginner);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Beginner Level");
        }
        loadFragment();

        indexButton = (Button) findViewById(R.id.beginnerIndex);
        indexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // It will navigate fragment in previous direction and check for the fragment if index fragment present then it will disable
        //prev button from screen else it will navigate fragments previously until its end.

        //It will navigate fragment in next direction and check for the last fragment if last fragment present in framelayout so it will disable
        // the next button so user can not click on next button.
    }

    private void loadFragment(){
        Fragment fragment = new BeginnerListFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.beginnerFrameLayout,fragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
