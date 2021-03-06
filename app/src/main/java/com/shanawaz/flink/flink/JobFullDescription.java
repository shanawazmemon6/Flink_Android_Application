package com.shanawaz.flink.flink;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanawaz.flink.flink.model.JobApplied;
import com.shanawaz.flink.flink.model.JobDetails;
import com.shanawaz.flink.flink.model.UserDetails;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class JobFullDescription extends AppCompatActivity {
       TextView job_title_text,job_desc,job_status,job_date,job_quali;
       Button apply;
    RestBasicInfo restBasicInfo;

    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_full_description);
        /*getWindow().setStatusBarColor(R.color.Status);*/

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
         restBasicInfo=new RestBasicInfo();
        String checkurl=""+restBasicInfo.BASE_URL+"check";
        JobApplied jobApplied=new JobApplied();


        jobApplied.setJobid(job_details.getId());
        SharedPreferences sharedPreferences = getSharedPreferences("login_credentials", MODE_PRIVATE);
        final String user_details = sharedPreferences.getString("login_user", "");
        final UserDetails prefence_user = gson.fromJson(user_details, UserDetails.class);
        jobApplied.setUsername(prefence_user.getUsername());
        final RestTemplate rest =restBasicInfo.converters();


        boolean status=rest.postForObject(checkurl,jobApplied,Boolean.class);

        if(status){
            apply.setText("Apply");
        }else {
            apply.setText("Already Job Applied");

        }

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=""+restBasicInfo.BASE_URL+"applyJob";
                 RestTemplate re =restBasicInfo.converters();


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


    @Override
    public void onBackPressed() {

    /*    Intent intent=new Intent(getApplicationContext(),UserActivity.class);
        intent.putExtra("veiw_pager",1);
        startActivity(intent);*/



        super.onBackPressed();


    }
}
