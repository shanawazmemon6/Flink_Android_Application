package com.shanawaz.flink.flink;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.shanawaz.flink.flink.model.JobDetails;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JobFragment extends android.support.v4.app.Fragment {

private RecycleJobAdpter jobAdpter;
public RecyclerView recyclerView_job;
    public ImageView jobimage;
    public TextView jobtitle;


    private Gson gson=new Gson();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.job_fragment, container, false);
        RestBasicInfo restBasicInfo=new RestBasicInfo();

        String url = "" + restBasicInfo.BASE_URL + "allJob";


          Fade fade_in=new Fade(Fade.IN);
        fade_in.setDuration(1000);
        getActivity().getWindow().setEnterTransition(fade_in);


        Fade fade_out=new Fade(Fade.OUT);
          getActivity().getWindow().setExitTransition(fade_out);

        final RestTemplate rest =restBasicInfo.converters();


        final List<JobDetails> job_list = rest.getForObject(url, List.class);
        recyclerView_job = (RecyclerView) view.findViewById(R.id.recycle_job);
        jobAdpter = new RecycleJobAdpter(job_list);
        LinearLayoutManager joblayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView_job.setLayoutManager(joblayoutManager);
        recyclerView_job.setHasFixedSize(true);
        recyclerView_job.setAdapter(jobAdpter);
        return view;
    }
      public   class RecycleJobAdpter extends RecyclerView.Adapter<RecycleJobAdpter.JobViewHolder> {
            List<JobDetails> job_list;

            public RecycleJobAdpter(List<JobDetails> job_adp_list) {
                this.job_list = job_adp_list;
            }


        public     class JobViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



                public JobViewHolder(View itemView) {
                    super(itemView);
                    jobimage = (ImageView) itemView.findViewById(R.id.job_image);
                    jobtitle = (TextView) itemView.findViewById(R.id.job_title);
                   itemView.setOnClickListener(this);
                }


            @Override
            public void onClick(View v) {

                Pair[] pairs=new Pair[2];
                pairs[0]=new Pair<View,String>(v.findViewById(R.id.job_image),"jobimage");
                pairs[1]=new Pair<View,String>(v.findViewById(R.id.job_title),"jobtitle");
              ActivityOptions activityOptions=  ActivityOptions.makeSceneTransitionAnimation(getActivity(),pairs);
                Intent job_fulldescription = new Intent(getActivity(), JobFullDescription.class);
                ObjectMapper mapper = new ObjectMapper();
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                mapper.setDateFormat(df);
                JobDetails jobDetails = mapper.convertValue(job_list.get(getItemViewType()), JobDetails.class);
                String job_json_string = gson.toJson(jobDetails);
                job_fulldescription.putExtra("job_list", job_json_string);
                startActivity(job_fulldescription,activityOptions.toBundle());

            }
        }

            @Override
            public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewholder_job, parent, false);

                return new JobViewHolder(view);
            }

            @Override
            public void onBindViewHolder(JobViewHolder holder, int position) {

                ObjectMapper mapper = new ObjectMapper();
                JobDetails jobDetails = mapper.convertValue(job_list.get(position), JobDetails.class);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
                mapper.setDateFormat(df);
                jobtitle.setText("" + jobDetails.getTitle());
                jobimage.setImageResource(R.mipmap.place);

            }

            @Override
            public int getItemViewType(int position) {

                return position;
            }

            @Override
            public int getItemCount() {
                return job_list.size();
            }


        }


}
