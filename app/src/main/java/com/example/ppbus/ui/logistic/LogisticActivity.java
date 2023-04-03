package com.example.ppbus.ui.logistic;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ppbus.R;
import com.google.android.material.navigation.NavigationView;

public class LogisticActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistic);

        initializeVariable();
        setSupportActionBar(toolbar);

        //Navigation Drawer 畫面的切換
        navController = Navigation.findNavController(this, R.id.logistic_nav_host);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_packages, R.id.nav_confirmed)
                .setOpenableLayout(drawerLayout)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration); // appbar上面的清單按鈕以及返回按鈕
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void initializeVariable() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_appbar, menu);
        return true;
    }

    //實現AppBar上面的清單按鈕以及返回按鈕
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
        //return super.onSupportNavigateUp();
    }
}