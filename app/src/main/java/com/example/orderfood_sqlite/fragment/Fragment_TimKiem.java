package com.example.orderfood_sqlite.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;


import com.example.orderfood_sqlite.MainActivity;
import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.adapter.TimKiemMonAnAdapter;
import com.example.orderfood_sqlite.dao.ThucDonDAO;
import com.example.orderfood_sqlite.dto.ThucDonDTO;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_TimKiem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_TimKiem extends Fragment {


    RecyclerView rcvTimKiemMonAn;
    TextView txtKoCoMonAn;
    ThucDonDAO thucDonDAO;
    List<ThucDonDTO> thucDonDTOList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_TimKiem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_TimKiem.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_TimKiem newInstance(String param1, String param2) {
        Fragment_TimKiem fragment = new Fragment_TimKiem();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment__tim_kiem, container, false);

        rcvTimKiemMonAn = view.findViewById(R.id.recycleviewTimKiemMonAn);
        txtKoCoMonAn = view.findViewById(R.id.textviewKhongCoMonAn);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("");
        setHasOptionsMenu(true);

        thucDonDAO = new ThucDonDAO(getContext());


        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchTuKhoaMonAn(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchTuKhoaMonAn(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void SearchTuKhoaMonAn(String query) {

        thucDonDTOList = thucDonDAO.timKiemMonAn(query);
        if (thucDonDTOList.size() > 0) {
            TimKiemMonAnAdapter timKiemMonAnAdapter = new TimKiemMonAnAdapter(thucDonDTOList);
            rcvTimKiemMonAn.setLayoutManager(new LinearLayoutManager(getContext()));
            rcvTimKiemMonAn.setAdapter(timKiemMonAnAdapter);
            txtKoCoMonAn.setVisibility(View.GONE);
            rcvTimKiemMonAn.setVisibility(View.VISIBLE);
        } else {
            txtKoCoMonAn.setVisibility(View.VISIBLE);
            rcvTimKiemMonAn.setVisibility(View.GONE);
        }


    }
}