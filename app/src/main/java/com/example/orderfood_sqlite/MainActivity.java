package com.example.orderfood_sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.orderfood_sqlite.fragment.Fragment_HienThiBanAnTrangChu;
import com.example.orderfood_sqlite.fragment.Fragment_HienThiThucDon;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView txtTenNguoiDungDrawer;

    FragmentManager fragmentManager;
    FragmentTransaction transaction;
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
        Fragment_HienThiBanAnTrangChu fragment_hienThiBanAnTrangChu = new Fragment_HienThiBanAnTrangChu();
        transaction.replace(R.id.content, fragment_hienThiBanAnTrangChu);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_trangChu:
                transaction = fragmentManager.beginTransaction();
                Fragment_HienThiBanAnTrangChu fragment_hienThiBanAnTrangChu = new Fragment_HienThiBanAnTrangChu();
                transaction.replace(R.id.content, fragment_hienThiBanAnTrangChu);
                transaction.commit();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.item_thucDon:
                transaction = fragmentManager.beginTransaction();
                Fragment_HienThiThucDon fragment_hienThiThucDon = new Fragment_HienThiThucDon();
                transaction.replace(R.id.content, fragment_hienThiThucDon);
                transaction.commit();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;
        }
        return true;
    }
}