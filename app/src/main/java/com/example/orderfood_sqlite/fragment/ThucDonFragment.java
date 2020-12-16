package com.example.orderfood_sqlite.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.adapter.ThucDonAdapter;
import com.example.orderfood_sqlite.dao.ThucDonDAO;
import com.example.orderfood_sqlite.dto.ThucDonDTO;

import java.util.List;

public class ThucDonFragment extends Fragment {

    GridView gvThucDon;

    ThucDonDAO thucDonDAO;
    List<ThucDonDTO> thucDonDTOList;
    ThucDonAdapter thucDonAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hienthi_thucdon, container, false);

        thucDonDAO = new ThucDonDAO(getContext());
        gvThucDon = view.findViewById(R.id.gvThucDon);


        Bundle bundle = getArguments();
        if (bundle != null) {
            int maLoai = bundle.getInt("maloai");
            thucDonDTOList = thucDonDAO.LayDanhSachThucDonTheoLoai(maLoai);
            thucDonAdapter = new ThucDonAdapter(getContext(),R.layout.item_thucdon,thucDonDTOList);
            gvThucDon.setAdapter(thucDonAdapter);
            thucDonAdapter.notifyDataSetChanged();

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
}
