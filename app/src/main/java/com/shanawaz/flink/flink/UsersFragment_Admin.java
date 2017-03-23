package com.shanawaz.flink.flink;


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


import java.util.List;


public class UsersFragment_Admin extends Fragment {
    TextView admin_username,admin_useremail,admin_userphone,admin_usergender,admin_userrole,admin_userstatus;
    Button admin_btn,student_btn,alumin_btn,employee_btn,accept_btn,reject_btn,block_btn;
    List<UserDetails> user;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.fragment_admin,container,false);
        RestBasicInfo restBasicInfo=new RestBasicInfo();
        String url=""+restBasicInfo.BASE_URL+"allusers";
        RestTemplate restTemplate = restBasicInfo.converters();
        user= restTemplate.getForObject(url,List.class);
        RecycleAdminUser_Adpter recycleAdminUser_adpter=new RecycleAdminUser_Adpter(user);
        final RecyclerView recyclerView_adminuser= (RecyclerView) view.findViewById(R.id.recycle_admin_user);
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),1);
        recyclerView_adminuser.setLayoutManager(layoutManager);
        recyclerView_adminuser.setAdapter(recycleAdminUser_adpter);

        final GestureDetector gestureDetector=new GestureDetector(new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {


                return true;
            }


        });

        recyclerView_adminuser.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View v = rv.findChildViewUnder(e.getX(), e.getY());
                if (v != null && gestureDetector.onTouchEvent(e)) {
                    final ObjectMapper mapper = new ObjectMapper();
                    final int pos=recyclerView_adminuser.getChildPosition(v);
                    final UserDetails user_det= mapper.convertValue(user.get(pos), UserDetails.class);

                    Toast.makeText(getActivity(), ""+user_det.getUsername(), Toast.LENGTH_SHORT).show();

                    //action to button

                    //Accepted
                    accept_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {



                        }
                    });

                    //Reject
                    reject_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    //Block
                    block_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

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


    public class RecycleAdminUser_Adpter extends RecyclerView.Adapter<RecycleAdminUser_Adpter.RecycleAdminuUser_Holder>{

        List<UserDetails> userDetails;


        public RecycleAdminUser_Adpter(List<UserDetails> user) {
            this.userDetails=user;
        }

        public class RecycleAdminuUser_Holder extends RecyclerView.ViewHolder {
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

            }
        }


        @Override
        public RecycleAdminuUser_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclervuholder_adminuser,parent,false);

            return new RecycleAdminuUser_Holder(view);
        }

        @Override
        public void onBindViewHolder(RecycleAdminuUser_Holder holder, int position) {

            ObjectMapper mapper = new ObjectMapper();
            final UserDetails user = mapper.convertValue(userDetails.get(position), UserDetails.class);
            admin_username.setText(""+user.getUsername());
            admin_useremail.setText(""+user.getEmail());
            admin_userphone.setText(""+user.getMobile());
            admin_usergender.setText(""+user.getGender());
            admin_userstatus.setText(""+user.getStatus());
            admin_userrole.setText(""+user.getRole());

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


}
