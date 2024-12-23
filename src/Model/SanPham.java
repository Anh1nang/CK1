package Model;

public class SanPham {
    private String maSp;
    private String tenSp;
    private String maDm;
    private int donGia;
    private int soLuong;
    private int isDeleted;

    public SanPham(){

    }

    public SanPham(String maSp, String tenSp, int donGia, int soLuong, String maDm) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.maDm = maDm;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.isDeleted = 0;
    }

    public String getMaSp() {
        return maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public String getMaDm() {
        return maDm;
    }

    public int getDonGia() {
        return donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public void setMaDm(String maDm) {
        this.maDm = maDm;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    @Override
    public String toString() {
        return this.tenSp;
    }
}
