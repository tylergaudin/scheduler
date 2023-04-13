package gaudin.scheduler.utilities;

import javafx.scene.control.Alert;

import java.time.*;

/**This defines the helpers class. It consists of an easy alert to shorten code and a LocalDateTime
 converter.*/
public class helpers {

    /**This method outputs a string as a WARNING alert.
     @param string the string to set the alert's content text.
     */
    public static void alert(String string)
    {
        Alert alert1 = new Alert(Alert.AlertType.WARNING);
        alert1.setContentText(string);
        alert1.showAndWait();
    }

    /**This method convert a LocalTime to a LocalDateTime instance in Eastern Standard Time.
     @param localTime time to be converted.
     @return LocalDateTime in EST.*/
    public static LocalDateTime timeChangeEST(LocalTime localTime)
    {
        LocalDate date = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(date, localTime);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime zonedDateTimeEST = zonedDateTime.withZoneSameInstant(ZoneId.of("US/Eastern"));
        return zonedDateTimeEST.toLocalDateTime();
    }
}
