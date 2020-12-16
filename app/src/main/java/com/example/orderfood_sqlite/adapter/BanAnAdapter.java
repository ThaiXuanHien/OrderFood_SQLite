package com.example.orderfood_sqlite.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.orderfood_sqlite.MainActivity;
import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.dao.BanAnDAO;
import com.example.orderfood_sqlite.dao.GoiMonDAO;
import com.example.orderfood_sqlite.dto.BanAnDTO;
import com.example.orderfood_sqlite.dto.GoiMonDTO;
import com.example.orderfood_sqlite.fragment.Fragment_HienThiThucDon;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class BanAnAdapter extends BaseAdapter implements View.OnClickListener {

    Context context;
    int layout;
    List<BanAnDTO> banAnList;
    ViewHolderBanAn viewHolderBanAn;
    BanAnDAO banAnDAO;
    GoiMonDAO goiMonDAO;
    FragmentManager fragmentManager;

    public BanAnAdapter(Context context, int layout, List<BanAnDTO> banAnList) {
        this.context = context;
        this.layout = layout;
        this.banAnList = banAnList;
        banAnDAO = new BanAnDAO(context);
        goiMonDAO = new GoiMonDAO(context);
        fragmentManager = ((MainActivity) context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return banAnList.size();
    }

    @Override
    public Object getItem(int position) {
        return banAnList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return banAnList.get(position).getMaBanAn();
    }


    public class ViewHolderBanAn {
        ImageView imgBanAn, imgGoiMon, imgThanhToan, imgHideButton;
        TextView txtTenBanAn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderBanAn = new ViewHolderBanAn();
            view = inflater.inflate(R.layout.item_banan, parent, false);
            viewHolderBanAn.imgBanAn = view.findViewById(R.id.imgItemBanAn);
            viewHolderBanAn.imgGoiMon = view.findViewById(R.id.imgGoiMonItemBanAn);
            viewHolderBanAn.imgThanhToan = view.findViewById(R.id.imgThanhToanItemBanAn);
            viewHolderBanAn.imgHideButton = view.findViewById(R.id.imgHideItemBanAn);
            viewHolderBanAn.txtTenBanAn = view.findViewById(R.id.txtTenItemBanAn);

            view.setTag(viewHolderBanAn);
        } else {
            viewHolderBanAn = (ViewHolderBanAn) view.getTag();
        }

        if (banAnList.get(position).isDuocChon()) {
            hienThiButton();
        } else {
            hideButton();
        }

        BanAnDTO banAnDTO = banAnList.get(position);

        String tinhTrang = banAnDAO.LayTinhTrangBanTheoMa(banAnDTO.getMaBanAn());
        if (tinhTrang.equals("true")) {
            viewHolderBanAn.imgBanAn.setImageResource(R.drawable.banantrue);
        } else {
            viewHolderBanAn.imgBanAn.setImageResource(R.drawable.banan);
        }

        viewHolderBanAn.txtTenBanAn.setText(banAnDTO.getTenBanAn());

        viewHolderBanAn.imgBanAn.setTag(position);

        viewHolderBanAn.imgBanAn.setOnClickListener(this);
        viewHolderBanAn.imgGoiMon.setOnClickListener(this);

        return view;
    }

    private void hideButton() {
        viewHolderBanAn.imgGoiMon.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imgThanhToan.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imgHideButton.setVisibility(View.INVISIBLE);
    }

    private void hienThiButton() {
        viewHolderBanAn.imgGoiMon.setVisibility(View.VISIBLE);
        viewHolderBanAn.imgThanhToan.setVisibility(View.VISIBLE);
        viewHolderBanAn.imgHideButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {

        viewHolderBanAn = (ViewHolderBanAn) ((View) v.getParent()).getTag();

        int viTriBanAn = (int) viewHolderBanAn.imgBanAn.getTag();
        int maBanAn = banAnList.get(viTriBanAn).getMaBanAn();

        int id = v.getId();
        switch (id) {
            case R.id.imgItemBanAn:

                int viTri = (int) v.getTag();
                banAnList.get(viTri).setDuocChon(true);
                hienThiButton();
                break;
            case R.id.imgGoiMonItemBanAn:

                Intent dataIntentTrangChu = ((MainActivity) context).getIntent();
                int maNguoiDung = dataIntentTrangChu.getIntExtra("maNguoiDung", 0);

                String tinhTrang = banAnDAO.LayTinhTrangBanTheoMa(maBanAn);
                if (tinhTrang.equals("false")) {

                    // thực hiện code thêm bảng gọi món và cập nhật lại tình trạng bàn
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                    String ngayGoi = dateFormat.format(calendar.getTime());

                    GoiMonDTO goiMonDTO = new GoiMonDTO();
                    goiMonDTO.setMaBan(maBanAn);
                    goiMonDTO.setMaNguoiDung(maNguoiDung);
                    goiMonDTO.setNgayGoi(ngayGoi);
                    goiMonDTO.setTinhTrang("false");

                    long kiemTra = goiMonDAO.themGoiMonAn(goiMonDTO);
                    banAnDAO.capNhatTinhTrangBanAn(maBanAn, "true");
                    if (kiemTra == 0) {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }

                }

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment_HienThiThucDon fragment_hienThiThucDon = new Fragment_HienThiThucDon();

                Bundle bDuLieuThucDon = new Bundle();
                bDuLieuThucDon.putInt("maban", maBanAn);

                fragment_hienThiThucDon.setArguments(bDuLieuThucDon);

                transaction.replace(R.id.content, fragment_hienThiThucDon).addToBackStack("hienthibanan");
                transaction.commit();

                break;
        }
    }
}
