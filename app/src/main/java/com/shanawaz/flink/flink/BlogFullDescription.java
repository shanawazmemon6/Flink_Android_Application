package com.shanawaz.flink.flink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shanawaz.flink.flink.model.BlogDetails;

public class BlogFullDescription extends AppCompatActivity {

    TextView blog_title,blog_breif,blog_writtenby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_full_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        Explode explode=new Explode();
        explode.setDuration(1000);
        getWindow().setEnterTransition(explode);





       Gson gson=new Gson();
        Intent getIntent=getIntent();
        final String blogList=getIntent.getStringExtra("blog_list");
        final BlogDetails blog_details=gson.fromJson(blogList,BlogDetails.class);

        blog_writtenby=(TextView)findViewById(R.id.blog_writtenname_desc);
        blog_breif=(TextView)findViewById(R.id.blog_breif_desc);
        blog_title=(TextView)findViewById(R.id.blog_title);

        blog_writtenby.setText(""+blog_details.getWrittenby());
        blog_breif.setText(""+blog_details.getBrief());
        blog_title.setText(""+blog_details.getTitle());

    }

}
