package com.gaurav.javascripttutorial.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.model.Menu;

import java.util.List;

/**
 * Created by gaurav on 06/12/17.
 */

public class CustomMenuAdapter extends ArrayAdapter<Menu> {

    private Context context;
    private int resourceId;
    private List<Menu> list;

    public CustomMenuAdapter(Context context, int resourceId, List<Menu> list){
        super(context,resourceId,list);
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.grid_view_layout,null);
        CardView cardView = (CardView) convertView.findViewById(R.id.menu_card_view);
        ImageView image = (ImageView) convertView.findViewById(R.id.menu_image);
        TextView textView = (TextView) convertView.findViewById(R.id.menuTitle);

        image.setImageResource(list.get(position).getDrawables());
        textView.setText(list.get(position).getTitles());

        /*if (position%2 ==1) {
            image.setImageResource(list.get(position).getDrawables());
            textView.setText(list.get(position).getTitles());
            textView.setBackgroundColor(convertView.getResources().getColor(R.color.colorPrimary));
            textView.setTextColor(convertView.getResources().getColor(R.color.list_divider));
        }else {
            image.setImageResource(list.get(position).getDrawables());
            textView.setText(list.get(position).getTitles());
            textView.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
            //ViewCompat.setElevation(textView,16);
            //ViewCompat.setElevation(cardView,16);

        } */
        return convertView;
    }
}
