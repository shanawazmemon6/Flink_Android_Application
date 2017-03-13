package com.shanawaz.flink.flink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanawaz.flink.flink.model.JobApplied;
import com.shanawaz.flink.flink.model.JobDetails;
import com.shanawaz.flink.flink.model.UserDetails;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JobFullDescription extends AppCompatActivity {
       TextView job_title_text,job_desc,job_status,job_date,job_quali;
       Button apply;
    private String base_url="http://192.168.0.4:8086/Flink_BE/";
    List<HttpMessageConverter<?>> messageConverters;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_full_description);

         gson=new Gson();
        Intent getIntent=getIntent();
        final String jobList=getIntent.getStringExtra("job_list");
        final JobDetails job_details=gson.fromJson(jobList,JobDetails.class);


        job_title_text= (TextView) findViewById(R.id.desc_title);
        job_desc= (TextView) findViewById(R.id.job_desc);
        job_status=(TextView)findViewById(R.id.job_status);
        job_date= (TextView) findViewById(R.id.job_posted);
        job_quali=(TextView) findViewById(R.id.job_quali) ;
        apply=(Button)findViewById(R.id.job_apply) ;
        job_title_text.setText(""+job_details.getTitle());
        job_desc.setText(""+job_details.getDescription());
        job_status.setText(""+job_details.getStatus());
        job_date.setText(""+job_details.getDate_time());
        job_quali.setText(""+job_details.getQualification());
        String checkurl=""+base_url+"check";
        JobApplied jobApplied=new JobApplied();

        jobApplied.setJobid(job_details.getId());
        SharedPreferences sharedPreferences = getSharedPreferences("login_credentials", MODE_PRIVATE);
        final String user_details = sharedPreferences.getString("login_user", "");
        final UserDetails prefence_user = gson.fromJson(user_details, UserDetails.class);
        jobApplied.setUsername(prefence_user.getUsername());
        RestTemplate rest=new RestTemplate();
         messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        rest.setMessageConverters(messageConverters);
        boolean status=rest.postForObject(checkurl,jobApplied,Boolean.class);

        if(status){
            apply.setText("Apply");
        }else {
            apply.setText("Already Job Applied");

        }

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=""+base_url+"applyJob";
                RestTemplate re=new RestTemplate();
                re.setMessageConverters(messageConverters);
                SharedPreferences sharedPreferences = getSharedPreferences("login_credentials", MODE_PRIVATE);
                final String user_details = sharedPreferences.getString("login_user", "");
                final UserDetails prefence_user = gson.fromJson(user_details, UserDetails.class);
                JobApplied jobApplied=new JobApplied();
                String apply="applied";
                jobApplied.setStatus_job(apply);
                jobApplied.setJobid(job_details.getId());
                jobApplied.setUsername(prefence_user.getUsername());
                jobApplied.setJobtitle(job_details.getTitle());
                String  applied= re.postForObject(url,jobApplied,String.class);
                JobApplied jobapply=gson.fromJson(applied,JobApplied.class);
                if(jobapply.getCode().equals("200")){

                    startActivity(new Intent(getApplicationContext(),UserActivity.class));
                    Toast.makeText(JobFullDescription.this, "applied Successfully", Toast.LENGTH_SHORT).show();

                }



            }
        });

    }

}
