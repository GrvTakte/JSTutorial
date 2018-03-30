package com.gaurav.javascripttutorial.subactivities;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.gaurav.javascripttutorial.R;
import com.gaurav.javascripttutorial.adapter.QuizReportAdapter;
import com.gaurav.javascripttutorial.model.Question;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 09/10/17.
 */

public class QuizReportActivity extends AppCompatActivity {

    private List<Question> arrayList;
    private ListView reportList;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_report_activity);

        arrayList = new ArrayList<>();
        addQuestionsToArray();
        reportList = (ListView) findViewById(R.id.quiz_report_listview);
        QuizReportAdapter adapter = new QuizReportAdapter(getApplicationContext(),R.layout.quiz_report_list_layout,arrayList);
        reportList.setAdapter(adapter);
    }

    private void addQuestionsToArray(){
        File rootPath = new File(Environment.getExternalStorageDirectory(),"questions");
        final File localFile = new File(rootPath,"questions.json");
        try {
            String jsonStr = null;
            FileInputStream inputStream = new FileInputStream(localFile);
            try {
                FileChannel fc = inputStream.getChannel();
                MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                jsonStr = Charset.defaultCharset().decode(bb).toString();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                inputStream.close();
            }
            JSONObject object = new JSONObject(jsonStr);
            JSONArray array = object.getJSONArray("array");
            for (int i=0; i<array.length();i++){
                JSONObject obj = array.getJSONObject(i);
                String question = obj.getString("questions");
                String answer = obj.getString("answer");
                arrayList.add(new Question(question,answer));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        toolbar = (Toolbar) findViewById(R.id.toolbar_quiz_report);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Quiz Report");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();

        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}