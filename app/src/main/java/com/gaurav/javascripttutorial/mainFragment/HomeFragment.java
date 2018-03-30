package com.gaurav.javascripttutorial.mainFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.SplashScreen;
import com.gaurav.javascripttutorial.adapter.ConceptSuggestionCursorAdapter;
import com.gaurav.javascripttutorial.adapter.CustomMenuAdapter;
import com.gaurav.javascripttutorial.communityBlog.CommunityBlog;
import com.gaurav.javascripttutorial.model.Menu;
import com.gaurav.javascripttutorial.model.SuggestTutes;
import com.gaurav.javascripttutorial.subactivities.ActivityInterview;
import com.gaurav.javascripttutorial.subactivities.AdvanceActivity;
import com.gaurav.javascripttutorial.subactivities.BeginnerActivity;
import com.gaurav.javascripttutorial.subactivities.PdfActivity;
import com.gaurav.javascripttutorial.subactivities.QuizActivity;
import com.gaurav.javascripttutorial.subactivities.QuizLeaderBoard;
import com.gaurav.javascripttutorial.subactivities.ReferenceSiteActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaurav on 2/15/2018.
 */

public class HomeFragment extends Fragment{

    private int[] drawables = new int[]{R.drawable.beginner,R.drawable.advance,R.drawable.interview,
            R.drawable.reference,R.drawable.pdf,R.drawable.blog,R.drawable.quiz};
    private String[] titles = new String[]{"Beginner\n Tutorial   ","Advance\n Tutorial","Interview\n Questions","Refernce\n Site","PDF\n Reference","JS Community Blog","JS\n Quiz"};
    List<Menu> list;
    TextView totalScore;

    // Suggestion EditText Work variables
    ListView suggestListView;
    List<SuggestTutes> suggestionList;
    SearchView suggestionEditText;
    private ConceptSuggestionCursorAdapter suggestionCursorAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View homeView = inflater.inflate(R.layout.home_fragment_activity,container,false);

        LayoutInflater inflater1 = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        getUserData(homeView);

        GridView gridView = (GridView) homeView.findViewById(R.id.gridview);
        addMenuData();
        CustomMenuAdapter adapter = new CustomMenuAdapter(homeView.getContext(),R.layout.grid_view_layout,list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(listener);

        suggestListView = (ListView) homeView.findViewById(R.id.suggestion_list_view);
        suggestionList = new ArrayList<>();
        suggestionEditText = (SearchView) homeView.findViewById(R.id.search_editText);

        CardView leaderBoard = (CardView) homeView.findViewById(R.id.leader_card_view);
        leaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeView.getContext(), QuizLeaderBoard.class);
                startActivity(intent);
            }
        });
        getSharedPrefData(homeView);
        return homeView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        addSuggestionData();
        suggestionCursorAdapter = new ConceptSuggestionCursorAdapter(getContext(),suggestionList);
        suggestListView.setAdapter(suggestionCursorAdapter);

        suggestionEditText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                suggestionCursorAdapter.getFilter().filter(newText.toString());
                return true;
            }
        });

        suggestionEditText.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    suggestListView.setVisibility(View.VISIBLE);
                }else {
                    suggestListView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getSharedPrefData(View view){
        totalScore = (TextView) view.findViewById(R.id.total_score);
        SharedPreferences preferences = view.getContext().getSharedPreferences("addScore",0);
        int oldScore = preferences.getInt("oldScore",0);

        if (oldScore == 0){
            totalScore.setText("Total Score: "+oldScore);
        }else {
            totalScore.setText("Total Score: "+oldScore);
        }
    }

    private void addSuggestionData(){
        /* String[] tutesInit = {"Begginer","Begginer","Begginer","Begginer","Begginer","Begginer","Begginer","Begginer","Begginer","Begginer","Begginer","Begginer",
                "Advanced","Advanced","Advanced","Advanced","Advanced","Advanced","Advanced","Advanced","Advanced","Advanced","Advanced","Advanced","Advanced",
                "Advanced","Advanced","Advanced","Advanced","Advanced","Advanced","Advanced","Advanced","Advanced","Advanced",}; */
        suggestionList.clear();
        String[] topic = {"Overview","Syntax","Enabling","Placement","Variables","Operators","If else","Switch case","While loop","For loop",
                "For in loop","Loop control","Functions","Events","Cookies","Page redirect","Dialog box","Void keyword","Page printing","Objects",
                "Numbers","Boolean","String","Arrays","Dates","Math","RegExp","DOM","Error and Exception","Form validation",
                "Animation","Multimedia","Debugging","Image Map","Browsers"};

        for (int i=0; i<topic.length; i++){
            suggestionList.add(new SuggestTutes(topic[i]));
        }
    }

    GridView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0 :
                    Intent intent = new Intent(view.getContext(), BeginnerActivity.class);
                    startActivity(intent);
                    break;
                case 1 :
                    Intent intentOne = new Intent(view.getContext(), AdvanceActivity.class);
                    startActivity(intentOne);
                    break;
                case 2 :
                    Intent intentTwo = new Intent(view.getContext(), ActivityInterview.class);
                    startActivity(intentTwo);
                    break;
                case 3 :
                    Intent intentThree = new Intent(view.getContext(), ReferenceSiteActivity.class);
                    startActivity(intentThree);
                    break;
                case 4 :
                    Intent intentFour = new Intent(view.getContext(), PdfActivity.class);
                    startActivity(intentFour);
                    break;
                case 5 :
                    Intent intentSix = new Intent(view.getContext(), CommunityBlog.class);
                    startActivity(intentSix);
                    break;
                case 6 :
                    Intent intentSeven = new Intent(view.getContext(), QuizActivity.class);
                    startActivity(intentSeven);
                    break;
            }
        }

    };

    private void addMenuData(){
        list = new ArrayList<>();
        for (int x=0; x<drawables.length; x++){
            list.add(new Menu(drawables[x],titles[x]));
        }
    }

    private void getUserData(final View view){
        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            if (auth.getCurrentUser() != null) {
                final FirebaseUser user = auth.getCurrentUser();
                getActivity().getWindow().getDecorView().post(new Runnable() {
                    @Override
                    public void run() {
                        ImageView user_bg = (ImageView) view.findViewById(R.id.circleUserImage);
                        TextView userName = (TextView) view.findViewById(R.id.userName);
                        TextView userEmail = (TextView) view.findViewById(R.id.user_email);
                        userName.setText(user.getDisplayName());
                        userEmail.setText(user.getEmail());

                        Glide.with(view.getContext())
                                .load(user.getPhotoUrl())
                                .into(user_bg);
                    }
                });
            } else {
                Intent intentSplash = new Intent(view.getContext(), SplashScreen.class);
                startActivity(intentSplash);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
