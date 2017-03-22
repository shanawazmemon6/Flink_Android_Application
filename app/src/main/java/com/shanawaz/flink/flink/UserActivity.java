package com.shanawaz.flink.flink;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;


public class UserActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;



    private ViewPager mViewPager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),getApplicationContext());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        Intent  get=getIntent();
        int val= get.getIntExtra("veiw_pager",0);
        mViewPager.setCurrentItem(val);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.Profile) {
            Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("login_credentials", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("login_user", "");
            if (editor.commit()) {
                startActivity(new Intent(getApplicationContext(), SplashScreen.class));
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
 class SectionsPagerAdapter extends FragmentPagerAdapter {

        Context user;

        public SectionsPagerAdapter(FragmentManager fm, Context user) {
            super(fm);
            this.user=user;
        }
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new BlogFragment();
                case 1:
                     return new JobFragment();

                default: return new BlogFragment();

            }

        }



        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Blog";
                case 1:
                    return "Job";

            }
            return null;
        }
    }

