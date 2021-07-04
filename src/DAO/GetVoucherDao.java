package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class GetVoucherDao {
    DBConnection dao = new DBConnection();
    Connection con = dao.connect();

    public int getVno() throws SQLException {
        int vno = 0;
        Statement stmt = con.createStatement();
                ResultSet res = stmt.executeQuery("SELECT count(PURCHASE_VOUCHER_ID) FROM pos.purchase_voucher where DATE=curdate() order by PURCHASE_VOUCHER_ID desc limit 1;");
        if(res.next())
            vno=res.getInt(1);
        return vno;
    }


}
