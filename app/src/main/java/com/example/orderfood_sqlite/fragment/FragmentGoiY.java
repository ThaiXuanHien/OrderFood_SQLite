package com.example.orderfood_sqlite.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood_sqlite.MainActivity;
import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.adapter.GoiYAdapter;
import com.example.orderfood_sqlite.dao.ThucDonDAO;
import com.example.orderfood_sqlite.dto.ThucDonDTO;

import java.util.List;

public class FragmentGoiY extends Fragment {

    RecyclerView rcvGoiY;

    List<ThucDonDTO> thucDonDTOList;
    GoiYAdapter goiYAdapter;
    ThucDonDAO thucDonDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_goiy, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.goiY);

        thucDonDAO = new ThucDonDAO(getContext());
        rcvGoiY = view.findViewById(R.id.rcvGoiY);

        thucDonDTOList = thucDonDAO.LayDanhSachThucDon();
        goiYAdapter = new GoiYAdapter(getContext(),thucDonDTOList);
        rcvGoiY.setLayoutManager(new GridLayoutManager(getContext(),2));
        rcvGoiY.setAdapter(goiYAdapter);



        return view;
    }
}
