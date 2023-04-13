package gaudin.scheduler.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import gaudin.scheduler.model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class populates the necessary CRUD abilities to query the database for
 appointment objects.
 */
public class ContactsQuery {
    /**This method queries the contact table and returns an observable list of contact objects.
     @return contacts an observable list of contact objects.
     */
    public static ObservableList<Contact> select() throws SQLException
    {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        String sql = "Select * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            Contact contact1 = new Contact(contactId, contactName, email);
            contacts.add(contact1);
        }
        return contacts;
    }
}
