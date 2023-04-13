package gaudin.scheduler.model;

/**This defines the Contact class.
 */
public class Contact {
    int contactId;
    String contactName;
    String email;

    /**The constructor for Contact objects.
     @param contactId the Id for the Contact.
     @param contactName the name of the Contact.
     @param email the email of the Contact object.
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**Gets the Id for the Contact object.
     @return the contactId.
     */
    public int getContactId() {
        return contactId;
    }

    /**Sets the Id for the Contact object.
     @param  contactId the Contact's Id.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**Gets the name for the Contact object.
     @return the contactName.
     */
    public String getContactName() {
        return contactName;
    }

    /**Sets the name for the Contact object.
     @param  contactName the Contact's name.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**Gets the email for the Contact object.
     @return the email.
     */
    public String getEmail() {
        return email;
    }

    /**Sets the email for the Contact object.
     @param  email the Contact's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**Overrides the toString function to only return the contactName attribute as a String.
     @return String of the contactName
     */
    @Override
    public String toString()
    {
        return contactName;
    }
}
