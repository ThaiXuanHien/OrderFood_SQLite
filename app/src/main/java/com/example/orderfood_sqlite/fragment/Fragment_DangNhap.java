package com.example.orderfood_sqlite.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.orderfood_sqlite.MainActivity;
import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.dao.NguoiDungDAO;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

public class Fragment_DangNhap extends Fragment {

    TextInputLayout inputLayoutTaiKhoan, inputLayoutMatKhau;
    Button btnLogin;
    NguoiDungDAO nguoiDungDAO;

    ImageView imgVietNam, imgEng;
    TextView txtTitleDangNhap;

    SharedPreferences loginFirst_Pref;
    SharedPreferences.Editor editorLoginFirst;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dangnhap, container, false);

        loadLocale();

        loginFirst_Pref = getActivity().getSharedPreferences("loginFirst",Context.MODE_PRIVATE);
        boolean isFirst = loginFirst_Pref.getBoolean("isFirst",true);
        Toast.makeText(getActivity(), "" + isFirst, Toast.LENGTH_SHORT).show();
        editorLoginFirst = loginFirst_Pref.edit();
        if(isFirst){

            editorLoginFirst.putBoolean("isFirst", false);
            editorLoginFirst.commit();
        }
        else {
            Intent intent = new Intent(getContext(), MainActivity.class);
            //getActivity().overridePendingTransition(R.anim.right_to_left_activity, R.anim.left_to_right_activity);
            startActivity(intent);
        }


        inputLayoutTaiKhoan = view.findViewById(R.id.inputLayoutTaiKhoan);
        inputLayoutMatKhau = view.findViewById(R.id.inputLayoutMatKhau);
        btnLogin = view.findViewById(R.id.btnLogin);
        imgVietNam = view.findViewById(R.id.imgVietNam);
        imgEng = view.findViewById(R.id.imgEng);
        txtTitleDangNhap = view.findViewById(R.id.titleDangNhap);

        nguoiDungDAO = new NguoiDungDAO(getContext());


        imgVietNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("vi");
                getActivity().recreate();
            }
        });

        imgEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("en");
                getActivity().recreate();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maNguoiDung = nguoiDungDAO.kiemTraDangNhap(inputLayoutTaiKhoan.getEditText().getText().toString().trim(),
                        inputLayoutMatKhau.getEditText().getText().toString().trim());

                int maQuyen = nguoiDungDAO.layQuyenNhanVien(maNguoiDung);

                if (maNguoiDung != 0) {
                    Toast.makeText(getActivity(), "" + isFirst, Toast.LENGTH_SHORT).show();
                    editorLoginFirst.putBoolean("isFirst", false);

                    editorLoginFirst.commit();


                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("maquyen", maQuyen);
                    editor.commit();

                    //Toast.makeText(getActivity(), "Đăng nhập thành công " + maNguoiDung, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("taiKhoan", inputLayoutTaiKhoan.getEditText().getText().toString().trim());
                    intent.putExtra("maNguoiDung", maNguoiDung);
                    //getActivity().overridePendingTransition(R.anim.right_to_left_activity, R.anim.left_to_right_activity);
                    startActivity(intent);

                } else {
                    Toast.makeText(getActivity(), "Đăng nhập thất bại ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }



    private void setLocale(String str) {
        Locale locale = new Locale(str);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getContext().getResources().updateConfiguration(configuration, getContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getContext().getSharedPreferences("lang", Context.MODE_PRIVATE).edit();
        editor.putString("myLang", str);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("lang",Context.MODE_PRIVATE);
        String lang = sharedPreferences.getString("lang","");
        setLocale(lang);
    }


}
