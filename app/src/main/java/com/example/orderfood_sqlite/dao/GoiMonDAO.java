package com.example.orderfood_sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood_sqlite.database.CreateDatabase;
import com.example.orderfood_sqlite.dto.GoiMonDTO;

public class GoiMonDAO {

    SQLiteDatabase sqLiteDatabase;

    public GoiMonDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        sqLiteDatabase = createDatabase.open();

    }

    public long themGoiMonAn(GoiMonDTO goiMonDTO) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(CreateDatabase.TB_GOIMON_MABAN, goiMonDTO.getMaBan());
        contentValues.put(CreateDatabase.TB_GOIMON_MANGUOIDUNG, goiMonDTO.getMaNguoiDung());
        contentValues.put(CreateDatabase.TB_GOIMON_NGAYGOI, goiMonDTO.getNgayGoi());
        contentValues.put(CreateDatabase.TB_GOIMON_TINHTRANG, goiMonDTO.getTinhTrang());

        long maGoiMon = sqLiteDatabase.insert(CreateDatabase.TB_GOIMON, null, contentValues);

        return maGoiMon;

    }
}
