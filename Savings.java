/**
 * @author Mario Macias, Kevin Aofia, Angel Ramos
 * @version PA4
 * @since April 6th, 2021
 *
 * Savings is a class that inherits from abstract class Account
 */
public class Savings extends Account{

    /**
     * Savings attributes
     */
    private String account;
    private double balance;
    private boolean accessToSavings = true; //for new accounts only

    /**
     * Savings default constructor
     */
    public Savings(){}

    /**
     *
     * @param idNo id
     * @param checkNo checking number
     * @param saveNo savings number
     * @param credNo credit number
     * @param checkBal checking balance
     * @param saveBal savings balance
     * @param credBal credit balance
     * @param max credit max balance
     *
     * Savings constructor specifying attributes from parent and savings class
     */
    public Savings(String idNo, String checkNo, String saveNo, String credNo, double checkBal, double saveBal, double credBal, double max){
        super(idNo, new Checking(checkNo,checkBal), new Credit(credNo, credBal, max));
        this.account = saveNo;
        this.balance = saveBal;
        setSaveAcc(this);
    }

    /**
     *
     * @param saveNo savings number
     * @param saveBal savings balance
     *
     * Savings constructor specifying attributes from parent and savings class
     */
    public Savings(String saveNo, double saveBal){
        this.account = saveNo;
        this.balance = saveBal;
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
     * @return return
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
     * this method overrides parent method and follows template design pattern
     */
    public double inquire(){
        return getBalance();
    }

    /**
     *
     * @param payee person to pay
     * @param sAmount amount of payment
     * @return boolean true or false
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
        }catch(NumberFormatException e){
            System.out.println();
            System.out.println("Please use only digits and a single decimal\nto input amount");
            return false;
        }
        return true;
    }

    /**
     *
     * @param user passing object to avoid null
     * @param accNo destination account
     * @param sAmount boolean true or false
     * @return this method overrides parent method and follows template design pattern
     */
    public boolean transfer(Checking user, String accNo, String sAmount){
        Checking checkAcc = null;
        Credit credAcc = null;
        if(Character.compare(accNo.charAt(0),'1') == 0){
            checkAcc = user.getCheckAcc();
        }
        else if(Character.compare(accNo.charAt(0),'3') == 0){
            credAcc = user.getCredAcc();
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
            else if(checkAcc != null){
                checkAcc.setBalance(checkAcc.getBalance()+amount);
            }
            else if(credAcc != null){
                if(credAcc.getBalance()+amount > 0.00){
                    System.out.println("Cannot transfer greater than credit amount");
                    return false;
                }
                else{
                    credAcc.setBalance(credAcc.getBalance()+amount);
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
     * @return boolean true or false
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
     * @return boolean true or false
     *
     * this method overrides parent method and follows template design pattern
     */
    public boolean deposit(String sAmount,Checking to){
        try{
            double amount = Double.parseDouble(sAmount);
            if(amount <= 0.00){
                System.out.println("Enter an amount greater than 0.00");
                return false;
            }
            if(to != null){
                to.setBalance(to.getBalance()+amount);
                return true;
            }
            this.setBalance(this.getBalance()+amount);
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
        return "Savings{" +
                "account='" + account + '\'' +
                ", balance=" + balance +
                '}';
    }
}
