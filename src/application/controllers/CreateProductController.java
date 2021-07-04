package application.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

//import DAO.GetAccountsDao;
import DAO.GetProductsDao;
import DAO.SetProductsDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CreateProductController {

	ObservableList<String> product_types = FXCollections.observableArrayList("Finished Goods", "Intermediate Product",
			"Raw Material", "Service");
	ObservableList<String> gst_types = FXCollections.observableArrayList("Capital ", "Exempted", "Nil", "Non-GST",
			"Normal", "Reverse Charge");
	ObservableList<String> units_types = FXCollections.observableArrayList("Nos", "Kgs", "Li", "Ton", "Bun");
	//ObservableList<Integer> gst_percentages = FXCollections.observableArrayList(5, 12, 18, 28);
	//ObservableList<String> accounts = FXCollections.observableArrayList();

	@FXML
	private AnchorPane anchorPane;



	@FXML
	private TextField name, alias, reorder, qunatity, selling_price, hsn, opening_stock;

	@FXML
	private TextArea desc;

	@FXML
	private Button  create_product;

	@FXML
	private ComboBox<String> product_type, units;

	@FXML
	private TextField gst_per;

	@FXML
	private CheckBox is_sale;

	GetProductsDao get_products_dao = new GetProductsDao();
	//GetAccountsDao get_acc_dao = new GetAccountsDao();
	SetProductsDao set_product_dao = new SetProductsDao();
	ApplicationController app_controller = new ApplicationController();

	@FXML
	public void initialize() throws SQLException {
//		ResultSet res = get_acc_dao.getAccounts(SessionController.cid);
//		while (res.next()) {
//			accounts.add(res.getString(3));
//		}

		product_type.getItems().addAll(product_types);
		product_type.getSelectionModel().select(0);
//		gst_type.getItems().addAll(gst_types);
//		gst_type.getSelectionModel().select(0);
		units.getItems().addAll(units_types);
		units.getSelectionModel().select(0);
	//	account.getItems().addAll(accounts);
//		account.getSelectionModel().select(0);
		//gst_per.getItems().addAll(gst_percentages);

		// setting default values
	//	discount.setText("0");
		opening_stock.setText("0");
	//	gst_type.getSelectionModel().select("Normal");
		reorder.setText("0");

		// listeners
		product_type.focusedProperty().addListener(e -> {
			product_type.show();
		});
//		gst_type.focusedProperty().addListener(e -> {
//			gst_type.show();
//		});
		units.focusedProperty().addListener(e -> {
			units.show();
		});
//		account.focusedProperty().addListener(e -> {
//			account.show();
//		});
//		gst_per.focusedProperty().addListener(e -> {
//			gst_per.show();
//		});

		// edit product
		if (SessionController.editpid != 0) {
			ResultSet rs = get_products_dao.getProduct(SessionController.editpid);
			while (rs.next()) {
				edit(rs.getString(2), rs.getString(6), rs.getString(13), rs.getString(8), rs.getString(9),
						rs.getString(7), rs.getString(5), rs.getString(12), rs.getBoolean(17), rs.getString(10),
						rs.getString(11));

			}




		}
	}

	@FXML
	void createProduct(ActionEvent event) throws SQLException {
		String p_name = name.getText();
		String p_alias = alias.getText();
		int p_reorder = Integer.parseInt(reorder.getText());
		String p_products_type = product_types.get(product_type.getSelectionModel().getSelectedIndex());
		String p_desc = desc.getText();

		//int aid = get_acc_dao.getAccount(accounts.get(account.getSelectionModel().getSelectedIndex()));

		int p_hsn = 0;
//		String p_gst_type = gst_types.get(gst_type.getSelectionModel().getSelectedIndex());

		String p_units = units_types.get(units.getSelectionModel().getSelectedIndex());

		boolean is_sale_item = is_sale.isSelected();

		float p_quantity = qunatity.getText().isEmpty()?0:Float.parseFloat(qunatity.getText());
//		float p_buying_price = buying_price.getText().isEmpty()?0:Float.parseFloat(buying_price.getText());
		float p_selling_price = selling_price.getText().isEmpty()?0:Float.parseFloat(selling_price.getText());

		int p_gst_per = gst_per.getText().isEmpty()?0:Integer.parseInt(gst_per.getText());
//		float p_disc = discount.getText().isEmpty()?0:Float.parseFloat(discount.getText());
		int p_opening_stock =opening_stock.getText().isEmpty()?0: Integer.parseInt(opening_stock.getText());
		
		if (SessionController.editpid == 0) {
			set_product_dao.setProduct( SessionController.cid, SessionController.fid, p_name, p_alias, p_hsn,
					p_desc, p_quantity, p_selling_price, p_gst_per, p_units,
					p_opening_stock, p_reorder,is_sale_item);
			app_controller.informationDialog("Product Added Successfully !", null);
		} else {
			set_product_dao.updateProduct( SessionController.editpid, p_name, p_alias, p_hsn, p_desc, p_quantity,
					 p_selling_price, p_gst_per, p_units, p_opening_stock,
					  p_reorder,is_sale_item);
			SessionController.editpid = 0;
			app_controller.informationDialog("Product Updated Successfully !", null);
		}
	

		Stage curr = (Stage) anchorPane.getScene().getWindow();
		curr.close();
	}

	//@FXML
//	void goToGSTtab(ActionEvent event) throws SQLException {
//		if(get_products_dao.checkItemExists(name.getText()) && SessionController.editpid==0)
//		{
//			app_controller.informationDialog("Product "+name.getText()+" already exists", null);
//			name.setText("");
//		}
//		else
//		tabPane.getSelectionModel().select(GST_tab);
//	}

//	@FXML
//	void goToOtherstab(ActionEvent event) {
//		tabPane.getSelectionModel().select(others_tab);
//	}

//	@FXML
//	void moveToNext(KeyEvent event) {
//		if (event.getCode() == KeyCode.TAB) {
//			desc.setText(desc.getText().replace("\t", ""));
//			next_to_gst.requestFocus();
//		}
//	}

	@FXML
	void showItems(KeyEvent event) {
		ComboBox<String> cbox = (ComboBox<String>) event.getSource();
		app_controller.showComboBoxItems(cbox, event);
	}

	void edit(String ename, String ealias, String ereorder, String equantity, String eselling,
			String edesc, String ehsn, String eopening, boolean issale,
			 String egst_per, String eunits) throws SQLException {
		if (name != null || ename != "") {
			name.setText(ename);
			alias.setText(ealias);
			reorder.setText(ereorder);
			qunatity.setText(equantity);
		//	buying_price.setText(ebuying);
			selling_price.setText(eselling);
		//	discount.setText(ediscount);
			desc.setText(edesc);
			hsn.setText(ehsn);
		//	inclusive_gst.setSelected(1 == eincl);
			opening_stock.setText(eopening);
//			ResultSet rs = get_acc_dao.getAccountDetails(eaccount);
//			rs.next();
//			account.getSelectionModel().select(rs.getString(3));
		//	product_type.getSelectionModel().select(eproduct_type);
		//	gst_type.getSelectionModel().select(egst_type);
			//int index_of_selected = gst_percentages.indexOf(Integer.parseInt(egst_per));
			//gst_per.getSelectionModel().select(index_of_selected);
			gst_per.setText(egst_per);
			is_sale.setSelected(issale);
			units.getSelectionModel().select(eunits);
			create_product.setText("Update Product");

		}
	}
	
	public void clearFields() {
		System.out.println("clearin fields");
		name.clear();
		alias.clear();
		reorder.setText("0");
		qunatity.clear();
		//buying_price.clear();
		selling_price.clear();
		opening_stock.setText("0");
		//discount.setText("0");
		hsn.clear();
		desc.clear();
		//inclusive_gst.setSelected(false);
		//gst_type.getSelectionModel().select("Normal");
		gst_per.clear();
		units.getSelectionModel().select(null);
		//product_type.getSelectionModel().select(null);
		//account.getSelectionModel().select(null);
	}
}
