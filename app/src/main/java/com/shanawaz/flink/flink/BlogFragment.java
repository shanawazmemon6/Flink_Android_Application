package com.shanawaz.flink.flink;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.shanawaz.flink.flink.model.BlogDetails;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BlogFragment extends Fragment{

     RecyclerView recyclerView_Blog;
    private RecyclerBlogAdpter recyclerBlogAdpter;
    public ImageView blog_user_image;
    public TextView blog_username,blog_title,blog_desc;
    public  List<BlogDetails> blog_list;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.blog_fragment,container,false);

        RestBasicInfo restBasicInfo=new RestBasicInfo();

        String url = "" + restBasicInfo.BASE_URL + "allBlog";
        final RestTemplate rest =restBasicInfo.converters();


        recyclerView_Blog= (RecyclerView) view.findViewById(R.id.recycler_blog);
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),1);

        recyclerView_Blog.setLayoutManager(layoutManager);
        recyclerView_Blog.setHasFixedSize(true);
        blog_list = rest.getForObject(url, List.class);
        recyclerBlogAdpter=new RecyclerBlogAdpter(blog_list);
        recyclerView_Blog.setAdapter(recyclerBlogAdpter);


               return view;

    }

    public  class RecyclerBlogAdpter extends RecyclerView.Adapter<RecyclerBlogAdpter.BlogViewHolder>{

        List<BlogDetails> blogDetailses;

        public RecyclerBlogAdpter(List<BlogDetails> blogDetailses) {
            this.blogDetailses=blogDetailses;

        }

        public class BlogViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

            public BlogViewHolder(View itemView) {
                super(itemView);

                blog_user_image=(ImageView)itemView.findViewById(R.id.blog_userimage);
                blog_username=(TextView)itemView.findViewById(R.id.blog_username);
                blog_title=(TextView)itemView.findViewById(R.id.blog_title);
                blog_desc=(TextView)itemView.findViewById(R.id.blog_desc);

             itemView.setOnClickListener(this);




            }

            @Override
            public void onClick(View v) {
                Pair[] pair=new Pair[3];
                pair[0]=new Pair<View,String>(v.findViewById(R.id.blog_userimage),"bloguserimage");
                pair[1]=new Pair<View,String>(v.findViewById(R.id.blog_username),"blogusername");
                pair[2]=new Pair<View,String>(v.findViewById(R.id.blog_title),"blogtitle");


                ActivityOptionsCompat activityOptions=ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),pair);


                Intent blog_fulldescription = new Intent(getActivity(), BlogFullDescription.class);
                int posi = recyclerView_Blog.getChildPosition(v);
                Gson gson=new Gson();
                ObjectMapper mapper = new ObjectMapper();
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                mapper.setDateFormat(df);
                BlogDetails blogDetails = mapper.convertValue(blog_list.get(posi), BlogDetails.class);
                String job_json_string = gson.toJson(blogDetails);
                blog_fulldescription.putExtra("blog_list", job_json_string);
                startActivity(blog_fulldescription,activityOptions.toBundle());
            }
        }

        @Override
        public BlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
             View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_blog,parent,false);


            return new BlogViewHolder(view);
        }

        @Override
        public void onBindViewHolder(BlogViewHolder holder, int position) {





            ObjectMapper mapper = new ObjectMapper();
            BlogDetails blogDetails = mapper.convertValue(blogDetailses.get(position), BlogDetails.class);
             blog_username.setText(""+blogDetails.getWrittenby());
            blog_title.setText(""+blogDetails.getTitle());
            blog_desc.setText(""+blogDetails.getDescription());

        }

        @Override
        public int getItemCount() {


            return blogDetailses.size();

        }






    }






}
