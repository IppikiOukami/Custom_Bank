/**
 * @author Mario Macias, Kevin Aofia, Angel Ramos
 * @version PA4
 * @since April 6th, 2021
 *
 * Credit is a class that inherits from abstract class Account and builds off of the parent class
 */
public class Credit extends Account{

    /**
     * Credit attributes
     */
    private String account;
    private double balance;
    private double creditMax;

    /**
     * Credit default constructor
     */
    public Credit(){}

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
     * Credit constructor specifying parent and credit attributes
     */
    public Credit(String idNo, String checkNo, String saveNo, String credNo, double checkBal, double saveBal, double credBal, double max){
        super(idNo, new Checking(checkNo,checkBal), new Savings(saveNo, saveBal));
        this.account = credNo;
        this.balance = credBal;
        this.creditMax = max*-1;
        setCredAcc(this);
    }

    /**
     *
     * @param saveNo saveNo
     * @param saveBal saveBal
     * @param max max
     *
     * Credit constructor specifying ONLY credit attributes
     */
    public Credit(String saveNo, double saveBal, double max){
        this.account = saveNo;
        this.balance = saveBal;
        this.creditMax = max*-1;
    }

    /**
     *
     * @return return
     */
    //getters and setters exempt from JavaDoc
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
     * @return return
     */
    public double getCreditMax() {
        return creditMax;
    }

    /**
     *
     * @param creditMax creditMax
     */
    public void setCreditMax(double creditMax) { //never used, delete or keep??
        this.creditMax = creditMax;
    }

    /**
     *
     * @return double
     *
     * This method overrides parent class following template design pattern
     */
    public double inquire(){
        RunBank.logger("Inquired Credit");
        return getBalance();
    }

    /**
     *
     * @param payee person to pay
     * @param sAmount amount of payment
     * @return boolean
     *
     * This method overrides parent class following template design pattern
     */
    public boolean pay(Checking payee, String sAmount){
        try{
            double amount = Double.parseDouble(sAmount);
            if(amount < 0.00){
                System.out.println("Enter an amount greater than 0.00");
                return false;
            }
            else if(this.getBalance()-amount < this.getCreditMax()){
                System.out.println("Insufficient funds");
                return false;
            }
            this.balance -= amount;
            payee.setBalance(payee.getBalance() + amount);
            RunBank.logger("Payed "+payee.getIDNumber()+" $"+sAmount);
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
        Savings saveAcc = null;
        Checking checkAcc = null;
        if(Character.compare(accNo.charAt(0),'2') == 0){
            saveAcc = user.getSaveAcc();
        }
        else if(Character.compare(accNo.charAt(0),'1') == 0){
            checkAcc = user.getCheckAcc();
        }
        else{
            System.out.println("Cannot transfer to same account\nVerify transfer details");
            return false;
        }
        try{
            double amount = Double.parseDouble(sAmount);
            if(amount < 0.00){
                System.out.println("Enter an amount greater than 0.00");
                return false;
            }
            this.balance -= amount;
            if(this.getBalance() < this.getCreditMax()){
                System.out.println("Insufficient Funds");
                return false;

            }
            else if(saveAcc != null){
                saveAcc.setBalance(saveAcc.getBalance()+amount);
                RunBank.logger("Transferred "+sAmount+" to Savings From Credit");
            }
            else{
                assert checkAcc != null;
                checkAcc.setBalance(checkAcc.getBalance()+amount);
                RunBank.logger("Transferred "+sAmount+" to Checking From Credit");
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
     * This method overrides parent class following template design pattern
     */
    public boolean withdraw(String sAmount){
        try {
            double amount = Double.parseDouble(sAmount);
            if(amount <= 0.00){
                System.out.println("Enter an amount greater than 0.00");
                return false;
            }
            if(this.getBalance()-amount<this.getCreditMax()){
                System.out.println("Insufficient funds");
                return false;
            }
            this.setBalance(this.getBalance()-amount);
            RunBank.logger("Withdrew "+sAmount+" from Credit");
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
     * This method overrides parent class following template design pattern
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
            if(amount+this.getBalance()>0.00){
                System.out.println("Deposit cannot exceed debt amount");
                return false;
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
     * This method overrides parent class following template design pattern
     */
    @Override
    public String toString() {
        return "Credit{" +
                "account='" + account + '\'' +
                ", balance=" + balance +
                ", creditMax=" + (creditMax*-1) +
                '}';
    }
}
