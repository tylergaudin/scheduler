package gaudin.scheduler.model;

/**This defines the Country class.
 */
public class Country {

    private int countryId;
    private String countryName;


    /**The constructor for Country objects.
     @param countryId the Id for the Country.
     @param countryName the name of the Country.
     */
     public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**Gets the Id for the Country object.
     @return the contactId.
     */
    public int getCountryId() {
        return countryId;
    }

    /**Sets the Id for the Country object.
     @param  countryId the Country's Id.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**Gets the name for the Country object.
     @return the contactName.
     */
    public String getCountryName() {
        return countryName;
    }

    /**Sets the name for the Country object.
     @param  countryName the Country's name.
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
