package com.example.orderfood_sqlite.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.dto.ThanhToanDTO;

import java.util.List;

public class ThanhToanAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<ThanhToanDTO> thanhToanDTOs;
    ViewHolderThanhToan viewHolderThanhToan;


    public ThanhToanAdapter(Context context, int layout, List<ThanhToanDTO> thanhToanDTOs) {
        this.context = context;
        this.layout = layout;
        this.thanhToanDTOs = thanhToanDTOs;
    }

    @Override
    public int getCount() {
        return thanhToanDTOs.size();
    }

    @Override
    public Object getItem(int position) {
        return thanhToanDTOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolderThanhToan {
        TextView txtTenMonAn, txtSoLuong, txtGiaTien;
        ImageView imgMonAnThanhToan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            viewHolderThanhToan = new ViewHolderThanhToan();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            viewHolderThanhToan.txtTenMonAn = (TextView) view.findViewById(R.id.txtTenMonAnThanhToan);
            viewHolderThanhToan.txtGiaTien = (TextView) view.findViewById(R.id.txtGiaTienThanhToan);
            viewHolderThanhToan.txtSoLuong = (TextView) view.findViewById(R.id.txtSoLuongThanhToan);
            viewHolderThanhToan.imgMonAnThanhToan = view.findViewById(R.id.imgItemThanhToan);

            view.setTag(viewHolderThanhToan);
        } else {
            viewHolderThanhToan = (ViewHolderThanhToan) view.getTag();
        }

        ThanhToanDTO thanhToanDTO = thanhToanDTOs.get(position);

        viewHolderThanhToan.txtTenMonAn.setText(thanhToanDTO.getTenMonAn());
        viewHolderThanhToan.txtSoLuong.setText(String.valueOf(thanhToanDTO.getSoLuong()));
        viewHolderThanhToan.txtGiaTien.setText(String.valueOf(thanhToanDTO.getGiaTien()));

        Uri uri = Uri.parse(thanhToanDTO.getHinhAnh());
        viewHolderThanhToan.imgMonAnThanhToan.setImageURI(uri);

        return view;
    }
}
