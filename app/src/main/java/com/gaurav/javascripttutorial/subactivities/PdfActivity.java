package com.gaurav.javascripttutorial.subactivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.adapter.CustomPdfAdapter;
import com.gaurav.javascripttutorial.model.PdfInfo;

import java.util.ArrayList;

/**
 * Created by gaurav on 19/09/17.
 */

public class PdfActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private ArrayList<PdfInfo> arrayList;
    private GridView gridview;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar_pdf);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Reference Pdf");
        }

        gridview = (GridView) findViewById(R.id.gridview_pdf);
        arrayList = new ArrayList<>();
        addBookToArrayList();

        CustomPdfAdapter adapter = new CustomPdfAdapter(getApplicationContext(),R.layout.pdf_listview_layout,arrayList);
        gridview.setAdapter(adapter);

        pref = getSharedPreferences("MyUrl",0);
        editor = pref.edit();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isNetworkAvailable()) {
                    String url = arrayList.get(position).getBookUrl();
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, "application/pdf");
                    if (intent.resolveActivity(getPackageManager())!=null) {
                        startActivity(intent);
                    }else {
                        Toast.makeText(PdfActivity.this, "You don't have proper application to do this action", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(PdfActivity.this, "Please check internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void addBookToArrayList(){
        //newly added pdf books list
        arrayList.add(new PdfInfo(R.drawable.principle_object_oriented_js,"http://solomon.ipv6.club.tw/Course/JavaScript/the-principles-of-object-oriented-javascript.pdf"));
        arrayList.add(new PdfInfo(R.drawable.learning_web_design,"http://www.reedbushey.com/60Learning%20Web%20Design%204th%20Edition.pdf"));
        arrayList.add(new PdfInfo(R.drawable.js_and_jquery,"http://web-algarve.com/books/JS%20AJAX%20jquery%20&%20angular/JavaScript%20&%20jQuery-%20The%20Missing%20Manual,%203rd%20Edition.pdf"));
        arrayList.add(new PdfInfo(R.drawable.functional_js,"http://pepa.holla.cz/wp-content/uploads/2016/08/Functional-Programming-in-JavaScript.pdf"));

        //oldlist
        arrayList.add(new PdfInfo(R.drawable.head_first_javascript,"http://www.psu.edu.sa/Colleges/Trade/DigitalLibrary/DocumentLibrary/Documents/head-first-javascript.22.pdf"));
        arrayList.add(new PdfInfo(R.drawable.professional_javascript_for_web_developers_3rd_edition,"http://files.cnblogs.com/files/coolicer/JavaScript%E9%AB%98%E7%BA%A7%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1%E7%AC%AC%E4%BA%8C%E7%89%88.pdf"));
        arrayList.add(new PdfInfo(R.drawable.javascript_for_kids,"http://pepa.holla.cz/wp-content/uploads/2015/11/JavaScript-for-Kids.pdf"));
        arrayList.add(new PdfInfo(R.drawable.eloquent_javascript_a_modern_introduction_to_programming,"http://eloquentjavascript.net/Eloquent_JavaScript.pdf"));
        arrayList.add(new PdfInfo(R.drawable.javascript_the_goodparts,"https://7chan.org/pr/src/OReilly_JavaScript_The_Good_Parts_May_2008.pdf"));
        arrayList.add(new PdfInfo(R.drawable.you_dont_know_js,"http://dl.finebook.ir/book/8f/13126.pdf"));
        arrayList.add(new PdfInfo(R.drawable.javascript_the_definite_guide,"http://www.arenahome.org/dir/B%20Per%20imparare%20e%20capire/informatica/musica/JavaScript%20-%20The%20Definitive%20Guide.pdf"));
        arrayList.add(new PdfInfo(R.drawable.effective_javascript,"http://ptgmedia.pearsoncmg.com/images/9780321812186/samplepages/0321812182.pdf"));
        arrayList.add(new PdfInfo(R.drawable.beginning_javascript,"http://pdf.th7.cn/down/files/1508/Beginning%20JavaScript,%205th%20Edition.pdf"));
        arrayList.add(new PdfInfo(R.drawable.javascript_design_pattern,"http://sd.blackball.lv/library/JavaScript_Patterns_(2010).pdf"));



    }

    @Override
    protected void onStart() {
        super.onStart();
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
