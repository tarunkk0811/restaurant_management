package application.controllers;


import DAO.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.*;


import application.custom_properties.PurchaseItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


public class PurchaseVoucherController extends ApplicationMainController {
	@FXML
	private AnchorPane ap;

	@FXML
	private HBox row1hbox;

	@FXML
	private HBox vnohbox;

	@FXML
	private HBox row2hbox;

	@FXML
	private ComboBox pmode;


	@FXML
	private HBox datehbox;


	@FXML
	private HBox row3hbox;

	@FXML
	private HBox billnohbox;


	@FXML
	private HBox billdatehbox;


	@FXML
	private HBox isigsthbox;
	;

	@FXML
	private HBox placehbox;

	@FXML
	private ScrollPane spane;

	@FXML
	private VBox topvbox;


	@FXML
	private TextArea remarks;


	@FXML
	private TableView purchasetv;

	@FXML
	private Text qty_total,gross_total,rate_total,discount_total,cgst_total, sgst_total, igst_total, oc_total,cess_total, taxable_total,net_amount,vno;


	@FXML
	private TableColumn<PurchaseItem, String> sno_col;

	@FXML
	private TableColumn<PurchaseItem, String> qty_col, rate_col, gross_col, disc_col, cgst_col, sgst_col, igst_col, ocharges_col, cess_col, taxable_value_col;

	@FXML
	private TableColumn<PurchaseItem, ComboBox> item_col, type_of_purchase_col;



	@FXML
	private DatePicker purchasedt, billdt;


	@FXML
	private TextField billno;

	@FXML
	private Button save;

	@FXML
	TextField ogst,odisc;


	public HBox getTotals_hbox() {
		return totals_hbox;
	}

	public void setTotals_hbox(HBox totals_hbox) {
		this.totals_hbox = totals_hbox;
	}

	//totals
	@FXML
	private HBox totals_hbox;

	@FXML
	private HBox qty_col_total;

	@FXML
	private HBox rate_col_total;

	@FXML
	private HBox gross_col_total;

	@FXML
	private HBox disc_col_total;

	@FXML
	private HBox cgst_col_total;

	@FXML
	private HBox sgst_col_total;


	@FXML
	private HBox igst_col_total;


	@FXML
	private HBox ocharges_col_total;


	@FXML
	private HBox cess_col_total;


	@FXML
	private HBox taxable_value_col_total;

	@FXML
	private HBox otaxable_value_col_total;

	@FXML
	private Text otaxable_total;

	@FXML
	private Text ogst_total;

	@FXML
	private Text odisc_total;


	@FXML
	private CheckBox is_odisc_per;




	HashMap<Integer,Double> tot_quantity = new HashMap<>(),tot_rate = new HashMap<>(),tot_gross = new HashMap<>(),
						tot_taxable_value = new HashMap<>() ,tot_discount = new HashMap<>(),tot_sgst = new HashMap<>(),
						tot_cgst = new HashMap<>(),tot_igst = new HashMap<>(),tot_other_charges = new HashMap<>(),
						tot_cess = new HashMap<>(),tot_new_col1 = new HashMap<>(),tot_new_col2 = new HashMap<>(),
						tot_new_col3 = new HashMap<>(),tot_new_col4 = new HashMap<>(),tot_new_col5 = new HashMap<>(),
						tot_net_value = new HashMap<>();

	HashMap<Integer,TextField> add_to_taxable_value = new HashMap<>();
	HashMap<Integer,TextField> add_to_net_value = new HashMap<>();



	static double total_qty,total_gross,total_rate,total_odisc,total_discount,total_cgst,total_sgst,total_igst,total_ogst,total_oc,total_cess,total_taxable,total_net_amount,total_col1,total_col2,total_col3,total_col4,total_col5;



	ObservableList<PurchaseItem> itemlist = FXCollections.observableArrayList();


