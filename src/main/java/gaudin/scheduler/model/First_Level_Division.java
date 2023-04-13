package gaudin.scheduler.model;

/**This defines the First_Level_Division class. First_Level_Division inherits the Country class.
 */
public class First_Level_Division extends Country {

    private int firstLevelId;
    private String divisionName;

    /**The constructor for First level division objects.
     @param countryId the Id for the associated Country.
     @param countryName the name of the associated Country.
     @param firstLevelId the Id of the First level division.
     @param divisionName the name of the First level division.
     */
    public First_Level_Division(int countryId, String countryName,
                                int firstLevelId, String divisionName) {
        super(countryId, countryName);
        this.firstLevelId = firstLevelId;
        this.divisionName = divisionName;
    }

    /**Gets the Id for the First level division object.
     @return the firstLevelId.
     */
    public int getFirstLevelId() {
        return firstLevelId;
    }

    /**Sets the Id for the First level division object.
     @param  firstLevelId the First level division's Id.
     */
    public void setFirstLevelId(int firstLevelId) {
        this.firstLevelId = firstLevelId;
    }

    /**Gets the name for the First level division object.
     @return the divisionName.
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**Sets the name for the First level division object.
     @param  divisionName the First level division's name.
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**Overrides the toString function to only return the divisionName attribute as a String.
     @return String of the divisionName
     */
    @Override
    public String toString()
    {
        return divisionName;
    }
}
