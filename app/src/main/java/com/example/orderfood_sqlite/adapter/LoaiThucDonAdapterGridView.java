package com.example.orderfood_sqlite.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.dao.LoaiThucDonDAO;
import com.example.orderfood_sqlite.dto.LoaiThucDonDTO;

import java.util.List;

public class LoaiThucDonAdapterGridView extends BaseAdapter {

    Context context;
    int layout;
    List<LoaiThucDonDTO> loaiThucDonDTOList;
    LoaiThucDonDAO loaiThucDonDAO;

    ViewHolderLoaiThucDonGridView viewHolderLoaiThucDonGridView;

    public LoaiThucDonAdapterGridView(Context context, int layout, List<LoaiThucDonDTO> loaiThucDonDTOList) {
        this.context = context;
        this.layout = layout;
        this.loaiThucDonDTOList = loaiThucDonDTOList;

        loaiThucDonDAO = new LoaiThucDonDAO(context);
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

    public class ViewHolderLoaiThucDonGridView {
        ImageView imgItemLoaiThucDonGV;
        TextView txtTenItemLoaiThucDonGV;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            viewHolderLoaiThucDonGridView = new ViewHolderLoaiThucDonGridView();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            viewHolderLoaiThucDonGridView.imgItemLoaiThucDonGV = view.findViewById(R.id.imgItemLoaiThucDon);
            viewHolderLoaiThucDonGridView.txtTenItemLoaiThucDonGV = view.findViewById(R.id.txtTenLoaiThucDon);

            view.setTag(viewHolderLoaiThucDonGridView);
        } else {
            viewHolderLoaiThucDonGridView = (ViewHolderLoaiThucDonGridView) view.getTag();
        }
        LoaiThucDonDTO loaiThucDonDTO = loaiThucDonDTOList.get(position);

        String hinhAnh = loaiThucDonDAO.layHinhLoaiThucDon(loaiThucDonDTO.getMaLoaiThucDon());


        Uri uri = Uri.parse(hinhAnh);
        viewHolderLoaiThucDonGridView.imgItemLoaiThucDonGV.setImageURI(uri);
        viewHolderLoaiThucDonGridView.txtTenItemLoaiThucDonGV.setText(loaiThucDonDTO.getTenLoaiThucDon());

        return view;
    }
}
