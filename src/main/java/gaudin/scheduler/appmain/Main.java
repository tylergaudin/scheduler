package gaudin.scheduler.appmain;

import gaudin.scheduler.DAO.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import gaudin.scheduler.model.Directory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

/**This class defines the Main class.
 */
public class Main extends Application {

    /**This method loads the Login Form and sets the stage.
     @param stage sets the stage.
     */
    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader fxmlloader = new FXMLLoader
                (Main.class.getResource("/gaudin/scheduler/view/LoginForm.fxml"));
        Scene scene = new Scene(fxmlloader.load());
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }
    /**This method launches the application, opens the databse connection and closes it
     as well as loading the Observable Lists in the directory with the database Objects.
     @param args the args.
     */
    public static void main(String[] args) throws SQLException, IOException {
        JDBC.openConnection();

        Locale.setDefault(new Locale("fr"));
        Directory.getAllUsers().addAll(UsersQuery.select());
        Directory.getAllFirstLevels().addAll(DivisionsQuery.selectFirstLevel());
        Directory.getAllCustomers().addAll(CustomersQuery.selectJoin());
        Directory.getAllAppointments().addAll(AppointmentsQuery.selectJoin());
        Directory.getAllContacts().addAll(ContactsQuery.select());

        launch(args);

        JDBC.closeConnection();
    }
}
