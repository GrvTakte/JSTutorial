package com.gaurav.javascripttutorial.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.model.LeaderBoard;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gaurav on 14/11/17.
 */

public class CustomLeaderBoardAdapter extends ArrayAdapter<LeaderBoard> {

    private Context context;
    private List<LeaderBoard> leaderBoards;
    private int resourceId;
    int number = 1;

    public CustomLeaderBoardAdapter(Context context, int resourceId, List<LeaderBoard> leaderBoards){
        super(context, resourceId,leaderBoards);
        this.resourceId = resourceId;
        this.context = context;
        this.leaderBoards = leaderBoards;
    }

    @Override
    public int getCount() {
        // Code to display only first ten elements present in list.
        /*if (number*10>leaderBoards.size()){
            return leaderBoards.size();
        }else {
            return number*10;
        } */

        return leaderBoards.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.leader_board_list_layout,null);

            CircleImageView userImage = (CircleImageView) view.findViewById(R.id.leader_board_user_image);
            TextView userName = (TextView) view.findViewById(R.id.leader_board_user_name);
            TextView userScore = (TextView) view.findViewById(R.id.leader_board_user_score);

            Glide.with(getContext())
                    .load(leaderBoards.get(position).getImageUrl())
                    .into(userImage);
            userName.setText(leaderBoards.get(position).getName());
            userScore.setText(String.valueOf(leaderBoards.get(position).getScore()));
        return view;
    }
}