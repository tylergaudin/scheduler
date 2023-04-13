package gaudin.scheduler.model;

/**This defines the Customer class. Customer inherits the First_Level_Division class.
 */
public class Customer extends First_Level_Division{

    private int customerId;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;

    /**The constructor for Customer objects.
     @param countryId the Id for the Country associated with the Customer.
     @param countryName the name of the Country associated with the Customer.
     @param firstLevelId the Id for the First level division associated with the Customer.
     @param divisionName the name for the First level division associated with the Customer.
     @param customerId the Id for the customer.
     @param name the name of the Customer.
     @param address the address of the Customer.
     @param postalCode the postal code of the Customer.
     @param phoneNumber the phoneNumber of the Customer.
     */
    public Customer(int countryId, String countryName, int firstLevelId, String divisionName,
                    int customerId, String name, String address, String postalCode, String phoneNumber)
    {
        super(countryId, countryName, firstLevelId, divisionName);
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }

    /**Gets the Id for the Customer object.
     @return the customerId.
     */
    public int getCustomerId() { return customerId; }

    /**Sets the Id for the Customer object.
     @param  customerId the Customer's Id.
     */
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    /**Gets the name for the Customer object.
     @return the name.
     */
    public String getName() {
        return name;
    }

    /**Sets the name for the Customer object.
     @param  name the Customer's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**Gets the address for the Customer object.
     @return the address.
     */
    public String getAddress() {
        return address;
    }

    /**Sets the address for the Customer object.
     @param  address the Customer's address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**Gets the postal code for the Customer object.
     @return the postalCode.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**Sets the postal code for the Customer object.
     @param  postalCode the Customer's postal code.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**Gets the phone number for the Customer object.
     @return the phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**Sets the phone number for the Customer object.
     @param  phoneNumber the Customer's phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
