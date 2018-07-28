package com.example.tenx.test272.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tenx.test272.Activity.Adapters.FragmentAdapter;
import com.example.tenx.test272.R;

import java.util.Objects;


public class HomeActivity extends AppCompatActivity {


    TabLayout tabLayout;
    FragmentAdapter fragAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //setting up the tablayout and viewPager
        fragAdapter = new FragmentAdapter(getSupportFragmentManager());
        tabLayout = findViewById(R.id.home_tablayout);
        viewPager = findViewById(R.id.contaier_view_pager);
        viewPager.setAdapter(fragAdapter);
        tabLayout.setupWithViewPager(viewPager);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.tablayout_icon_home);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.tablayout_icon_whatshot);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.tablayout_icon_notifications);



    }








}
