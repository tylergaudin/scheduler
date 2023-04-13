package gaudin.scheduler.controller;

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
import gaudin.scheduler.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ResourceBundle;

/**This defines the AppointmentReportsController class.
 */
public class AppointmentReportsController implements Initializable {

    private Stage stage;

    private Parent scene;

    private ObservableList<String> contacts = FXCollections.observableArrayList();

    private ObservableList<Appointment> contactFilter = FXCollections.observableArrayList();

    private ObservableList<String> months = FXCollections.observableArrayList();

    private ObservableList<String> customers = FXCollections.observableArrayList();

    private ObservableList<String> types = FXCollections.observableArrayList();

    @FXML
    private Label apptMonTypeLbl;

    @FXML
    private Label apptPerCustLbl;

    @FXML
    private ComboBox<String> reportContactComboBox;

    @FXML
    private ComboBox<String> reportCustomerComboBox;

    @FXML
    private TableColumn<Appointment, String> apptTableContactCol;

    @FXML
    private TableColumn<Appointment, Integer> apptTableCustomerIdCol;

    @FXML
    private TableColumn<Appointment, String> apptTableDescriptionCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> apptTableEndTimeCol;

    @FXML
    private TableColumn<Appointment, Integer> apptTableIdCol;

    @FXML
    private TableColumn<Appointment, String> apptTableLocationCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> apptTableStartTimeCol;

    @FXML
    private TableColumn<Appointment, String> apptTableTitleCol;

    @FXML
    private TableColumn<Appointment, String> apptTableTypeCol;

    @FXML
    private TableColumn<Appointment, Integer> apptTableUserIdCol;

    @FXML
    private TableView<Appointment> apptTableView;

    @FXML
    private ComboBox<String> reportMonthComboBox;

    @FXML
    private ComboBox<String> reportTypeComboBox;

    /**This method navigates to the AppointmentView scene.
     @param event when the user clicks the return to Appointments button.
     */
    @FXML
    public void onActionReturn(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load
                (getClass().getResource("/gaudin/scheduler/view/AppointmentView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method filters Appointments based on a Contact selection. It fills the apptTableView
     * with the Appointments based on the Contact selected.
     @param event the user makes a selection in the Contact combo box.
     */
    @FXML
    public void onActionContactSelect(ActionEvent event)
    {
        contactFilter.clear();
        for (Appointment appointment : Directory.getAllAppointments())
        {
            if (appointment.getContactName().contains(reportContactComboBox.getValue()))
            {
                contactFilter.add(appointment);
            }
        }
        apptTableView.setItems(contactFilter);
    }

    /**This method sets the apptPerCustLbl. It loops through allCustomers find a selected
     customer, then loops through appointments and increments count when appointments
     match the customer Id. It then sets the apptPerCustLbl.
     @param event the user makes a selection in the Customer combo box.
     */
    @FXML
    public void onActionCustomerSelect(ActionEvent event)
    {
        int count = 0;

        for (Customer customer : Directory.getAllCustomers())
        {
            if (reportCustomerComboBox.getValue().contains(customer.getName()))
            {
                for (Appointment appointment : Directory.getAllAppointments())
                {
                    if (appointment.getCustomerId() == customer.getCustomerId())
                    {
                        count++;
                    }
                }
                apptPerCustLbl.setText(String.valueOf(count));
                return;
            }
        }
    }

    /**This method counts the number of Appointments in a given month of a specified type.
     It checks the reportMonthComboBox and the reportTypeComboBox counts the number of Appointments
     that meet both crieteria.
     @param event the user makes a selection in the Month combo box.
     */
    @FXML
    public void onActionMonthSelect(ActionEvent event) throws SQLException
    {
        String string = reportMonthComboBox.getValue();
        ObservableList<Appointment> filter = FXCollections.observableArrayList();

        for (Appointment appointment : Directory.getAllAppointments())
        {
            if (reportTypeComboBox.getValue() == null || reportMonthComboBox.getValue() == null)
            {
                return;
            }
            else if (reportTypeComboBox.getValue().contains(appointment.getType()))
            {
                if (Month.of(appointment.getStartTime().toLocalDate().getMonthValue())
                        == Month.valueOf(string.toUpperCase()))
                {
                    filter.add(appointment);
                }
                apptMonTypeLbl.setText(String.valueOf(filter.size()));
            }
        }
    }
    /**This method counts the number of Appointments in a given month of a specified type.
     It checks the reportMonthComboBox and the reportTypeComboBox counts the number of Appointments
     that meet both crieteria.
     @param event the user makes a selection in the Type combo box.
     */
    @FXML
    public void onActionTypeSelect(ActionEvent event) throws SQLException
    {
        String string = reportMonthComboBox.getValue();
        ObservableList<Appointment> filter = FXCollections.observableArrayList();

        for (Appointment appointment : Directory.getAllAppointments())
        {
            if (reportTypeComboBox.getValue() == null || reportMonthComboBox.getValue() == null)
            {
                return;
            }
            else if (reportTypeComboBox.getValue().contains(appointment.getType()))
            {
                if (Month.of(appointment.getStartTime().toLocalDate().getMonthValue())
                        == Month.valueOf(string.toUpperCase()))
                {
                    filter.add(appointment);
                }
                apptMonTypeLbl.setText(String.valueOf(filter.size()));
            }
        }
    }

    /**Loads the apptTableView and loads all of the combo boxes.
     @param url the scene path.
     @param rb the resource bundle.
     */
    public void initialize(URL url, ResourceBundle rb)
    {
        //Load items in table Main.view.
        apptTableView.setItems(Directory.getAllAppointments());

        apptTableIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTableTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptTableDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptTableLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptTableContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        apptTableTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptTableStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        apptTableEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        apptTableCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptTableUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        //Load Contact combo box.
        for (Contact contact : Directory.getAllContacts())
        {
            contacts.add(String.valueOf(contact.getContactName()));
        }
        reportContactComboBox.setItems(contacts);

        //Load Customer Id combo box.
        for (Customer customer : Directory.getAllCustomers())
        {
            customers.add(String.valueOf(customer.getName()));
        }
        reportCustomerComboBox.setItems(customers);

        //Load Month combo box.
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        reportMonthComboBox.setItems(months);


        //Load Type combo box.
        for (Appointment appointment : Directory.getAllAppointments())
        {
            types.add(String.valueOf(appointment.getType()));
        }
        reportTypeComboBox.setItems(types);
    }
}
