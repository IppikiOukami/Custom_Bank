/**
 * @author Mario Macias, Kevin Aofia, Angel Ramos
 * @version PA4
 * @since April 6th, 2021
 *
 * Security is our interface for verification statutes
 */
public interface Security {

    /**
     *
     * @param attempt attempt
     * @return boolean
     *
     * shell for employee verification
     */
    public boolean employeeVerification(String attempt);

    /**
     *
     * @param attempt attempt
     * @return boolean
     *
     * shell for customer verification
     */
    public boolean customerVerification(String attempt);
}
