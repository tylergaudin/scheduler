package gaudin.scheduler.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import gaudin.scheduler.model.Directory;
import gaudin.scheduler.model.First_Level_Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class is abstract and populates the necessary CRUD abilities to query the database for
 First level division objects.
 */
public abstract class DivisionsQuery {

    /**This method reads from the First level division table.
     It creates an Observable list of Customer objects.
     @return the Customers Observable List.
     */
    public static ObservableList<First_Level_Division> selectFirstLevel() throws SQLException
    {
        ObservableList<First_Level_Division> Divisions = FXCollections.observableArrayList();
        String sql = "SELECT first_level_divisions.Division_ID, first_level_divisions.Division, \n" +
                "countries.Country_ID, countries.Country " +
                "FROM client_schedule.first_level_divisions INNER JOIN\n" +
                "countries ON first_level_divisions.Country_ID=countries.Country_ID;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Integer countryId = rs.getInt("countries.Country_ID");
            String countryName = rs.getString("countries.Country");
            Integer divisionId = rs.getInt("first_level_divisions.Division_ID");
            String divisionName = rs.getString("first_level_divisions.Division");
            First_Level_Division division1 = new First_Level_Division (countryId, countryName, divisionId, divisionName);
            Directory.addCountry(division1);
            Divisions.add(division1);
        }
        return Divisions;
    }
}
