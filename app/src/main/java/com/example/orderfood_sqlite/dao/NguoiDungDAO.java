package com.example.orderfood_sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood_sqlite.database.CreateDatabase;
import com.example.orderfood_sqlite.dto.NguoiDungDTO;

import java.util.ArrayList;
import java.util.List;

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
        contentValues.put(CreateDatabase.TB_NGUOIDUNG_MAQUYEN, nguoiDungDTO.getMaQuyen());


        long kiemTra = sqLiteDatabase.insert(CreateDatabase.TB_NGUOIDUNG, null, contentValues);
        return kiemTra;
    }

    public int kiemTraDangNhap(String taiKhoan, String matKhau) {
        String truyVan = "SELECT * FROM " + CreateDatabase.TB_NGUOIDUNG + " WHERE " + CreateDatabase.TB_NGUOIDUNG_TAIKHOAN + " = '" + taiKhoan
                + "' AND " + CreateDatabase.TB_NGUOIDUNG_MATKHAU + " = '" + matKhau + "'";

        int maNguoiDung = 0;
        Cursor cursor = sqLiteDatabase.rawQuery(truyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            maNguoiDung = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NGUOIDUNG_MANGUOIDUNG));
            cursor.moveToNext();
        }

        return maNguoiDung;
    }

    public int layQuyenNhanVien(int maNguoiDung) {
        int maQuyen = 0;
        String truyVan = "SELECT * FROM " + CreateDatabase.TB_NGUOIDUNG + " WHERE " + CreateDatabase.TB_NGUOIDUNG_MANGUOIDUNG + " = " + maNguoiDung;
        Cursor cursor = sqLiteDatabase.rawQuery(truyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            maQuyen = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NGUOIDUNG_MAQUYEN));
            cursor.moveToNext();
        }

        return maQuyen;
    }

    public List<NguoiDungDTO> layDanhSachNguoiDung() {
        List<NguoiDungDTO> nguoiDungDTOList = new ArrayList<NguoiDungDTO>();
        String truyVan = "SELECT * FROM " + CreateDatabase.TB_NGUOIDUNG;
        Cursor cursor = sqLiteDatabase.rawQuery(truyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NguoiDungDTO nguoiDungDTO = new NguoiDungDTO();
            nguoiDungDTO.setGioiTinh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NGUOIDUNG_GIOITINH)));
            nguoiDungDTO.setHoTen(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NGUOIDUNG_HOTEN)));
            nguoiDungDTO.setSdt(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NGUOIDUNG_SDT)));
            nguoiDungDTO.setTaiKhoan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NGUOIDUNG_TAIKHOAN)));
            nguoiDungDTO.setMatKhau(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NGUOIDUNG_MATKHAU)));
            nguoiDungDTO.setMaNguoiDung(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NGUOIDUNG_MANGUOIDUNG)));

            nguoiDungDTOList.add(nguoiDungDTO);
            cursor.moveToNext();
        }

        return nguoiDungDTOList;
    }

    public NguoiDungDTO layDanhSachNguoiDungTheoMa(int maNguoiDung) {
        NguoiDungDTO nguoiDungDTO = new NguoiDungDTO();
        String truyVan = "SELECT * FROM " + CreateDatabase.TB_NGUOIDUNG + " WHERE " + CreateDatabase.TB_NGUOIDUNG_MANGUOIDUNG + " = " + maNguoiDung;
        Cursor cursor = sqLiteDatabase.rawQuery(truyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            nguoiDungDTO.setGioiTinh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NGUOIDUNG_GIOITINH)));
            nguoiDungDTO.setHoTen(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NGUOIDUNG_HOTEN)));
            nguoiDungDTO.setSdt(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NGUOIDUNG_SDT)));
            nguoiDungDTO.setTaiKhoan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NGUOIDUNG_TAIKHOAN)));
            nguoiDungDTO.setMatKhau(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NGUOIDUNG_MATKHAU)));
            nguoiDungDTO.setMaNguoiDung(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NGUOIDUNG_MANGUOIDUNG)));

            cursor.moveToNext();
        }

        return nguoiDungDTO;
    }

    public boolean capNhatNguoiDung(NguoiDungDTO nguoiDungDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_NGUOIDUNG_TAIKHOAN, nguoiDungDTO.getTaiKhoan());
        contentValues.put(CreateDatabase.TB_NGUOIDUNG_MATKHAU, nguoiDungDTO.getMatKhau());
        contentValues.put(CreateDatabase.TB_NGUOIDUNG_HOTEN, nguoiDungDTO.getHoTen());
        contentValues.put(CreateDatabase.TB_NGUOIDUNG_SDT, nguoiDungDTO.getSdt());
        contentValues.put(CreateDatabase.TB_NGUOIDUNG_GIOITINH, nguoiDungDTO.getGioiTinh());
        contentValues.put(CreateDatabase.TB_NGUOIDUNG_MAQUYEN, nguoiDungDTO.getMaQuyen());

        long kiemTra = sqLiteDatabase.update(CreateDatabase.TB_NGUOIDUNG, contentValues, CreateDatabase.TB_NGUOIDUNG_MANGUOIDUNG + " = " + nguoiDungDTO.getMaNguoiDung(), null);
        if (kiemTra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean xoaNguoiDung(int manv) {

        long kiemTra = sqLiteDatabase.delete(CreateDatabase.TB_NGUOIDUNG, CreateDatabase.TB_NGUOIDUNG_MANGUOIDUNG + " = " + manv, null);
        if (kiemTra != 0) {
            return true;
        } else {
            return false;
        }
    }
}
