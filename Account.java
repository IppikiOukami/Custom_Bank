/**
 * @author Mario Macias, Kevin Aofia, Angel Ramos
 * @version PA4
 * @since April 6th, 2021
 *
 * Account is our abstract class that will provide our checking, savings, and credit accounts with basic functionalities
 * Account will also implement our printable interface
 */
public abstract class Account implements Printable{

    private String idNumber;
    private Checking checkAcc;
    private Savings saveAcc;
    private Credit credAcc;
    private double sCheck;
    private double sSave;
    private double sCred;

    /**
     * Default Constructor
     */
    public Account() {
    }

    /**
     * Fills account with Savings Object and Credit Object
     * @param IDIn Id number
     * @param checkIn Checking Object
     * @param saveIn Savings Object
     * @param credIn Credit Object
     */
    public Account(String IDIn, Checking checkIn, Savings saveIn, Credit credIn) {
        this.idNumber = IDIn;
        this.checkAcc = checkIn;
        this.saveAcc = saveIn;
        this.credAcc = credIn;
    }

    /**
     *
     * @param IDIn IDIn
     * @param saveIn saveIn
     * @param credIn credIn
     */
    public Account(String IDIn, Savings saveIn, Credit credIn) {
        this.idNumber = IDIn;
        this.saveAcc = saveIn;
        this.credAcc = credIn;
    }

    /**
     *
     * @param IDIn IDIn
     * @param checkIn checkIn
     * @param credIn credIn
     */
    public Account(String IDIn, Checking checkIn, Credit credIn) {
        this.idNumber = IDIn;
        this.checkAcc = checkIn;
        this.credAcc = credIn;
    }

    /**
     *
     * @param IDIn Id
     * @param checkIn checkin
     * @param saveIn savein
     */
    public Account(String IDIn, Checking checkIn, Savings saveIn) {
        this.idNumber = IDIn;
        this.checkAcc = checkIn;
        this.saveAcc = saveIn;
    }

    /**
     *
     * @return return
     */
    public String getIDNumber() {
        return idNumber;
    }

    /**
     *
     * @param idNo id
     */
    public void setIDNumber(String idNo) {
        this.idNumber = idNo;
    }

    /**
     *
     * @return return
     */
    public Checking getCheckAcc() {
        return checkAcc;
    }

    /**
     *
     * @param checkAcc checkacc
     */
    public void setCheckAcc(Checking checkAcc) {
        this.checkAcc = checkAcc;
    }

    /**
     *
     * @return return
     */
    public Savings getSaveAcc() {
        return saveAcc;
    }

    /**
     *
     * @param saveAcc saveacc
     */
    public void setSaveAcc(Savings saveAcc) {
        this.saveAcc = saveAcc;
    }

    /**
     *
     * @return ggetcredacc
     */
    public Credit getCredAcc() {
        return credAcc;
    }

    /**
     *
     * @param credAcc credacc
     */
    public void setCredAcc(Credit credAcc) {
        this.credAcc = credAcc;
    }

    /**
     *
     * @return getscheck
     */
    public double getsCheck() {
        return sCheck;
    }

    /**
     *
     * @param sCheck check
     */
    public void setsCheck(double sCheck) {
        this.sCheck = sCheck;
    }

    /**
     *
     * @return ssave
     */
    public double getsSave() {
        return sSave;
    }

    /**
     *
     * @param sSave ssave
     */
    public void setsSave(double sSave) {
        this.sSave = sSave;
    }

    /**
     *
     * @return return
     */
    public double getsCred() {
        return sCred;
    }

    /**
     *
     * @param sCred scred
     */
    public void setsCred(double sCred) {
        this.sCred = sCred;
    }

    // Template Design Methods
    /**
     * Checks balance of account
     * @return account balance
     */
    public double inquire(){
        return 0.00;
    }

    /**
     * Pays from calling account to payee
     * @param payee person to pay
     * @param sAmount amount of payment
     * @return boolean of status
     */
    public boolean pay(Checking payee, String sAmount){return false;}

    /**
     * transfers money between accounts
     * @param accNo destination account
     * @param sAmount amount to transfer
     * @return boolean of status
     */
    public boolean transfer(String accNo, String sAmount){return false;}

    /**
     * withdraws given amount from calling account
     * @param sAmount amount to withdraw
     * @return boolean of status
     */
    public boolean withdraw(String sAmount){return false;}

    /**
     * Deposits external amount into calling account
     * @param sAmount amount to deposit
     * @param to if depositing to different account from caller
     * @return boolean of status
     */
    public boolean deposit(String sAmount, Checking to){return true;}

    /**
     * Formats data for csv export
     * @return string of formatted data
     */
    public String toCSV(){
        return (this.getCheckAcc().getAccount()+","+this.getCheckAcc().getBalance()+","+this.getSaveAcc().getAccount()+","+this.getSaveAcc().getBalance()+","+this.getCredAcc().getAccount()+","+this.getCredAcc().getBalance()+","+ this.getCredAcc().getCreditMax() * -1);
    }

    /**
     * Formats account data to display on manager menu
     * @return string
     */
    @Override
    public String toString() {
        return "Accounts{" +
                "Checking Number='" + this.getCheckAcc().getAccount() + '\'' +
                ", Balance='" + this.getCheckAcc().getBalance() + '\'' +
                ", Savings Number='" + this.getSaveAcc().getAccount() + '\'' +
                ", Balance='" + this.getSaveAcc().getBalance() + '\'' +
                ", Credit Number='" + this.getCredAcc().getAccount() + '\'' +
                ", Balance='" + this.getCredAcc().getBalance() + '\'' +
                ", Credit limit='"+ this.getCredAcc().getCreditMax() + '\'' +
                '}';
    }
}

