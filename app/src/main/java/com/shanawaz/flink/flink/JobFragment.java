package com.shanawaz.flink.flink;


import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.shanawaz.flink.flink.model.JobDetails;
import com.shanawaz.flink.flink.model.UserDetails;

import org.springframework.http.HttpMethod;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.support.Base64;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JobFragment extends android.support.v4.app.Fragment {

private RecycleJobAdpter jobAdpter;
public RecyclerView recyclerView_job;
    private String base_url="http://192.168.0.3:8086/Flink_BE/";

    private Gson gson=new Gson();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.job_fragment,container,false);

        String url=""+base_url+"allJob";
        RestTemplate rest=new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        rest.setMessageConverters(messageConverters);
        List<JobDetails> job_list= rest.getForObject(url,List.class);
        recyclerView_job= (RecyclerView) view.findViewById(R.id.recycle_job);
        jobAdpter=new RecycleJobAdpter(job_list);
        LinearLayoutManager joblayoutManager=new LinearLayoutManager(getActivity());
        recyclerView_job.setLayoutManager(joblayoutManager);
        recyclerView_job.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getActivity(),joblayoutManager.getOrientation());
        recyclerView_job.addItemDecoration(dividerItemDecoration);
        recyclerView_job.setAdapter(jobAdpter);
        final GestureDetector gesture=new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        recyclerView_job.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View v=rv.findChildViewUnder(e.getX(),e.getY());

                if(v!=null&&gesture.onTouchEvent(e)){
                    Toast.makeText(getActivity(), ""+recyclerView_job.getChildPosition(v), Toast.LENGTH_SHORT).show();

                    FragmentManager fm=getFragmentManager();
                    FragmentTransaction fragmentTransaction=fm.beginTransaction();

                    fragmentTransaction.commit();
                   return true;
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        return view;
    }




    public class RecycleJobAdpter extends RecyclerView.Adapter<RecycleJobAdpter.JobViewHolder>{

        List<JobDetails> job_list;

       public RecycleJobAdpter(List<JobDetails> job_adp_list){
           this.job_list=job_adp_list;
       }



        public class JobViewHolder extends RecyclerView.ViewHolder {
          public   ImageView jobimage;
           public TextView  jobtitle;


            public JobViewHolder(View itemView) {
                super(itemView);
                jobimage= (ImageView) itemView.findViewById(R.id.job_image);
                jobtitle= (TextView) itemView.findViewById(R.id.job_title);

            }



        }

        @Override
        public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewholder_job,parent,false);

            return new JobViewHolder(view);
        }

        @Override
        public void onBindViewHolder(JobViewHolder holder, int position) {
             ObjectMapper mapper=new ObjectMapper();
            JobDetails jobDetails= mapper.convertValue(job_list.get(position),JobDetails.class);
            holder.jobtitle.setText(""+jobDetails.getTitle());
            holder.jobimage.setImageResource(R.mipmap.place);

        }

        @Override
        public int getItemViewType(int position) {

            return super.getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            return job_list.size() ;
        }


    }



}
