package com.example.orderfood_sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood_sqlite.database.CreateDatabase;
import com.example.orderfood_sqlite.dto.ThucDonDTO;

import java.util.ArrayList;
import java.util.List;

public class ThucDonDAO {
    SQLiteDatabase database;

    public ThucDonDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean themThucDon(ThucDonDTO thucDonDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_MONAN_TENMONAN, thucDonDTO.getTenThucDon());
        contentValues.put(CreateDatabase.TB_MONAN_GIATIEN, thucDonDTO.getGiaTien());
        contentValues.put(CreateDatabase.TB_MONAN_MALOAI, thucDonDTO.getMaLoaiThucDon());
        contentValues.put(CreateDatabase.TB_MONAN_HINHANH, thucDonDTO.getHinhAnh());

        long kiemTra = database.insert(CreateDatabase.TB_MONAN, null, contentValues);
        if (kiemTra != 0) {
            return true;
        } else {
            return false;
        }

    }

    public List<ThucDonDTO> LayDanhSachThucDon() {
        List<ThucDonDTO> monAnDTOs = new ArrayList<ThucDonDTO>();

        String truyVan = "SELECT * FROM " + CreateDatabase.TB_MONAN;
        Cursor cursor = database.rawQuery(truyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ThucDonDTO monAnDTO = new ThucDonDTO();
            monAnDTO.setHinhAnh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH)) + "");
            monAnDTO.setTenThucDon(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));
            monAnDTO.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            monAnDTO.setMaThucDon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MAMON)));
            monAnDTO.setMaLoaiThucDon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MALOAI)));

            monAnDTOs.add(monAnDTO);
            cursor.moveToNext();
        }

        return monAnDTOs;

    }

    public List<ThucDonDTO> LayDanhSachThucDonTheoLoai(int maLoai) {
        List<ThucDonDTO> monAnDTOs = new ArrayList<ThucDonDTO>();

        String truyVan = "SELECT * FROM " + CreateDatabase.TB_MONAN + " WHERE " + CreateDatabase.TB_MONAN_MALOAI + " = '" + maLoai + "' ";
        Cursor cursor = database.rawQuery(truyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ThucDonDTO monAnDTO = new ThucDonDTO();
            monAnDTO.setHinhAnh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH)) + "");
            monAnDTO.setTenThucDon(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));
            monAnDTO.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            monAnDTO.setMaThucDon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MAMON)));
            monAnDTO.setMaLoaiThucDon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MALOAI)));

            monAnDTOs.add(monAnDTO);
            cursor.moveToNext();
        }

        return monAnDTOs;

    }

    public boolean capNhatLaiTenThucDon(ThucDonDTO thucDonDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_MONAN_TENMONAN, thucDonDTO.getTenThucDon());
        contentValues.put(CreateDatabase.TB_MONAN_GIATIEN, thucDonDTO.getGiaTien());
        contentValues.put(CreateDatabase.TB_MONAN_MALOAI, thucDonDTO.getMaLoaiThucDon());
        contentValues.put(CreateDatabase.TB_MONAN_HINHANH, thucDonDTO.getHinhAnh());

        long kiemTra = database.update(CreateDatabase.TB_MONAN, contentValues, CreateDatabase.TB_MONAN_MAMON + " = " + thucDonDTO.getMaThucDon(), null);
        if (kiemTra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean xoaThucDon(int maMonAn) {

        long kiemTra = database.delete(CreateDatabase.TB_MONAN, CreateDatabase.TB_MONAN_MAMON + " = " + maMonAn, null);
        if (kiemTra != 0) {
            return true;
        } else {
            return false;
        }

    }
}
