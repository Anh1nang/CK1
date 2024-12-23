package Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerSevice {
    Connection con;
    String user = "sa";
    String password = "1234";
    String url = "jdbc:sqlserver://localhost;databaseName=DBQLSP;encrypt=true;trustServerCertificate=true;";

    public SQLServerSevice() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("ket noi du lieu thanh cong");
        } catch (ClassNotFoundException e) {
            System.out.println("khong tim thay jdbc");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("khong the ket noi du lieu");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SQLServerSevice s = new SQLServerSevice();
    }
}
