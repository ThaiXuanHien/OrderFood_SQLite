package com.example.orderfood_sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood_sqlite.database.CreateDatabase;
import com.example.orderfood_sqlite.dto.ThucDonDTO;

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
}
