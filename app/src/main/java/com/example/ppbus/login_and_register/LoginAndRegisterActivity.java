package com.example.ppbus.login_and_register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ppbus.R;
import com.google.android.material.tabs.TabLayout;

public class LoginAndRegisterActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPaperAdapter viewPaperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);


        initializeVariable();

        setUpTabLayoutWithViewPaper();

    }

    private void initializeVariable() {
        tabLayout = findViewById(R.id.tl_login_signup);
        viewPager2 = findViewById(R.id.vp2_login_signup);
    }

    private void setUpTabLayoutWithViewPaper() {

        tabLayout.addTab(tabLayout.newTab().setText("LOGIN"));
        tabLayout.addTab(tabLayout.newTab().setText("REGISTER"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPaperAdapter = new ViewPaperAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(viewPaperAdapter);
        for (int i = 0; i <= 1; i++){
            TextView textView = (TextView) LayoutInflater.from(LoginAndRegisterActivity.this).inflate(R.layout.tab_title, null);
            tabLayout.getTabAt(i).setCustomView(textView);
        }

        View tab0 = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(0);
        ViewGroup.MarginLayoutParams p0 = (ViewGroup.MarginLayoutParams) tab0.getLayoutParams();
        p0.setMargins(0, 0, 70, 0);
        tab0.requestLayout();

        View tab1 = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(1);
        ViewGroup.MarginLayoutParams p1 = (ViewGroup.MarginLayoutParams) tab1.getLayoutParams();
        p1.setMargins(70, 0, 0, 0);
        tab1.requestLayout();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }

}