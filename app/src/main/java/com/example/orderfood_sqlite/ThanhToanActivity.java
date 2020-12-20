package com.example.orderfood_sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood_sqlite.adapter.ThanhToanAdapter;
import com.example.orderfood_sqlite.dao.BanAnDAO;
import com.example.orderfood_sqlite.dao.GoiMonDAO;
import com.example.orderfood_sqlite.dto.ThanhToanDTO;
import com.example.orderfood_sqlite.fragment.Fragment_HienThiBanAnTrangChu;

import java.util.List;

public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener {

    GridView gvThanhToan;
    Button btnThanhToan, btnHuy;
    TextView txtTongTien;
    GoiMonDAO goiMonDAO;
    BanAnDAO banAnDAO;
    List<ThanhToanDTO> thanhToanDTOList;
    ThanhToanAdapter thanhToanAdapter;
    long tongTien = 0;
    int maBan = 0;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        gvThanhToan = findViewById(R.id.gvThanhToan);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnHuy = findViewById(R.id.btnHuyThanhToan);
        txtTongTien = findViewById(R.id.txtTongTien);
        fragmentManager = getSupportFragmentManager();

        goiMonDAO = new GoiMonDAO(this);
        banAnDAO = new BanAnDAO(this);

        maBan = getIntent().getIntExtra("maban", 0);
        if (maBan != 0) {
            hienThiThanhToan();

            for (int i = 0; i < thanhToanDTOList.size(); i++) {
                int sl = thanhToanDTOList.get(i).getSoLuong();
                int gia = thanhToanDTOList.get(i).getGiaTien();

                tongTien += (sl * gia);
            }
            txtTongTien.setText("Tổng tiền : " + tongTien + " Đ");

        }

        btnThanhToan.setOnClickListener(this);
        btnHuy.setOnClickListener(this);

    }

    private void hienThiThanhToan() {
        int maGoiMon = (int) goiMonDAO.layMaGoiMonTheoMaBan(maBan, "false");
        thanhToanDTOList = goiMonDAO.layDanhSachMonAnTheoMaGoiMon(maGoiMon);
        thanhToanAdapter = new ThanhToanAdapter(this, R.layout.item_thanhtoan, thanhToanDTOList);
        gvThanhToan.setAdapter(thanhToanAdapter);
        thanhToanAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnThanhToan:

                boolean kiemTraBanAn = banAnDAO.capNhatTinhTrangBanAn(maBan,"false");
                boolean kiemTraGoiMon = goiMonDAO.capNhatTrangThaiGoiMonTheoMaBan(maBan,"true");
                if(kiemTraBanAn && kiemTraGoiMon){
                    Toast.makeText(ThanhToanActivity.this,"Thành toán thành công", Toast.LENGTH_SHORT);
                    hienThiThanhToan();
                }else{
                    Toast.makeText(ThanhToanActivity.this,"Lỗi", Toast.LENGTH_SHORT);
                }

                break;
            case R.id.btnHuyThanhToan:


                finish();
                break;
        }
    }
}