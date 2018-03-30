package com.gaurav.javascripttutorial.subactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentAnimation;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentArrays;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentBoolean;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentBrowsers;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentCookies;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentDate;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentDebugging;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentDialogBox;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentDom;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentErrorsException;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentEvents;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentFormValidation;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentFunctions;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentImageMap;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentMath;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentMultiMedia;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentNumbers;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentObjects;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentPagePrinting;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentPageRedirect;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentRegExp;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentString;
import com.gaurav.javascripttutorial.advanceSubFragments.FragmentVoidKeyword;
import com.gaurav.javascripttutorial.beginnerSubFragments.FragmentControls;
import com.gaurav.javascripttutorial.beginnerSubFragments.FragmentEnabling;
import com.gaurav.javascripttutorial.beginnerSubFragments.FragmentForinloop;
import com.gaurav.javascripttutorial.beginnerSubFragments.FragmentForloop;
import com.gaurav.javascripttutorial.beginnerSubFragments.FragmentIfelse;
import com.gaurav.javascripttutorial.beginnerSubFragments.FragmentOperators;
import com.gaurav.javascripttutorial.beginnerSubFragments.FragmentOverview;
import com.gaurav.javascripttutorial.beginnerSubFragments.FragmentPlacements;
import com.gaurav.javascripttutorial.beginnerSubFragments.FragmentSwitchcase;
import com.gaurav.javascripttutorial.beginnerSubFragments.FragmentSyntax;
import com.gaurav.javascripttutorial.beginnerSubFragments.FragmentVariables;
import com.gaurav.javascripttutorial.beginnerSubFragments.FragmentWhileloop;

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_activity);
        Intent intent = getIntent();
        String searchResult = intent.getStringExtra("searchResult");

        loadFragment(searchResult);
    }

    static String[] topic = {"Overview","Syntax","Enabling","Placement","Variables","Operators","If else","Switch case","While loop","For loop",
            "For in loop","Loop control","Functions","Events","Cookies","Page redirect","Dialog box","Void keyword","Page printing","Objects",
            "Numbers","Boolean","String","Arrays","Dates","Math","RegExp","DOM","Error and Exception","Form validation",
            "Animation","Multimedia","Debugging","Image Map","Browsers"};

    private void loadFragment(String searchResult){
        Fragment fragment = null;

        switch (searchResult){
            case "overview":
                fragment = new FragmentOverview();
                break;
            case "syntax":
                fragment = new FragmentSyntax();
                break;
            case "enabling":
                fragment = new FragmentEnabling();
                break;
            case "placement":
                fragment = new FragmentPlacements();
                break;
            case "variables":
                fragment = new FragmentVariables();
                break;
            case "operators":
                fragment = new FragmentOperators();
                break;
            case "if else":
                fragment = new FragmentIfelse();
                break;
            case "switch case":
                fragment = new FragmentSwitchcase();
                break;
            case "while loop":
                fragment = new FragmentWhileloop();
                break;
            case "for loop":
                fragment = new FragmentForloop();
                break;
            case "for in loop":
                fragment = new FragmentForinloop();
                break;
            case "loop control":
                fragment = new FragmentControls();
                break;
            case "functions":
                fragment = new FragmentFunctions();
                break;
            case "events":
                fragment = new FragmentEvents();
                break;
            case "cookies":
                fragment = new FragmentCookies();
                break;
            case "page redirect":
                fragment = new FragmentPageRedirect();
                break;
            case "dialog box":
                fragment = new FragmentDialogBox();
                break;
            case "void keyword":
                fragment = new FragmentVoidKeyword();
                break;
            case "page printing":
                fragment = new FragmentPagePrinting();
                break;
            case "objects":
                fragment = new FragmentObjects();
                break;
            case "numbers":
                fragment = new FragmentNumbers();
                break;
            case "boolean":
                fragment = new FragmentBoolean();
                break;
            case "string":
                fragment = new FragmentString();
                break;
            case "arrays":
                fragment = new FragmentArrays();
                break;
            case "dates":
                fragment = new FragmentDate();
                break;
            case "math":
                fragment = new FragmentMath();
                break;
            case "regexp":
                fragment = new FragmentRegExp();
                break;
            case "dom":
                fragment = new FragmentDom();
                break;
            case "error and exception":
                fragment = new FragmentErrorsException();
                break;
            case "form validation":
                fragment = new FragmentFormValidation();
                break;
            case "animation":
                fragment = new FragmentAnimation();
                break;
            case "multimedia":
                fragment = new FragmentMultiMedia();
                break;
            case "debugging":
                fragment = new FragmentDebugging();
                break;
            case "image map":
                fragment = new FragmentImageMap();
                break;
            case "browsers":
                fragment = new FragmentBrowsers();
                break;
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.search_frame_layout,fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
