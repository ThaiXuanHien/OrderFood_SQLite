package com.example.orderfood_sqlite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CreateDatabase extends SQLiteOpenHelper {

    public static String TB_NGUOIDUNG = "NGUOIDUNG";
    public static String TB_MONAN = "MONAN";
    public static String TB_LOAIMONAN = "LOAIMONAN";
    public static String TB_BANAN = "BANAN";
    public static String TB_GOIMON = "GOIMON";
    public static String TB_CHITIETGOIMON = "CHITIETGOIMON";
    public static String TB_QUYEN = "QUYEN";

    public static String TB_QUYEN_MAQUYEN = "MAQUYEN";
    public static String TB_QUYEN_TENQUYEN = "TENQUYEN";

    public static String TB_NGUOIDUNG_MANGUOIDUNG = "MANGUOIDUNG";
    public static String TB_NGUOIDUNG_MAQUYEN = "MAQUYEN";
    public static String TB_NGUOIDUNG_TAIKHOAN = "TAIKHOAN";
    public static String TB_NGUOIDUNG_MATKHAU = "MATKHAU";
    public static String TB_NGUOIDUNG_HOTEN = "HOTEN";
    public static String TB_NGUOIDUNG_GIOITINH = "GIOITINH";
    public static String TB_NGUOIDUNG_SDT = "SDT";


    public static String TB_MONAN_MAMON = "MAMON";
    public static String TB_MONAN_TENMONAN = "TENMONAN";
    public static String TB_MONAN_GIATIEN = "GIATIEN";
    public static String TB_MONAN_MALOAI = "MALOAI";
    public static String TB_MONAN_HINHANH = "HINHANH";

    public static String TB_LOAIMONAN_MALOAI = "MALOAI";
    public static String TB_LOAIMONAN_TENLOAI = "TENLOAI";

    public static String TB_BANAN_MABAN = "MABAN";
    public static String TB_BANAN_TENBAN = "TENBAN";
    public static String TB_BANAN_TINHTRANG = "TINHTRANG";

    public static String TB_GOIMON_MAGOIMON = "MAGOIMON";
    public static String TB_GOIMON_MANV = "MANV";
    public static String TB_GOIMON_NGAYGOI = "NGAYGOI";
    public static String TB_GOIMON_TINHTRANG = "TINHTRANG";
    public static String TB_GOIMON_MABAN = "MABAN";

    public static String TB_CHITIETGOIMON_MAGOIMON = "MAGOIMON";
    public static String TB_CHITIETGOIMON_MAMONAN = "MAMONAN";
    public static String TB_CHITIETGOIMON_SOLUONG = "SOLUONG";

    public CreateDatabase(@Nullable Context context) {
        super(context, "OrderFood", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*String tbNHANVIEN = "CREATE TABLE " + TB_NGUOIDUNG + " ( " + TB_NGUOIDUNG_MANGUOIDUNG + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_NGUOIDUNG_TAIKHOAN + " TEXT, " + TB_NGUOIDUNG_MATKHAU + " TEXT, " + TB_NGUOIDUNG_HOTEN + " TEXT, "
                + TB_NGUOIDUNG_SDT + " TEXT, " + TB_NGUOIDUNG_GIOITINH + " INTEGER , " + TB_NGUOIDUNG_MAQUYEN + " INTEGER )";*/

        String tbNGUOIDUNG = "CREATE TABLE " + TB_NGUOIDUNG + " ( " + TB_NGUOIDUNG_MANGUOIDUNG + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_NGUOIDUNG_TAIKHOAN + " TEXT, " + TB_NGUOIDUNG_MATKHAU + " TEXT, " + TB_NGUOIDUNG_HOTEN + " TEXT, "
                + TB_NGUOIDUNG_SDT + " TEXT, " + TB_NGUOIDUNG_GIOITINH + " TEXT" + ")";

        String tbBANAN = "CREATE TABLE " + TB_BANAN + " ( " + TB_BANAN_MABAN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_BANAN_TENBAN + " TEXT, " + TB_BANAN_TINHTRANG + " TEXT )";

        String tbMONAN = "CREATE TABLE " + TB_MONAN + " ( " + TB_MONAN_MAMON + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_MONAN_TENMONAN + " TEXT, " + TB_MONAN_MALOAI + " INTEGER, " + TB_MONAN_GIATIEN + " TEXT, "
                + TB_MONAN_HINHANH + " TEXT ) ";

        String tbLOAIMON = "CREATE TABLE " + TB_LOAIMONAN + " ( " + TB_LOAIMONAN_MALOAI + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TB_LOAIMONAN_TENLOAI + " TEXT )";

        String tbQUYEN = "CREATE TABLE " + TB_QUYEN + " ( " + TB_QUYEN_MAQUYEN + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TB_QUYEN_TENQUYEN + " TEXT )";

        String tbGOIMON = "CREATE TABLE " + TB_GOIMON + " ( " + TB_GOIMON_MAGOIMON + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_GOIMON_MABAN + " INTEGER, " + TB_GOIMON_MANV + " INTEGER, " + TB_GOIMON_NGAYGOI + " TEXT, "
                + TB_GOIMON_TINHTRANG + " TEXT )";

        String tbCHITIETGOIMON = "CREATE TABLE " + TB_CHITIETGOIMON + " ( " + TB_CHITIETGOIMON_MAGOIMON + " INTEGER, "
                + TB_CHITIETGOIMON_MAMONAN + " INTEGER, " + TB_CHITIETGOIMON_SOLUONG + " INTEGER, "
                + " PRIMARY KEY ( " + TB_CHITIETGOIMON_MAGOIMON + "," + TB_CHITIETGOIMON_MAMONAN + "))";

        db.execSQL(tbNGUOIDUNG);
        db.execSQL(tbBANAN);
        db.execSQL(tbMONAN);
        db.execSQL(tbLOAIMON);
        db.execSQL(tbGOIMON);
        db.execSQL(tbCHITIETGOIMON);
        db.execSQL(tbQUYEN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase open() {
        return this.getWritableDatabase();
    }
}
