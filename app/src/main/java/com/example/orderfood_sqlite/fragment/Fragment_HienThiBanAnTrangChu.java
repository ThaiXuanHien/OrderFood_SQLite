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
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.orderfood_sqlite.MainActivity;
import com.example.orderfood_sqlite.PopUpThemBanAnActivity;
import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.adapter.BanAnAdapter;
import com.example.orderfood_sqlite.dao.BanAnDAO;
import com.example.orderfood_sqlite.dto.BanAnDTO;

import java.util.List;

public class Fragment_HienThiBanAnTrangChu extends Fragment{


    public static final int REQUEST_CODE_THEMBA = 111;
    public static final String TAG = Fragment_HienThiBanAnTrangChu.class.getName();

    GridView gvBanAn;
    List<BanAnDTO> banAnList;
    BanAnDAO banAnDAO;
    BanAnAdapter banAnAdapter;

    int maQuyen = 0;
    SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hienthibanan_trangchu, container, false);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.trangChu);

        gvBanAn = view.findViewById(R.id.gvBanAn);

        banAnDAO = new BanAnDAO(getContext());
        refreshBanAn();

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maQuyen = sharedPreferences.getInt("maquyen", 0);
        if (maQuyen == 1) {
            registerForContextMenu(gvBanAn);
        }


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("a","Fragment BanAN OnResume");
        refreshBanAn();
    }

    public void refreshBanAn() {
        banAnList = banAnDAO.LayTatCaBanAn();
        banAnAdapter = new BanAnAdapter(getContext(), R.layout.item_banan, banAnList);
        gvBanAn.setAdapter(banAnAdapter);
        banAnAdapter.notifyDataSetChanged();
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
        int maBan = banAnList.get(viTri).getMaBanAn();

        switch (id) {
            case R.id.item_sua:

                Intent intent = new Intent(getContext(), PopUpThemBanAnActivity.class);
                intent.putExtra("maban", maBan);
                intent.putExtra("mode", true);
                intent.putExtra("tenBan", banAnList.get(viTri).getTenBanAn());
                Toast.makeText(getContext(), banAnList.get(viTri).getTenBanAn(), Toast.LENGTH_SHORT).show();
                startActivityForResult(intent, REQUEST_CODE_THEMBA);
                break;
            case R.id.item_xoa:
                boolean kiemTra = banAnDAO.xoaBanAn(maBan);
                if (kiemTra) {
                    refreshBanAn();
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
            MenuItem menuItem = menu.add(1, R.id.item_menu_toolbar_themBanAn, 1, R.string.themBanAn);
            menuItem.setIcon(R.drawable.thembanan);
            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.item_menu_toolbar_themBanAn:
                Intent intent = new Intent(getContext(), PopUpThemBanAnActivity.class);
                intent.putExtra("mode", false);
                startActivityForResult(intent, REQUEST_CODE_THEMBA);
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_THEMBA) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = data;
                boolean kiemTra = intent.getBooleanExtra("LuuBanAn", false);
                System.out.println("them");
                if (kiemTra) {
                    refreshBanAn();
                    Toast.makeText(getContext(), "Lưu bàn ăn thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Lưu bàn ăn thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
