package com.shanawaz.flink.flink;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shanawaz.flink.flink.model.BlogDetails;
import com.shanawaz.flink.flink.model.UserDetails;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UsersFragment_Admin extends Fragment {
    TextView admin_username,admin_useremail,admin_userphone,admin_usergender,admin_userrole,admin_userstatus;
    Button admin_btn,student_btn,alumin_btn,employee_btn,accept_btn,reject_btn,block_btn;
   public List<UserDetails> user_list;
    RestBasicInfo restBasicInfo;
    RestTemplate restTemplate;
    RecyclerView recyclerView_adminuser;
    RecycleAdminUser_Adpter recycleAdminUser_adpter;
    public String allurl;
    int po;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.fragment_admin,container,false);
         restBasicInfo=new RestBasicInfo();
         allurl=""+restBasicInfo.BASE_URL+"allusers";
         restTemplate = restBasicInfo.converters();
        user_list= restTemplate.getForObject(allurl,List.class);
         recycleAdminUser_adpter=new RecycleAdminUser_Adpter(user_list);

          recyclerView_adminuser= (RecyclerView) view.findViewById(R.id.recycle_admin_user);
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),1);
        recyclerView_adminuser.setLayoutManager(layoutManager);
        recyclerView_adminuser.setAdapter(recycleAdminUser_adpter);


        return view;
    }


    public class RecycleAdminUser_Adpter extends RecyclerView.Adapter<RecycleAdminUser_Adpter.RecycleAdminuUser_Holder> {

        List<UserDetails> userDetails;


        public RecycleAdminUser_Adpter(List<UserDetails> user) {
            this.userDetails=user;
        }

        public class RecycleAdminuUser_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public RecycleAdminuUser_Holder(View itemView) {
                super(itemView);

                admin_username= (TextView) itemView.findViewById(R.id.admin_username);
                admin_useremail= (TextView) itemView.findViewById(R.id.admin_useremail);
                admin_userphone= (TextView) itemView.findViewById(R.id.admin_userphone);
                admin_usergender= (TextView) itemView.findViewById(R.id.admin_usergender);
                admin_userrole= (TextView) itemView.findViewById(R.id.admin_userrole);
                admin_userstatus= (TextView) itemView.findViewById(R.id.admin_userstatus);

                accept_btn=(Button)itemView.findViewById(R.id.accept_btn);
                reject_btn=(Button)itemView.findViewById(R.id.reject_btn);
                block_btn=(Button)itemView.findViewById(R.id.block_btn);

                admin_btn=(Button)itemView.findViewById(R.id.admin_btn);
                employee_btn=(Button)itemView.findViewById(R.id.employee_btn);
                student_btn=(Button)itemView.findViewById(R.id.student_btn);
                alumin_btn=(Button)itemView.findViewById(R.id.alumin_btn);
                 itemView.setOnClickListener(this);



            }

            @Override
            public void onClick(View v) {

            }
        }


        @Override
        public RecycleAdminuUser_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclervuholder_adminuser,parent,false);

            return new RecycleAdminuUser_Holder(view);
        }

        @Override
        public void onBindViewHolder(RecycleAdminuUser_Holder holder, final int position) {

            final ObjectMapper mapper = new ObjectMapper();
            final UserDetails user = mapper.convertValue(userDetails.get(position), UserDetails.class);



            final RestTemplate restTemplate = restBasicInfo.converters();

            admin_username.setText(""+user.getUsername());
            admin_useremail.setText(""+user.getEmail());
            admin_userphone.setText(""+user.getMobile());
            admin_usergender.setText(""+user.getGender());
            admin_userstatus.setText(""+user.getStatus());
            admin_userrole.setText(""+user.getRole());

            final String status_url=""+restBasicInfo.BASE_URL+"status/{username}/{status}";
            final String role_url=""+restBasicInfo.BASE_URL+"role/{username}/{role}";


            //action
            accept_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Map<String,String> statusmap=new HashMap<String, String>();
                    statusmap.put("username",user.getUsername());
                    statusmap.put("status","accepted");
                    restTemplate.getForObject(status_url,String.class,statusmap);
                    List<UserDetails>  user_inner= restTemplate.getForObject(allurl,List.class);
                    user_list=user_inner;
                    update_adpter(user_inner,position);
                }});

            reject_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,String> statusmap=new HashMap<String, String>();
                    statusmap.put("username",user.getUsername());
                    statusmap.put("status","rejected");
                    restTemplate.getForObject(status_url,String.class,statusmap);
                  List<UserDetails>  user_inner= restTemplate.getForObject(allurl,List.class);
                             user_list=user_inner;
                    update_adpter(user_inner,position);
                }});
            block_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,String> statusmap=new HashMap<String, String>();
                    statusmap.put("username",user.getUsername());
                    statusmap.put("status","blocked");
                    restTemplate.getForObject(status_url,String.class,statusmap);
                    List<UserDetails>  user_inner= restTemplate.getForObject(allurl,List.class);
                    user_list=user_inner;
                    update_adpter(user_inner,position);
                }});
            //Role button

            admin_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,String> rolemap=new HashMap<String, String>();
                    rolemap.put("username",user.getUsername());
                    rolemap.put("role","Admin");
                    restTemplate.getForObject(role_url,String.class,rolemap);
                    List<UserDetails>  user_inner= restTemplate.getForObject(allurl,List.class);
                    user_list=user_inner;
                    update_adpter(user_inner,position);
                }
            });

            student_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,String> rolemap=new HashMap<String, String>();
                    rolemap.put("username",user.getUsername());
                    rolemap.put("role","Student");
                    restTemplate.getForObject(role_url,String.class,rolemap);
                    List<UserDetails>  user_inner= restTemplate.getForObject(allurl,List.class);
                    user_list=user_inner;
                    update_adpter(user_inner,position);
                }
            });

            alumin_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,String> rolemap=new HashMap<String, String>();
                    rolemap.put("username",user.getUsername());
                    rolemap.put("role","Alumni");
                    restTemplate.getForObject(role_url,String.class,rolemap);
                    List<UserDetails>  user_inner= restTemplate.getForObject(allurl,List.class);
                    user_list=user_inner;
                    update_adpter(user_inner,position);
                }
            });

            employee_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,String> rolemap=new HashMap<String, String>();
                    rolemap.put("username",user.getUsername());
                    rolemap.put("role","Employee");
                    restTemplate.getForObject(role_url,String.class,rolemap);
                    List<UserDetails>  user_inner= restTemplate.getForObject(allurl,List.class);
                    user_list=user_inner;
                    update_adpter(user_inner,position);

                }
            });


            //Color to button
            if(user.getStatus().equals("accepted")){
                accept_btn.setBackgroundColor(getResources().getColor(R.color.Status));

            }
            if(user.getStatus().equals("rejected")){
                reject_btn.setBackgroundColor(getResources().getColor(R.color.Status));
            }
            if(user.getStatus().equals("blocked")){
                block_btn.setBackgroundColor(getResources().getColor(R.color.Status));

            }

            if(user.getRole().equals("Admin")){
                admin_btn.setBackgroundColor(getResources().getColor(R.color.Status));

            }
            if(user.getRole().equals("Alumni")){
                alumin_btn.setBackgroundColor(getResources().getColor(R.color.Status));
            }
            if(user.getRole().equals("Employee")){
                employee_btn.setBackgroundColor(getResources().getColor(R.color.Status));

            }
            if(user.getRole().equals("Student")){
                student_btn.setBackgroundColor(getResources().getColor(R.color.Status));
            }



        }



        @Override
        public int getItemCount() {
            return userDetails.size();
        }
    }
public void update_adpter(List<UserDetails> user_inner,int position){
    recycleAdminUser_adpter=new RecycleAdminUser_Adpter(user_inner);
    recyclerView_adminuser.setAdapter(recycleAdminUser_adpter);
    recyclerView_adminuser.scrollToPosition(position);
}

}
