package com.example.orderfood_sqlite.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.dto.ThucDonDTO;

import java.util.List;

public class TimKiemMonAnAdapter extends RecyclerView.Adapter<TimKiemMonAnAdapter.ViewHolder> {

    List<ThucDonDTO> thucDonDTOList;

    public TimKiemMonAnAdapter(List<ThucDonDTO> thucDonDTOList) {
        this.thucDonDTOList = thucDonDTOList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timkiemmonan, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ThucDonDTO thucDonDTO = thucDonDTOList.get(position);

        holder.imgItemTKMonAn.setImageURI(Uri.parse(thucDonDTO.getHinhAnh()));
        holder.txtGiaItemTKMonAn.setText("Giá : " + thucDonDTO.getGiaTien() + " đ");
        holder.txtTenItemTKMonAn.setText(thucDonDTO.getTenThucDon());

    }

    @Override
    public int getItemCount() {
        return thucDonDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgItemTKMonAn;
        TextView txtTenItemTKMonAn, txtGiaItemTKMonAn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItemTKMonAn = itemView.findViewById(R.id.imgItemTKMonAn);
            txtTenItemTKMonAn = itemView.findViewById(R.id.txtTenItemTKMonAn);
            txtGiaItemTKMonAn = itemView.findViewById(R.id.txtGiaItemTKMonAn);

        }
    }
}
