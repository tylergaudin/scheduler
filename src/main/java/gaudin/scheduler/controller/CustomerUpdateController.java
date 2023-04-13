package gaudin.scheduler.controller;

import gaudin.scheduler.DAO.CustomersQuery;
import gaudin.scheduler.utilities.helpers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import gaudin.scheduler.model.Country;
import gaudin.scheduler.model.Customer;
import gaudin.scheduler.model.Directory;
import gaudin.scheduler.model.First_Level_Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**This defines the CustomerUpdateController class.
 */
public class CustomerUpdateController implements Initializable {

    private Stage stage;

    private Parent scene;

    private ObservableList<String> countries = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Customer, String> custTableAddressCol;

    @FXML
    private TableColumn<Customer, String> custTableCountryCol;

    @FXML
    private TableColumn<Customer, String> custTableFirstLevelCol;

    @FXML
    private TableColumn<Customer, Integer> custTableIdCol;

    @FXML
    private TableColumn<Customer, String> custTableNameCol;

    @FXML
    private TableColumn<Customer, String> custTablePhoneCol;

    @FXML
    private TableColumn<Customer, String> custTablePostalCodeCol;

    @FXML
    private TableView<Customer> custTableView;

    @FXML
    private TextField updateAddressTxt;

    @FXML
    private ComboBox<String> updateCountryComboBox;

    @FXML
    private ComboBox<First_Level_Division> updateFirstLevelComboBox;

    @FXML
    private TextField updateIdTxt;

    @FXML
    private TextField updateNameTxt;

    @FXML
    private TextField updatePhoneNumberTxt;

    @FXML
    private TextField updatePostalCodeTxt;

    private static Customer mainCustomer = null;

    public static void setMainCustomer(Customer mainCustomer) {
        CustomerUpdateController.mainCustomer = mainCustomer;
    }

    /**This method navigates to the CustomerView scene.
     @param event when the user clicks cancel.
     */
    @FXML
    public void onActionCancelUpdate(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/scheduler/view/CustomerView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method updates Customer in allCustomers and in the database.
     It then navigates to the CustomerView scene.
     @param event when the user clicks save.
     */
    @FXML
    public void onActionSaveCustomer(ActionEvent event) throws IOException, SQLException {
        try
        {
            int custId = Integer.parseInt(updateIdTxt.getText());
            String name = updateNameTxt.getText();
            String address = updateAddressTxt.getText();
            Integer division_Id =
                    updateFirstLevelComboBox.getSelectionModel().getSelectedItem().getFirstLevelId();
            String postalCode = updatePostalCodeTxt.getText();
            String phoneNumber = updatePhoneNumberTxt.getText();

            CustomersQuery.update(custId, name, address, postalCode, phoneNumber, division_Id);
            Directory.getAllCustomers().clear();
            Directory.getAllCustomers().addAll(CustomersQuery.selectJoin());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/gaudin/scheduler/view/CustomerView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(Exception e){
            helpers.alert("Please revise your entries. All fields must be filled out. " +
                    "Error: " + e);
            return;
        }
    }

    /**This method loads the updateFirstLevelComboBox with data based on the Country selected
     in the updateCountryCBox.
     @param event when the user makes a Country selection.
     */
    @FXML
    public void onActionCountrySelect(ActionEvent event)
    {
        ObservableList<First_Level_Division> filter = FXCollections.observableArrayList();
        for (String country : updateCountryComboBox.getItems())
        {
            if(updateCountryComboBox.getSelectionModel().getSelectedItem().contains(country))
            {
                for (First_Level_Division fd : Directory.getAllFirstLevels())
                {
                    if (fd.getCountryName().contains(country))
                    {
                        filter.add(fd);
                    }
                }
            }
        }
        updateFirstLevelComboBox.setItems(filter);
    }

    /**This method loads the updateFirstLevelComboBox with data based on the Country selected
     in the updateCountryCBox.
     */
    public void onActionCountrySelect()
    {
        ObservableList<First_Level_Division> filter = FXCollections.observableArrayList();
        for (String country : updateCountryComboBox.getItems())
        {
            if(updateCountryComboBox.getSelectionModel().getSelectedItem().contains(country))
            {
                for (First_Level_Division fd : Directory.getAllFirstLevels())
                {
                    if (fd.getCountryName().contains(country))
                    {
                        filter.add(fd);
                    }
                }
            }
        }
        updateFirstLevelComboBox.setItems(filter);
    }

    /**Loads the custTableView, loads the text boxes and combo boxes for updating
     with the Customer to update and loads all the combo boxes with data to choose from.
     @param url the scene path.
     @param rb the resource bundle.
     */
    public void initialize(URL url, ResourceBundle rb)
    {
        custTableView.setItems(Directory.getAllCustomers());

        custTableIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custTableNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        custTableAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custTableCountryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        custTableFirstLevelCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        custTablePostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        custTablePhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        custTableView.getSelectionModel().select(mainCustomer);

        for (Country country : Directory.getAllCountries())
        {
            countries.add(country.getCountryName());
        }

        updateIdTxt.setText(String.valueOf(mainCustomer.getCustomerId()));
        updateNameTxt.setText(String.valueOf(mainCustomer.getName()));
        updateCountryComboBox.setItems(countries);
        updateCountryComboBox.getSelectionModel().select(mainCustomer.getCountryName());
        onActionCountrySelect();
        updateAddressTxt.setText(mainCustomer.getAddress());
        updateFirstLevelComboBox.getSelectionModel().select
                (Directory.searchFirstLevel(mainCustomer.getFirstLevelId()));
        updatePostalCodeTxt.setText(String.valueOf(mainCustomer.getPostalCode()));
        updatePhoneNumberTxt.setText(String.valueOf(mainCustomer.getPhoneNumber()));
    }
}
