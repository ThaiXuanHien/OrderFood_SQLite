package com.example.orderfood_sqlite.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.adapter.NguoiDungAdapter;
import com.example.orderfood_sqlite.dao.NguoiDungDAO;
import com.example.orderfood_sqlite.dto.NguoiDungDTO;

import java.util.List;

public class Fragment_NguoiDung extends Fragment {

    ListView lvNguoiDung;
    NguoiDungDAO nguoiDungDAO;
    List<NguoiDungDTO> nguoiDungDTOList;

    NguoiDungAdapter nguoiDungAdapter;

    FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nguoidung, container, false);
        setHasOptionsMenu(true);
        fragmentManager = getActivity().getSupportFragmentManager();

        nguoiDungDAO = new NguoiDungDAO(getActivity());

        lvNguoiDung = view.findViewById(R.id.lvNguoiDung);

        registerForContextMenu(lvNguoiDung);
        refreshNguoiDung();


        return view;
    }

    private void refreshNguoiDung() {
        nguoiDungDTOList = nguoiDungDAO.layDanhSachNguoiDung();

        nguoiDungAdapter = new NguoiDungAdapter(getContext(), R.layout.item_nguoidung, nguoiDungDTOList);
        lvNguoiDung.setAdapter(nguoiDungAdapter);
        nguoiDungAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu_popup, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int viTri = menuInfo.position;
        int maNguoiDung = nguoiDungDTOList.get(viTri).getMaNguoiDung();

        switch (id) {
            case R.id.item_sua:
                FragmentTransaction transactionThemNguoiDung = fragmentManager.beginTransaction();
                Fragment_DangKy fragment_dangKy = new Fragment_DangKy();

                Bundle bundle = new Bundle();
                bundle.putInt("maNguoiDung", maNguoiDung);
                fragment_dangKy.setArguments(bundle);
                transactionThemNguoiDung.replace(R.id.content, fragment_dangKy);
                transactionThemNguoiDung.commit();
                break;

            case R.id.item_xoa:
                boolean kiemTra = nguoiDungDAO.xoaNguoiDung(maNguoiDung);
                if (kiemTra) {
                    refreshNguoiDung();
                    Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
                ;
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem itThemBanAn = menu.add(1, R.id.item_menu_toolbar_themNguoiDung, 1, R.string.themNguoiDung);
        itThemBanAn.setIcon(android.R.drawable.ic_menu_add);
        itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_menu_toolbar_themNguoiDung:
                FragmentTransaction transactionThemNguoiDung = fragmentManager.beginTransaction();
                Fragment_DangKy fragment_dangKy = new Fragment_DangKy();
                transactionThemNguoiDung.replace(R.id.content, fragment_dangKy);
                transactionThemNguoiDung.commit();
                break;
        }
        return true;
    }
}