	ObservableList<String> type_of_purchase = FXCollections.observableArrayList();
	ObservableList<String> purchase_account_list = FXCollections.observableArrayList("PURCHASE-SGST", "PURCHASE-IGST");
	ObservableList<String> ptypes = FXCollections.observableArrayList("Cash","Phonepe","Google Pay","Amazon Pay","Others");
	ObservableList<String> states = FXCollections.observableArrayList();

	HashMap<Integer, String> items_with_hsn = new HashMap<>();
	HashMap<String, Integer> items_with_ids = new HashMap<String, Integer>();
	//HashMap<String, Integer> Accounts_with_ids = new HashMap<String, Integer>();
	ObservableList<String> item_names = getAllItems();
	double prev,nv,ov ;
	String previtemname;
	//GetAccountsDao get_acc_dao = new GetAccountsDao();
	GetProductsDao get_products_dao = new GetProductsDao();
	ApplicationController app_controller = new ApplicationController();
	GetVoucherDao get_voucher_dao = new GetVoucherDao();

	public PurchaseVoucherController() throws SQLException {
	}
	 int sno=1;
	String var1="",var2="",var3="",var4="",var5="",var6="",var7="",var8="",var9="";

	@FXML
	public void initialize() throws Exception {

		addNewFields();
		hideOrUnhideFields();
		purchasedt.setConverter(new StringConverter<LocalDate>()
		{
			private DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("dd/MM/yyyy");

			@Override
			public String toString(LocalDate localDate)
			{
				if(localDate==null)
					return "";
				return dateTimeFormatter.format(localDate);
			}

			@Override
			public LocalDate fromString(String dateString)
			{
				if(dateString==null || dateString.trim().isEmpty())
				{
					return null;
				}
				return LocalDate.parse(dateString,dateTimeFormatter);
			}
		});
		purchasedt.setValue(LocalDate.now());


		prev = 0.0;
		nv=0;ov=0;
		total_qty=0;total_gross=0;total_rate=0;total_discount=0;total_cgst=0;
		total_ogst=0;
		total_odisc=0;
		total_sgst=0;total_igst=0;total_oc=0;total_cess=0;total_taxable=0;total_net_amount=0;
		total_col1=0;total_col2=0;total_col3=0;total_col4=0;total_col5=0;
//		ResultSet vendors_rs = get_acc_dao.getVendors(SessionController.cid);
//		while (vendors_rs.next()) {
//			Accounts_with_ids.put(vendors_rs.getString(2), vendors_rs.getInt(1));
//			accounts.add(vendors_rs.getString(2));
//		}
//		Collections.sort(accounts);
//		vendor.setEditable(true);
//		vendor.getItems().addAll(accounts);
		items_with_hsn = get_products_dao.getProductWithHsn(SessionController.cid);
		// adding place of supply
//		ResultSet rs = get_acc_dao.getStates();
//
//		while (rs.next()) {
//			String statename = rs.getString(1);
//			states.add(statename);
//		}
//		Collections.sort(states);
//		place.setEditable(true);
//		place.getItems().addAll(states);

		//get vno
		int vnumber = get_voucher_dao.getVno()+1;
		vno.setText(String.valueOf(vnumber));
//		place.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
//			app_controller.filter(place, states, event);
//		});

//		vendor.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
//			app_controller.filter(vendor, accounts, event);
//		});

//		purchase_account.getItems().addAll(purchase_account_list);

		// geting products and adding to observable list
//

		pmode.getItems().addAll(ptypes);
		pmode.getSelectionModel().select(0);


//		type_of_purchase.add("Taxable Supply");
//		type_of_purchase.add("CC Exempt supply");
//		type_of_purchase.add("Exemp Supply");
//		type_of_purchase.add("CC Non Business");
//		type_of_purchase.add("Non Business");
//		type_of_purchase.add("Ineligible Credit Section");
//		type_of_purchase.add("CR Not available");

		//purchasetv.setMaxWidth(purchasetv.getMaxWidth()+temp2.getMaxWidth());

		sno_col.setCellValueFactory(new PropertyValueFactory<PurchaseItem, String>("sno"));
		item_col.setCellValueFactory(new PropertyValueFactory<PurchaseItem, ComboBox>("items"));
		qty_col.setCellValueFactory(new PropertyValueFactory<PurchaseItem, String>("quantity"));
		rate_col.setCellValueFactory(new PropertyValueFactory<PurchaseItem, String>("rate"));
		gross_col.setCellValueFactory(new PropertyValueFactory<PurchaseItem, String>("gross"));
		disc_col.setCellValueFactory(new PropertyValueFactory<PurchaseItem, String>("discount"));
		igst_col.setCellValueFactory(new PropertyValueFactory<PurchaseItem, String>("igst"));
		ocharges_col.setCellValueFactory(new PropertyValueFactory<PurchaseItem, String>("other_charges"));
		cess_col.setCellValueFactory(new PropertyValueFactory<PurchaseItem, String>("cess"));
		taxable_value_col.setCellValueFactory(new PropertyValueFactory<PurchaseItem, String>("taxable_value"));
		type_of_purchase_col.setCellValueFactory(new PropertyValueFactory<PurchaseItem, ComboBox>("type_of_purchase"));


		addNewColumns();

		linkEventListeners(itemlist);

		// appyling focus property
//		vendor.focusedProperty().addListener(e -> {
//			vendor.show();
//		});

//		place.focusedProperty().addListener(e -> {
//			place.show();
//		});

		/*purchase_account.focusedProperty().addListener(e -> {
			purchase_account.show();
		});*/

		//purchase_account.getSelectionModel().select(0);

		purchasetv.refresh(); // is this really needed?




		purchasedt.setValue(LocalDate.now());

		addRows(13);

		/*itemlist.get(itemlist.size()-1).getQuantity().focusedProperty().addListener((observableValue, aBoolean, t1) -> {
			addRows(1);
		});*/



		purchasetv.applyCss();

		/*Stage temp = (Stage) ap.getScene().getWindow();
		temp.setOnShown((event)->{
			System.out.println("completed");

		});*/


	}


