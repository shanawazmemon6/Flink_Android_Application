package com.shanawaz.flink.flink;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shanawaz.flink.flink.model.BlogDetails;
import com.shanawaz.flink.flink.model.JobDetails;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class BlogFragment extends Fragment{

     RecyclerView recyclerView_Blog;
    private String base_url="http://192.168.0.4:8086/Flink_BE/";
    private RecyclerBlogAdpter recyclerBlogAdpter;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.blog_fragment,container,false);
        String url = "" + base_url + "allBlog";
        RestTemplate rest = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        rest.setMessageConverters(messageConverters);

        recyclerView_Blog= (RecyclerView) view.findViewById(R.id.recycler_blog);
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),1);
        recyclerView_Blog.setLayoutManager(layoutManager);
        recyclerView_Blog.setHasFixedSize(true);
        final List<BlogDetails> blog_list = rest.getForObject(url, List.class);
        recyclerBlogAdpter=new RecyclerBlogAdpter(blog_list);
        recyclerView_Blog.setAdapter(recyclerBlogAdpter);

        return view;

    }

    public  class RecyclerBlogAdpter extends RecyclerView.Adapter<RecyclerBlogAdpter.BlogViewHolder>{

        List<BlogDetails> blogDetailses;

        public RecyclerBlogAdpter(List<BlogDetails> blogDetailses) {
            this.blogDetailses=blogDetailses;

        }

        public class BlogViewHolder extends RecyclerView.ViewHolder{
            public ImageView blog_user_image;
            public TextView blog_username,blog_title,blog_desc;

            public BlogViewHolder(View itemView) {
                super(itemView);

                blog_user_image=(ImageView)itemView.findViewById(R.id.blog_userimage);
                blog_username=(TextView)itemView.findViewById(R.id.blog_username);
                blog_title=(TextView)itemView.findViewById(R.id.blog_title);

                blog_desc=(TextView)itemView.findViewById(R.id.blog_desc);





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
             holder.blog_username.setText(""+blogDetails.getWrittenby());
             holder.blog_title.setText(""+blogDetails.getTitle());
            holder.blog_desc.setText(""+blogDetails.getDescription());

        }

        @Override
        public int getItemCount() {


            return blogDetailses.size();

        }






    }


}
