/**
 * @author Mario Macias, Kevin Aofia, Angel Ramos
 * @version PA4
 * @since April 6th, 2021
 *
 * Customer is a class that inherits from abstract class Person and builds off of the parent class
 * Customer also implements our security interface
 */
public class Customer extends Person implements Security{

    /**
     * Customer attributes
     */
    private String checkingNumber;
    private String savingNumber;
    private String creditNumber;

    /**
     * Customer default constructor
     */
    public Customer(){}

    /**
     *
     * @param firstName firstName
     * @param lastName lastName
     * @param DOB DOB
     * @param address address
     * @param phone phone
     * @param idNo idNo
     * @param checkingNumber checkingNumber
     * @param savingNumber savingNumber
     * @param creditNumber creditNumber
     * @param password password
     * @param email email
     *
     * Customer constructor specifying parent and customer attributes
     */
    public Customer(String firstName, String lastName, String DOB, String address, String phone, String idNo, String checkingNumber, String savingNumber, String creditNumber, String password, String email) {
        super(firstName, lastName, DOB, address, phone, idNo, email,password);
        this.checkingNumber = checkingNumber;
        this.savingNumber = savingNumber;
        this.creditNumber = creditNumber;
    }

    /**
     *
     * @param checkingNumber checkingNumber
     * @param savingNumber savingNumber
     * @param creditNumber creditNumber
     *
     * Customer constructor specifying customer attributes
     */
    public Customer(String checkingNumber, String savingNumber, String creditNumber) {
        this.checkingNumber = checkingNumber;
        this.savingNumber = savingNumber;
        this.creditNumber = creditNumber;
    }

    /**
     *
     * @return return
     */
    //getters and setter exempt from JavaDoc
    public String getCheckingNumber() {
        return checkingNumber;
    }

    /**
     *
     * @param checkingNumber checkingnumber
     */
    public void setCheckingNumber(String checkingNumber) {
        this.checkingNumber = checkingNumber;
    }

    /**
     *
     * @return return
     */
    public String getSavingNumber() {return savingNumber;}

    /**
     *
     * @param savingNumber savingNumber
     */
    public void setSavingNumber(String savingNumber) {
        this.savingNumber = savingNumber;
    }

    /**
     *
     * @return return
     */
    public String getCreditNumber() {
        return creditNumber;
    }

    /**
     *
     * @param creditNumber creditnumber
     */
    public void setCreditNumber(String creditNumber) {
        this.creditNumber = creditNumber;
    }

    /**
     *
     * @param attempt attempt
     * @return boolean
     *
     * method build off of security interface
     */
    @Override
    public boolean employeeVerification(String attempt) {
        return false;
    }

    /**
     *
     * @param attempt attempt
     * @return boolean
     *
     * method build off of security interface
     */
    @Override
    public boolean customerVerification(String attempt) {
        if (this.getPassword().equals(attempt)) return true;
        System.out.println("Invalid Password try again.");
        return false;
    }
}
