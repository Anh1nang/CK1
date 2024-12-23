package Service;

import Model.DanhMuc;
import Model.SanPham;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class DanhMucService extends SQLServerSevice{
    public Vector<DanhMuc> DocToanBoDanhMuc(){
        Vector<DanhMuc> vec =new Vector<DanhMuc>();
        try{
            String sql = "select * from DanhMuc where isDeleted = 0";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                DanhMuc dm = new DanhMuc();
                dm.setMaDm(rs.getString(1));
                dm.setTenDm(rs.getString(2));
                dm.setIsDeleted(0);
                vec.add(dm);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return vec;
    }

    public int themDanhMuc(DanhMuc dm){
        try {
            String url = "insert into DanhMuc values (?, ?, ?)";
            PreparedStatement preSt = con.prepareStatement(url);
            preSt.setString(1, dm.getMaDm());
            preSt.setString(2, dm.getTenDm());
            preSt.setInt(3, dm.getIsDeleted());
            return preSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int capNhapDanhMuc(DanhMuc dm){
        try {
            String url = "update DanhMuc set TenDM = ? where MaDM = ?";
            PreparedStatement preSt = con.prepareStatement(url);
            preSt.setString(1, dm.getTenDm());
            preSt.setString(2, dm.getMaDm());
            return preSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public int xoaDanhMuc(String maDM){
        int result= 0;
        try{
            String sql = "UPDATE Model.DanhMuc SET isdeleted = 1  WHERE maDM= ?";
            PreparedStatement preparedStatement= con.prepareStatement(sql);
            preparedStatement.setString(1, maDM);
            System.out.println("xoa san pham: "+ maDM);
            result= preparedStatement.executeUpdate();
            System.out.println("ket qua xoa: "+result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    public DanhMuc timDanhMucTheoMaDanhMuc(SanPham sp){
        DanhMuc dm = new DanhMuc();
        try{
            String sql = "select * from DanhMuc where MaDM = ?";
            PreparedStatement preSt = con.prepareStatement(sql);
            preSt.setString(1, sp.getMaDm());
            ResultSet rs = preSt.executeQuery();
            while(rs.next()){
                dm.setMaDm(rs.getString(1));
                dm.setTenDm(rs.getString(2));
            }
            return dm;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
