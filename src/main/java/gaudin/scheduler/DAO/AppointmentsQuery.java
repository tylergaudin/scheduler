package gaudin.scheduler.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import gaudin.scheduler.model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**This class is abstract and populates the necessary CRUD abilities to query the database for
 Appointment objects.
 */
public abstract class AppointmentsQuery {

    /**This method inserts a new Appointment into the database. It returns the number of rows affected
     in the database.
     @param title the title of the appointment.
     @param description the description of the appointment.
     @param location the location of the appointment.
     @param type the type of appointment.
     @param start the start LocalDateTime for the appointment.
     @param end the end LocalDateTime for the appointment.
     @param customerId the Id for the customer associated with the appointment.
     @param userId the Id for the user associated with the appointment.
     @param contactId the Id for the contact associated with the appointment.
     @return the rowsAffected as an int in the database.
     */
    public static int insert(String title, String description, String location, String type,
                             LocalDateTime start, LocalDateTime end, int customerId, int userId,
                             int contactId) throws SQLException
    {
        String sql = "INSERT INTO APPOINTMENTS " +
                "(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, " +
                "Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**This method updates an Appointment in the database. It checks the supplied Appointment Id and
     updates the corresponding row in the database and returns the number of rows affected.
     @param appointmentId the Id of the appointment to update.
     @param title the title of the appointment.
     @param description the description of the appointment.
     @param location the location of the appointment.
     @param type the type of appointment.
     @param start the start LocalDateTime for the appointment.
     @param end the end LocalDateTime for the appointment.
     @param customerId the Id for the customer associated with the appointment.
     @param userId the Id for the user associated with the appointment.
     @param contactId the Id for the contact associated with the appointment.
     @return the rowsAffected int in the database.
     */
    public static int update(int appointmentId, String title, String description,
                             String location, String type, LocalDateTime start,
                             LocalDateTime end, int customerId, int userId, int contactId)
            throws SQLException
    {
        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, " +
                "Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?," +
                "User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        ps.setInt(10, appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**This method deletes a selected Appointment from the database. It returns the number of
     rows affected in the database.
     @param appointmentId the Id of the appointment to delete.
     @return the rowsAffected int in the database.
     */
    public static int deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**This method deletes all associated Appointments for a Customer from the database.
     It returns the number of rows affected in the database.
     @param customerId the Id of the customer whose appointments you wish to delete.
     @return the rowsAffected int in the database.
     */
    public static int deleteCustAppointments(int customerId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /**This method reads the Appointment table, and contact table and joins them to
     create an Observable list of Appointment objects.
     @return the Appointments Observable List.
     */
    public static ObservableList<Appointment> selectJoin() throws SQLException
    {
        ObservableList<Appointment> Appointments = FXCollections.observableArrayList();
        String sql = "SELECT appointments.Appointment_ID, appointments.Title, " +
                "appointments.Description, appointments.Location, " +
                "appointments.Contact_ID, contacts.Contact_Name, appointments.Type," +
                "appointments.Start, appointments.End, appointments.Customer_ID, " +
                "appointments.User_ID " +
                "FROM client_schedule.appointments INNER JOIN contacts " +
                "ON appointments.Contact_ID=contacts.Contact_ID " +
                "order by appointments.Appointment_ID;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Integer appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String contactName = rs.getString("Contact_Name");
            Integer contactId = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            Integer customerId = rs.getInt("Customer_ID");
            Integer userId = rs.getInt("User_ID");
            Appointment appointment1 = new Appointment(appointmentId, title, description, location,
                    contactName, contactId, type, startTime, endTime, customerId, userId);
            Appointments.add(appointment1);
        }
        return Appointments;
    }

    /**This method reads the Appointment table, and contact table and joins them to
     create a filtered Observable list. The list consists of Appointment objects that
     are between two times and of a specified type.
     @param type the type to filter by.
     @param start the start LocalDateTime for the window.
     @param end the end LocalDateTime for the window.
     @return the Appointments Observable List with Appointments inside the time window.
     */
    public static ObservableList<Appointment> selectMonth
            (String type, LocalDateTime start, LocalDateTime end) throws SQLException
    {
        ObservableList<Appointment> Appointments = FXCollections.observableArrayList();
        String sql = "SELECT appointments.Appointment_ID, appointments.Title, " +
                "appointments.Description, appointments.Location, appointments.Contact_ID, " +
                "contacts.Contact_Name, appointments.Type, appointments.Start, " +
                "appointments.End, appointments.Customer_ID, appointments.User_ID \n" +
                " FROM client_schedule.appointments INNER JOIN contacts \n" +
                " ON appointments.Contact_ID=contacts.Contact_ID \n" +
                " WHERE appointments.Start BETWEEN ? AND ? AND appointments.Type= ? \n" +
                " order by appointments.Appointment_ID;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(3,type);
        ps.setTimestamp(1, Timestamp.valueOf(start));
        ps.setTimestamp(2, Timestamp.valueOf(end));
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Integer appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String contactName = rs.getString("Contact_Name");
            Integer contact = rs.getInt("Contact_ID");
            String type1 = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            Integer customerId = rs.getInt("Customer_ID");
            Integer userId = rs.getInt("User_ID");
            Appointment appointment1 = new Appointment(appointmentId, title, description, location,
                    contactName, contact, type1, startTime, endTime, customerId, userId);
            Appointments.add(appointment1);
        }
        return Appointments;
    }
}
