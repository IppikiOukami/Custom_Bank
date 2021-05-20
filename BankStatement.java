import java.sql.Timestamp;
import java.util.HashMap;

import static java.lang.System.currentTimeMillis;

/**
 * @author Mario Macias, Kevin Aofia, Angel Ramos
 * @version PA4
 * @since April 6th, 2021
 *
 * BankStatement is a class that handles the functionalities of how we create bank statements for users
 */
public class BankStatement {

    /**
     * BankStatement attributes
     */
    private Checking accounts;
    private Customer user;

    /**
     * BankStatement default constructor
     */
    public BankStatement(){}

    /**
     *
     * @param account account
     * @param user user
     *
     * BankStatement constructor specifying BankStatement attributes
     */
    public BankStatement(Checking account, Customer user){
        this.accounts = account;
        this.user = user;
    }

    /**
     *
     * @return return
     */
    //getters and setters exempt from JavaDoc
    public Checking getAccounts() {
        return accounts;
    }

    /**
     *
     * @param accounts accounts
     */
    public void setAccounts(Checking accounts) {
        this.accounts = accounts;
    }

    /**
     *
     * @return return
     */
    public Customer getUser() {
        return user;
    }

    /**
     *
     * @param user user
     */
    public void setUser(Customer user) {
        this.user = user;
    }

    /**
     *
     * @param idNo idNo
     * @param clients clients
     * @param accounts accounts
     * @param log log
     * @return string
     *
     * This method builds a statement for a user and saves it as a txt file
     */
    public static String makeStatement(String idNo, HashMap clients, HashMap accounts, HashMap log){
        Checking account = (Checking) accounts.get(idNo);
        Customer user = (Customer) clients.get(idNo);
        String actions;
        if(log.get(idNo)==null){
            actions = "No actions this session";
        }
        else{
            actions = (String) log.get(idNo);
        }
        return "Customer Information:\t\t\t\t\t" +new Timestamp(currentTimeMillis()) +"\n"+
                "Name: "+ user.getFirstName()+" "+user.getLastName()+"\n"+
                "Date of Birth: "+user.getDOB()+"\n"+
                "Address: "+user.getAddress()+"\n"+
                "Phone: "+user.getPhone()+"\n"+
                "Email: "+user.getEmail()+"\n"+
                "ID Number: "+user.getIdNo()+"\n\n"+
                "Checking Starting Balance: "+account.getsCheck()+" End Balance: "+account.getBalance()+"\n"+
                "Savings Starting Balance: "+account.getsSave()+" End Balance: "+account.getSaveAcc().getBalance()+"\n"+
                "Credit Starting Balance: "+account.getsCred()+" End Balance: "+account.getCredAcc().getBalance()+"\n\n"+
                "Transactions/Actions: \n"+
                actions+"\n\n\n"+"End of Statement";
    }
}
