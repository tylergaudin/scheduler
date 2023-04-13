package gaudin.scheduler.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import gaudin.scheduler.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class is abstract and populates the necessary CRUD abilities to query the database for
 User objects.
 */
public abstract class UsersQuery {

    /**This method inserts a new User into the database. It returns the number of rows affected
     in the database.
     @param userName the Username of the User.
     @param password the Password of the User.
     @return the rowsAffected as an int in the database.
     */
    public static int insert(String userName, String password) throws SQLException
    {
        String sql = "INSERT INTO USERS (User_Name, Password) VALUES (?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**This method updates a User record in the database. It returns the number of rows affected
     in the database.
     @param userId the User Id of the User.
     @param userName the Username of the User.
     @param password the password of the User.
     @return the rowsAffected as an int in the database.
     */
    public static int update(int userId, String userName, String password) throws SQLException
    {
        String sql = "UPDATE USERS SET User_Name = ?, Password = ? WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        ps.setInt(3, userId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**This method deletes a selected User from the database. It returns the number of
     rows affected in the database.
     @param userId the Id of the User to delete.
     @return the rowsAffected int in the database.
     */
    public static int delete(int userId) throws SQLException {
        String sql = "DELETE FROM USERS WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, userId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**This method queries the User table and returns an observable list of User objects.
     @return users an observable list of User objects.
     */
    public static ObservableList<User> select() throws SQLException
    {
        ObservableList<User> users = FXCollections.observableArrayList();
        String sql = "Select * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            User user1 = new User(userId, userName, password);
            users.add(user1);
        }
        return users;
    }
}
