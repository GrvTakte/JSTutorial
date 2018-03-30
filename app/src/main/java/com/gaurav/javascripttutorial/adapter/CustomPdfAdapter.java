package com.gaurav.javascripttutorial.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.model.PdfInfo;

import java.util.ArrayList;

/**
 * Created by gaurav on 19/09/17.
 */

public class CustomPdfAdapter extends ArrayAdapter<PdfInfo> {

    Animation animation;
    Context context;
    ArrayList<PdfInfo> arrayList;
    int resourceId;

    public  CustomPdfAdapter(Context context, int resourceId, ArrayList<PdfInfo> arrayList){
        super(context,resourceId,arrayList);
        this.context = context;
        this.resourceId = resourceId;
        this.arrayList = arrayList;
        animation = AnimationUtils.loadAnimation(context,R.anim.card_exit);
    }

    @Override
    public int getCount() {
        return arrayList.size()-1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pdf_listview_layout,null);
        CardView cardView = (CardView) view.findViewById(R.id.animate_card_view);
        ImageView cover = (ImageView) view.findViewById(R.id.coverImage);
        cover.setImageResource(arrayList.get(position).getCover());
        cardView.startAnimation(animation);
        return view;
    }
}
