package com.example.orderfood_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.orderfood_sqlite.dao.BanAnDAO;
import com.google.android.material.textfield.TextInputLayout;

public class PopUpThemBanAnActivity extends AppCompatActivity {

    TextInputLayout inputLayoutThemBanAn;
    Button btnDongY;

    BanAnDAO banAnDAO;
    int maBan = 0;
    boolean mode;
    String tenBan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_them_ban_an);

        inputLayoutThemBanAn = findViewById(R.id.inputLayoutTenBanAn);
        btnDongY = findViewById(R.id.btnDongY);

        banAnDAO = new BanAnDAO(this);

        Intent intent = getIntent();
        maBan = intent.getIntExtra("maban", 0);
        mode = intent.getBooleanExtra("mode", false);
        tenBan = intent.getStringExtra("tenBan");

        if (mode) {
            setTitle("Cập nhật bàn ăn");
            inputLayoutThemBanAn.getEditText().setText(tenBan);
        }


        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenBanAn = inputLayoutThemBanAn.getEditText().getText().toString().trim();

                boolean kiemTra;
                if (!tenBanAn.equals("")) {
                    if (mode) {
                        kiemTra = banAnDAO.capNhatLaiTenBan(maBan, tenBanAn);

                    } else {
                        kiemTra = banAnDAO.ThemBanAn(tenBanAn);
                    }

                    Intent intent = new Intent();
                    intent.putExtra("LuuBanAn", kiemTra);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });


    }
}