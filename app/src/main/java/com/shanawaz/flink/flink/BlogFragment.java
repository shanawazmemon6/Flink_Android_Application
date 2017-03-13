package com.shanawaz.flink.flink;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BlogFragment extends Fragment{

    private String base_url="http://192.168.0.4:8086/Flink_BE/";


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.blog_fragment,container,false);



        return view;

    }
}
