package com.example.orderfood_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.orderfood_sqlite.dao.LoaiThucDonDAO;
import com.google.android.material.textfield.TextInputLayout;

public class ThemLoaiThucDonActivity extends AppCompatActivity {

    Button btnDongY;
    TextInputLayout inputLayoutThemLoaiThucDon;
    LoaiThucDonDAO loaiThucDonDAO;

    int maLoai = 0;
    String tenLoai = "";
    boolean mode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_loai_thuc_don);

        loaiThucDonDAO = new LoaiThucDonDAO(this);
        inputLayoutThemLoaiThucDon = findViewById(R.id.inputLayoutThemLoaiThucDon);
        btnDongY = findViewById(R.id.btnDongY);

        Intent intent = getIntent();
        maLoai = intent.getIntExtra("maloai", 0);
        mode = intent.getBooleanExtra("modeLTD", false);
        tenLoai = intent.getStringExtra("tenloai");

        if (mode) {
            setTitle("Cập nhật loại thực đơn");
            inputLayoutThemLoaiThucDon.getEditText().setText(tenLoai);
        }

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoaiThucDon = inputLayoutThemLoaiThucDon.getEditText().getText().toString().trim();
                if (tenLoaiThucDon.isEmpty()) {
                    inputLayoutThemLoaiThucDon.setError("Vui lòng nhập tên loại thực đơn");
                    return;
                } else {
                    inputLayoutThemLoaiThucDon.setErrorEnabled(false);
                    inputLayoutThemLoaiThucDon.setError(null);
                    boolean kiemTra;

                    if (mode) {

                        kiemTra = loaiThucDonDAO.capNhatLaiTenLoaiThucDon(maLoai, tenLoaiThucDon);
                        Intent intent = new Intent();
                        intent.putExtra("capNhatLoaiThucDon", kiemTra);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } else {
                        kiemTra = loaiThucDonDAO.ThemLoaiThucDon(tenLoaiThucDon);
                        Intent intent = new Intent();
                        intent.putExtra("themLoaiThucDon", kiemTra);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }


                }
            }
        });

    }
}