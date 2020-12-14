package com.example.orderfood_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.orderfood_sqlite.dao.BanAnDAO;
import com.google.android.material.textfield.TextInputLayout;

public class PopUpThemBanAnActivity extends AppCompatActivity {

    TextInputLayout inputLayoutThemBanAn;
    Button btnDongY;

    BanAnDAO banAnDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_them_ban_an);

        inputLayoutThemBanAn = findViewById(R.id.inputLayoutThemBanAn);
        btnDongY = findViewById(R.id.btnDongY);

        banAnDAO = new BanAnDAO(this);

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenBanAn = inputLayoutThemBanAn.getEditText().getText().toString().trim();
                if (!tenBanAn.equals("")) {
                    boolean kiemTra = banAnDAO.ThemBanAn(tenBanAn);
                    Intent intent = new Intent();
                    intent.putExtra("themBanAn", kiemTra);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });


    }
}