	private void addNewColumns() throws IOException, ParseException {
		int i=1;
		JSONObject settings = getJson();
		JSONObject pv = (JSONObject) settings.get("purchasevoucher");
		JSONObject columns = (JSONObject) pv.get("columns");
		JSONArray newcols = (JSONArray) columns.get("newcolumn");
		Iterator objs = newcols.iterator();
		while(objs.hasNext()){
			JSONObject column = (JSONObject) objs.next();
			String name = (String) column.get("name");
			String add_to = (String) column.get("add_to");
			String default_value = (String) column.get("default");
			purchasetv.setPrefWidth(purchasetv.getPrefWidth()+100);
			//	columns adding to  table
				if(i<10) {
					TableColumn<PurchaseItem, String> temp2 = new TableColumn<PurchaseItem, String>(name);
					temp2.setSortable(false);
					temp2.setMaxWidth(100);
					temp2.setCellValueFactory(new PropertyValueFactory<PurchaseItem, String>("newcol" + i));
					String fname = name.split("\\s")[0].toLowerCase(Locale.ROOT)+"_col";
					temp2.setId(fname);


					purchasetv.getColumns().add(temp2);


					if(i==1)
						var1 = add_to;
					else if(i==2)
						var2 = add_to;
					else if(i==3)
						var3 = add_to;
					else if(i==4)
						var4 = add_to;
					else if(i==5)
						var5 = add_to;
					else if(i==6)
						var6=add_to;
					else if(i==7)
						var7 = add_to;
					else if(i==8)
						var8 = add_to;
					else if(i==9)
						var9 = add_to;

					i++;
				}

		}

		}



