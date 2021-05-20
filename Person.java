/**
 * @author Mario Macias, Kevin Aofia, Angel Ramos
 * @version PA4
 * @since April 6th, 2021
 *
 * Person is our abstract class that will provide our customer, and administrator with the basic functionalities
 * Person will also implement our printable interface
 */
public abstract class Person implements Printable {

    /**
     * Person attributes
     */
    private String firstName;
    private String lastName;
    private String DOB;
    private String address;
    private String phone;
    private String idNo;
    private String email;
    private String password;

    /**
     * Person default constructor
     */
    public Person(){}

    /**
     *
     * @param firstName firstName
     * @param lastName lastName
     * @param DOB DOB
     * @param address address
     * @param phone phone
     * @param idNo idNo
     * @param email email
     * @param password password
     *
     * Person constructor specifying parent and person attributes
     */
    public Person(String firstName, String lastName, String DOB, String address, String phone, String idNo,String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.address = address;
        this.phone = phone;
        this.idNo = idNo;
        this.email = email;
        this.password = password;
    }

    //getter and setter exempt from JavaDoc
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * View of Customer contents
     * @return string of data with customer contents
     */
    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", DOB='" + DOB + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", idNo='" + idNo + '\'' +
                ", email='"+ email + '\'' +
                '}';
    }

    /**
     * Creates a string of line data about the person
     * @return string of personal data formatted for csv
     */
    @Override
    public String toCSV(){
        return (this.getFirstName()+","+this.getLastName()+","+this.getDOB()+","+this.getAddress()+","+this.getPhone()+","+this.getEmail()+","+this.getPassword()+","+this.getIdNo()+",");
    }
}
