package com.example.orderfood_sqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orderfood_sqlite.adapter.LoaiThucDonAdapterSpinner;
import com.example.orderfood_sqlite.dao.LoaiThucDonDAO;
import com.example.orderfood_sqlite.dao.ThucDonDAO;
import com.example.orderfood_sqlite.dto.LoaiThucDonDTO;
import com.example.orderfood_sqlite.dto.ThucDonDTO;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class ThemThucDonActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_THEMLOAITD = 123;
    private static final int REQUEST_CODE_OPEN_GALLERY = 234;


    ImageView imgThemLoaiThucDon, imgThucDon, imgGallery;
    Spinner spinnerLoaiThucDon;
    LoaiThucDonDAO loaiThucDonDAO;
    ThucDonDAO thucDonDAO;
    List<LoaiThucDonDTO> loaiThucDonDTOList;
    LoaiThucDonAdapterSpinner loaiThucDonAdapter;
    TextInputLayout inputLayoutTenThucDon, inputLayoutGiaThucDon;
    Button btnDongYThemThucDon, btnHuyThemThucDon;

    String duongDanHinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_thuc_don);

        loaiThucDonDAO = new LoaiThucDonDAO(this);
        thucDonDAO = new ThucDonDAO(this);

        anhXa();

        hienThiLoaiThucDonSpinner();
        imgThemLoaiThucDon.setOnClickListener(this);
        imgGallery.setOnClickListener(this);
        btnDongYThemThucDon.setOnClickListener(this);
        btnHuyThemThucDon.setOnClickListener(this);
    }

    private void anhXa() {
        imgThemLoaiThucDon = findViewById(R.id.imgThemLoaiThucDon);
        spinnerLoaiThucDon = findViewById(R.id.spinner_loaiThucDon);
        imgThucDon = findViewById(R.id.imgThucDon);
        imgGallery = findViewById(R.id.imgGallery);
        btnDongYThemThucDon = findViewById(R.id.btnDongYThemThucDon);
        btnHuyThemThucDon = findViewById(R.id.btnHuyThemThucDon);
        inputLayoutTenThucDon = findViewById(R.id.inputLayoutTenThucDon);
        inputLayoutGiaThucDon = findViewById(R.id.inputLayoutGiaThucDon);
    }

    public void hienThiLoaiThucDonSpinner() {
        loaiThucDonDTOList = loaiThucDonDAO.LayDanhSachLoaiThucDon();
        loaiThucDonAdapter = new LoaiThucDonAdapterSpinner(this, R.layout.custom_spinner_loaithucdon, loaiThucDonDTOList);
        spinnerLoaiThucDon.setAdapter(loaiThucDonAdapter);
        loaiThucDonAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.imgThemLoaiThucDon:
                Intent intent = new Intent(this, ThemLoaiThucDonActivity.class);
                startActivityForResult(intent, REQUEST_CODE_THEMLOAITD);
                break;
            case R.id.imgGallery:
                Intent intentOpenGallery = new Intent();
                intentOpenGallery.setType("image/*");
                intentOpenGallery.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(intentOpenGallery, "Chọn Folder"), REQUEST_CODE_OPEN_GALLERY);
                break;
            case R.id.btnDongYThemThucDon:

                int viTri = spinnerLoaiThucDon.getSelectedItemPosition();

                String tenThucDon = inputLayoutTenThucDon.getEditText().getText().toString().trim();
                String giaTien = inputLayoutGiaThucDon.getEditText().getText().toString().trim();

                if (!tenThucDon.isEmpty() && !giaTien.isEmpty() && !tenThucDon.equals("") && duongDanHinh != null) {
                    ThucDonDTO monAnDTO = new ThucDonDTO();
                    monAnDTO.setGiaTien(giaTien);
                    monAnDTO.setHinhAnh(duongDanHinh);
                    int maLoaiThucDon = loaiThucDonDTOList.get(viTri).getMaLoaiThucDon();
                    monAnDTO.setMaLoaiThucDon(maLoaiThucDon);
                    monAnDTO.setTenThucDon(tenThucDon);

                    boolean kiemTra = thucDonDAO.themThucDon(monAnDTO);
                    Intent iThemTD = new Intent();
                    iThemTD.putExtra("themThucDon", kiemTra);
                    setResult(Activity.RESULT_OK, iThemTD);

//                    if (kiemTra) {
//                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
//                    }
                    finish();

                } else {
                    Toast.makeText(this, "Lỗi chưa điền đầy đủ", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnHuyThemThucDon:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_THEMLOAITD) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = data;
                boolean kiemTra = intent.getBooleanExtra("themLoaiThucDon", false);
                if (kiemTra) {
                    hienThiLoaiThucDonSpinner();
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == REQUEST_CODE_OPEN_GALLERY && data != null) {
            if (resultCode == Activity.RESULT_OK) {

                duongDanHinh = data.getData().toString();

                imgThucDon.setImageURI(data.getData());
            }
        }
    }
}