package com.example.orderfood_sqlite.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
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
import androidx.fragment.app.FragmentTransaction;

import com.example.orderfood_sqlite.MainActivity;
import com.example.orderfood_sqlite.PopUpThemBanAnActivity;
import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.ThemLoaiThucDonActivity;
import com.example.orderfood_sqlite.ThemThucDonActivity;
import com.example.orderfood_sqlite.adapter.LoaiThucDonAdapterGridView;
import com.example.orderfood_sqlite.dao.LoaiThucDonDAO;
import com.example.orderfood_sqlite.dto.LoaiThucDonDTO;

import java.util.List;

public class Fragment_HienThiThucDon extends Fragment {

    GridView gvLoaiThucDon;
    List<LoaiThucDonDTO> loaiThucDonDTOList;
    LoaiThucDonDAO loaiThucDonDAO;

    FragmentManager fragmentManager;

    public static final int REQUEST_THEMTD = 456;
    public static final int REQUEST_THEMLTD = 4567;
    public static final String TAG = Fragment_HienThiThucDon.class.getName();


    int maBan = 0;

    int maQuyen = 0;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hienthi_thucdon, container, false);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Loại thực đơn");

        gvLoaiThucDon = view.findViewById(R.id.gvThucDon);

        fragmentManager = getActivity().getSupportFragmentManager();

        refreshThucDon();



        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maQuyen = sharedPreferences.getInt("maquyen", 0);


        if (maQuyen == 1) {
            registerForContextMenu(gvLoaiThucDon);
        }

        Bundle bDuLieuThucDon = getArguments();
        if (bDuLieuThucDon != null) {
            maBan = bDuLieuThucDon.getInt("maban");
            Log.d("maban", maBan + "");
        }

        gvLoaiThucDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int maLoai = loaiThucDonDTOList.get(position).getMaLoaiThucDon();

                ThucDonFragment thucDonFragment = new ThucDonFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("maloai", maLoai);
                bundle.putInt("maban", maBan);
                thucDonFragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // addToBackStack để trở về fragment trước đó
                transaction.replace(R.id.content, thucDonFragment).addToBackStack("hienthiloai");

                transaction.commit();
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
        int maLoai = loaiThucDonDTOList.get(viTri).getMaLoaiThucDon();

        switch (id) {
            case R.id.item_sua:

                Intent intent = new Intent(getContext(), ThemLoaiThucDonActivity.class);
                intent.putExtra("maloai", maLoai);
                intent.putExtra("modeLTD", true);
                intent.putExtra("tenloai", loaiThucDonDTOList.get(viTri).getTenLoaiThucDon());
                Toast.makeText(getContext(), loaiThucDonDTOList.get(viTri).getTenLoaiThucDon() + "   " + maLoai, Toast.LENGTH_SHORT).show();
                startActivityForResult(intent, REQUEST_THEMLTD);
                break;
            case R.id.item_xoa:
                boolean kiemTra = loaiThucDonDAO.xoaLoaiThucDon(maLoai);
                if (kiemTra) {
                    refreshThucDon();
                    Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (maQuyen == 1) {
            MenuItem menuItem = menu.add(1, R.id.item_menu_toolbar_themThucDon, 1, R.string.themThucDon);
            menuItem.setIcon(R.drawable.themthucdon);
            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.item_menu_toolbar_themThucDon:
                Intent intent = new Intent(getContext(), ThemThucDonActivity.class);
                startActivityForResult(intent, REQUEST_THEMTD);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_THEMTD) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = data;
                boolean kiemTra = intent.getBooleanExtra("themThucDon", false);
                if (kiemTra) {
                    refreshThucDon();
                    Toast.makeText(getContext(), "Thêm thực đơn thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Thêm thực đơn thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (requestCode == REQUEST_THEMLTD) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = data;
                boolean kiemTra = intent.getBooleanExtra("capNhatLoaiThucDon", false);
                if (kiemTra) {
                    refreshThucDon();
                    Toast.makeText(getContext(), "Cập nhật thực đơn thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Cập nhật thực đơn thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void refreshThucDon() {
        loaiThucDonDAO = new LoaiThucDonDAO(getContext());
        loaiThucDonDTOList = loaiThucDonDAO.LayDanhSachLoaiThucDon();
        LoaiThucDonAdapterGridView adapterGridView = new LoaiThucDonAdapterGridView(getContext(), R.layout.item_loaithucdon, loaiThucDonDTOList);
        gvLoaiThucDon.setAdapter(adapterGridView);
        adapterGridView.notifyDataSetChanged();
    }
}
