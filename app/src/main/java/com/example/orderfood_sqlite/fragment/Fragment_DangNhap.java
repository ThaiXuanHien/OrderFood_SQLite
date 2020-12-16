package com.example.orderfood_sqlite.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.orderfood_sqlite.MainActivity;
import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.dao.NguoiDungDAO;
import com.google.android.material.textfield.TextInputLayout;

public class Fragment_DangNhap extends Fragment {

    TextInputLayout inputLayoutTaiKhoan, inputLayoutMatKhau;
    Button btnLogin;
    NguoiDungDAO nguoiDungDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dangnhap, container, false);

        inputLayoutTaiKhoan = view.findViewById(R.id.inputLayoutTaiKhoan);
        inputLayoutMatKhau = view.findViewById(R.id.inputLayoutMatKhau);
        btnLogin = view.findViewById(R.id.btnLogin);
        nguoiDungDAO = new NguoiDungDAO(getContext());


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maNguoiDung = nguoiDungDAO.kiemTraDangNhap(inputLayoutTaiKhoan.getEditText().getText().toString().trim(),
                        inputLayoutMatKhau.getEditText().getText().toString().trim());
                if (maNguoiDung != 0) {
                    Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("taiKhoan", inputLayoutTaiKhoan.getEditText().getText().toString().trim());
                    intent.putExtra("maNguoidung",maNguoiDung);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Đăng nhập thất bại ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
