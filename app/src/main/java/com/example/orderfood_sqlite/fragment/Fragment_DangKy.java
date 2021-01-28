package com.example.orderfood_sqlite.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.dao.NguoiDungDAO;
import com.example.orderfood_sqlite.dao.QuyenDAO;
import com.example.orderfood_sqlite.dto.NguoiDungDTO;
import com.example.orderfood_sqlite.dto.QuyenDTO;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class Fragment_DangKy extends Fragment implements View.OnClickListener {

    TextInputLayout inputLayoutHoTen, inputLayoutTaiKhoanDangKy, inputLayoutSDT, inputLayoutMatkhauDangKy, inputLayoutNhapLaiMK;
    RadioGroup rdgGioiTinh;
    TextView txtTitleDangKy;
    Button btnRegister;
    Spinner spinnerQuyen;
    RadioButton rdNam, rdNu;
    String gioiTinh = "";
    NguoiDungDAO nguoiDungDAO;
    QuyenDAO quyenDAO;
    int maNguoiDung = 0;

    List<String> quyenList;
    List<QuyenDTO> quyenDTOList;
    boolean mode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dangky, container, false);

        anhXa(view);
        btnRegister.setOnClickListener(this);
        nguoiDungDAO = new NguoiDungDAO(getContext());
        quyenDAO = new QuyenDAO(getContext());

        /*quyenDAO.themQuyen("Quản lý");
        quyenDAO.themQuyen("Người dùng");*/

        Bundle bundle = getArguments();
        if (bundle != null) {

            mode = bundle.getBoolean("mode");

            spinnerQuyen.setVisibility(View.VISIBLE);

            quyenList = new ArrayList<>();
            quyenDTOList = quyenDAO.layDanhSachQuyen();
            for (int i = 0; i < quyenDTOList.size(); i++) {
                String tenQuyen = quyenDTOList.get(i).getTenQuyen();
                quyenList.add(tenQuyen);
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, quyenList);
            spinnerQuyen.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();


            maNguoiDung = bundle.getInt("maNguoiDung", 0);

            if (maNguoiDung != 0) {
                txtTitleDangKy.setText("Cập nhật \nngười dùng");
                txtTitleDangKy.setTextSize(30);
                btnRegister.setText("Lưu");

                NguoiDungDTO nguoiDungDTO = nguoiDungDAO.layDanhSachNguoiDungTheoMa(maNguoiDung);
                inputLayoutHoTen.getEditText().setText(nguoiDungDTO.getHoTen());
                inputLayoutTaiKhoanDangKy.getEditText().setText(nguoiDungDTO.getTaiKhoan());
                inputLayoutMatkhauDangKy.getEditText().setText(nguoiDungDTO.getMatKhau());
                inputLayoutSDT.getEditText().setText(nguoiDungDTO.getSdt());
                if (nguoiDungDTO.getGioiTinh().equals("Nam")) {
                    rdNam.setChecked(true);
                } else {
                    rdNu.setChecked(true);
                }
            }


            if (!mode) {
                txtTitleDangKy.setText("Thêm \nngười dùng");
                txtTitleDangKy.setTextSize(30);
                btnRegister.setText("Lưu");
            }

        }


        return view;
    }

    private void anhXa(View view) {
        inputLayoutTaiKhoanDangKy = view.findViewById(R.id.inputLayoutTaiKhoanDangKy);
        inputLayoutMatkhauDangKy = view.findViewById(R.id.inputLayoutMatkhauDangKy);
        inputLayoutHoTen = view.findViewById(R.id.inputLayoutHoTen);
        inputLayoutSDT = view.findViewById(R.id.inputLayoutSDT);
        inputLayoutNhapLaiMK = view.findViewById(R.id.inputLayoutNhapLaiMK);
        rdgGioiTinh = view.findViewById(R.id.rdgGioiTinh);
        btnRegister = view.findViewById(R.id.btnRegister);
        txtTitleDangKy = view.findViewById(R.id.titleDangKy);
        rdNam = view.findViewById(R.id.rdbNam);
        rdNu = view.findViewById(R.id.rdbNu);
        spinnerQuyen = view.findViewById(R.id.spinnerQuyen);

    }

    public void themNguoiDung() {
        int idGioiTinh = rdgGioiTinh.getCheckedRadioButtonId();

        switch (idGioiTinh) {
            case R.id.rdbNam:
                gioiTinh = "Nam";
                break;
            case R.id.rdbNu:
                gioiTinh = "Nữ";
                break;
        }
        NguoiDungDTO nguoiDungDTO = new NguoiDungDTO();
        nguoiDungDTO.setTaiKhoan(inputLayoutTaiKhoanDangKy.getEditText().getText().toString().trim());
        nguoiDungDTO.setMatKhau(inputLayoutMatkhauDangKy.getEditText().getText().toString().trim());
        nguoiDungDTO.setHoTen(inputLayoutHoTen.getEditText().getText().toString().trim());
        nguoiDungDTO.setSdt(inputLayoutSDT.getEditText().getText().toString().trim());
        nguoiDungDTO.setGioiTinh(gioiTinh);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mode = bundle.getBoolean("mode");
            if (!mode) {
                int viTri = spinnerQuyen.getSelectedItemPosition();
                int maQuyen = quyenDTOList.get(viTri).getMaQuyen();
                nguoiDungDTO.setMaQuyen(maQuyen);
            }
        }

        nguoiDungDTO.setMaQuyen(2);


        long kiemTra = nguoiDungDAO.themNguoiDung(nguoiDungDTO);


        if (kiemTra != 0) {
            Toast.makeText(getContext(), R.string.themThanhCong, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), R.string.thatBai, Toast.LENGTH_SHORT).show();
        }
    }

    public void capNhatNguoiDung() {
        int idGioiTinh = rdgGioiTinh.getCheckedRadioButtonId();

        switch (idGioiTinh) {
            case R.id.rdbNam:
                gioiTinh = "Nam";
                break;
            case R.id.rdbNu:
                gioiTinh = "Nữ";
                break;
        }
        NguoiDungDTO nguoiDungDTO = new NguoiDungDTO();
        nguoiDungDTO.setTaiKhoan(inputLayoutTaiKhoanDangKy.getEditText().getText().toString().trim());
        nguoiDungDTO.setMatKhau(inputLayoutMatkhauDangKy.getEditText().getText().toString().trim());
        nguoiDungDTO.setHoTen(inputLayoutHoTen.getEditText().getText().toString().trim());
        nguoiDungDTO.setSdt(inputLayoutSDT.getEditText().getText().toString().trim());
        nguoiDungDTO.setGioiTinh(gioiTinh);
        nguoiDungDTO.setMaNguoiDung(maNguoiDung);

        int viTri = spinnerQuyen.getSelectedItemPosition();
        int maQuyen = quyenDTOList.get(viTri).getMaQuyen();
        nguoiDungDTO.setMaQuyen(maQuyen);

        boolean kiemTra = nguoiDungDAO.capNhatNguoiDung(nguoiDungDTO);

        if (kiemTra) {
            Toast.makeText(getContext(), R.string.capNhatThanhCong, Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack();

        } else {
            Toast.makeText(getContext(), R.string.thatBai, Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnRegister:
                if (!validateTaiKhoan() | !validateMatKhau() | !validateNhapLaiMatKhau() |
                        !validateHoTen() | !validateSDT()) {
                    return;
                } else {
                    if (maNguoiDung != 0) {
                        capNhatNguoiDung();
                    } else {
                        themNguoiDung();
                    }

//                    int idGioiTinh = rdgGioiTinh.getCheckedRadioButtonId();
//
//                    switch (idGioiTinh) {
//                        case R.id.rdbNam:
//                            gioiTinh = "Nam";
//                            break;
//                        case R.id.rdbNu:
//                            gioiTinh = "Nữ";
//                            break;
//                    }
//                    NguoiDungDTO nguoiDungDTO = new NguoiDungDTO();
//                    nguoiDungDTO.setTaiKhoan(inputLayoutTaiKhoanDangKy.getEditText().getText().toString().trim());
//                    nguoiDungDTO.setMatKhau(inputLayoutMatkhauDangKy.getEditText().getText().toString().trim());
//                    nguoiDungDTO.setHoTen(inputLayoutHoTen.getEditText().getText().toString().trim());
//                    nguoiDungDTO.setSdt(inputLayoutSDT.getEditText().getText().toString().trim());
//                    nguoiDungDTO.setGioiTinh(gioiTinh);
//                    //nguoiDungDTO.setMaQuyen(1);
//                    long kiemTra = nguoiDungDAO.themNguoiDung(nguoiDungDTO);
//                    System.out.println(gioiTinh);
//                    if (kiemTra != 0) {
//                        Toast.makeText(getContext(), "Thêm người dùng thành công", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getContext(), "Thêm người dùng thất bại", Toast.LENGTH_SHORT).show();
//                    }
                }
                break;
        }
    }

    private boolean validateTaiKhoan() {
        String tk = inputLayoutTaiKhoanDangKy.getEditText().getText().toString().trim();
        if (tk.isEmpty()) {
            inputLayoutTaiKhoanDangKy.setError("Bạn chưa nhập tài khoản");
            return false;
        } else if (tk.length() > 30) {
            inputLayoutTaiKhoanDangKy.setError("Tài khoản quá 30 ký tự");
            return false;
        } else if (tk.contains(" ")) {
            inputLayoutTaiKhoanDangKy.setError("Tài khoản chứa khoảng trắng");
            return false;
        } else {
            inputLayoutTaiKhoanDangKy.setErrorEnabled(false);
            inputLayoutTaiKhoanDangKy.setError(null);
            return true;
        }
    }

    private boolean validateMatKhau() {
        String mk = inputLayoutMatkhauDangKy.getEditText().getText().toString().trim();
        if (mk.isEmpty()) {
            inputLayoutMatkhauDangKy.setError("Bạn chưa nhập mật khẩu");
            return false;
        } else {
            inputLayoutMatkhauDangKy.setError(null);
            inputLayoutMatkhauDangKy.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateNhapLaiMatKhau() {
        String nlmk = inputLayoutNhapLaiMK.getEditText().getText().toString().trim();
        if (nlmk.isEmpty()) {
            inputLayoutNhapLaiMK.setError("Bạn chưa nhập lại mật khẩu");
            return false;
        } else if (!nlmk.equals(inputLayoutMatkhauDangKy.getEditText().getText().toString().trim())) {
            inputLayoutNhapLaiMK.setError("Mật khẩu không trùng khớp");
            return false;
        } else {
            inputLayoutNhapLaiMK.setError(null);
            inputLayoutNhapLaiMK.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateHoTen() {
        String hoTen = inputLayoutHoTen.getEditText().getText().toString().trim();
        if (hoTen.isEmpty()) {
            inputLayoutHoTen.setError("Bạn chưa nhập Họ tên");
            return false;
        } else {
            inputLayoutHoTen.setError(null);
            inputLayoutHoTen.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateSDT() {
        String sdt = inputLayoutSDT.getEditText().getText().toString().trim();
        if (sdt.isEmpty()) {
            inputLayoutSDT.setError("Bạn chưa nhập Số điện thoại");
            return false;
        } else if (sdt.contains(" ")) {
            inputLayoutSDT.setError("Số điện thoại không đúng định dạng");
            return false;
        } else {
            inputLayoutSDT.setError(null);
            inputLayoutSDT.setErrorEnabled(false);
            return true;
        }
    }
}
