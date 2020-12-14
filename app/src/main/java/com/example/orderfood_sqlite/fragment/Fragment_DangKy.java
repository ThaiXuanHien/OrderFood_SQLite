package com.example.orderfood_sqlite.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.orderfood_sqlite.R;
import com.example.orderfood_sqlite.dao.NguoiDungDAO;
import com.example.orderfood_sqlite.dto.NguoiDungDTO;
import com.google.android.material.textfield.TextInputLayout;

public class Fragment_DangKy extends Fragment implements View.OnClickListener {

    TextInputLayout inputLayoutHoTen, inputLayoutTaiKhoanDangKy, inputLayoutSDT, inputLayoutMatkhauDangKy, inputLayoutNhapLaiMK;
    RadioGroup rdgGioiTinh;
    Button btnRegister;
    String gioiTinh = "";
    NguoiDungDAO nguoiDungDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dangky, container, false);

        anhXa(view);
        btnRegister.setOnClickListener(this);
        nguoiDungDAO = new NguoiDungDAO(getContext());
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
                    //nguoiDungDTO.setMaQuyen(1);
                    long kiemTra = nguoiDungDAO.themNguoiDung(nguoiDungDTO);
                    System.out.println(gioiTinh);
                    if (kiemTra != 0) {
                        Toast.makeText(getContext(), "Thêm người dùng thành công", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Thêm người dùng thất bại", Toast.LENGTH_SHORT).show();
                    }
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
