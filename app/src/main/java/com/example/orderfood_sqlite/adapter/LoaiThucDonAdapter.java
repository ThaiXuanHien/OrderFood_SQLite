package com.example.orderfood_sqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.dto.LoaiThucDonDTO;

import java.util.List;

public class LoaiThucDonAdapter extends BaseAdapter {

    Context context;
    int layout;
    List<LoaiThucDonDTO> loaiThucDonDTOList;
    ViewHolderLoaiThucDon viewHolderLoaiThucDon;

    public LoaiThucDonAdapter(Context context, int layout, List<LoaiThucDonDTO> loaiThucDonDTOList) {
        this.context = context;
        this.layout = layout;
        this.loaiThucDonDTOList = loaiThucDonDTOList;
    }

    @Override
    public int getCount() {
        return loaiThucDonDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiThucDonDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return loaiThucDonDTOList.get(position).getMaLoaiThucDon();
    }

    public class ViewHolderLoaiThucDon {
        TextView txtItemSpinnerTenLoaiThucDon;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            viewHolderLoaiThucDon = new ViewHolderLoaiThucDon();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_spinner_loaithucdon, parent, false);

            viewHolderLoaiThucDon.txtItemSpinnerTenLoaiThucDon = view.findViewById(R.id.txtItemSpinnerTenLoaiThucDon);

            view.setTag(viewHolderLoaiThucDon);
        } else {
            viewHolderLoaiThucDon = (ViewHolderLoaiThucDon) view.getTag();
        }

        LoaiThucDonDTO loaiThucDonDTO = loaiThucDonDTOList.get(position);
        viewHolderLoaiThucDon.txtItemSpinnerTenLoaiThucDon.setText(loaiThucDonDTO.getTenLoaiThucDon());
        viewHolderLoaiThucDon.txtItemSpinnerTenLoaiThucDon.setTag(loaiThucDonDTO.getMaLoaiThucDon());

        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            viewHolderLoaiThucDon = new ViewHolderLoaiThucDon();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_spinner_loaithucdon, parent, false);

            viewHolderLoaiThucDon.txtItemSpinnerTenLoaiThucDon = view.findViewById(R.id.txtItemSpinnerTenLoaiThucDon);

            view.setTag(viewHolderLoaiThucDon);
        } else {
            viewHolderLoaiThucDon = (ViewHolderLoaiThucDon) view.getTag();
        }

        LoaiThucDonDTO loaiThucDonDTO = loaiThucDonDTOList.get(position);
        viewHolderLoaiThucDon.txtItemSpinnerTenLoaiThucDon.setText(loaiThucDonDTO.getTenLoaiThucDon());
        viewHolderLoaiThucDon.txtItemSpinnerTenLoaiThucDon.setTag(loaiThucDonDTO.getMaLoaiThucDon());

        return view;
    }
}