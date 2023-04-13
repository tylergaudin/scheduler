package gaudin.scheduler.controller;

import gaudin.scheduler.DAO.AppointmentsQuery;
import gaudin.scheduler.utilities.LambdaAlert;
import gaudin.scheduler.utilities.LambdaNavigate;
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
import gaudin.scheduler.model.Appointment;
import gaudin.scheduler.model.Directory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**This defines the AppointmentViewController class.
 */
public class AppointmentViewController implements Initializable {

    private Stage stage;

    private Parent scene;

    /**This Lambda navigates to an FXML file based on a button event. This cut down on my
     total lines of code significantly and allowed me to switch between event types easily.
     */
    private LambdaNavigate navButton = (p, event) -> {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(p));
        stage.setScene(new Scene(scene));
        stage.show();
    };

    /**This Lambda navigates to an FXML file based on a radio button event. This cut down on my
     total lines of code significantly and allowed me to switch between event types easily.
     */
    private LambdaNavigate navRadioButton = (p, event) -> {
        stage = (Stage) ((RadioButton) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(p));
        stage.setScene(new Scene(scene));
        stage.show();
    };
    /**This Lambda displays an alert of type warning with the text set as a string. This cut down on my
     total lines of code significantly and allowed me to switch alerts easily.
     */
    private LambdaAlert confirm = n -> {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(n);
        alert.showAndWait();
    };

    /**This Lambda displays an alert of type error with the text set as a string. This cut down on my
     total lines of code significantly and allowed me to switch alerts easily.
     */
    private LambdaAlert error = n -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(n);
        alert.showAndWait();
    };

    @FXML
    private RadioButton apptAllViewRBtn;

    @FXML
    private RadioButton apptMonthViewRBtn;

    @FXML
    private RadioButton apptWeekViewRBtn;

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

    /**This method deletes the selected appointment. It checks to see if anything is selected,
     then prompts the user to confirm they want to delete the selected Appointment. It then
     either exits or deletes the selected appointment from allAppointments and the database.
     Then it confirms the delete was a success or outputs the error in an alert.
     @param event when the user clicks the delete button.
     */
    @FXML
    public void onActionDeleteAppointment(ActionEvent event) throws SQLException
    {
        Appointment selectedAppointment = apptTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null)
        {
            error.alert("There is nothing selected to delete.");
        }
        else
        {
            try
            {
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the " +
                        "selected appointment.\n\t\tAre you sure?");

                Optional<ButtonType> result = alert1.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK)
                {
                    AppointmentsQuery.deleteAppointment(selectedAppointment.getAppointmentId());
                    Directory.getAllAppointments().remove(selectedAppointment);

                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION,
                            "Appointment number " + selectedAppointment.getAppointmentId() +
                                    " of type " + selectedAppointment.getType() + " was deleted.");
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

    /**This method navigates to the AppointmentCreate scene.
     @param event when the user clicks the add button.
     It includes the navButton Lambda and navigates to the AppointmentCreate screen.
     */
    @FXML
    public void onActionAddAppointment(ActionEvent event) throws IOException
    {
        navButton.navToScene("/gaudin/scheduler/view/AppointmentCreate.fxml", event);
    }

    /**This method navigates to the AppointmentView scene.
     @param event when the user clicks the All Appointments radio button.
     It includes the navRadioButton Lambda and navigates to the AppointmentView screen.
     */
    @FXML
    public void onActionDisplayAllView(ActionEvent event) throws IOException
    {
        navRadioButton.navToScene("/gaudin/scheduler/view/AppointmentView.fxml", event);
    }

    /**This method navigates to the AppointmentMonthView scene.
     @param event when the user clicks the Month radio button.
     It includes the navRadioButton Lambda and navigates to the AppointmentMonthView screen.
     */
    @FXML
    public void onActionDisplayMonthView(ActionEvent event) throws IOException
    {
        navRadioButton.navToScene("/gaudin/scheduler/view/AppointmentMonthView.fxml", event);
    }

    /**This method navigates to the AppointmentWeekView scene.
     @param event when the user clicks the Week radio button.
     It includes the navRadioButton Lambda and navigates to the AppointmentWeekView screen.
     */
    @FXML
    public void onActionDisplayWeekView(ActionEvent event) throws IOException
    {
        navRadioButton.navToScene("/gaudin/scheduler/view/AppointmentWeekView.fxml", event);
    }

    /**This method navigates to the CustomerView scene.
     @param event when the user clicks the go to directory button.
     It includes the navButton Lambda and navigates to the CustomerView screen.
     */
    @FXML
    public void onActionGoToDirectory(ActionEvent event) throws IOException
    {
        navButton.navToScene("/gaudin/scheduler/view/CustomerView.fxml", event);
    }

    /**This method navigates to the AppointmentReports scene.
     @param event when the user clicks the Reports button.
     It includes the navButton Lambda and navigates to the AppointmentReports screen.
     */
    @FXML
    public void onActionDisplayReports(ActionEvent event) throws IOException
    {
        navButton.navToScene("/gaudin/scheduler/view/AppointmentReports.fxml", event);
    }

    /**This method navigates to the AppointmentUpdate scene. It checks to see if there
     is a selected Appointment, sends the Appointment to the AppointmentUpdateController then
     navigates to the AppointmentUpdate scene.
     @param event when the user clicks the update button.
     It includes the navButton Lambda and navigates to the AppointmentUpdate screen.
     */
    @FXML
    public void onActionUpdateAppointment(ActionEvent event) throws IOException {
        Appointment selectedAppointment = apptTableView.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Caution");
            alert1.setContentText("There is nothing selected to update.");
            alert1.showAndWait();
        }
        else {
            AppointmentUpdateController.setMainAppointment(selectedAppointment);
            navButton.navToScene("/gaudin/scheduler/view/AppointmentUpdate.fxml", event);
        }
    }

    /**Loads the apptTableView.
     @param url the scene path.
     @param rb the resource bundle.
     */
    public void initialize(URL url, ResourceBundle rb)
    {
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
    }
}
