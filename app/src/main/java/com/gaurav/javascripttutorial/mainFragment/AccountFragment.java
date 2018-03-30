package com.gaurav.javascripttutorial.mainFragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.SplashScreen;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Gaurav on 2/15/2018.
 */

public class AccountFragment extends Fragment {

    private static String TAG = "AccountFragment";

    private float[] yData = {50f, 50};
    private String[] xData = {"Beginner", "Advance"};

    CircleImageView userImage;
    TextView userName;
    TextView userEmail;

    Button logout;

    FirebaseAuth auth;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View accountView = inflater.inflate(R.layout.account_fragment_activity, container, false);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        logout = (Button) accountView.findViewById(R.id.logout);

        userImage = (CircleImageView) accountView.findViewById(R.id.account_user_image);
        userName = (TextView) accountView.findViewById(R.id.account_user_name);
        userEmail = (TextView) accountView.findViewById(R.id.account_user_mail);

        userName.setText(user.getDisplayName());
        userEmail.setText(user.getEmail());

        Glide.with(accountView.getContext())
                .load(user.getPhotoUrl())
                .into(userImage);

        return accountView;
    }

    @Override
    public void onStart() {
        super.onStart();
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Logging out...");
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        auth.signOut();
                        dialog.dismiss();
                        Intent intent = new Intent(getContext(), SplashScreen.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                },3000);
            }
        });
    }
}
