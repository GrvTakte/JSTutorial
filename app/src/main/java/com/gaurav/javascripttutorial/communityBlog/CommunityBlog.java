package com.gaurav.javascripttutorial.communityBlog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gaurav.javascripttutorial.MainActivity;
import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.SplashScreen;
import com.gaurav.javascripttutorial.adapter.CustomBlogAdapter;
import com.gaurav.javascripttutorial.model.Blog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gaurav on 02/11/17.
 */

public class CommunityBlog extends AppCompatActivity{

    Toolbar toolbar;
    CircleImageView userImage;
    TextView userName;
    Button sendButton;
    EditText editTextBlog;
    ListView blogListView;
    DatabaseReference mReference;
    ProgressDialog proDialog;

    FirebaseAuth auth;
    Handler handler;

    List<Blog> blogs;

    String Name, url, id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_community);

        mReference = FirebaseDatabase.getInstance().getReference("blogs");

        toolbar = (Toolbar) findViewById(R.id.toolbar_blog_community);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayUseLogoEnabled(false);
        }

        if (!isNetworkConnected()){
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Offline Access")
                    .setMessage("Please Be Online To See Latest Blogs!")
                    .setIcon(R.drawable.offline)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            dialog.show();
        }else {
            proDialog = new ProgressDialog(this);
            proDialog.setMessage("Loading, Please wait....");
            proDialog.show();
        }

        auth = FirebaseAuth.getInstance();
        userImage = (CircleImageView) findViewById(R.id.userImage);
        userName = (TextView) findViewById(R.id.userName);
        editTextBlog = (EditText) findViewById(R.id.editText_blog);
        blogListView = (ListView) findViewById(R.id.blog_listview);

        FirebaseUser user = auth.getCurrentUser();
        if (user!=null) {
            Name = user.getDisplayName();
            url = user.getPhotoUrl().toString();
            id = user.getUid();
            userName.setText(Name);

            Glide.with(this)
                    .load(url)
                    .into(userImage);
        }else {
            Intent intent = new Intent(CommunityBlog.this, SplashScreen.class);
            startActivity(intent);
        }
        sendButton = (Button) findViewById(R.id.blog_send_button);
        blogs = new ArrayList<>();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBlog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Updating list whenever user post blog.
                mReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        blogs.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            Blog blog = postSnapshot.getValue(Blog.class);
                            blogs.add(0,blog);
                        }
                        CustomBlogAdapter adapter = new CustomBlogAdapter(getApplicationContext(), blogs);
                        blogListView.setAdapter(adapter);
                        proDialog.dismiss();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private void saveBlog(){
        String id1 = mReference.push().getKey();
        String blogText = editTextBlog.getText().toString().trim();
        Calendar time = Calendar.getInstance();
        String currentTime = new SimpleDateFormat("HH:mm").format(time.getTime());
        String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        if (!TextUtils.isEmpty(blogText)) {
            Blog blog = new Blog(id1, Name, url, blogText, currentTime, currentDate,0);
            mReference.child(id1).setValue(blog);
            editTextBlog.setText("");
        }else {
            Toast.makeText(this, "Please Type Micro Blog", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            Intent intent = new Intent(CommunityBlog.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean isNetworkConnected(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}