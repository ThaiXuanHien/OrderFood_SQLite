package com.example.orderfood_sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood_sqlite.database.CreateDatabase;
import com.example.orderfood_sqlite.dto.BanAnDTO;

import java.util.ArrayList;
import java.util.List;

public class BanAnDAO {
    SQLiteDatabase database;

    public BanAnDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemBanAn(String tenBan) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN, tenBan);
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG, "false");

        long kiemTra = database.insert(CreateDatabase.TB_BANAN, null, contentValues);
        if (kiemTra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<BanAnDTO> LayTatCaBanAn() {
        List<BanAnDTO> banAnDTOList = new ArrayList<BanAnDTO>();
        String truyVan = "SELECT * FROM " + CreateDatabase.TB_BANAN;
        Cursor cursor = database.rawQuery(truyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BanAnDTO banAnDTO = new BanAnDTO();
            banAnDTO.setMaBanAn(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_BANAN_MABAN)));
            banAnDTO.setTenBanAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TENBAN)));

            banAnDTOList.add(banAnDTO);
            cursor.moveToNext();
        }
        return banAnDTOList;
    }

    public String LayTinhTrangBanTheoMa(int maban) {
        String tinhTrang = "";
        String truyVan = "SELECT * FROM " + CreateDatabase.TB_BANAN + " WHERE " + CreateDatabase.TB_BANAN_MABAN + " = '" + maban + "'";
        Cursor cursor = database.rawQuery(truyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tinhTrang = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TINHTRANG));

            cursor.moveToNext();
        }

        return tinhTrang;
    }

    public boolean capNhatTinhTrangBanAn(int maBan, String tinhTrang) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG, tinhTrang);

        int kiemTra = database.update(CreateDatabase.TB_BANAN, contentValues, CreateDatabase.TB_BANAN_MABAN + " = '" + maBan + "'", null);

        if (kiemTra != 0) {
            return true;
        } else {
            return false;
        }

    }
}
