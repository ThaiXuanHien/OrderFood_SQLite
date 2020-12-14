package com.example.orderfood_sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood_sqlite.database.CreateDatabase;
import com.example.orderfood_sqlite.dto.NguoiDungDTO;

public class NguoiDungDAO {

    SQLiteDatabase sqLiteDatabase;

    public NguoiDungDAO(Context context) {
        CreateDatabase database = new CreateDatabase(context);
        sqLiteDatabase = database.open();
    }

    public long themNguoiDung(NguoiDungDTO nguoiDungDTO) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_NGUOIDUNG_TAIKHOAN, nguoiDungDTO.getTaiKhoan());
        contentValues.put(CreateDatabase.TB_NGUOIDUNG_MATKHAU, nguoiDungDTO.getMatKhau());
        contentValues.put(CreateDatabase.TB_NGUOIDUNG_HOTEN, nguoiDungDTO.getHoTen());
        contentValues.put(CreateDatabase.TB_NGUOIDUNG_SDT, nguoiDungDTO.getSdt());
        contentValues.put(CreateDatabase.TB_NGUOIDUNG_GIOITINH, nguoiDungDTO.getGioiTinh());
        //contentValues.put(CreateDatabase.TB_NGUOIDUNG_MAQUYEN, nguoiDungDTO.getMaQuyen());


        long kiemTra = sqLiteDatabase.insert(CreateDatabase.TB_NGUOIDUNG, null, contentValues);
        return kiemTra;
    }

    public boolean KiemTraDangNhap(String tenDangNhap, String matKhau) {
        String query = "SELECT * FROM " + CreateDatabase.TB_NGUOIDUNG + " WHERE " + CreateDatabase.TB_NGUOIDUNG_TAIKHOAN + " = '" + tenDangNhap
                + "' AND " + CreateDatabase.TB_NGUOIDUNG_MATKHAU + " = '" + matKhau + "'";


        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }


    }
}
