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
import com.example.orderfood_sqlite.dto.ThucDonDTO;

import java.util.List;

public class ThucDonAdapter extends BaseAdapter {

    Context context;
    int layout;
    List<ThucDonDTO> thucDonDTOList;

    ViewHolderThucDon viewHolderThucDon;

    public ThucDonAdapter(Context context, int layout, List<ThucDonDTO> thucDonDTOList) {
        this.context = context;
        this.layout = layout;
        this.thucDonDTOList = thucDonDTOList;
    }

    @Override
    public int getCount() {
        return thucDonDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return thucDonDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return thucDonDTOList.get(position).getMaThucDon();
    }

    public class ViewHolderThucDon {
        ImageView imgItemThucDon;
        TextView txtTenItemThucDon, txtGiaItemThucDon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            viewHolderThucDon = new ViewHolderThucDon();
            ViewGroup root;
            view = layoutInflater.inflate(layout, parent, false);
            viewHolderThucDon.imgItemThucDon = view.findViewById(R.id.imgItemThucDon);
            viewHolderThucDon.txtTenItemThucDon = view.findViewById(R.id.txtTenThucDon);
            viewHolderThucDon.txtGiaItemThucDon = view.findViewById(R.id.txtGiaThucDon);

            view.setTag(viewHolderThucDon);
        }
        else {
            viewHolderThucDon = (ViewHolderThucDon) view.getTag();
        }

        ThucDonDTO thucDonDTO = thucDonDTOList.get(position);

        Uri uri = Uri.parse(thucDonDTO.getHinhAnh());

        viewHolderThucDon.imgItemThucDon.setImageURI(uri);
        viewHolderThucDon.txtTenItemThucDon.setText(thucDonDTO.getTenThucDon());
        viewHolderThucDon.txtGiaItemThucDon.setText("Giá : " + thucDonDTO.getGiaTien() + " Đ");


        return view;
    }
}
