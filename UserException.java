/**
 * @author Mario Macias, Kevin Aofia, Angel Ramos
 * @version PA4
 * @since April 6th, 2021
 *
 * userException inherits from Exception and is our custom exception for PA4
 */
public class UserException extends Exception{

    /**
     * UserException attribute
     */
    private final String errorMessage; //String will be custom message set by bank

    /**
     *
     * @param errorMessageIn errorMessageIn
     *
     * This method will receive a string and set the attribute errorMessage to the given string
     */
    public UserException(String errorMessageIn) { //receive custom error message from "throw new" clause
        this.errorMessage = errorMessageIn;
    }

    /**
     *
     * @return string
     *
     * This method will return our string value of errorMessage
     */
    public String toString() {
        return this.errorMessage; //message that will be returned to catch block
    }

}
