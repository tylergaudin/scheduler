package gaudin.scheduler.controller;

import gaudin.scheduler.DAO.AppointmentsQuery;
import gaudin.scheduler.DAO.CustomersQuery;
import gaudin.scheduler.model.Appointment;
import gaudin.scheduler.utilities.helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import gaudin.scheduler.model.Customer;
import gaudin.scheduler.model.Directory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**This defines the CustomerViewController class.
 */
public class CustomerViewController implements Initializable {

    private Stage stage;

    private Parent scene;

    @FXML
    private TableColumn<Customer, String> custTableAddressCol;

    @FXML
    private TableColumn<Customer, Integer> custTableIdCol;

    @FXML
    private TableColumn<Customer, String> custTableNameCol;

    @FXML
    private TableColumn<Customer, String> custTableCountryCol;

    @FXML
    private TableColumn<Customer, String> custTableFirstLevelCol;

    @FXML
    private TableColumn<Customer, String> custTablePhoneCol;

    @FXML
    private TableColumn<Customer, String> custTablePostalCodeCol;

    @FXML
    private TableView<Customer> custTableView;

    /**This method navigates to the CustomerCreate scene.
     @param event when the user clicks the add button.
     */
    @FXML
    public void onActionAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("gaudin/scheduler/view/CustomerCreate.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method deletes the selected Customer. It checks to see if anything is selected,
     then prompts the user to confirm they want to delete the selected Customer. It then
     either exits or deletes the selected Customer from allCustomers and the database.
     Then it confirms the delete was a success or outputs the error in an alert.
     @param event when the user clicks the delete button.
     */
    @FXML
    public void onActionDeleteCustomer(ActionEvent event) throws SQLException
    {
        Customer selectedCustomer = custTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Caution");
            alert1.setContentText("There is nothing selected to delete.");
            alert1.showAndWait();
        }
        else
        {
            try
            {
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the " +
                        "selected customer and associated appointments.\n\t\tAre you sure?");

                Optional<ButtonType> result = alert1.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK)
                {

                    for (Appointment appointment : Directory.getAllAppointments())
                    {
                        if (selectedCustomer.getCustomerId() == appointment.getCustomerId())
                        {
                            AppointmentsQuery.deleteCustAppointments(selectedCustomer.getCustomerId());
                            break;
                        }
                    }

                    CustomersQuery.delete(selectedCustomer.getCustomerId());
                    Directory.getAllCustomers().remove(selectedCustomer);
                    Directory.getAllAppointments().removeIf(appointment ->
                            appointment.getCustomerId() == selectedCustomer.getCustomerId());


                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION,
                            selectedCustomer.getName() + " was deleted.");
                    alert2.showAndWait();
                }
                else if (result.isPresent() && result.get() == ButtonType.CANCEL)
                {
                    return;
                }
            }
            catch (Exception e)
            {
                helpers.alert("Delete did not succeed. Error:" + e);
            }
        }
    }

    /**This method navigates to the AppointmentView scene.
     @param event when the user clicks the go to Appointments button.
     */
    @FXML
    public void onActionGoToAppointments(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/scheduler/view/AppointmentView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method navigates to the AppointmentUpdate scene. It checks to see if there
     is a selected Customer, sends the Customer to the CustomerUpdateController then
     navigates to the CustomerUpdate scene.
     @param event when the user clicks the update button.
     */
    @FXML
    public void onActionUpdateCustomer(ActionEvent event) throws IOException
    {
        Customer selectedCustomer = custTableView.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Caution");
            alert1.setContentText("There is nothing selected to update.");
            alert1.showAndWait();
        }
        else
        {
            CustomerUpdateController.setMainCustomer(selectedCustomer);
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/gaudin/scheduler/view/CustomerUpdate.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**Loads the custTableView with Customer objects.
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
    }
}
