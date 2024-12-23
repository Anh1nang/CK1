public class DanhMuc {
    private String maDm;
    private String tenDm;
    private int isDeleted;

    public DanhMuc(){

    }
    public DanhMuc(String maDm, String tenDm){
        this.maDm = maDm;
        this.tenDm = tenDm;
    }

    public String getMaDm() {
        return maDm;
    }

    public String getTenDm() {
        return tenDm;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setMaDm(String maDm) {
        this.maDm = maDm;
    }

    public void setTenDm(String tenDm) {
        this.tenDm = tenDm;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString(){
        return this.tenDm;
    }
}
