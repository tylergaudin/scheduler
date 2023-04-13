package gaudin.scheduler.controller;

import gaudin.scheduler.DAO.AppointmentsQuery;
import gaudin.scheduler.utilities.helpers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import gaudin.scheduler.model.Appointment;
import gaudin.scheduler.model.Directory;
import gaudin.scheduler.model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

/**This defines the SaveFormCreateController class.
 */
public class SaveFormCreateController implements Initializable {

    private Stage stage;

    private Parent scene;

    private static Appointment userAppointment = null;

    public static void setUserAppointment(Appointment userAppointment)
    {
        SaveFormCreateController.userAppointment = userAppointment;
    }

    public static User mainUser = null;

    public static void setMainUser(User mainUser) {
        SaveFormCreateController.mainUser = mainUser;
    }

    private String title = userAppointment.getTitle();
    String description = userAppointment.getDescription();
    String location = userAppointment.getLocation();
    String type = userAppointment.getType();
    LocalDateTime start = userAppointment.getStartTime();
    LocalDateTime end = userAppointment.getEndTime();
    int customerId = userAppointment.getCustomerId();
    int userId = userAppointment.getUserId();
    int contactId = userAppointment.getContactId();

    @FXML
    private Button cancelButton;

    @FXML
    private Label countryLabel;

    @FXML
    private Label loginLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField passwordTxt;

    @FXML
    private Button submitButton;

    @FXML
    private Label userNameLabel;

    @FXML
    private TextField userNameTxt;

    /**This method navigates to the AppointmentView scene.
     @param event when the user clicks the cancel button.
     */
    @FXML
    public void onActionCancelUserDetails(ActionEvent event) throws IOException
    {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load
                (getClass().getResource("/gaudin/scheduler/view/AppointmentView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method submits user details for validation. The method checks the database to
     validate if the username and password is correct. It also checks the language setting
     of the User and displays alerts in the correct language.
     @param event the user clicks save.
     */
    @FXML
    public void onActionSubmitUserDetails(ActionEvent event) throws SQLException, IOException {
        ObservableList<User> users = Directory.getAllUsers();
        for (User user: users)
        {
            if(user.getUserId() == mainUser.getUserId())
            {
                if (userNameTxt.getText().contains(user.getUserName())
                        && passwordTxt.getText().contains(user.getPassword())) {

                    AppointmentsQuery.insert(title, description, location, type, start,
                            end, customerId, userId, contactId);
                    Directory.getAllAppointments().clear();
                    Directory.getAllAppointments().addAll(AppointmentsQuery.selectJoin());
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load
                            (getClass().getResource("/gaudin/scheduler/view/AppointmentView.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

                    return;
                }
            }
        }
        if (Locale.getDefault().getLanguage().equals("fr"))
        {
            helpers.alert("Identifiant ou mot de passe incorrect.");
        }
        else
        {
            helpers.alert("Incorrect username or password.");
        }
    }

    /**Translates the form into the user's language setting.
     @param url the scene path.
     @param rb the resource bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        String userZoneId = String.valueOf(ZoneId.systemDefault());
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();
        for (String zone : zoneIds)
        {
            if (zone.contains(userZoneId))
            {
                countryLabel.setText(zone);
            }
        }

        rb = ResourceBundle.getBundle("main", Locale.getDefault());
        if (!Locale.getDefault().getLanguage().equals("fr")
                && !Locale.getDefault().getLanguage().equals("en"))
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Language Error");
            alert1.setContentText("Language not supported.");
            alert1.showAndWait();
        }
        else if (Locale.getDefault().getLanguage().equals("fr"))
        {
            userNameLabel.setText(rb.getString("Username"));
            passwordLabel.setText(rb.getString("Password"));
            loginLabel.setText(rb.getString("Save"));
            submitButton.setText(rb.getString("Save"));
            cancelButton.setText(rb.getString("Cancel"));
        }
        else if (Locale.getDefault().getLanguage().equals("en"))
        {
            userNameLabel.setText(rb.getString("Nom_d'utilisateur"));
            passwordLabel.setText(rb.getString("Mot_de_passe"));
            loginLabel.setText(rb.getString("Sauvegarder"));
            submitButton.setText(rb.getString("Sauvegarder"));
            cancelButton.setText(rb.getString("Annuler"));
        }
    }
}
