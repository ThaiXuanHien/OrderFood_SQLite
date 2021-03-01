package com.example.orderfood_sqlite.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.orderfood_sqlite.fragment.Fragment_DangKy;
import com.example.orderfood_sqlite.fragment.Fragment_DangNhap;

public class ViewPagerDangNhapDangKyAdapter extends FragmentPagerAdapter {
    public ViewPagerDangNhapDangKyAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment_DangNhap fragmentDangNhap = new Fragment_DangNhap();

                return fragmentDangNhap;
            case 1:
                Fragment_DangKy fragmentDangKy = new Fragment_DangKy();
                return fragmentDangKy;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:

                return "Đăng nhập";
            case 1:

                return "Đăng ký";
            default:
                return null;
        }
    }
}
