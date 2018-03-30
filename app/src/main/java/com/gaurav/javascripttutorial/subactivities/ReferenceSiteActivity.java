package com.gaurav.javascripttutorial.subactivities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.adapter.CustomReferenceSiteAdapter;
import com.gaurav.javascripttutorial.model.ReferenceSiteInfo;

import java.util.ArrayList;

/**
 * Created by gaurav on 19/09/17.
 */

public class ReferenceSiteActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private ListView listView;
    private ArrayList<ReferenceSiteInfo> arrayList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reference_site_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar_reference_site);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Reference Site");
        }

        listView = (ListView) findViewById(R.id.listview_reference_site);
        arrayList = new ArrayList<>();
        addDataToArrayList();
        CustomReferenceSiteAdapter adapter = new CustomReferenceSiteAdapter(getApplicationContext(),R.layout.reference_listview_layout,arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (isNetworkAvailable()) {
                    String url = arrayList.get(position).getWebsiteUrl();
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }else {
                    Toast.makeText(ReferenceSiteActivity.this, "Please check internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void addDataToArrayList(){
        arrayList.add(new ReferenceSiteInfo("Javascript.com","https://www.javascript.com/"));
        arrayList.add(new ReferenceSiteInfo("W3Schools","https://www.w3schools.com/js/default.asp"));
        arrayList.add(new ReferenceSiteInfo("Mozilla Developer Network","https://developer.mozilla.org/en-US/docs/Web/JavaScript"));
        arrayList.add(new ReferenceSiteInfo("Superhero.js","http://superherojs.com/"));
        arrayList.add(new ReferenceSiteInfo("LetsCodeJavaScript","http://www.letscodejavascript.com/"));
        arrayList.add(new ReferenceSiteInfo("Code Avengers","https://www.codeavengers.com/"));
        arrayList.add(new ReferenceSiteInfo("Codeacademy","https://www.codecademy.com/learn/introduction-to-javascript"));
        arrayList.add(new ReferenceSiteInfo("Eduonix","https://www.eduonix.com/courses/Web-Development/Learn-Javascript-And-JQuery-From-Scratch"));
        arrayList.add(new ReferenceSiteInfo("Adobe KnowHow","https://www.adobeknowhow.com/courselanding/learn-javascript-basics"));
        arrayList.add(new ReferenceSiteInfo("AboutTech","https://www.thoughtco.com/javascript-programming-4133476"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

}
