package com.example.orderfood_sqlite.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderfood_sqlite.MainActivity;
import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.adapter.GoiYAdapter;
import com.example.orderfood_sqlite.dao.ThucDonDAO;
import com.example.orderfood_sqlite.dto.ThucDonDTO;

import java.util.ArrayList;
import java.util.List;

public class FragmentGoiY extends Fragment {

    public static final String TAG = FragmentGoiY.class.getName();

    RecyclerView rcvGoiY;
    ViewFlipper viewFlipper;

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
        viewFlipper = view.findViewById(R.id.viewflipperGoiY);

        thucDonDTOList = thucDonDAO.LayDanhSachThucDon();
        goiYAdapter = new GoiYAdapter(getContext(),thucDonDTOList);
        rcvGoiY.setLayoutManager(new GridLayoutManager(getContext(),2));
        rcvGoiY.setAdapter(goiYAdapter);

        actionViewFlipper();


        return view;
    }

    private void actionViewFlipper() {
        ArrayList<Integer> listQuangCao = new ArrayList<>();
        listQuangCao.add(R.drawable.qc1);
        listQuangCao.add(R.drawable.qc2);
        listQuangCao.add(R.drawable.qc3);
        listQuangCao.add(R.drawable.qc4);
        listQuangCao.add(R.drawable.qc5);

        for (int i = 0; i < listQuangCao.size(); i++) {
            ImageView imageViewQuangCao = new ImageView(getContext());
            Glide.with(getContext()).load(listQuangCao.get(i)).into(imageViewQuangCao);

            imageViewQuangCao.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageViewQuangCao);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_SlideInRight = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        Animation animation_SlideOutRight = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_SlideInRight);
        viewFlipper.setOutAnimation(animation_SlideOutRight);

        viewFlipper.setAutoStart(true);

    }
}
