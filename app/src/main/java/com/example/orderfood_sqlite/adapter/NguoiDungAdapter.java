package com.example.orderfood_sqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.dto.NguoiDungDTO;

import java.util.List;

public class NguoiDungAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<NguoiDungDTO> nguoiDungDTOList;
    ViewHolderNguoiDung viewHolderNguoiDung;

    public NguoiDungAdapter(Context context, int layout, List<NguoiDungDTO> nguoiDungDTOList) {
        this.context = context;
        this.layout = layout;
        this.nguoiDungDTOList = nguoiDungDTOList;
    }

    @Override
    public int getCount() {
        return nguoiDungDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return nguoiDungDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return nguoiDungDTOList.get(position).getMaNguoiDung();
    }

    public class ViewHolderNguoiDung {
        ImageView imgHinhAnhNguoiDung;
        TextView txtTenItemNguoiDung, txtSDTItemNguoiDung;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            viewHolderNguoiDung = new ViewHolderNguoiDung();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            viewHolderNguoiDung.imgHinhAnhNguoiDung = (ImageView) view.findViewById(R.id.imgItemNguoiDung);
            viewHolderNguoiDung.txtTenItemNguoiDung = (TextView) view.findViewById(R.id.txtTenItemNguoiDung);
            viewHolderNguoiDung.txtSDTItemNguoiDung = (TextView) view.findViewById(R.id.txtSDTItemNguoiDung);

            view.setTag(viewHolderNguoiDung);
        } else {
            viewHolderNguoiDung = (ViewHolderNguoiDung) view.getTag();
        }

        NguoiDungDTO nguoiDungDTO = nguoiDungDTOList.get(position);

        viewHolderNguoiDung.txtTenItemNguoiDung.setText(nguoiDungDTO.getHoTen());
        viewHolderNguoiDung.txtSDTItemNguoiDung.setText(String.valueOf(nguoiDungDTO.getSdt()));
        return view;
    }
}
