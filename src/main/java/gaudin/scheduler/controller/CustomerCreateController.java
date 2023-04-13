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
import javafx.stage.Stage;
import gaudin.scheduler.model.Country;
import gaudin.scheduler.model.Directory;
import gaudin.scheduler.model.First_Level_Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**This defines the CustomerCreateController class.
 */
public class CustomerCreateController implements Initializable {

    private Stage stage;

    private Parent scene;

    private ObservableList<String> countries = FXCollections.observableArrayList();

    @FXML
    private TextField custAddressTxt;

    @FXML
    public ComboBox<String> custCountryCBox;

    @FXML
    private TextField custNameTxt;

    @FXML
    private TextField custPhoneNumberTxt;

    @FXML
    private TextField custPostalCodeTxt;

    @FXML
    public ComboBox<First_Level_Division> custStateProvCBox;

    /**This method creates a new Customer object and Inserts it into the database.
     It then navigates to the CustomerView scene.
     @param event when the user clicks save.
     */
    @FXML
    public void onActionAddCust(ActionEvent event) throws IOException, SQLException
    {
        try
        {
            String name = custNameTxt.getText();
            String address = custAddressTxt.getText();

            Integer division_Id =
                    custStateProvCBox.getSelectionModel().getSelectedItem().getFirstLevelId();

            String postalCode = custPostalCodeTxt.getText();
            String phoneNumber = custPhoneNumberTxt.getText();

            CustomersQuery.insert(name, address, postalCode, phoneNumber, division_Id);
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

    /**This method navigates to the CustomerView scene.
     @param event when the user clicks cancel.
     */
    @FXML
    public void onActionCancel(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/scheduler/view/CustomerView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**This method loads the custStateProvCBox with data based on the Country selected
     in the custCountryCBox.
     @param event when the user makes a Country selection.
     */
    @FXML
    public void onActionCountrySelect(ActionEvent event)
    {
        ObservableList<First_Level_Division> filter = FXCollections.observableArrayList();
        for (String country : custCountryCBox.getItems())
        {
            if(custCountryCBox.getSelectionModel().getSelectedItem().contains(country))
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
        custStateProvCBox.setItems(filter);
    }

    /**Loads the custCountryCBox combo box with data.
     @param url the scene path.
     @param rb the resource bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        for (Country country : Directory.getAllCountries())
        {
            countries.add(country.getCountryName());
        }
        custCountryCBox.setItems(countries);
    }
}
