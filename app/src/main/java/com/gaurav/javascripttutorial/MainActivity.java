package com.gaurav.javascripttutorial;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.gaurav.javascripttutorial.adapter.FragmentPagerAdapter;
import com.gaurav.javascripttutorial.mainFragment.AccountFragment;
import com.gaurav.javascripttutorial.mainFragment.HomeFragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private InterstitialAd ads;

    private BottomNavigationView bottomNavigationView;
    ViewPager pager;

    HomeFragment homeFragment;
    AccountFragment accountFragment;

    MenuItem menuItem;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        pager = (ViewPager) findViewById(R.id.viewPager);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        pager.setCurrentItem(0);
                        break;
                    case R.id.nav_account:
                        pager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem!=null){
                    menuItem.setChecked(false);
                }else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }

                Log.d("Page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                menuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        setUpViewPager(pager);
    }


    private void setUpViewPager(ViewPager pager){
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        accountFragment = new AccountFragment();
        adapter.addFragment(homeFragment);
        adapter.addFragment(accountFragment);
        pager.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        long cacheExpiration=0;

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        ads = new InterstitialAd(getApplicationContext());
        ads.setAdUnitId(getString(R.string.admob_intertial_ad));

        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mFirebaseRemoteConfig.activateFetched();
                        }else {
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        final AdRequest request = new AdRequest.Builder().build();
        ads.loadAd(request);
        ads.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                if (ads.isLoaded()){
                    ads.show();
                }
            }
            @Override
            public void onAdFailedToLoad(int i) {
                ads.loadAd(request);
            }
            @Override
            public void onAdClosed() {
                finish();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        return false;
    }

    @Override
    public void onBackPressed() {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Exit");
            dialogBuilder.setMessage("You want to quit the application?");
            dialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    moveTaskToBack(true);
                    finish();
                    return;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
                   android.support.v7.app.AlertDialog dialogBuilder = new android.support.v7.app.AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("You want to logout from current account?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(MainActivity.this, SplashScreen.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        return super.onOptionsItemSelected(item);
    }

    public void rateApp(View view){
        String url = "https://play.google.com/store/apps/details?id=com.gaurav.javascripttutorial";
        Intent rateIntent = new Intent();
        rateIntent.setAction(Intent.ACTION_VIEW);
        rateIntent.setData(Uri.parse(url));
        startActivity(rateIntent);
    }

    public void shareApp(View view){
        String html = "*JS Tutorial Application* \n" +
                "_Your JS buddy which helps you in learning Javascript_ \n" +
                "*Install Now* \n" +
                "https://play.google.com/store/apps/details?id=com.gaurav.javascripttutorial";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, html);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}