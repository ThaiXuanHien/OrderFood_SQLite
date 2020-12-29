package com.example.orderfood_sqlite.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.orderfood_sqlite.PopUpSoLuongActivity;
import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.ThemLoaiThucDonActivity;
import com.example.orderfood_sqlite.ThemThucDonActivity;
import com.example.orderfood_sqlite.adapter.ThucDonAdapter;
import com.example.orderfood_sqlite.dao.ThucDonDAO;
import com.example.orderfood_sqlite.dto.ThucDonDTO;

import java.util.List;

public class ThucDonFragment extends Fragment {

    private static final int REQUEST_THEMMONAN = 999;
    GridView gvThucDon;

    ThucDonDAO thucDonDAO;
    List<ThucDonDTO> thucDonDTOList;
    ThucDonAdapter thucDonAdapter;

    int maBan, maLoai;
    int maQuyen = 0;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hienthi_thucdon, container, false);

        thucDonDAO = new ThucDonDAO(getContext());
        gvThucDon = view.findViewById(R.id.gvThucDon);


        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maQuyen = sharedPreferences.getInt("maquyen", 0);


        if (maQuyen == 1) {
            registerForContextMenu(gvThucDon);
        }


        Bundle bundle = getArguments();
        if (bundle != null) {
            maLoai = bundle.getInt("maloai");
            maBan = bundle.getInt("maban");
            thucDonDTOList = thucDonDAO.LayDanhSachThucDonTheoLoai(maLoai);
            thucDonAdapter = new ThucDonAdapter(getContext(), R.layout.item_thucdon, thucDonDTOList);
            gvThucDon.setAdapter(thucDonAdapter);
            thucDonAdapter.notifyDataSetChanged();

            gvThucDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (maBan != 0) {
                        Intent iSoLuong = new Intent(getActivity(), PopUpSoLuongActivity.class);
                        iSoLuong.putExtra("maban", maBan);
                        iSoLuong.putExtra("mamonan", thucDonDTOList.get(position).getMaThucDon());

                        startActivity(iSoLuong);
                    }
                }
            });

        }


        // addToBackStack để trở về fragment trước đó
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    getFragmentManager().popBackStack("hienthiloai", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_popup, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        int viTri = info.position;
        int maMonAn = thucDonDTOList.get(viTri).getMaThucDon();

        switch (id) {
            case R.id.item_sua:

                Intent intent = new Intent(getContext(), ThemThucDonActivity.class);
                intent.putExtra("mamonan", maMonAn);
                intent.putExtra("modeMonAn", true);
                intent.putExtra("tenMonAn", thucDonDTOList.get(viTri).getTenThucDon());
                intent.putExtra("gia", thucDonDTOList.get(viTri).getGiaTien());
                intent.putExtra("hinhanh", thucDonDTOList.get(viTri).getHinhAnh());
                intent.putExtra("loaithucdon", thucDonDTOList.get(viTri).getMaLoaiThucDon());

                Toast.makeText(getContext(), thucDonDTOList.get(viTri).getTenThucDon() + "   " + maMonAn + " " + thucDonDTOList.get(viTri).getMaLoaiThucDon(), Toast.LENGTH_SHORT).show();
                startActivityForResult(intent, REQUEST_THEMMONAN);
                break;
            case R.id.item_xoa:
                boolean kiemTra = thucDonDAO.xoaThucDon(maMonAn);
                if (kiemTra) {
                    refreshThucDon(maLoai);
                    Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void refreshThucDon(int maLoai) {
        thucDonDTOList = thucDonDAO.LayDanhSachThucDonTheoLoai(maLoai);
        thucDonAdapter = new ThucDonAdapter(getContext(), R.layout.item_thucdon, thucDonDTOList);
        gvThucDon.setAdapter(thucDonAdapter);
        thucDonAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_THEMMONAN) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = data;
                boolean kiemTra = intent.getBooleanExtra("capNhatMonAn", false);
                if (kiemTra) {
                    refreshThucDon(maLoai);
                    Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }
}
