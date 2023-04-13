package gaudin.scheduler.controller;

import gaudin.scheduler.DAO.AppointmentsQuery;
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
public class AppointmentWeekViewController implements Initializable {

    private Stage stage;

    private Parent scene;

    private ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();

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

    /**This method navigates to the AppointmentCreate scene.
     @param event when the user clicks the add button.
     */
    @FXML
    public void onActionAddAppointment(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/scheduler/view/AppointmentCreate.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

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
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Caution");
            alert1.setContentText("There is nothing selected to delete.");
            alert1.showAndWait();
        }
        else
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
    }

    /**This method navigates to the AppointmentView scene.
     @param event when the user clicks the All Appointments radio button.
     */
    @FXML
    public void onActionDisplayAllView(ActionEvent event) throws IOException
    {
        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/scheduler/view/AppointmentView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method navigates to the AppointmentMonthView scene.
     @param event when the user clicks the Month radio button.
     */
    @FXML
    public void onActionDisplayMonthView(ActionEvent event) throws IOException
    {
        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/scheduler/view/AppointmentMonthView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method navigates to the AppointmentWeekView scene.
     @param event when the user clicks the Week radio button.
     */
    @FXML
    public void onActionDisplayWeekView(ActionEvent event) throws IOException
    {
        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/scheduler/view/AppointmentWeekView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method navigates to the CustomerView scene.
     @param event when the user clicks the go to directory button.
     */
    @FXML
    public void onActionGoToDirectory(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/scheduler/view/CustomerView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method navigates to the AppointmentReports scene.
     @param event when the user clicks the Reports button.
     */
    @FXML
    public void onActionDisplayReports(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/gaudin/scheduler/view/AppointmentReports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method navigates to the AppointmentUpdate scene. It checks to see if there
     is a selected Appointment, sends the Appointment to the AppointmentUpdateController then
     navigates to the AppointmentUpdate scene.
     @param event when the user clicks the update button.
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
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/gaudin/scheduler/view/AppointmentUpdate.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**Loads the apptTableView and filters the results by Appointments within 7 days.
     @param url the scene path.
     @param rb the resource bundle.
     */
    public void initialize(URL url, ResourceBundle rb)
    {
        for (Appointment appointment : Directory.getAllAppointments())
        {
            if (appointment.getStartTime().
                    isAfter(LocalDateTime.now()) && appointment.getStartTime().isBefore
                    (LocalDateTime.now().plusDays(7)))
            {
                weekAppointments.add(appointment);
            }
        }


        apptTableView.setItems(weekAppointments);

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
