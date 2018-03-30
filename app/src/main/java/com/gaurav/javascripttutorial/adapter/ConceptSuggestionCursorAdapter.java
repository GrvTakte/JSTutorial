package com.gaurav.javascripttutorial.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.model.SuggestTutes;
import com.gaurav.javascripttutorial.subactivities.SearchResultActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaurav on 3/28/2018.
 */

public class ConceptSuggestionCursorAdapter extends BaseAdapter implements Filterable {

    List<SuggestTutes> originalList;
    List<SuggestTutes> filteredList;
    private LayoutInflater inflater;
    private ItemFilter mFilter = new ItemFilter();
    private Context context;

    public ConceptSuggestionCursorAdapter(Context context, List<SuggestTutes> data){
        this.originalList = data;
        this.filteredList = data;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_custom_suggestion,null);

            viewHolder = new ViewHolder();
            viewHolder.topic = (TextView) convertView.findViewById(R.id.topic);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.topic.setText(filteredList.get(position).getTopic());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchResultActivity.class);
                intent.putExtra("searchResult",filteredList.get(position).getTopic().toLowerCase());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder{
        TextView topic;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<SuggestTutes> list = originalList;

            int count = list.size();

            final ArrayList<SuggestTutes> nList = new ArrayList<>(count);

            String filterableString;

            for (int i=0; i<count; i++){
                filterableString = list.get(i).getTopic();
                if (filterableString.toLowerCase().contains(filterString)){
                    nList.add(new SuggestTutes(filterableString));
                }
            }

            results.values = nList;
            results.count = nList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<SuggestTutes>) results.values;
            notifyDataSetChanged();
        }
    }

}
