package com.gaurav.javascripttutorial.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.adapter.CustomArrayAdapter;
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
import com.gaurav.javascripttutorial.model.Beginner;

import java.util.ArrayList;

/**
 * Created by gaurav on 21/09/17.
 */

public class AdvanceListFragment extends Fragment {

    private ListView listView;
    private ArrayList<Beginner> arrayList;
    private String[] beginners = {"JS Functions","JS Events","JS Cookies","JS Page Redirect","JS Dialog Box","JS Void Keyword","JS Page Printing","JS Objects"
            ,"JS Numbers","JS Boolean","JS String","JS Arrays","JS Date","JS Math","JS RegExp","JS DOM","JS Errors and Exceptions","JS Form Validation"
            ,"JS Animation","JS Multimedia","JS Debugging","JS ImageMap","JS Browsers"};
    private String[] initials = {"F","E","C","P","D","V","P","O","N","B","S","A","D","M","R","D","E","F","A","M","D","I","B"};
    private int[] colors = {R.color.colorOne,R.color.colorTwo,R.color.colorThree,R.color.colorFour,R.color.colorFive,R.color.colorSix,R.color.colorSeven,
            R.color.colorEight,R.color.colorNine,R.color.colorTen,R.color.colorOne,R.color.colorTwo,R.color.colorThree,R.color.colorFour,R.color.colorFive,R.color.colorSix,R.color.colorSeven,
            R.color.colorEight,R.color.colorNine,R.color.colorTen,R.color.colorOne,R.color.colorTwo,R.color.colorThree};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.advance_list_fragment,container,false);

        listView = (ListView) view.findViewById(R.id.advancelistView);
        addDataToArray();
        CustomArrayAdapter adapter = new CustomArrayAdapter(getContext(),R.layout.listview_layout,arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment= null;
                switch (position){
                    case 0:
                        fragment = new FragmentFunctions();
                        break;
                    case 1:
                        fragment = new FragmentEvents();
                        break;
                    case 2:
                        fragment = new FragmentCookies();
                        break;
                    case 3:
                        fragment = new FragmentPageRedirect();
                        break;
                    case 4:
                        fragment = new FragmentDialogBox();
                        break;
                    case 5:
                        fragment = new FragmentVoidKeyword();
                        break;
                    case 6:
                        fragment = new FragmentPagePrinting();
                        break;
                    case 7:
                        fragment = new FragmentObjects();
                        break;
                    case 8:
                        fragment = new FragmentNumbers();
                        break;
                    case 9:
                        fragment = new FragmentBoolean();
                        break;
                    case 10:
                        fragment = new FragmentString();
                        break;
                    case 11:
                        fragment = new FragmentArrays();
                        break;
                    case 12:
                        fragment = new FragmentDate();
                        break;
                    case 13:
                        fragment = new FragmentMath();
                        break;
                    case 14:
                        fragment = new FragmentRegExp();
                        break;
                    case 15:
                        fragment = new FragmentDom();
                        break;
                    case 16:
                        fragment = new FragmentErrorsException();
                        break;
                    case 17:
                        fragment = new FragmentFormValidation();
                        break;
                    case 18:
                        fragment = new FragmentAnimation();
                        break;
                    case 19:
                        fragment = new FragmentMultiMedia();
                        break;
                    case 20:
                        fragment = new FragmentDebugging();
                        break;
                    case 21:
                        fragment = new FragmentImageMap();
                        break;
                    case 22:
                        fragment = new FragmentBrowsers();
                        break;
                }
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.advanceFrameLayout,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    private void addDataToArray(){
        arrayList = new ArrayList<>();
        for (int i=0; i<beginners.length; i++){
            arrayList.add(new Beginner(beginners[i].toString(),initials[i].toString(),colors[i]));
        }
    }
}