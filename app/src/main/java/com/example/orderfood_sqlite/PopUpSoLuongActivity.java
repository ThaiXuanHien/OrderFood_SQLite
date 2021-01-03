package com.example.orderfood_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.orderfood_sqlite.dao.GoiMonDAO;
import com.example.orderfood_sqlite.dto.ChiTietGoiMonDTO;
import com.google.android.material.textfield.TextInputLayout;

public class PopUpSoLuongActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout inputLayoutSoLuong;
    Button btnDongY;

    GoiMonDAO goiMonDAO;

    int maBan, maMonAn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_so_luong);

        btnDongY = findViewById(R.id.btnDongY);
        inputLayoutSoLuong = findViewById(R.id.inputLayoutSoLuong);

        goiMonDAO = new GoiMonDAO(this);

        Intent intent = getIntent();
        maBan = intent.getIntExtra("maban", 0);
        maMonAn = intent.getIntExtra("mamonan", 0);

        btnDongY.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int maGoiMon = (int) goiMonDAO.layMaGoiMonTheoMaBan(maBan, "false");

        boolean kiemTra = goiMonDAO.kiemTraMonAnDaTonTai(maGoiMon,maMonAn);
        if(kiemTra){
            //cập nhật món ăn đã tồn tại
            int soLuongCu = goiMonDAO.laySoLuongMonAnTheoMaGoiMon(maGoiMon,maMonAn);
            int soLuongMoi = Integer.parseInt(inputLayoutSoLuong.getEditText().getText().toString());

            int tongSoLuong = soLuongCu + soLuongMoi;

            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(maGoiMon);
            chiTietGoiMonDTO.setMaMonAn(maMonAn);
            chiTietGoiMonDTO.setSoLuong(tongSoLuong);

            boolean capNhat = goiMonDAO.CapNhatSoLuong(chiTietGoiMonDTO);
            if(capNhat){
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }

        }else{
            //thêm món ăn
            int soLuong = Integer.parseInt(inputLayoutSoLuong.getEditText().getText().toString());
            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(maGoiMon);
            chiTietGoiMonDTO.setMaMonAn(maMonAn);
            chiTietGoiMonDTO.setSoLuong(soLuong);

            boolean themChiTietGoiMon = goiMonDAO.themChiTietGoiMon(chiTietGoiMonDTO);
            if(themChiTietGoiMon){
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }
}