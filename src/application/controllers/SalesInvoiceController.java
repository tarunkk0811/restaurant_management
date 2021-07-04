package application.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class SalesInvoiceController {

    @FXML
    private DatePicker order_date;

    @FXML
    private TextField order_table_no;

    @FXML
    private Text order_invoice;

    @FXML
    private TableView<?> purchasetv;

    @FXML
    private TableColumn<?, ?> order_sno_col;

    @FXML
    private TableColumn<?, ?> order_item_col;

    @FXML
    private TableColumn<?, ?> order_qty_col;

    @FXML
    private TableColumn<?, ?> order_rate_col;

    @FXML
    private TableColumn<?, ?> order_gross_col;

    @FXML
    private HBox totals_hbox;

    @FXML
    private HBox order_qty_col_total;

    @FXML
    private Text order_qty_total;

    @FXML
    private HBox order_rate_col_total;

    @FXML
    private Text order_rate_total;

    @FXML
    private HBox order_gross_col_total;

    @FXML
    private Text order_gross_total;

    @FXML
    private Button order_save;

    @FXML
    private Button order_print;

    @FXML
    private TextField instructions;

    @FXML
    private Button order_summary;

    @FXML
    private Button cancel_order;

    @FXML
    private Button new_order;

    @FXML
    private CheckBox online_order;

    @FXML
    private Text sale_invoice;

    @FXML
    private CheckBox online_sale;

    @FXML
    private TextField customer_name;

    @FXML
    private TextField phone;

    @FXML
    private TextField sale_table_no;

    @FXML
    private DatePicker sale_date;

    @FXML
    private ComboBox<?> pmode;

    @FXML
    private TextArea remarks;

    @FXML
    private TableColumn<?, ?> sale_sno_col;

    @FXML
    private TableColumn<?, ?> sale_item_col;

    @FXML
    private TableColumn<?, ?> sale_qty_col;

    @FXML
    private TableColumn<?, ?> sale_rate_col;

    @FXML
    private TableColumn<?, ?> sale_gross_col;

    @FXML
    private TableColumn<?, ?> sale_disc_col;

    @FXML
    private TableColumn<?, ?> sale_igst_col;

    @FXML
    private TableColumn<?, ?> sale_taxable_value_col;

    @FXML
    private HBox sale_qty_col_total;

    @FXML
    private Text sale_qty_total;

    @FXML
    private HBox sale_rate_col_total;

    @FXML
    private Text sale_rate_total;

    @FXML
    private HBox sale_gross_col_total;

    @FXML
    private Text sale_gross_total;

    @FXML
    private HBox sale_disc_col_total;

    @FXML
    private Text sale_discount_total;

    @FXML
    private HBox sale_igst_col_total;

    @FXML
    private Text sale_igst_total;

    @FXML
    private HBox sale_taxable_value_col_total;

    @FXML
    private Text sale_taxable_total;

    @FXML
    private HBox sale_otaxable_value_col_total;

    @FXML
    private Text sale_otaxable_total;

    @FXML
    private TextField ogst;

    @FXML
    private TextField odisc;

    @FXML
    private CheckBox is_odisc_per;

    @FXML
    private Text ogst_total;

    @FXML
    private Text odisc_total;

    @FXML
    private Button save_sale;

    @FXML
    private Button print_sale;

    @FXML
    private Text net_amount;


}