	private void hideOrUnhideFields() throws IOException, ParseException {
		JSONObject settings = getJson();
		JSONObject hidden =   getJsonObject("hiddenfields", getJsonObject("fields",getJsonObject("purchasevoucher",settings)))  ;

		Iterator keys = hidden.keySet().iterator();


		topvbox.getChildren().remove((HBox) ap.lookup("#billnohbox"));

		while(keys.hasNext()) {
			String key = (String) keys.next();
			Node n;
			if((n=row1hbox.lookup( "#"+key)) !=null)
				row1hbox.getChildren().remove(n);

			else if((n=row2hbox.lookup( "#"+key)) !=null)
				row2hbox.getChildren().remove(n);

			else if((n=row3hbox.lookup( "#"+key)) !=null)
				row3hbox.getChildren().remove(n);

		}

	}

	@FXML
	public void printData(){
		ObservableList<TableColumn> cols =purchasetv.getColumns();
		for(TableColumn column:cols){
			if(column.getText().equalsIgnoreCase("taxable value")){
				purchasetv.getColumns().remove(column);
				purchasetv.getColumns().add(column);
			}
		}
	}

	private void addNewFields() throws IOException, ParseException {
		JSONObject settings = getJson();

		JSONObject fields = getJsonObject("fields",getJsonObject("purchasevoucher",settings));
		JSONArray newfields= (JSONArray) fields.get("newfield");

		Iterator obj = newfields.iterator();
		while (obj.hasNext()){
			JSONObject newf = (JSONObject) obj.next();
			int rno = Integer.parseInt((String) newf.get("rno"));
			String type = (String) newf.get("type");
			String name = (String) newf.get("name");
			String default_value = (String) newf.get("default");
			String list = (String) newf.get("combobox_list");
			addToHBox(rno,name,type,list,default_value);
		}
	}

	private void addToHBox(int rno, String name, String type, String list, String default_value) {
		String hboxid = name.split(" ")[0].toLowerCase() + "hbox";
		HBox newhbox = new HBox();
		newhbox.setId(hboxid);

		if(type.equalsIgnoreCase("Text")){
		TextField tf = new TextField();
		tf.setPromptText(name);
		if(!default_value.isEmpty())
			tf.setText(default_value);
		newhbox.getChildren().add(tf);
		}
		else if(type.equalsIgnoreCase("Dropdown")){
			ObservableList<String> dropdownlist = FXCollections.observableArrayList();
			String[] arr = list.split(",");
			for(String s:arr){
				dropdownlist.add(s);
			}
			ComboBox<String> ddcbox = new ComboBox<String>(dropdownlist);

			newhbox.getChildren().add(ddcbox);
		}
		if(rno==1)
			row1hbox.getChildren().add(newhbox);

		else if(rno==2)
			row2hbox.getChildren().add(newhbox);

		else
			row3hbox.getChildren().add(newhbox);
	}

	private void linkEventListeners(ObservableList<PurchaseItem> itemlist) {

		for (PurchaseItem item : itemlist) {
			ObservableList<String> items = FXCollections.observableArrayList(item_names);

			item.getItems().getEditor().focusedProperty().addListener((event,wasFocused, isNowFocused) -> {

				if (!isNowFocused) {
					if ((!item.getItemName().isEmpty()) && (items_with_ids.containsKey(item.getItemName())) && (!previtemname.equals(item.getItemName()))) {

						try {

							ResultSet res = get_products_dao.getProductDetails(items_with_ids.get(item.getItemName().trim()));
							if(res.next()) {
								// if rate field in empty then set the rate field
								// then updates the total_rate

								String rate = doubleToStringF((double)res.getFloat(1));

								item.getRate().setText(rate);

								int gst = res.getInt(2);
								//item.getDiscount().setText(String.valueOf(res.getFloat(3)));
//								if(isigst.isSelected()) {
//
//								}
//								else{
//									item.getCgst().setText(String.valueOf(gst/2));
//									item.getSgst().setText(String.valueOf(gst/2));
//									item.getIgst().setText("");
//								}
									item.getIgst().setText(String.valueOf(gst));

							}

						} catch (SQLException throwables) {
							throwables.printStackTrace();
						}
					}
				}
				else{
					previtemname = item.getItemName();

				}
			});

			item.getItems().addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
				app_controller.filter(item.getItems(), items, event);

				//app_controller.filter_hsn(item.getItems(),items_with_hsn,event);

			});
			/********************************************************************/

