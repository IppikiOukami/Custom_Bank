/**
 * @author Mario Macias, Kevin Aofia, Angel Ramos
 * @version PA4
 * @since April 6th, 2021
 *
 * Printable is our interface for our printing capabilities
 */
public interface Printable {

    /**
     *
     * @return string
     *
     * shell  for our csv to string
     */
    public String toCSV();

    /**
     *
     * @return string
     *
     * shell for our string to generate a sentence
     */
    public String toString();
}
