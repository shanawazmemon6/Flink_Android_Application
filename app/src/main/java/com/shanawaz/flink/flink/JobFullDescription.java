package com.shanawaz.flink.flink;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanawaz.flink.flink.model.JobDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JobFullDescription extends AppCompatActivity {
       TextView job_title_text,job_desc,job_status,job_date,job_quali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_full_description);

        Gson gson=new Gson();
        Intent getIntent=getIntent();
        String jobList=getIntent.getStringExtra("job_list");
        JobDetails job_details=gson.fromJson(jobList,JobDetails.class);


        job_title_text= (TextView) findViewById(R.id.desc_title);
        job_desc= (TextView) findViewById(R.id.job_desc);
        job_status=(TextView)findViewById(R.id.job_status);
        job_date= (TextView) findViewById(R.id.job_posted);
        job_quali=(TextView) findViewById(R.id.job_quali) ;

        job_title_text.setText(""+job_details.getTitle());
        job_desc.setText(""+job_details.getDescription());
        job_status.setText(""+job_details.getStatus());
        job_date.setText(""+job_details.getDate_time());
        job_quali.setText(""+job_details.getQualification());

    }

}
