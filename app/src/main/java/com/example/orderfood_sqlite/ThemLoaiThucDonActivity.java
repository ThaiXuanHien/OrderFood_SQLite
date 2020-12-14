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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_loai_thuc_don);

        loaiThucDonDAO = new LoaiThucDonDAO(this);
        inputLayoutThemLoaiThucDon = findViewById(R.id.inputLayoutThemLoaiThucDon);
        btnDongY = findViewById(R.id.btnDongY);

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
                    boolean kiemTra = loaiThucDonDAO.ThemLoaiThucDon(tenLoaiThucDon);

                    Intent intent = new Intent();
                    intent.putExtra("themLoaiThucDon", kiemTra);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });

    }
}