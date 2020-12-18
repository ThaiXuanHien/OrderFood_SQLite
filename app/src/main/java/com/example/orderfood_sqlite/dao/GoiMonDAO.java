package com.example.orderfood_sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood_sqlite.database.CreateDatabase;
import com.example.orderfood_sqlite.dto.ChiTietGoiMonDTO;
import com.example.orderfood_sqlite.dto.GoiMonDTO;
import com.example.orderfood_sqlite.dto.ThanhToanDTO;

import java.util.ArrayList;
import java.util.List;

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

    public long layMaGoiMonTheoMaBan(int maBan, String tinhTrang) {
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_GOIMON + " WHERE " + CreateDatabase.TB_GOIMON_MABAN + " = '" + maBan + "' AND "
                + CreateDatabase.TB_GOIMON_TINHTRANG + " = '" + tinhTrang + "'";

        long maGoiMon = 0;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            maGoiMon = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_MAGOIMON));

            cursor.moveToNext();
        }

        return maGoiMon;
    }

    public boolean kiemTraMonAnDaTonTai(int maGoiMon, int maMonAn) {
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " WHERE " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN
                + " = " + maMonAn + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + maGoiMon;

        Cursor cursor = sqLiteDatabase.rawQuery(truyvan, null);
        if (cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public int laySoLuongMonAnTheoMaGoiMon(int maGoiMon, int maMonAn) {
        int soLuong = 0;
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " WHERE " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN
                + " = " + maMonAn + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + maGoiMon;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            soLuong = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG));

            cursor.moveToNext();
        }

        return soLuong;
    }

    public boolean CapNhatSoLuong(ChiTietGoiMonDTO chiTietGoiMonDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_SOLUONG, chiTietGoiMonDTO.getSoLuong());

        long kiemTra = sqLiteDatabase.update(CreateDatabase.TB_CHITIETGOIMON, contentValues, CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + chiTietGoiMonDTO.getMaGoiMon()
                + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = " + chiTietGoiMonDTO.getMaMonAn(), null);

        if (kiemTra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean themChiTietGoiMon(ChiTietGoiMonDTO chiTietGoiMonDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_SOLUONG, chiTietGoiMonDTO.getSoLuong());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_MAGOIMON, chiTietGoiMonDTO.getMaGoiMon());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_MAMONAN, chiTietGoiMonDTO.getMaMonAn());

        long kiemTra = sqLiteDatabase.insert(CreateDatabase.TB_CHITIETGOIMON, null, contentValues);

        if (kiemTra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<ThanhToanDTO> layDanhSachMonAnTheoMaGoiMon(int maGoiMon) {
        String truyVan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " ct," + CreateDatabase.TB_MONAN + " ma WHERE "
                + "ct." + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = ma." + CreateDatabase.TB_MONAN_MAMON
                + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = '" + maGoiMon + "'";

        List<ThanhToanDTO> thanhToanDTOs = new ArrayList<ThanhToanDTO>();
        Cursor cursor = sqLiteDatabase.rawQuery(truyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ThanhToanDTO thanhToanDTO = new ThanhToanDTO();
            thanhToanDTO.setSoLuong(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG)));
            thanhToanDTO.setGiaTien(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            thanhToanDTO.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));
            thanhToanDTO.setHinhAnh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH)));
            thanhToanDTOs.add(thanhToanDTO);

            cursor.moveToNext();
        }

        return thanhToanDTOs;
    }

    public boolean capNhatTrangThaiGoiMonTheoMaBan(int maBan,String tinhTrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_TINHTRANG,tinhTrang);

        long kiemTra = sqLiteDatabase.update(CreateDatabase.TB_GOIMON,contentValues,CreateDatabase.TB_GOIMON_MABAN + " = '" + maBan + "'",null);
        if(kiemTra != 0){
            return true;
        }else{
            return false;
        }
    }
}
