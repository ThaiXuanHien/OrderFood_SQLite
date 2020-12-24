package com.example.orderfood_sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood_sqlite.database.CreateDatabase;
import com.example.orderfood_sqlite.dto.QuyenDTO;

import java.util.ArrayList;
import java.util.List;

public class QuyenDAO {

    SQLiteDatabase sqLiteDatabase;

    public QuyenDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        sqLiteDatabase = createDatabase.open();
    }

    public void themQuyen(String tenQuyen) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_QUYEN_TENQUYEN, tenQuyen);
        sqLiteDatabase.insert(CreateDatabase.TB_QUYEN, null, contentValues);
    }

    public List<QuyenDTO> layDanhSachQuyen() {
        List<QuyenDTO> quyenDTOs = new ArrayList<QuyenDTO>();
        String truyVan = "SELECT * FROM " + CreateDatabase.TB_QUYEN;
        Cursor cursor = sqLiteDatabase.rawQuery(truyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            QuyenDTO quyenDTO = new QuyenDTO();
            quyenDTO.setMaQuyen(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_QUYEN_MAQUYEN)));
            quyenDTO.setTenQuyen(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_QUYEN_TENQUYEN)));

            quyenDTOs.add(quyenDTO);

            cursor.moveToNext();
        }

        return quyenDTOs;
    }
}
