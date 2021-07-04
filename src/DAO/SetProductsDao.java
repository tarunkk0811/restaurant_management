package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SetProductsDao {
	DBConnection dao = new DBConnection();
	Connection con = dao.connect();
	public void setProduct( int cid, int fid, String name, String alias, int hsn, String desc, float qty,
						   float selling_cost, float gper, String units, int opening_stock, int reorder,boolean isSale) throws SQLException {
			
			String query = "INSERT INTO `pos`.`inventory`"
					+ "(`CID`,`FID`,`NAME`,`ALIAS`,`HSN`,`DESCRIPTION`,`QUANTITY`,"
					+ "`SELLING_COST`,`GST_PER`,`UNITS`,"
					+ "`OPENING_STOCK`,`REORDER_LEVEL`,`isSale`,"
					+ "`DATE_UPDATED`)"
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP);";

		PreparedStatement stmt = con.prepareStatement(query);

		stmt.setInt(1, cid);
		stmt.setInt(2, fid);
		stmt.setString(3, dao.capitalize(name));
		stmt.setString(4, dao.capitalize(alias));
		stmt.setInt(5, hsn);
		stmt.setString(6, desc);
		stmt.setFloat(7, qty);
		//buying
		stmt.setFloat(8, selling_cost);
	//	stmt.setString(10, gtype);
		stmt.setFloat(9, gper);
		stmt.setString(10, units);
		stmt.setInt(11, opening_stock);
	//	stmt.setFloat(14, disc);
		//stmt.setBoolean(12, incl_gst);
		stmt.setInt(12, reorder);
		stmt.setBoolean(13,isSale);
	//	stmt.setString(17, ptype);
		
		
		stmt.execute();

	}
	
	public void updateProduct( int pid, String name, String alias, int hsn, String desc, float qty,
							  float selling_cost,  float gper, String units, int opening_stock,
							   int reorder,boolean isSale) throws SQLException {
		String query = "UPDATE `pos`.`inventory` SET `NAME` = ?,`HSN` = ?,"
				+ "`ALIAS` = ?,`DESCRIPTION` = ?,`QUANTITY` = ?,`SELLING_COST` = ?,"
				+ "`GST_PER` = ?,`UNITS` = ?,`OPENING_STOCK` = ?,"
				+ "`REORDER_LEVEL` = ?,`isSale`=?,`DATE_UPDATED` = CURRENT_TIMESTAMP "
				+ " WHERE `PID` = ?;";

		PreparedStatement stmt = con.prepareStatement(query);

		stmt.setString(1, dao.capitalize(name));
		stmt.setInt(2, hsn);
		stmt.setString(3, dao.capitalize(alias));
		stmt.setString(4, desc);
		stmt.setFloat(5, qty);
	//	stmt.setFloat(6, buying_cost);
		stmt.setFloat(6, selling_cost);
	//	stmt.setString(8, gtype);
		stmt.setFloat(7, gper);
		stmt.setString(8, units);
		stmt.setInt(9, opening_stock);
	//	stmt.setBoolean(12, incl_gst);
	//	stmt.setFloat(13, disc);
		stmt.setInt(10, reorder);
	//	stmt.setString(15, ptype);
	//	stmt.setInt(16,aid);
		stmt.setInt(11, pid);
		stmt.setBoolean(12,isSale);
		
		
		stmt.execute();


	}
	
	public void deleteProduct(int pid) throws SQLException{
		String query="DELETE FROM `pos`.`inventory`\r\n"
				+ "WHERE pid="+pid;
		Statement stmt = con.createStatement();
		stmt.executeUpdate(query);
	}

}
