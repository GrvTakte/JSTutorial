package com.gaurav.javascripttutorial.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.model.Notification;

import java.util.List;

/**
 * Created by Gaurav on 2/16/2018.
 */

public class CustomNotificationAdapter extends ArrayAdapter<Notification> {

    Context context;
    List<Notification> list;
    int resourceId;

    public CustomNotificationAdapter(Context context, int resourceId, List<Notification> list){
        super(context,resourceId,list);
        this.context = context;
        this.resourceId = resourceId;
        this.list = list;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(resourceId,null);

        TextView text = (TextView) view.findViewById(R.id.notification_list_text);
        ImageView image = (ImageView) view.findViewById(R.id.notification_list_image);

        text.setText(list.get(position).getText());

        Glide.with(getContext())
                .load(list.get(position).getImage())
                .into(image);

        return view;
    }
}
