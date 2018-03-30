package com.gaurav.javascripttutorial.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.adapter.CustomArrayAdapter;
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
import com.gaurav.javascripttutorial.model.Beginner;

import java.util.ArrayList;

/**
 * Created by gaurav on 21/09/17.
 */

public class BeginnerListFragment extends Fragment {

    private ListView listView;
    private ArrayList<Beginner> arrayList;
    private String[] beginners = {"JS Overview","JS Syntax","JS Enabling","JS Placements","JS Variables","JS Operators","JS If_Else","JS Switch_Case",
            "JS While_Loop","JS For_Loop","JS For_In_Loop","JS Loop Control",};
    private String[] initials = {"O","S","E","P","V","O","I","S","W","F","F","L",};
    private int[] colors = {R.color.colorOne,R.color.colorTwo,R.color.colorThree,R.color.colorFour,R.color.colorFive,R.color.colorSix,R.color.colorSeven,
            R.color.colorEight,R.color.colorNine,R.color.colorTen,R.color.colorTwo,R.color.colorThree};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.beginner_list_fragment,container,false);

        listView = (ListView) view.findViewById(R.id.beginnerlistView);
        addDataToArray();
        CustomArrayAdapter adapter = new CustomArrayAdapter(getContext(),R.layout.listview_layout,arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment= null;
                switch (position){
                    case 0:
                        fragment = new FragmentOverview();
                        break;
                    case 1:
                        fragment = new FragmentSyntax();
                        break;
                    case 2:
                        fragment = new FragmentEnabling();
                        break;
                    case 3:
                        fragment = new FragmentPlacements();
                        break;
                    case 4:
                        fragment = new FragmentVariables();
                        break;
                    case 5:
                        fragment = new FragmentOperators();
                        break;
                    case 6:
                        fragment = new FragmentIfelse();
                        break;
                    case 7:
                        fragment = new FragmentSwitchcase();
                        break;
                    case 8:
                        fragment = new FragmentWhileloop();
                        break;
                    case 9:
                        fragment = new FragmentForloop();
                        break;
                    case 10:
                        fragment = new FragmentForinloop();
                        break;
                    case 11:
                        fragment = new FragmentControls();
                        break;
                }
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.beginnerFrameLayout,fragment);
                transaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_from_left);
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