			item.getQuantity().focusedProperty().addListener((observableValue, wasFocussed, isNowFocussed) -> {
				if(!isNowFocussed){
					if(Integer.parseInt(item.getSno()) == sno -2)
						addRows(1);
					if(!item.getItemName().isEmpty())
						calculate(item);
				}
			});

			item.getRate().focusedProperty().addListener((observableValue, wasFocussed, isNowFocussed) -> {
				if(!isNowFocussed){
					if(isDiff(tot_rate,item.getRateValue(),parseToInt(item.getSno())))
						calculate(item);
				}
			});

			item.getGross().focusedProperty().addListener((observableValue, wasFocussed, isNowFocussed) -> {
				if(!isNowFocussed){
					if(isDiff(tot_gross,item.getGrossValue(),parseToInt(item.getSno()))){
						item.getRate().setText(doubleToStringF(item.getGrossValue()/item.getQuantityValue()));
						calculate(item);
					}
				}
			});

			item.getDiscount().focusedProperty().addListener((observableValue, wasFocussed, isNowFocussed) -> {
				if(!isNowFocussed){
					if(isDiff(tot_discount,item.getDiscountValue(),parseToInt(item.getSno())))
						calculate(item);
				}
			});

			item.getSgst().focusedProperty().addListener((observableValue, wasFocussed, isNowFocussed) -> {
				if(!isNowFocussed){
					if(isDiff(tot_sgst,item.getSgstValue(),parseToInt(item.getSno())))
						calculate(item);
				}
			});

			item.getCgst().focusedProperty().addListener((observableValue, wasFocussed, isNowFocussed) -> {
				if(!isNowFocussed){
					if(isDiff(tot_cgst,item.getCgstValue(),parseToInt(item.getSno())))
						calculate(item);
				}
			});

			item.getIgst().focusedProperty().addListener((observableValue, wasFocussed, isNowFocussed) -> {
				if(!isNowFocussed){
					if(isDiff(tot_igst,item.getIgstValue(),parseToInt(item.getSno())))
						calculate(item);
				}
			});

			item.getOther_charges().focusedProperty().addListener((observableValue, wasFocussed, isNowFocussed) -> {
				if(!isNowFocussed){
					if(isDiff(tot_other_charges,parseToDouble(item.getOther_charges().getText()),parseToInt(item.getSno())))
						calculate(item);
				}
			});

			item.getCess().focusedProperty().addListener((observableValue, wasFocussed, isNowFocussed) -> {
				if(!isNowFocussed){
					if(isDiff(tot_cess,parseToDouble(item.getCess().getText()),parseToInt(item.getSno())))
						calculate(item);
				}
			});

			if(!var1.isEmpty() && !var1.equalsIgnoreCase("none")){
				item.getNewcol1().focusedProperty().addListener((observableValue, wasFocussed, isNowFocussed) -> {
					if(!isNowFocussed){
						if(isDiff(tot_new_col1,item.getNewCol1Value(),parseToInt(item.getSno())))
							calculate(item);
					}
				});
			}


			if(!var2.isEmpty() && !var2.equalsIgnoreCase("none")){
				item.getNewcol2().focusedProperty().addListener((observableValue, wasFocussed, isNowFocussed) -> {
					if(!isNowFocussed){
						if(isDiff(tot_new_col2,item.getNewCol2Value(),parseToInt(item.getSno())))
							calculate(item);
					}
				});
			}


			if(!var3.isEmpty() && !var3.equalsIgnoreCase("none")){
				item.getNewcol3().focusedProperty().addListener((observableValue, wasFocussed, isNowFocussed) -> {
					if(!isNowFocussed){
						if(isDiff(tot_new_col3,item.getNewCol3Value(),parseToInt(item.getSno())))
							calculate(item);
					}
				});
			}


