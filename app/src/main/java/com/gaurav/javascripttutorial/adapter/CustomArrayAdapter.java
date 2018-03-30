package com.gaurav.javascripttutorial.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.model.Beginner;

import java.util.ArrayList;

/**
 * Created by gaurav on 18/09/17.
 */

public class CustomArrayAdapter extends ArrayAdapter<Beginner> {

    Context context;
    ArrayList<Beginner> arrayList;
    int resourceId;

    public CustomArrayAdapter(Context context, int resourceId ,ArrayList<Beginner> arrayList){
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
        view = inflater.inflate(R.layout.listview_layout,null);
        Button beginnerButton = (Button) view.findViewById(R.id.buttonLayout_beginner);
        TextView beginnerTextview = (TextView) view.findViewById(R.id.textView_beginner);

        beginnerButton.setText(arrayList.get(position).getInitial());
        beginnerButton.setBackgroundColor(view.getResources().getColor(arrayList.get(position).getColor()));
        beginnerTextview.setText(arrayList.get(position).getName());
        return view;
    }
}