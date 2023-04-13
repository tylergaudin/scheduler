package gaudin.scheduler.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import gaudin.scheduler.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class is abstract and populates the necessary CRUD abilities to query the database for
 Customer objects.
 */
public abstract class CustomersQuery {

    /**This method inserts a new Customer into the database. It returns the number of rows affected
     in the database.
     @param name the name of the Customer.
     @param address the address of the Customer.
     @param postalCode the customer's postal code.
     @param phone the phone number of the Customer.
     @param divisionId the Id that represents the First level division associated with the Customer.
     @return the rowsAffected as an int in the database.
     */
    public static int insert(String name, String address, String postalCode, String phone,
                             int divisionId) throws SQLException
    {
        String sql = "INSERT INTO CUSTOMERS " +
                "(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**This method updates a Customer record in the database. It returns the number of rows affected
    in the database.
     @param customerId the customer's Id number.
     @param name the name of the Customer.
     @param address the address of the Customer.
     @param postalCode the customer's postal code.
     @param phone the phone number of the Customer.
     @param divisionId the Id that represents the First level division associated with the Customer.
     @return the rowsAffected as an int in the database.
     */
    public static int update(int customerId, String name, String address, String postalCode,
                             String phone, int divisionId) throws SQLException
    {
        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, " +
                "Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);
        ps.setInt(6, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**This method deletes a selected Customer from the database. It returns the number of
     rows affected in the database.
     @param customerId the Id of the Customer to delete.
     @return the rowsAffected int in the database.
     */
    public static int delete(int customerId) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**This method reads from the Customer, First leve division, and Country tables.
     It joins them to create an Observable list of Customer objects.
     @return the Customers Observable List.
     */
    public static ObservableList<Customer> selectJoin() throws SQLException
    {
        ObservableList<Customer> Customers = FXCollections.observableArrayList();
        String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, " +
                "customers.Postal_Code, \n customers.Phone, first_level_divisions.Division_ID, " +
                "first_level_divisions.Division, \n countries.Country_ID, countries.Country " +
                "FROM client_schedule.customers INNER JOIN \n first_level_divisions ON " +
                "customers.Division_ID=first_level_divisions.Division_ID INNER JOIN\n" +
                "countries ON first_level_divisions.Country_ID=countries.Country_ID " +
                "order by customers.Customer_ID;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Integer customerId = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            Integer country_Id = rs.getInt("Country_ID");
            String country = rs.getString("Country");
            Integer firstLevel_Id = rs.getInt("Division_ID");
            String firstLevel = rs.getString("Division");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            Customer customer1 = new Customer(country_Id, country, firstLevel_Id, firstLevel,
                    customerId, name, address, postalCode, phone);
            Customers.add(customer1);
        }
        return Customers;
    }
}
