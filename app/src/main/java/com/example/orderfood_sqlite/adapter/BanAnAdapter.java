package com.example.orderfood_sqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.dto.BanAnDTO;

import java.util.List;

public class BanAnAdapter extends BaseAdapter implements View.OnClickListener {

    Context context;
    int layout;
    List<BanAnDTO> banAnList;

    ViewHolderBanAn viewHolderBanAn;

    public BanAnAdapter(Context context, int layout, List<BanAnDTO> banAnList) {
        this.context = context;
        this.layout = layout;
        this.banAnList = banAnList;
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
        viewHolderBanAn.txtTenBanAn.setText(banAnDTO.getTenBanAn());

        viewHolderBanAn.imgBanAn.setTag(position);
        viewHolderBanAn.imgBanAn.setOnClickListener(this);

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
        int id = v.getId();
        switch (id) {
            case R.id.imgItemBanAn:

                int viTri = (int) v.getTag();
                banAnList.get(viTri).setDuocChon(true);
                hienThiButton();

                break;
        }
    }
}
