import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class SanPhamSevice extends SQLServerSevice{
    public int capNhatSanPham(SanPham sp){
        try{
            String sql = "update SanPham set TenSP = ?," +
                    "SoLuong = ?," +
                    "DonGia = ?," +
                    "MaDM = ?" +
                    "where maSP = ?";
            PreparedStatement preSt = con.prepareStatement(sql);
            preSt.setString(1, sp.getTenSp());
            preSt.setInt(2, sp.getSoLuong());
            preSt.setInt(3, sp.getDonGia());
            preSt.setString(4, sp.getMaDm());
            preSt.setString(5, sp.getMaSp());
            return preSt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;

    }
    public int luuSanPham(SanPham sp){
        try{
            String sql = "insert into SanPham values (?, ?, ?, ?, ?, ?)";
            PreparedStatement preSt = con.prepareStatement(sql);
            preSt.setString(1, sp.getMaSp());
            preSt.setString(2,sp.getTenSp());
            preSt.setInt(3, sp.getSoLuong());
            preSt.setInt(4, sp.getDonGia());
            preSt.setString(5, sp.getMaDm());
            preSt.setInt(6,0);
            return preSt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
    public Vector<SanPham> docSanPhamTheoMuc(String maDm){
        Vector<SanPham> vec = new Vector<>();
        try{
            String sql = "select * from SanPham where MaDm = ?";
            PreparedStatement preSt = con.prepareStatement(sql);
            preSt.setString(1, maDm);
            ResultSet rs = preSt.executeQuery();
            while(rs.next()){
                SanPham sp = new SanPham();
                sp.setMaSp(rs.getString(1));
                sp.setTenSp(rs.getString(2));
                sp.setSoLuong(rs.getInt(3));
                sp.setDonGia(rs.getInt(4));
                sp.setMaDm(rs.getString(5));
                sp.setIsDeleted(0);
                vec.add(sp);
                System.out.println(sp.getMaDm());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vec;
    }

    public SanPham timKiemSanPham(String maSP, String tenSp){
        SanPham DetailSp = null;
        try{
            String sql = "select * from SanPham where MaSP = ? and TenSP = ? and isDeleted = 0";
            PreparedStatement preSt = con.prepareStatement(sql);
            preSt.setString(1, maSP);
            preSt.setString(2, tenSp);
            ResultSet rs = preSt.executeQuery();
            while (rs.next()){
                DetailSp = new SanPham();
                DetailSp.setMaSp(rs.getString(1));
                DetailSp.setTenSp(rs.getString(2));
                DetailSp.setSoLuong(rs.getInt(3));
                DetailSp.setDonGia(rs.getInt(4));
                DetailSp.setMaDm(rs.getString(5));
                DetailSp.setIsDeleted(rs.getInt(6));
            }
            return DetailSp;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DetailSp;

    }
}
