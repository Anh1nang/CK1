import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class Application extends JFrame {
    JList<DanhMuc> lDanhMuc;

    JButton btnThemDanhMuc, btnChinhSuaDanhMuc, btnXoaDanhMuc;

    DefaultTableModel dtmSanPham;
    JTable tblSanPham;

    JComboBox<DanhMuc> cboDanhMuc;

    JTextField tMaSanPham, tTenSanPham, tSoLuong, tDonGia;

    JButton btnTaoMoiSanPham, btnLuuSanPham, btnXoaSanPham, btnTimKiemSanPham;

    DanhMuc dmSelected;
    Vector<SanPham> dsSp ;
    public Application(){
        addControl();
        addEvent();

        hienThiDanhMuc();
    }

    private void hienThiDanhMuc(){
        DanhMucService dms = new DanhMucService();
        Vector<DanhMuc> vec = dms.DocToanBoDanhMuc();
        lDanhMuc.setListData(vec);
        cboDanhMuc.removeAllItems();
        for(DanhMuc dm : vec){
            cboDanhMuc.addItem(dm);
        }
    }

    private void addEvent(){
        lDanhMuc.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dmSelected = lDanhMuc.getSelectedValue();
                if(dmSelected == null) return;
                SanPhamSevice sps = new SanPhamSevice();
                dsSp = sps.docSanPhamTheoMuc(dmSelected.getMaDm());
                dtmSanPham.setRowCount(0);
                for(SanPham sp : dsSp){
                    Vector<Object> vec = new Vector<Object>();
                    vec.add(sp.getMaSp());
                    vec.add(sp.getTenSp());
                    vec.add(sp.getSoLuong());
                    vec.add(sp.getDonGia());

                    dtmSanPham.addRow(vec);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        }
        );
        btnTaoMoiSanPham.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hienThiTaoMoiSanPham();
            }
        });
        btnLuuSanPham.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SanPham sp = new SanPham();
                sp.setMaDm(dmSelected.getMaDm());
                sp.setMaSp(tMaSanPham.getText());
                sp.setTenSp(tTenSanPham.getText());
                sp.setDonGia(Integer.parseInt(tDonGia.getText()));
                sp.setSoLuong(Integer.parseInt(tSoLuong.getText()));
                SanPhamSevice sps = new SanPhamSevice();
                if(sps.capNhatSanPham(sp) == 1){
                    JOptionPane.showMessageDialog(null, "Cap nhat thanh Cong!");
                }else{
                    JOptionPane.showMessageDialog(null, "Cap nhat that bai!");
                }
            }
        });

        tblSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblSanPham.getSelectedRow();
                SanPham sp = dsSp.get(row);

                int index = findIndexByName(lDanhMuc, dmSelected.getTenDm());
                cboDanhMuc.setSelectedIndex(index);
                tMaSanPham.setText(sp.getMaSp());
                tTenSanPham.setText(sp.getTenSp());
                tTenSanPham.setEditable(true);
                tSoLuong.setText(sp.getSoLuong() + "");
                tSoLuong.setEditable(true);
                tDonGia.setText(sp.getDonGia() + "");
                tDonGia.setEditable(true);
            }
        });

        btnThemDanhMuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HienThiThemDanhMuc htdm = new HienThiThemDanhMuc();
                hienThiDanhMuc();
            }
        });

        btnChinhSuaDanhMuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hienThiCapNhapDanhMuc();
                hienThiDanhMuc();
            }
        });

        btnXoaDanhMuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dmSelected == null){
                    JOptionPane.showMessageDialog(null, "Vui Long Chon Muc Can Xoa");
                    return;
                }
                DanhMucService dms = new DanhMucService();
                if(dms.xoaDanhMuc(dmSelected) == 1){
                    JOptionPane.showMessageDialog(null, "Xoa Thanh Cong!");
                    hienThiDanhMuc();
                }else{
                    JOptionPane.showMessageDialog(null, "Xoa Khong Thanh Cong!");
                }
            }
        });
        btnTimKiemSanPham.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hienThiTimKiemSanPham();
            }
        });

        btnXoaSanPham.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tTenSanPham.setText("");
                tSoLuong.setText("");
                tDonGia.setText("");
                tTenSanPham.requestFocus();
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(Application.this,
                        "Ban co muon thoat chuong trinh",
                        "Thoat chuong trinh",
                        JOptionPane.YES_NO_OPTION);
                if(option == 0){
                    System.exit(0);
                }
            }
        });
    }

    private void hienThiTaoMoiSanPham(){
        if(dmSelected == null){
            JOptionPane.showMessageDialog(null,"hay chon danh muc can chinh sua");
            return;
        }
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel lMaSanPham = new JLabel("Ma SP");
        JTextField tMaSanPham = new JTextField(20);
        JLabel lTenSanPham = new JLabel("Ten SP");
        JTextField tTenSanPham = new JTextField(20);
        JLabel lSoLuong = new JLabel("So luong");
        JTextField tSoLuong = new JTextField(20);
        JLabel lDonGia = new JLabel("Don Gia");
        JTextField tDonGia = new JTextField(20);
        panel.add(lMaSanPham);
        panel.add(tMaSanPham);
        panel.add(lTenSanPham);
        panel.add(tTenSanPham);
        panel.add(lSoLuong);
        panel.add(tSoLuong);
        panel.add(lDonGia);
        panel.add(tDonGia);

        int option = JOptionPane.showConfirmDialog(null, panel,
                "Them Danh Muc",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if(option == JOptionPane.OK_OPTION){
            String maSanPham = tMaSanPham.getText();
            String tenSanPham= tTenSanPham.getText();
            int soLuong = 0;
            try{
                soLuong = Integer.parseInt(tSoLuong.getText());
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "Hay nhap so vao So Luong!");
            }

            int donGia = 0;
            try {
                donGia = Integer.parseInt(tDonGia.getText());
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "Hay nhap so vao Don Gia!");
            }
            if(maSanPham.isEmpty() || tenSanPham.isEmpty() || tSoLuong.getText().isEmpty() || tDonGia.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"Thong tin khong day du");
            }else{
                SanPham sp = new SanPham(maSanPham, tenSanPham, soLuong, donGia, dmSelected.getMaDm());
                SanPhamSevice sps = new SanPhamSevice();
                int result = sps.luuSanPham(sp);
                if(result == 1){
                    JOptionPane.showMessageDialog(null,"Cap nhat thanh thanh cong!");
                }else{
                    JOptionPane.showMessageDialog(null, "cap nhat khong thanh cong");
                }
            }
        }
    }
    private void hienThiCapNhapDanhMuc(){
        if(dmSelected == null){
            JOptionPane.showMessageDialog(null,"hay chon danh muc can chinh sua");
        }
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel lMaDanhMuc = new JLabel("Ma Danh Muc");
        JTextField tMaDanhMuc = new JTextField(dmSelected.getMaDm());
        tMaDanhMuc.setEditable(false);
        JLabel lTenDanhMuc = new JLabel("Ten Danh Muc");
        JTextField tTenDanhMuc = new JTextField(20);
        panel.add(lMaDanhMuc);
        panel.add(tMaDanhMuc);
        panel.add(lTenDanhMuc);
        panel.add(tTenDanhMuc);
        boolean isInputValid = false;
        while (!isInputValid) {
            int option = JOptionPane.showConfirmDialog(null, panel,
                    "Them Danh Muc",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if(option == JOptionPane.OK_OPTION){
                String tenDanhMuc = tTenDanhMuc.getText();
                if(tenDanhMuc.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Thong tin khong day du");
                }else{
                    isInputValid = true;
                    dmSelected.setTenDm(tenDanhMuc);
                    DanhMucService dms = new DanhMucService();
                    int result = dms.capNhapDanhMuc(dmSelected);
                    if(result == 1){
                        JOptionPane.showMessageDialog(null,"Cap nhat thanh thanh cong!");
                    }else{
                        JOptionPane.showMessageDialog(null, "cap nhat khong thanh cong");
                    }
                }
            }
        }

    }

    private void hienThiTimKiemSanPham() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel lMaSanPham = new JLabel("Ma San Pham");
        JTextField tMaSanPham = new JTextField(20);
        JLabel lTenSanPham = new JLabel("Ten San Pham");
        JTextField tTenSanPham = new JTextField(20);
        panel.add(lMaSanPham);
        panel.add(tMaSanPham);
        panel.add(lTenSanPham);
        panel.add(tTenSanPham);

        boolean isInputValid = false;
        while (!isInputValid) {
            int option = JOptionPane.showConfirmDialog(null, panel,
                    "Tim Kiem San Pham",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                String maSanPham = tMaSanPham.getText();
                String tenSanPham = tTenSanPham.getText();
                SanPhamSevice sps = new SanPhamSevice();
                if (tenSanPham.isEmpty() || maSanPham.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Thong tin khong day du");
                } else {
                    SanPham sp = sps.timKiemSanPham(maSanPham, tenSanPham);
                    if (sp != null) {
                        System.out.println(sp);
                        isInputValid = true;
                        Vector<Object> vec = new Vector<Object>();
                        vec.add(sp.getMaSp());
                        vec.add(sp.getTenSp());
                        vec.add(sp.getSoLuong());
                        vec.add(sp.getDonGia());
                        dtmSanPham.setRowCount(0);
                        dtmSanPham.addRow(vec);
                        tblSanPham.setRowSelectionInterval(0, 0);

                        DanhMucService dms = new DanhMucService();
                        dmSelected = dms.timDanhMucTheoMaDanhMuc(sp);
                        int index = findIndexByName(lDanhMuc, dmSelected.getTenDm());
                        System.out.println(index);
                        lDanhMuc.setSelectedIndex(index);

                        cboDanhMuc.setSelectedIndex(index);
                        this.tMaSanPham.setText(sp.getMaSp());
                        this.tTenSanPham.setText(sp.getTenSp());
                        this.tTenSanPham.setEditable(true);
                        this.tSoLuong.setText(sp.getSoLuong() + "");
                        this.tSoLuong.setEditable(true);
                        this.tDonGia.setText(sp.getDonGia() + "");
                        this.tDonGia.setEditable(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Thong Tin Khong Chinh Xac");
                    }

                }
            }

        }
    }
    private int findIndexByName(JList<DanhMuc> lDanhMuc, String name){
        ListModel <DanhMuc> model = lDanhMuc.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            System.out.println(model.getElementAt(i).toString());
            if(model.getElementAt(i).toString().equals(name)){
                return i;
            }
        }
        return -1;
    }

    private void addControl(){
        Container con = this.getContentPane();
        con.setLayout(new BorderLayout());

        JPanel pnLeft = new JPanel();
        pnLeft.setPreferredSize(new Dimension(300,0));
        JPanel pnRigt = new JPanel();
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnLeft, pnRigt);
        sp.setOneTouchExpandable(true);
        con.add(sp, BorderLayout.CENTER);

        pnLeft.setLayout(new BorderLayout());
        lDanhMuc = new JList<DanhMuc>();
        TitledBorder tbDm = new TitledBorder(BorderFactory.createLineBorder(Color.CYAN),
                "DANH MUC SAN PHAM");
        lDanhMuc.setBorder(tbDm);

        JScrollPane spLDanhMuc = new JScrollPane(lDanhMuc,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
                );

        pnLeft.add(spLDanhMuc, BorderLayout.CENTER);

        JPanel pnButton = new JPanel();
        btnThemDanhMuc = new JButton("Them Danh Muc");
        btnChinhSuaDanhMuc = new JButton("Cap nhat danh muc");
        btnXoaDanhMuc = new JButton("Xoa Danh Muc");
        pnButton.add(btnThemDanhMuc);
        pnButton.add(btnChinhSuaDanhMuc);
        pnButton.add(btnXoaDanhMuc);

        pnLeft.add(pnButton, BorderLayout.SOUTH);

        pnRigt.setLayout(new BorderLayout());
        JPanel pnTopOfRight = new JPanel(new BorderLayout());
        pnRigt.add(pnTopOfRight, BorderLayout.CENTER);
        pnTopOfRight.setPreferredSize(new Dimension(300,0));

        dtmSanPham = new DefaultTableModel();
        dtmSanPham.addColumn("Ma San Pham");
        dtmSanPham.addColumn("Ten San Pham");
        dtmSanPham.addColumn("So Luong");
        dtmSanPham.addColumn("Don Gia");
        tblSanPham = new JTable(dtmSanPham);
        JScrollPane spSanPham = new JScrollPane(tblSanPham,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
                );

        pnRigt.add(spSanPham, BorderLayout.CENTER);

        JPanel pnBottomOfRight = new JPanel();
        pnBottomOfRight.setLayout(new BoxLayout(pnBottomOfRight, BoxLayout.Y_AXIS));
        pnRigt.add(pnBottomOfRight, BorderLayout.SOUTH);

        JPanel pnDanhMuc = new JPanel();
        pnDanhMuc.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lDanhMuc = new JLabel("Danh Muc");
        cboDanhMuc = new JComboBox<DanhMuc>();
        cboDanhMuc.setPreferredSize(new Dimension(300, 30));
        pnDanhMuc.add(lDanhMuc);
        pnDanhMuc.add(cboDanhMuc);
        pnBottomOfRight.add(pnDanhMuc);

        JPanel pnMaSp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lMaSp = new JLabel("Ma SP");
        tMaSanPham = new JTextField(30);
        tMaSanPham.setEditable(false);
        pnMaSp.add(lMaSp);
        pnMaSp.add(tMaSanPham);
        pnBottomOfRight.add(pnMaSp);

        JPanel pnTenSp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lTenSp= new JLabel("Ten SP");
        tTenSanPham = new JTextField(30);
        tTenSanPham.setEditable(false);
        pnTenSp.add(lTenSp);
        pnTenSp.add(tTenSanPham);
        pnBottomOfRight.add(pnTenSp);

        JPanel pnSoLuong = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lSoLuong = new JLabel("So Luong");
        tSoLuong = new JTextField(30);
        tSoLuong.setEditable(false);
        pnSoLuong.add(lSoLuong);
        pnSoLuong.add(tSoLuong);
        pnBottomOfRight.add(pnSoLuong);

        JPanel pnDonGia = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lDonGia = new JLabel("Don Gia");
        tDonGia = new JTextField(30);
        tDonGia.setEditable(false);
        pnDonGia.add(lDonGia);
        pnDonGia.add(tDonGia);
        pnBottomOfRight.add(pnDonGia);

        lMaSp.setPreferredSize(lSoLuong.getPreferredSize());
        lTenSp.setPreferredSize(lSoLuong.getPreferredSize());
        lDonGia.setPreferredSize(lSoLuong.getPreferredSize());

        JPanel pnButtonSanPham = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnTaoMoiSanPham = new JButton("Tao Moi");
        btnLuuSanPham = new JButton("Luu");
        btnXoaSanPham = new JButton("Xoa");
        btnTimKiemSanPham = new JButton("Tim kiem");
        pnButtonSanPham.add(btnTaoMoiSanPham);
        pnButtonSanPham.add(btnLuuSanPham);
        pnButtonSanPham.add(btnXoaSanPham);
        pnButtonSanPham.add(btnTimKiemSanPham);
        pnBottomOfRight.add(pnButtonSanPham);


    }

    public void showWindow(){
        this.setSize(1000,600);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
