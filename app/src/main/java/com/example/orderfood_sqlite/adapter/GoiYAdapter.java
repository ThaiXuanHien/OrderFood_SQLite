package com.example.orderfood_sqlite.adapter;

import android.content.Context;
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

public class GoiYAdapter extends RecyclerView.Adapter<GoiYAdapter.ViewHolder> {

    Context context;
    List<ThucDonDTO> thucDonDTOList;

    public GoiYAdapter(Context context, List<ThucDonDTO> thucDonDTOList) {
        this.context = context;
        this.thucDonDTOList = thucDonDTOList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_monan_goiy, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ThucDonDTO thucDonDTO = thucDonDTOList.get(position);
        holder.imgItemGoiY.setImageURI(Uri.parse(thucDonDTO.getHinhAnh()));
        holder.txtTenItemGoiY.setText(thucDonDTO.getTenThucDon());
        holder.txtGiaItemGoiY.setText("Giá : " + thucDonDTO.getGiaTien() + " đ");

    }

    @Override
    public int getItemCount() {
        return thucDonDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItemGoiY;
        TextView txtTenItemGoiY, txtGiaItemGoiY;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItemGoiY = itemView.findViewById(R.id.imgItemGoiY);
            txtTenItemGoiY = itemView.findViewById(R.id.txtTenItemGoiY);
            txtGiaItemGoiY = itemView.findViewById(R.id.txtGiaItemGoiY);
        }
    }
}
