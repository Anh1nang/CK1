import javax.swing.*;

public class HienThiThemDanhMuc extends JPanel {
    public HienThiThemDanhMuc(){
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel lMaDanhMuc = new JLabel("Ma Danh Muc");
        JTextField tTenDanhMuc = new JTextField(20);
        JLabel lTenDanhMuc = new JLabel("Ten Danh Muc");
        JTextField tMaDanhMuc = new JTextField(20);
        this.add(lMaDanhMuc);
        this.add(tMaDanhMuc);
        this.add(lTenDanhMuc);
        this.add(tTenDanhMuc);

        int option = JOptionPane.showConfirmDialog(null, this,
                "Them Danh Muc",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if(option == JOptionPane.OK_OPTION){
            String tenDanhMuc = tTenDanhMuc.getText();
            String maDanhMuc = tMaDanhMuc.getText();
            if(tenDanhMuc.isEmpty() || maDanhMuc.isEmpty()){
                JOptionPane.showMessageDialog(null,"Thong tin khong dau du");
            }else{
                DanhMuc dm = new DanhMuc(maDanhMuc, tenDanhMuc);
                DanhMucService dms = new DanhMucService();
                int result = dms.themDanhMuc(dm);
                if(result == 1){
                    JOptionPane.showMessageDialog(null,"Them thanh cong!");
                }else{
                    JOptionPane.showMessageDialog(null, "Them khong thanh cong");
                }
            }
        }
    }
}
