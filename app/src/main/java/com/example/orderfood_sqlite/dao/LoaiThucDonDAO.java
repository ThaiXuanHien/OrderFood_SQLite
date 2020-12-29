package com.example.orderfood_sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood_sqlite.database.CreateDatabase;
import com.example.orderfood_sqlite.dto.LoaiThucDonDTO;

import java.util.ArrayList;
import java.util.List;

public class LoaiThucDonDAO {
    SQLiteDatabase database;

    public LoaiThucDonDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemLoaiThucDon(String tenLoaiThucDon) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_LOAIMONAN_TENLOAI, tenLoaiThucDon);

        long kiemTra = database.insert(CreateDatabase.TB_LOAIMONAN, null, contentValues);
        if (kiemTra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<LoaiThucDonDTO> LayDanhSachLoaiThucDon() {
        List<LoaiThucDonDTO> loaiMonAnDTOs = new ArrayList<LoaiThucDonDTO>();

        String query = "SELECT * FROM " + CreateDatabase.TB_LOAIMONAN;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LoaiThucDonDTO loaiThucDonDTO = new LoaiThucDonDTO();
            loaiThucDonDTO.setMaLoaiThucDon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_MALOAI)));
            loaiThucDonDTO.setTenLoaiThucDon(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_TENLOAI)));

            loaiMonAnDTOs.add(loaiThucDonDTO);

            cursor.moveToNext();
        }

        return loaiMonAnDTOs;
    }

    public String layHinhLoaiThucDon(int maLoaiThucDon) {
        String hinhAnh = "";
        String truyVan = "SELECT * FROM " + CreateDatabase.TB_MONAN + " WHERE " + CreateDatabase.TB_MONAN_MALOAI + " = '" + maLoaiThucDon + "' "
                + " AND " + CreateDatabase.TB_MONAN_HINHANH + " != '' ORDER BY " + CreateDatabase.TB_MONAN_MAMON + " LIMIT 1";
        Cursor cursor = database.rawQuery(truyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            hinhAnh = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH));
            cursor.moveToNext();
        }

        return hinhAnh;
    }

    public boolean capNhatLaiTenLoaiThucDon(int maLoai, String tenLoai) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_LOAIMONAN_TENLOAI, tenLoai);

        long kiemtra = database.update(CreateDatabase.TB_LOAIMONAN, contentValues, CreateDatabase.TB_LOAIMONAN_MALOAI + " = '" + maLoai + "'", null);

        if (kiemtra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean xoaLoaiThucDon(int maLoai) {

        long kiemTra = database.delete(CreateDatabase.TB_LOAIMONAN, CreateDatabase.TB_LOAIMONAN_MALOAI + " = " + maLoai, null);
        if (kiemTra != 0) {
            return true;
        } else {
            return false;
        }

    }
}
