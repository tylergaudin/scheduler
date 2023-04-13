package gaudin.scheduler.controller;

import gaudin.scheduler.model.Appointment;
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
import gaudin.scheduler.model.Directory;
import gaudin.scheduler.model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**This defines the LoginFormController class.
 */
public class LoginFormController implements Initializable {

    private Stage stage;

    private Parent scene;

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

    /**This method writes a text file that records login attempts, and submits user details for
     validation. The method checks the database to validate if the username and password is correct.
     It also checks to see if the user has an appointment in the next 15 minutes then prints a message
     letting the User know either there are no upcoming appointments or lists the appointment. It also
     checks the language setting of the User and displays alerts in the correct language.
     @param event the user clicks submit.
     */
    @FXML
    public void onActionSubmitUserDetails(ActionEvent event) throws SQLException, IOException
    {
        ObservableList<User> users = Directory.getAllUsers();
        String filename = "login_activity.txt", item;
        FileWriter fWriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fWriter);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);

        for (User user: users)
        {
            if (userNameTxt.getText().contains(user.getUserName())
                    && passwordTxt.getText().contains(user.getPassword()))
            {
                item = "User " + user.getUserName() + " successfully logged in at " +
                        formatDateTime;
                outputFile.println(item);
                outputFile.close();
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/gaudin/scheduler/view/CustomerView.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
                for (Appointment a : Directory.getAllAppointments())
                {
                    LocalDateTime n = LocalDateTime.now();

                    if (a.getUserId() == user.getUserId()) {
                        if ((n.isBefore(a.getStartTime()) || n.isEqual(a.getStartTime()))
                                && (n.isAfter(a.getStartTime().minusMinutes(15))
                                || n.isEqual(a.getStartTime().minusMinutes(15)))) {
                            if (Locale.getDefault().getLanguage().equals("fr"))
                            {
                                helpers.alert("Le rendez-vous numéro "  + a.getAppointmentId() +
                                        " à " + a.getStartTime().toLocalTime() + " le " +
                                        a.getStartTime().toLocalDate() +
                                        "est dans les quinze minutes.");
                            }
                            else
                            {
                                helpers.alert("Appointment number " + a.getAppointmentId() + " at "
                                        + a.getStartTime().toLocalTime() + " on " +
                                        a.getStartTime().toLocalDate() +
                                        " is within 15 minutes.");
                            }
                            SaveFormCreateController.setMainUser(user);
                            SaveFormUpdateController.setMainUser(user);
                            return;
                        }
                    }
                }
                SaveFormCreateController.setMainUser(user);
                SaveFormUpdateController.setMainUser(user);
                if (Locale.getDefault().getLanguage().equals("fr"))
                {
                    helpers.alert("Vous n'avez aucun rendez-vous à venir.");
                }
                else
                {
                    helpers.alert("You have no upcoming appointments.");
                }
                return;
            }
        }
        item = "User " + userNameTxt.getText() + " gave invalid login at " + formatDateTime;
        outputFile.println(item);
        outputFile.close();

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
            loginLabel.setText(rb.getString("Login"));
            submitButton.setText(rb.getString("Submit"));
        }
        else if (Locale.getDefault().getLanguage().equals("en"))
        {
            userNameLabel.setText(rb.getString("Nom_d'utilisateur"));
            passwordLabel.setText(rb.getString("Mot_de_passe"));
            loginLabel.setText(rb.getString("Connexion"));
            submitButton.setText(rb.getString("Soumettre"));
        }
    }
}
