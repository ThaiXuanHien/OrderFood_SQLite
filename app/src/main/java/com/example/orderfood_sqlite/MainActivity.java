package com.example.orderfood_sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.orderfood_sqlite.fragment.FragmentGoiY;
import com.example.orderfood_sqlite.fragment.Fragment_HienThiBanAnTrangChu;
import com.example.orderfood_sqlite.fragment.Fragment_HienThiThucDon;
import com.example.orderfood_sqlite.fragment.Fragment_NguoiDung;
import com.example.orderfood_sqlite.fragment.Fragment_TimKiem;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView txtTenNguoiDungDrawer;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbarTrangChu);


        txtTenNguoiDungDrawer = navigationView.getHeaderView(0).findViewById(R.id.txtTenNguoiDungDrawer);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Mo, R.string.Dong) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        // set tên người dùng sau khi đăng nhập
        Intent intent = getIntent();
        txtTenNguoiDungDrawer.setText(intent.getStringExtra("taiKhoan"));

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        FragmentGoiY fragmentGoiY = new FragmentGoiY();
        transaction.replace(R.id.content, fragmentGoiY);
        transaction.commit();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;


        switch (id) {
            case R.id.item_goiY:

                clearFragment(fragmentManager);
                fragment = new FragmentGoiY();
                item.setChecked(true);

                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.commit();
                drawerLayout.closeDrawers();

                break;
            case R.id.item_timKiem:
                clearFragment(fragmentManager);
                fragment = new Fragment_TimKiem();
                item.setChecked(true);

                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.commit();
                drawerLayout.closeDrawers();

                break;
            case R.id.item_trangChu:
                clearFragment(fragmentManager);
                fragment = new Fragment_HienThiBanAnTrangChu();
                item.setChecked(true);

                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.commit();
                drawerLayout.closeDrawers();

                break;
            case R.id.item_thucDon:
                clearFragment(fragmentManager);
                fragment = new Fragment_HienThiThucDon();
                item.setChecked(true);

                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.commit();
                drawerLayout.closeDrawers();

                break;
            case R.id.item_nguoiDung:
                clearFragment(fragmentManager);
                fragment = new Fragment_NguoiDung();
                item.setChecked(true);

                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.commit();
                drawerLayout.closeDrawers();

                break;
            case R.id.item_dangXuat:

                SharedPreferences loginFirst_Pref = getSharedPreferences("loginFirst", Context.MODE_PRIVATE);
                Editor editor = loginFirst_Pref.edit();
                editor.clear();
                editor.commit();
                editor.apply();
                finish();
                break;
        }



        return true;
    }

    private static void clearFragment(FragmentManager fragmentManager) {
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }
}