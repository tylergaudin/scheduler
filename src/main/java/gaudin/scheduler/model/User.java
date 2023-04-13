package gaudin.scheduler.model;

/**This defines the First_Level_Division class.
 */
public class User {

    private int userId;
    private String userName;
    private String password;

    /**The constructor for User objects.
     @param userId the Id for the User.
     @param userName the User's username.
     @param password the User's password.
     */
    public User(int userId, String userName, String password){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**Gets the Id for the User object.
     @return the userId.
     */
    public int getUserId() {
        return userId;
    }

    /**Sets the Id for the User object.
     @param  userId the User's Id.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**Gets the username for the User object.
     @return the userName.
     */
    public String getUserName() {
        return userName;
    }

    /**Sets the username for the User object.
     @param  userName the User's username.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**Gets the password for the User object.
     @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**Sets the password for the User object.
     @param  password the User's password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**Overrides the toString function to only return the User Id and username attribute as a String.
     @return String of the userId and userName.
     */
    @Override
    public String toString()
    {
        return userId + " " + userName;
    }
}
