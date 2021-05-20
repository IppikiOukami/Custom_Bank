/**
 * @author Mario Macias, Kevin Aofia, Angel Ramos
 * @version PA4
 * @since April 6th, 2021
 *
 * Checking is a class that inherits from abstract class Account and builds off of the parent class
 */
public class Checking extends Account{

    /**
     * Checking attriubutes
     */
    private String account;
    private double balance;

    /**
     * Checking default constructor
     */
    public Checking(){}

    /**
     *
     * @param idNo idNo
     * @param checkNo checkNo
     * @param saveNo saveNo
     * @param credNo credNo
     * @param checkBal checkBal
     * @param saveBal saveBal
     * @param credBal credBal
     * @param max max
     *
     * Checking constructor specifying attributes from parent class and Checking class
     */
    public Checking(String idNo, String checkNo, String saveNo, String credNo, double checkBal, double saveBal, double credBal, double max) {
        super(idNo, new Savings(saveNo, saveBal), new Credit(credNo, credBal, max));
        this.account = checkNo;
        this.balance = checkBal;
        setCheckAcc(this);
    }

    /**
     *
     * @param checkNo checkNo
     * @param checkBal checkBal
     *
     * Checking constructor specifying attributes from only Checking class
     */
    public Checking(String checkNo, double checkBal){
        this.account = checkNo;
        this.balance = checkBal;
    }

    /**
     *
     * @return return
     */
    //getters and setter exempt from JavaDoc
    public String getAccount() {
        return account;
    }

    /**
     *
     * @param account account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     *
     * @return getbalance
     */
    public double getBalance() {
        return balance;
    }

    /**
     *
     * @param balance balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     *
     * @return double
     *
     * This method overrides parent class following template design pattern
     */
    public double inquire() {
        RunBank.logger("Inquired Checking");
        if(this.account != null){
            return this.balance;
        }
        return 0.00;
    }

    /**
     *
     * @param payee person to pay
     * @param sAmount amount of payment
     * @return boolean
     *
     * this method overrides parent method and follows template design pattern
     */
    public boolean pay(Checking payee, String sAmount){
        try{
            double amount = Double.parseDouble(sAmount);
            if(amount < 0.00){
                System.out.println("Enter an amount greater than 0.00");
                return false;
            }
            else if(amount > this.getBalance()){
                System.out.println("Insufficient funds");
                return false;
            }
            this.balance -= amount;
            payee.setBalance(payee.getBalance() + amount);
            RunBank.logger("payed "+payee.getIDNumber()+" $"+sAmount);
        }catch(NumberFormatException e){
            System.out.println();
            System.out.println("Please use only digits and a single decimal\nto input amount");
            return false;
        }
        return true;
    }

    /**
     *
     * @param accNo destination account
     * @param sAmount amount to transfer
     * @return boolean
     *
     * this method overrides parent method and follows template design pattern
     */
    public boolean transfer(String accNo, String sAmount){
        Savings saveAcc = null;
        Credit credAcc = null;
        if(Character.compare(accNo.charAt(0),'2' ) == 0){
            saveAcc = this.getSaveAcc();
        }
        else if(Character.compare(accNo.charAt(0),'3') == 0){
            credAcc = this.getCredAcc();
        }
        else{
            System.out.println("Cannot transfer to same account\nVerify transfer details");
            return false;
        }
        try{
            double amount = Double.parseDouble(sAmount);
            if(amount < 0.00 || amount > this.getBalance()){
                System.out.println("Enter an amount greater than 0.00 but less than current balance");
                return false;
            }
            this.balance -= amount;
            if(amount > this.getBalance()+amount){
                System.out.println("Insufficient Funds");
                return false;
            }
            else if(saveAcc != null){
                saveAcc.setBalance(saveAcc.getBalance()+amount);
                RunBank.logger("Transferred $"+sAmount+" to Savings From Checking");
            }
            else if(credAcc != null){
                if(credAcc.getBalance()+amount > 0.00){
                    System.out.println("Cannot transfer greater than credit amount");
                    return false;
                }
                else{
                    credAcc.setBalance(credAcc.getBalance()+amount);
                    RunBank.logger("Transferred $"+sAmount+" to Credit From Checking");
                }
            }
        }catch(NumberFormatException e){
            System.out.println();
            System.out.println("Please use only digits and a single decimal\nto input amount");
            return false;
        }
        return true;
    }

    /**
     *
     * @param sAmount amount to withdraw
     * @return boolean
     *
     * this method overrides parent method and follows template design pattern
     */
    public boolean withdraw(String sAmount){
        try {
            double amount = Double.parseDouble(sAmount);
            if(amount <= 0.00){
                System.out.println("Enter an amount greater than 0.00");
                return false;
            }
            if(amount > this.getBalance()){
                System.out.println("Insufficient funds");
                return false;
            }
            this.setBalance(this.getBalance()-amount);
            RunBank.logger("Withdrew $"+sAmount+" From Checking");
        } catch (NumberFormatException e) {
            System.out.println();
            System.out.println("Please use only digits and a single decimal\nto input amount");
        }
        return true;
    }

    /**
     *
     * @param sAmount amount to deposit
     * @param to if depositing to different account from caller
     * @return boolean
     *
     * this method overrides parent method and follows template design pattern
     */
    public boolean deposit(String sAmount, Checking to){
        try{
            double amount = Double.parseDouble(sAmount);
            if(amount <= 0.00){
                System.out.println("Enter an amount greater than 0.00");
                return false;
            }
            if(to != null){
                to.setBalance(to.getBalance()+amount);
                RunBank.logger("Deposited $"+sAmount+" to "+to.getIDNumber());
                return true;
            }
            this.setBalance(this.getBalance()+amount);
            RunBank.logger("Deposited $"+sAmount+" to Checking");
        }catch(NumberFormatException e){
            System.out.println("Please use only digits and a single decimal\nto input amount");
            return false;
        }
        return true;
    }

    /**
     *
     * @return string
     *
     * this method overrides parent method and follows template design pattern
     */
    @Override
    public String toString() {
        return "Checking{" +
                "account='" + account + '\'' +
                ", balance=" + balance +
                '}';
    }

}
