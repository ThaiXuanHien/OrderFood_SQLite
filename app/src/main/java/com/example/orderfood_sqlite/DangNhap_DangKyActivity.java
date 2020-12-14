package com.example.orderfood_sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import com.example.orderfood_sqlite.adapter.ViewPagerDangNhapDangKyAdapter;
import com.example.orderfood_sqlite.database.CreateDatabase;
import com.google.android.material.tabs.TabLayout;

public class DangNhap_DangKyActivity extends AppCompatActivity {

    TabLayout tabLayoutDangNhap;
    ViewPager viewPagerDangNhap;
    Toolbar toolbarDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap_dangky);
        CreateDatabase createDatabase = new CreateDatabase(this);
        createDatabase.open();
        anhXa();

    }

    private void anhXa() {
        tabLayoutDangNhap = findViewById(R.id.tablayoutDangNhapDangKy);
        viewPagerDangNhap = findViewById(R.id.viewpagerDangNhapDangKy);
        toolbarDangNhap = findViewById(R.id.toolbarDangNhapDangKy);
        setSupportActionBar(toolbarDangNhap);

        ViewPagerDangNhapDangKyAdapter viewPagerDangNhapAdapter = new ViewPagerDangNhapDangKyAdapter(getSupportFragmentManager());
        viewPagerDangNhap.setAdapter(viewPagerDangNhapAdapter);
        viewPagerDangNhapAdapter.notifyDataSetChanged();

        tabLayoutDangNhap.setupWithViewPager(viewPagerDangNhap);

    }

}