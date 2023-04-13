package gaudin.scheduler.model;

import java.time.LocalDateTime;

/**This defines the Appointment class.
 */
public class Appointment {

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String contactName;
    private int contactId;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int customerId;
    private int userId;

    /**The constructor for Appointment objects.
     @param appointmentId the Appointment's Id.
     @param title the title of the Appointment.
     @param description the description of the Appointment.
     @param location the location of the Appointment.
     @param contactName the name of the Contact associated with the Appointment.
     @param contactId the Id for the Contact associated with the Appointment.
     @param type the type of Appointment.
     @param startTime the start LocalDateTime for the Appointment.
     @param endTime the end LocalDateTime for the Appointment.
     @param customerId the Id for the customer associated with the Appointment.
     @param userId the Id for the user associated with the Appointment.
     */
    public Appointment(int appointmentId, String title, String description,
                       String location, String contactName, int contactId,
                       String type, LocalDateTime startTime, LocalDateTime endTime,
                       int customerId, int userId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactName = contactName;
        this.contactId = contactId;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerId = customerId;
        this.userId = userId;
    }

    /**Gets the Id for the Appointment object.
     @return the appointmentId.
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**Sets the Id for the Appointment object.
     @param  appointmentId the Appointment's Id.
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**Gets the Title for the Appointment object.
     @return the title of the Appointment.
     */
    public String getTitle() {
        return title;
    }

    /**Sets the title of the Appointment object.
     @param title the title of the Appointment.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**Gets the description for the Appointment object.
     @return the description of the Appointment.
     */
    public String getDescription() {
        return description;
    }

    /**Sets the description of the Appointment object.
     @param description the description of the Appointment.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**Gets the Location for the Appointment object.
     @return the location of the Appointment.
     */
    public String getLocation() {
        return location;
    }

    /**Sets the location of the Appointment object.
     @param location the location for the Appointment.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**Gets the associated Contact name for the Appointment object.
     @return the contactName.
     */
    public String getContactName() {
        return contactName;
    }

    /**Sets the associated Contact name of the Appointment object.
     @param contactName the associated Contact name for the Appointment.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**Gets the associated Contact Id for the Appointment object.
     @return the contactId.
     */
    public int getContactId() {
        return contactId;
    }

    /**Sets the associated Contact Id of the Appointment object.
     @param contactId the associated contactId for the Appointment.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**Gets the type for the Appointment object.
     @return the type of Appointment.
     */
    public String getType() {
        return type;
    }

    /**Sets the type of Appointment object.
     @param type the type of Appointment.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**Gets the start time for the Appointment object.
     @return the LocalDateTime startTime.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**Sets the start time for the Appointment object.
     @param startTime the LocalDateTime startTime.
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**Gets the end time for the Appointment object.
     @return the LocalDateTime endTime.
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**Sets the end time for the Appointment object.
     @param endTime the LocalDateTime endTime.
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**Gets the associated Customer Id for the Appointment object.
     @return the customerId.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**Sets the associated Customer Id for the Appointment object.
     @param customerId the associated customerId.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**Gets the associated User Id for the Appointment object.
     @return the userId.
     */
    public int getUserId() {
        return userId;
    }

    /**Sets the associated User Id for the Appointment object.
     @param userId the associated userId.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
