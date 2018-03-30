package com.gaurav.javascripttutorial.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.model.ReferenceSiteInfo;

import java.util.ArrayList;

/**
 * Created by gaurav on 20/09/17.
 */

public class CustomReferenceSiteAdapter extends ArrayAdapter<ReferenceSiteInfo> {

    Context context;
    ArrayList<ReferenceSiteInfo> arrayList;
    int resourceId;
    private int lastPosition = -1;

    public CustomReferenceSiteAdapter(Context context, int resourceId, ArrayList<ReferenceSiteInfo> arrayList){
        super(context,resourceId,arrayList);
        this.context = context;
        this.resourceId = resourceId;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.reference_listview_layout,null);

        Animation animation = AnimationUtils.loadAnimation(context,(position>lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);

        TextView websiteName = (TextView) view.findViewById(R.id.siteTitle);
        TextView websiteUrl = (TextView) view.findViewById(R.id.siteUrl);

        websiteName.setText(arrayList.get(position).getWebsiteName());
        websiteUrl.setText(arrayList.get(position).getWebsiteUrl());

        view.startAnimation(animation);
        lastPosition = position;
        return view;
    }
}
