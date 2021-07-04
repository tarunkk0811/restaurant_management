package DAO;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.*;

public class SetPurchaseVoucher {
    DBConnection dao = new DBConnection();
    Connection con = dao.connect();

    public Boolean setPV(int fid, DatePicker purchasedt, String tgross, String tigst, double total_ogst, double total_odisc,String taxable,String tnetval,String pmode,String remarks,String billno,DatePicker billdt) throws SQLException {
        String query = "INSERT INTO `pos`.`purchase_voucher`\n" +
                "(`FID`,\n" +
                "`DATE`,\n" +
                "`IGST`,\n" +
                "`ogsst`,\n" +
                "`GROSS`,\n" +
                "`DISCOUNT`,\n" +
                "`TAXABLE_VALUE`,\n" +
                "`NET`,\n" +
                "`BILL_NO`,\n" +
                "`BILL_DATE`,\n" +
                "`payment_mode`,\n" +
                "`remarks`,\n" +
                "`DATE_UPDATED`)\n" +
                "VALUES\n" +
                "(?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp);\n";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1,fid);
        stmt.setDate(2, Date.valueOf(purchasedt.getValue()));
        //    stmt.setString(4,cname.getText());
        stmt.setString(3,tigst);
       // stmt.setString(6,tdiscount);
        stmt.setDouble(4,total_ogst);
        stmt.setString(5,tgross);
        stmt.setDouble(6,total_odisc);
        stmt.setString(7,taxable);
        stmt.setString(8,tnetval);
        int bno = billno.isEmpty() ? 0 : Integer.parseInt(billno);
        stmt.setInt(9,bno);
        stmt.setDate(10, billdt.getValue()==null?null:Date.valueOf(billdt.getValue()));
        stmt.setString(11,pmode);
        stmt.setString(12,remarks);
        System.out.println(stmt);
        return  stmt.execute();
    }

}
