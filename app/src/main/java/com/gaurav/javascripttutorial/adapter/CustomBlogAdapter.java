package com.gaurav.javascripttutorial.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.model.Blog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gaurav on 02/11/17.
 */

public class CustomBlogAdapter extends ArrayAdapter<Blog> {

    Context context;
    List<Blog> blogs;
    FirebaseAuth auth;
    DatabaseReference reference;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public CustomBlogAdapter(Context context, List<Blog> blogs){
        super(context, R.layout.blog_listview_layout,blogs);
        this.context = context;
        this.blogs = blogs;
        pref = context.getSharedPreferences("Likes",0);
        editor = pref.edit();
    }


    @Nullable
    @Override
    public Blog getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("blogs");
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.blog_listview_layout, null);
        }

        CircleImageView userImage = (CircleImageView) view.findViewById(R.id.imageUserBlog);
        TextView userName = (TextView) view.findViewById(R.id.textBlogUserName);
        TextView userBlog = (TextView) view.findViewById(R.id.textBlogMain);
        TextView userTime = (TextView) view.findViewById(R.id.textTimeBlog);
        TextView userDate = (TextView) view.findViewById(R.id.textDateBlog);
        final LikeButton likeButton = (LikeButton) view.findViewById(R.id.like_button);
        final TextView likeCount = (TextView) view.findViewById(R.id.like_count);
        final String id = blogs.get(position).getId();

        Glide.with(view).load(blogs.get(position).getImageurl()).into(userImage);
        userName.setText(blogs.get(position).getName());
        userBlog.setText(blogs.get(position).getBlog());
        userTime.setText(blogs.get(position).getTime());
        userDate.setText(blogs.get(position).getDate());
        likeCount.setText(String.valueOf(blogs.get(position).getLikeCounter()));

        boolean likedStatus = pref.getBoolean(id,false);

        if (likedStatus){
            likeButton.setLiked(true);
        }else {
            likeButton.setLiked(false);
        }

        likeButton.setOnLikeListener(new OnLikeListener() {

            @Override
            public void liked(LikeButton likeButton) {
                int i = Integer.parseInt(likeCount.getText().toString().trim());
                i++;
                likeCount.setText(String.valueOf(i));
                DatabaseReference likeRef = reference.child(blogs.get(position).getId());
                likeRef.child("likeCounter").setValue(i);
                editor.putBoolean(id,true);
                editor.commit();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                int i = Integer.parseInt(likeCount.getText().toString().trim());
                i--;
                likeCount.setText(String.valueOf(i));
                DatabaseReference likeRef = reference.child(blogs.get(position).getId());
                likeRef.child("likeCounter").setValue(i);
                editor.putBoolean(id,false);
                editor.commit();
            }
        });
        return view;
    }
}
