package com.example.orderfood_sqlite.dto;

public class LoaiThucDonDTO {
    private int maLoaiThucDon;
    private String tenLoaiThucDon,hinAnh;



    public int getMaLoaiThucDon() {
        return maLoaiThucDon;
    }

    public void setMaLoaiThucDon(int maLoaiThucDon) {
        this.maLoaiThucDon = maLoaiThucDon;
    }

    public String getTenLoaiThucDon() {
        return tenLoaiThucDon;
    }

    public void setTenLoaiThucDon(String tenLoaiThucDon) {
        this.tenLoaiThucDon = tenLoaiThucDon;
    }

    public String getHinAnh() {
        return hinAnh;
    }

    public void setHinAnh(String hinAnh) {
        this.hinAnh = hinAnh;
    }
}