			if(!var4.isEmpty() && !var4.equalsIgnoreCase("none")){
				item.getNewcol4().focusedProperty().addListener((observableValue, wasFocussed, isNowFocussed) -> {
					if(!isNowFocussed){
						if(isDiff(tot_new_col4,item.getNewCol4Value(),parseToInt(item.getSno())))
							calculate(item);
					}
				});
			}


			if(!var5.isEmpty() && !var5.equalsIgnoreCase("none")){
				item.getNewcol5().focusedProperty().addListener((observableValue, wasFocussed, isNowFocussed) -> {
					if(!isNowFocussed){
						if(isDiff(tot_new_col5,item.getNewCol5Value(),parseToInt(item.getSno())))
							calculate(item);
					}
				});
			}


			// overall disc
			odisc.focusedProperty().addListener((observableValue, wasFocussed, isNowFocussed) -> {
				if(!isNowFocussed) {
					calculateOverAllDiscAndGst();
				}
			});


			// overall gst
			ogst.focusedProperty().addListener((observableValue, wasFocussed, isNowFocussed) -> {
				if (!isNowFocussed) {
					calculateOverAllDiscAndGst();
				}
			});



					/******************************************************************************/

		}

	}

	private void calculateOverAllDiscAndGst(){
		double temp_total_taxable = Double.parseDouble(calculateSum(tot_taxable_value));
		double disc_in_rs = calculateDiscountInRs(temp_total_taxable);
		temp_total_taxable -= disc_in_rs;
		double gst_in_rs = calculateGstInRs(temp_total_taxable);
		if(!odisc.getText().isEmpty()){
			odisc_total.setVisible(true);
			odisc_total.setText("Rs."+doubleToStringF(disc_in_rs*-1));
		}
		if(!ogst.getText().isEmpty()) {
			ogst_total.setVisible(true);
			ogst_total.setText("Rs."+doubleToStringF(gst_in_rs));
		}

		otaxable_total.setText(doubleToStringF(temp_total_taxable));
		net_amount.setText(doubleToStringF(temp_total_taxable+gst_in_rs));

	}
	private  double calculateGstInRs(double total_taxable){
		return (total_taxable*parseToDouble(ogst.getText())/100);
	}

	private double calculateDiscountInRs(double total_taxable){
			return is_odisc_per.isSelected() ?
				(total_taxable*parseToDouble(odisc.getText())/100) :
				 parseToDouble(odisc.getText());
	}

	private void calculate(PurchaseItem item) {


		int item_sno = parseToInt(item.getSno());
		double item_net_value = 0;
		double taxable = 0;
		// rate
		tot_rate.put(item_sno, item.getRateValue());
		rate_total.setText(calculateSum(tot_rate));

		// quantity
		tot_quantity.put(item_sno, item.getQuantityValue());
		qty_total.setText(calculateSum(tot_quantity));

		//gross
		// calculate gross value of an item
		double gross = item.getRateValue() * item.getQuantityValue();
		item.getGross().setText(doubleToStringF(gross));
		tot_gross.put(item_sno, item.getGrossValue());
		gross_total.setText(calculateSum(tot_gross));

		// discount
		// calculate discount value of an item
		double discount_in_rs = (item.getDiscountValue() * gross) / 100;
		tot_discount.put(item_sno, discount_in_rs);
		discount_total.setText(calculateSum(tot_discount));

		//////////////////////////////////////////////////////////
		discount_in_rs=0;


		// new cols
		if (!var1.equalsIgnoreCase("none") && !var1.isEmpty()) {
			if(var1.equalsIgnoreCase("taxable value"))
				taxable += item.getNewCol1Value();
			else
				item_net_value += item.getNewCol1Value();
			tot_new_col1.put(item_sno, item.getNewCol1Value());
		}

		if (!var2.equalsIgnoreCase("none") && !var2.isEmpty()) {
			if(var2.equalsIgnoreCase("taxable value"))
				taxable += item.getNewCol2Value();
			else
				item_net_value += item.getNewCol2Value();
			tot_new_col2.put(item_sno, item.getNewCol2Value());
		}
		if (!var3.equalsIgnoreCase("none") && !var3.isEmpty()) {
			if(var3.equalsIgnoreCase("taxable value"))
				taxable += item.getNewCol3Value();
			else
				item_net_value += item.getNewCol3Value();
			tot_new_col3.put(item_sno, item.getNewCol3Value());
		}
		if (!var4.equalsIgnoreCase("none") && !var4.isEmpty()) {
			if(var4.equalsIgnoreCase("taxable value"))
				taxable += item.getNewCol4Value();
			else
				item_net_value += item.getNewCol4Value();
			tot_new_col4.put(item_sno, item.getNewCol4Value());
		}
		if (!var5.equalsIgnoreCase("none") && !var5.isEmpty()) {
			if(var5.equalsIgnoreCase("taxable value"))
				taxable += item.getNewCol5Value();
			else
				item_net_value += item.getNewCol5Value();
			tot_new_col5.put(item_sno, item.getNewCol5Value());
		}




		// taxable
		// calculate taxable value of an item
		taxable += gross-discount_in_rs;
		item_net_value += taxable;
		item.getTaxable_value().setText(doubleToStringF(taxable));
		tot_taxable_value.put(item_sno,item.getTaxableValue());
		taxable_total.setText(calculateSum(tot_taxable_value));


		/****************** After calculating Taxable Value *****************************/

		// tax
		// calculate tax value of an item
//		double sgst = item.getTaxableValue()*item.getSgstValue()/100;
//		double cgst = item.getTaxableValue()*item.getCgstValue()/100;
		double igst = item.getTaxableValue()*item.getIgstValue()/100;
//		if(!isigst.isSelected()){
//			// sgst
//			item_net_value += sgst;
//			tot_sgst.put(item_sno,sgst);
//			sgst_total.setText(calculateSum(tot_sgst));
//
//			// cgst
//			item_net_value += cgst;
//			tot_cgst.put(item_sno,cgst);
//			cgst_total.setText(calculateSum(tot_cgst));
//		}else{
			// igst
			item_net_value += igst;
			tot_igst.put(item_sno,igst);
			igst_total.setText(calculateSum(tot_igst));


//		// other charges
//		double other_charges = parseToDouble(item.getOther_charges().getText());
//		item_net_value += other_charges;
//		tot_other_charges.put(item_sno,other_charges);
//		oc_total.setText(calculateSum(tot_other_charges));
//
//		// cess
//		double cess = parseToDouble(item.getCess().getText());
//		item_net_value += cess;
//		tot_cess.put(item_sno,cess);
//		cess_total.setText(calculateSum(tot_cess));

		// total net
		tot_net_value.put(item_sno,item_net_value);
		net_amount.setText(calculateSum(tot_net_value));
	}

	private String calculateSum(HashMap<Integer, Double> hm) {
		double sum = 0;
		for(double value:hm.values())
			sum += value;
		return doubleToStringF(sum);
	}


	private boolean isDiff(HashMap<Integer, Double> tot_map, double newValue, int item_sno) {
		return (tot_map.containsKey(item_sno) && newValue != tot_map.get(item_sno));
	}

	private void calculateTaxable(PurchaseItem item) {

		double taxable = item.getGrossValue()-(item.getGrossValue()*item.getDiscountValue())/100;

		if (var1.equalsIgnoreCase("taxable value"))
			taxable += item.getNewCol1Value();
		if (var2.equalsIgnoreCase("taxable value"))
			taxable += item.getNewCol2Value();
		if (var3.equalsIgnoreCase("taxable value"))
			taxable += item.getNewCol3Value();
		if (var4.equalsIgnoreCase("taxable value"))
			taxable += item.getNewCol4Value();
		if (var5.equalsIgnoreCase("taxable value"))
			taxable += item.getNewCol5Value();

		item.getTaxable_value().setText(doubleToStringF(taxable));

	}

	private void addNewColListeners(String var,String old_total_col,double total_col, String oldValue, String newValue, PurchaseItem item) {
		if (!var.equalsIgnoreCase("None")){
			if(var.equalsIgnoreCase("Net Value")){
				total_net_amount=calculateTotal(old_total_col,String.valueOf(total_col),total_net_amount);
				net_amount.setText(doubleToStringF(total_net_amount));
			}else{
				double taxable_value = item.getTaxableValue();
				taxable_value = calculateTotal(oldValue,newValue,taxable_value);
				item.getTaxable_value().setText(doubleToStringF(taxable_value));
			}
		}
	}


	private ObservableList<String> getAllItems() throws SQLException {
		ObservableList<String> items = FXCollections.observableArrayList();
		ResultSet res =new GetProductsDao().getProducts(SessionController.cid);
		while (res.next()) {
			items_with_ids.put(res.getString(2), res.getInt(1));
			items.add(res.getString(2));
		}
		Collections.sort(items);
		return items;
	}



	public void addRows(int n) {

		purchasetv.scrollTo(sno+n);
		int temp;
		ObservableList<PurchaseItem> extrarowslist = FXCollections.observableArrayList();

		for(temp=sno;temp<sno+n;temp++) {
			ObservableList<String> items = FXCollections.observableArrayList(item_names);
			extrarowslist.add(new PurchaseItem(temp, items, type_of_purchase, "", "", "", "", "", "", "", "", "", "","","","","","","","","",""));
		}

		purchasetv.getItems().addAll(extrarowslist);
		linkEventListeners(extrarowslist);
		itemlist.addAll(extrarowslist); //
		purchasetv.refresh();
		sno=temp;

		spane.setVvalue(0);

	}



	public double calculateTotal(String oldValue,String newValue,double total){
		if(newValue.isEmpty())
			nv=0;
		else
			nv=Double.parseDouble(newValue);
		if(oldValue.isEmpty())
			ov=0;
		else
			ov = Double.parseDouble(oldValue);

		if(!newValue.isEmpty() && oldValue.isEmpty()){
			total+=nv;
		}
		else if(!oldValue.isEmpty()&&!newValue.isEmpty()) {

			total = total- ov + nv;
		}
		else if(ov!=0 && nv==0)
			total-=ov;
		return  total;
	}


	public double calculatePercentageTotal(String oldValue,String newValue,double total,double target){
		if(newValue.isEmpty())
			nv=0;
		else
			nv=Double.parseDouble(newValue);
		if(oldValue.isEmpty())
			ov=0;
		else
			ov = Double.parseDouble(oldValue);

		if(!newValue.isEmpty() && oldValue.isEmpty()){
			total+= (target*nv)/100;
		}
		else if(!oldValue.isEmpty()&&!newValue.isEmpty()) {

			total = total- (target*ov/100) + (target*nv/100);
		}
		else if(ov!=0 && nv==0)
			total-=(target*ov/100);
		return  total;
	}

	public double calculatePercentageGst(String oldValue,String newValue,double total,int gst){
		if(newValue.isEmpty())
			nv=0;
		else {
			nv = Double.parseDouble(newValue);
			nv = (nv*(gst)/100);
		}
		if(oldValue.isEmpty())
			ov=0;
		else {
			ov = Double.parseDouble(oldValue);
			ov = ((ov*gst)/100);
		}

		return calculateTotal(String.valueOf(ov),String.valueOf(nv),total);
	}


	public TableView getTV() {
		return  this.purchasetv;
	}


	@FXML
	void onSave(ActionEvent event) throws SQLException {

		Boolean res=new SetPurchaseVoucher().setPV(SessionController.fid,purchasedt,calculateSum(tot_gross),calculateSum(tot_igst),total_ogst,total_odisc,calculateSum(tot_taxable_value),calculateSum(tot_net_value),(String) pmode.getSelectionModel().getSelectedItem(),remarks.getText(),billno.getText(),billdt);
		if(res)
			new ApplicationController().informationDialog("Not saved! ",null);
		else
			new ApplicationController().informationDialog("Succefully saved Data",null);

		// close the window
		Stage stage = (Stage) save.getScene().getWindow();
		stage.close();


	}

}
