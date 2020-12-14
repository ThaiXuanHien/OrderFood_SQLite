package com.example.orderfood_sqlite.dto;

public class ThucDonDTO {
    private int maThucDon,maLoaiThucDon;
    private String tenThucDon,giaTien;
    private String hinhAnh;

    public int getMaLoaiThucDon() {
        return maLoaiThucDon;
    }

    public void setMaLoaiThucDon(int maLoaiThucDon) {
        this.maLoaiThucDon = maLoaiThucDon;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getMaThucDon() {
        return maThucDon;
    }

    public void setMaThucDon(int maThucDon) {
        this.maThucDon = maThucDon;
    }

    public String getTenThucDon() {
        return tenThucDon;
    }

    public void setTenThucDon(String tenThucDon) {
        this.tenThucDon = tenThucDon;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }
}
