package com.shanawaz.flink.flink;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shanawaz.flink.flink.model.UserDetails;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    Button loginbtn;
    EditText email;
    EditText passowrd;
    String base_url="http://192.168.0.7:8086/Flink_BE/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_splash_screen);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        loginbtn = (Button) findViewById(R.id.loginbtn);
        email = (EditText) findViewById(R.id.email);
        passowrd = (EditText) findViewById(R.id.password);
         Gson gson=new Gson();

        SharedPreferences sharedPreferences = getSharedPreferences("login_credentials", MODE_PRIVATE);
        final String user_details = sharedPreferences.getString("login_user", "");
        if (!user_details.equals("")) {
            final UserDetails prefence_user = gson.fromJson(user_details, UserDetails.class);
            if (prefence_user.getCode().equals("200")) {
                UserDetails  userDetails = new UserDetails();
                 userDetails.setUsername(prefence_user.getUsername());
                 userDetails.setPassword(prefence_user.getPassword());
                login(userDetails);
            }

        }

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDetails  userDetails = new UserDetails();
                String mailid = email.getText().toString();
                userDetails.setUsername(mailid);
                userDetails.setPassword(passowrd.getText().toString());
                login(userDetails);

            }
        });
}


public void login(UserDetails user_obj){

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
    String url = "" + base_url + "loginAuthentication";
    RestTemplate rest = new RestTemplate();
    List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
    messageConverters.add(new FormHttpMessageConverter());
    messageConverters.add(new StringHttpMessageConverter());
    messageConverters.add(new MappingJackson2HttpMessageConverter());
    rest.setMessageConverters(messageConverters);
    String user = rest.postForObject(url, user_obj, String.class);
    UserDetails us_obj = gson.fromJson(user, UserDetails.class);
    String status = us_obj.getStatus();
    String code = us_obj.getCode();
    String role = us_obj.getRole();



    if (code.equals("200")) {
        String logindetails = gson.toJson(us_obj);

        if ((role.equals("Student") || role.equals("Alumni") || role.equals("Alumni")) && (status.equals("accepted"))) {

            SharedPreferences  sharedPreferences = getSharedPreferences("login_credentials", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("login_user", logindetails);
            if (editor.commit()) {
                startActivity(new Intent(getApplicationContext(), UserActivity.class));
                finish();
            }

        } else if (role.equals("Admin")) {

            SharedPreferences   sharedPreferences = getSharedPreferences("login_credentials", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("login_user", logindetails);
            if (editor.commit()) {
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                finish();

            }

        }

    }




}

}








