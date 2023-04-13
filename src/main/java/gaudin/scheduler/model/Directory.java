package gaudin.scheduler.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This defines the Directory class. This houses Observable lists that organize the objects.
 */
public class Directory {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    private static ObservableList<First_Level_Division> allFirstLevels = FXCollections.observableArrayList();
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    /**This method searches the list of all countries by country Id.
     @param searchId the country Id to search for.
     */
    public static Country searchCountry(int searchId)
    {
        for(Country country: allCountries)
        {
            if(country.getCountryId() == searchId)
            {
                return country;
            }
        }
        return null;
    }

    /**This method checks to see if allCountries is empty, then adds the country to allCountries
     then checks to see if the country is already in the list and adds it if it is not.
     @param newCountry the country to search for.
     */
    public static void addCountry(Country newCountry)
    {

        if(allCountries.size() == 0)
        {
            allCountries.add(newCountry);
        }
        else if (searchCountry(newCountry.getCountryId()) == null)
        {
            allCountries.add(newCountry);
        }
    }

    /**This method searches the list of all First level divisions by First Level Id.
     @param searchId the First level Id to search for.
     */
    public static First_Level_Division searchFirstLevel(int searchId)
    {
        for(First_Level_Division firstLevel: allFirstLevels)
        {
            if(firstLevel.getFirstLevelId() == searchId)
            {
                return firstLevel;
            }
        }
        return null;
    }

    /**This method checks to see if allCustomers is empty, then adds the customer to allCustomers
     then checks to see if the customer is already in the list and adds it if it is not.
     @param newCustomer the customer to search for.
     */
    public static void addCustomer(Customer newCustomer)
    {

        if(allCustomers.size() == 0)
        {
            allCustomers.add(newCustomer);
        }
        else if (searchCustomer(newCustomer.getCustomerId()) == null)
        {
            allCustomers.add(newCustomer);
        }
    }

    /**This method searches the list of all Customers by Customer Id.
     @param searchId the Customer Id to search for.
     */
    public static Customer searchCustomer(int searchId)
    {
        for(Customer customer: allCustomers)
        {
            if(customer.getCustomerId() == searchId)
            {
                return customer;
            }
        }
        return null;
    }

    /**This method searches the list of all Users by User Id.
     @param searchId the User Id to search for.
     */
    public static User searchUser(int searchId)
    {
        for(User user: allUsers)
        {
            if(user.getUserId()== searchId)
            {
                return user;
            }
        }
        return null;
    }

    /**This method searches the list of all Contacts by Contact Id.
     @param searchId the Contact Id to search for.
     */
    public static Contact searchContact(String searchId)
    {
        for(Contact contact: allContacts)
        {
            if(contact.getContactName().contains(searchId))
            {
                return contact;
            }
        }
        return null;
    }

    /**Gets the observable list of all Customer objects.
     @return allCustomers.
     */
    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    /**Gets the observable list of all Appointment objects.
     @return allAppointments.
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    /**Gets the observable list of all User objects.
     @return allUsers.
     */
    public static ObservableList<User> getAllUsers() {
        return allUsers;
    }

    /**Gets the observable list of all Contact objects.
     @return allContacts.
     */
    public static ObservableList<Contact> getAllContacts() { return allContacts; }

    /**Gets the observable list of all First level division objects.
     @return allFirstLevels.
     */
    public static ObservableList<First_Level_Division> getAllFirstLevels() {
        return allFirstLevels;
    }

    /**Gets the observable list of all Country objects.
     @return allCountries.
     */
    public static ObservableList<Country> getAllCountries() { return allCountries; }
}
