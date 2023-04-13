package gaudin.scheduler.controller;

import gaudin.scheduler.DAO.AppointmentsQuery;
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
import gaudin.scheduler.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**This defines the AppointmentCreateController class.
 */
public class AppointmentCreateController implements Initializable {

    private Stage stage;

    private Parent scene;

    private ObservableList<String> contacts = FXCollections.observableArrayList();

    private ObservableList<LocalTime> times = FXCollections.observableArrayList();

    private ObservableList<String> customers = FXCollections.observableArrayList();

    private ObservableList<String> users = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> apptContactComboBox;

    @FXML
    private ComboBox<String> apptCustomerIdComboBox;

    @FXML
    private TextField apptDescriptionTxt;

    @FXML
    private ComboBox<LocalTime> apptEndComboBox;

    @FXML
    private DatePicker apptEndDatePicker;

    @FXML
    private TextField apptLocationTxt;

    @FXML
    private ComboBox<LocalTime> apptStartComboBox;

    @FXML
    private DatePicker apptStartDatePicker;

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
    private TextField apptTitleTxt;

    @FXML
    private TextField apptTypeTxt;

    @FXML
    private ComboBox<String> apptUserIdComboBox;

    /**This method navigates to the AppointmentView scene.
     @param event when the user clicks cancel.
     */
    @FXML
    public void onActionCancelAddAppointment(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/scheduler/view/AppointmentView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method creates a new Appointment object and Inserts it into the database.
     It then navigates to the SaveFormCreate scene.
     @param event when the user clicks save.
     */
    @FXML
    public void onActionSaveAppointment(ActionEvent event) throws IOException, SQLException
    {
        try {
                String title = apptTitleTxt.getText();
                String description = apptDescriptionTxt.getText();
                String location = apptLocationTxt.getText();
                String type = apptTypeTxt.getText();
                LocalTime time = LocalTime.parse
                        (String.valueOf(apptStartComboBox.getSelectionModel().getSelectedItem()));
                LocalDate date = apptStartDatePicker.getValue();
                LocalDateTime start = LocalDateTime.of(date, time);
                LocalTime time1 = LocalTime.parse
                        (String.valueOf(apptEndComboBox.getSelectionModel().getSelectedItem()));
                LocalDate date1 = apptEndDatePicker.getValue();
                LocalDateTime end = LocalDateTime.of(date1, time1);
                int customerId = Integer.parseInt
                        (apptCustomerIdComboBox.getSelectionModel().getSelectedItem());
                int userId = Integer.parseInt
                        (apptUserIdComboBox.getSelectionModel().getSelectedItem());
                int contactId = Directory.searchContact
                        (apptContactComboBox.getValue()).getContactId();

                if (start.isAfter(end))
                {
                    helpers.alert
                            ("End date and time must be after start date and time. Try again.");
                    return;
                }
                else if
                (title.isBlank() || description.isBlank() || location.isBlank() || type.isBlank())
                {
                    helpers.alert("\tText input fields cannot be blank. " +
                            "\n\tPlease check entries and try again.");
                    return;
                }
                else
                {
                    ObservableList<Appointment> sj = AppointmentsQuery.selectJoin();
                    for (Appointment app : sj)
                    {
                        LocalDateTime a = app.getStartTime();
                        LocalDateTime b = app.getEndTime();
                        if (app.getCustomerId() == customerId)
                        {
                            if ((start.isAfter(a) || start.isEqual(a)) && start.isBefore(b))
                            {
                                helpers.alert("New appointment overlaps appointment # "
                                        + app.getAppointmentId() + "\n\t\tTry again.");
                                return;
                            } else if (end.isAfter(a) && (end.isBefore(b) || end.isEqual(b)))
                            {
                                helpers.alert("New appointment overlaps appointment # "
                                        + app.getAppointmentId() + "\n\t\tTry again.");
                                return;
                            } else if ((start.isBefore(a) || start.isEqual(a)) &&
                                    (end.isAfter(b) || end.isEqual(b)))
                            {
                                helpers.alert("New appointment overlaps appointment # "
                                        + app.getAppointmentId() + "\n\t\tTry again.");
                                return;
                            }
                        }
                    }
                    SaveFormCreateController.setUserAppointment(new Appointment
                            (1, title, description, location, "", contactId,
                                    type, start, end, customerId, userId));
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource
                            ("/gaudin/scheduler/view/SaveFormCreate.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        catch(Exception e){
            helpers.alert("Please revise your entries. All fields must be filled out. " +
                    "Error: " + e);
            return;
        }
    }
    /**Loads the apptTableView and all the combo boxes with data.
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
        apptContactComboBox.setItems(contacts);

        //Load Start and End combo boxes.
        LocalTime st = LocalTime.parse("08:00:00");
        LocalTime et = LocalTime.parse("22:15:00");

        for (LocalDateTime i = helpers.timeChangeEST(st);
             i.isBefore(helpers.timeChangeEST(et)); i = i.plusMinutes(15))
        {
            times.add(i.toLocalTime());
        }
        apptStartComboBox.setItems(times);
        apptEndComboBox.setItems(times);

        //Load Customer Id combo box.
        for (Customer customer : Directory.getAllCustomers())
        {
            customers.add(String.valueOf(customer.getCustomerId()));
        }
        apptCustomerIdComboBox.setItems(customers);

        //Load User Id combo box.
        for (User user : Directory.getAllUsers())
        {
            users.add(String.valueOf(user.getUserId()));
        }
        apptUserIdComboBox.setItems(users);
    }
}
