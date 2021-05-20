import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Mario Macias, Kevin Aofia, Angel Ramos
 * @version PA4
 * @since April 6th, 2021
 *
 * Administrator is a class that inherits from the abstract class Person and implements security interface
 */
public class Administrator extends Person implements Security{

    /**
     * Administration attributes
     */
    private static String bankStatements = "src\\BankStatements\\";
    private final String passcode = "password";
    private final String transRead = "src\\Transaction Actions.csv";

    /**
     * Administration default constructor
     */
    public Administrator() {
    }

    /**
     *
     * @param firstName firstName
     * @param lastName lastName
     * @param DOB DOB
     * @param address address
     * @param phone phone
     * @param idNo id number
     * @param email email
     * @param password password
     *
     * Administrator constructor specifying attributes from parent class
     */
    public Administrator(String firstName, String lastName, String DOB, String address, String phone, String idNo, String email,String password) {
        super(firstName, lastName, DOB, address, phone, idNo, email,password);
    }

    public String getPasscode() {
        return passcode;
    }

    /**
     *
     * @param password default password
     * @param attempts attempts remaining before exiting
     * @return boolean true or false
     *
     * This method verifies management password before proceeding to management screen
     */
    public boolean verify(String password, int attempts) throws UserException {
        if (password.equalsIgnoreCase(passcode)){
            return true;
        }
        if(attempts == 0){
            throw new UserException("Too many invalid attempts. Returning to start screen. Please start over and try again.");
        }
        System.out.println("Invalid password. "+ attempts + " attempts remaining.");
        return false;
    }

    /**
     *
     * @param users Hashmap of users
     * @param accounts Hashmap of accounts
     *
     * This method runs the main admin menu and displays interface to interact with admin actions
     */
    public void adminMenu(HashMap users, HashMap accounts){
        int opt;
        Scanner cursor = new Scanner(System.in);
        boolean runningAdminMenu = true;
        while(runningAdminMenu){
            System.out.println("-----------------------------------------------------------------------------------------");
            System.out.println("\t\tManagement Menu (ADMIN EYES ONLY)\n");
            System.out.println("\tMenu Options:\n\t\t1) View User Account\n\t\t2) View All Users Accounts\n\t\t3) Create Statements\n\t\t4) Run Transaction Actions\n\t\t5) Return to Menu\n\t\t6) Exit");
            System.out.println("-----------------------------------------------------------------------------------------");
            try {
                System.out.print(">>>");
                opt = Integer.parseInt(cursor.nextLine());
            }catch (NumberFormatException e) {
                RunBank.logger("Admin invalid entry option in Administrator.java method adminMenu()");
                System.out.println("Invalid Entry. Please Try again.");
                adminMenu(users, accounts);
                break;
            }
            switch (opt) {
                case 1 -> {
                    System.out.println("-----------------------------------------------------------------------------------------");
                    System.out.println("\tHow would you like to search for an account?\n\t\t1) Enter user's name\n\t\t2) Enter user's account number");
                    System.out.println("-----------------------------------------------------------------------------------------");
                    System.out.print(">>>");
                    String searchBy = cursor.nextLine();
                    if (Integer.parseInt(searchBy) == 1) {
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.println("\tEnter user's first name: ");
                        System.out.println("-----------------------------------------------------------------------------------------");

                        System.out.print(">>>");
                        String fName = cursor.nextLine();

                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.println("\tEnter user's last name: ");
                        System.out.println("-----------------------------------------------------------------------------------------");

                        System.out.print(">>>");
                        String lName = cursor.nextLine();

                        Customer thisGuy = getByName(fName, lName, users); //user not found will print in getByName()
                        //if customer is not a user and returns null, return to menu
                        if (thisGuy == null) {
                            continue;
                        }
                        //else, show user account info
                        Checking thisAccounts = (Checking) accounts.get(thisGuy.getIdNo());
                        System.out.println(thisGuy.toString());
                        System.out.println(thisAccounts.toString());
                        System.out.println(thisAccounts.getSaveAcc().toString());
                        System.out.println(thisAccounts.getCredAcc().toString());
                        RunBank.logger("Admin viewed " + thisGuy.getFirstName() + " " + thisGuy.getLastName() + "'s account.");
                    }
                    else if(Integer.parseInt(searchBy) == 2) {
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.println("\tEnter a user's checking, savings, or credit account number: ");
                        System.out.println("-----------------------------------------------------------------------------------------");

                        System.out.print(">>>");
                        String userAccount = cursor.nextLine();

                        Customer thisGuy = getByAccount(userAccount, users); //user not found will print in getByAccount()

                        //if no account is found and returns null, return to menu
                        if (thisGuy == null) {
                            continue;
                        }
                        //else, show user account info
                        Checking thisAccounts = (Checking) accounts.get(thisGuy.getIdNo());
                        System.out.println(thisGuy.toString());
                        System.out.println(thisAccounts.toString());
                        System.out.println(thisAccounts.getSaveAcc().toString());
                        System.out.println(thisAccounts.getCredAcc().toString());
                        RunBank.logger("Admin viewed " + thisGuy.getFirstName() + " " + thisGuy.getLastName() + "'s account.");
                    }
                }
                case 2 -> {
                    Set keys = users.keySet();
                    for (Object key : keys) {
                        Checking acc = (Checking) accounts.get(key);
                        Customer customer = (Customer) users.get(key);
                        System.out.println(customer.toString());
                        System.out.println(acc.toString());
                        System.out.println(acc.getSaveAcc().toString());
                        System.out.println(acc.getCredAcc().toString());
                        System.out.println("*****************************************************************************************");
                    }
                    System.out.println("Done listing all user accounts.");
                    RunBank.logger("Admin viewed all user accounts on + " + LocalDateTime.now());
                }
                case 3 -> {
                    System.out.println("-----------------------------------------------------------------------------------------");
                    System.out.println("\t\t1) Create statement by user's name\n\t\t2) Create statement by user's ID number");
                    System.out.println("-----------------------------------------------------------------------------------------");

                    try {
                        System.out.print(">>>");
                        opt = Integer.parseInt(cursor.nextLine());
                    } catch (NumberFormatException e) {
                        RunBank.logger("Admin entered invalid entry option in Administration.java method adminMenu()");
                        System.out.println("Invalid Entry. Please try again");
                        adminMenu(users, accounts);
                        break;
                    }
                    switch (opt) {
                        case 1 -> {
                            RunBank.logger("Admin attempted to create statement by name on " + LocalDateTime.now());
                            System.out.println("-----------------------------------------------------------------------------------------");
                            System.out.println("\tEnter user's first Name: ");
                            System.out.println("-----------------------------------------------------------------------------------------");

                            System.out.print(">>>");
                            String fName = cursor.nextLine();
                            System.out.println("-----------------------------------------------------------------------------------------");
                            System.out.println("\tEnter user's last Name: ");
                            System.out.println("-----------------------------------------------------------------------------------------");

                            System.out.print(">>>");
                            String lName = cursor.nextLine();

                            //if user is not a customer and getByName returns null, return to menu
                            if (getByName(fName, lName, users) == null) { //user not found will print in getByName()
                                adminMenu(users, accounts);
                                break;
                            }
                            Customer found = (Customer) getByName(fName, lName, users);
                            makeStatement(found.getIdNo(), users, accounts);
                        }
                        case 2 -> {
                            RunBank.logger("Admin attempted to create statement by ID on " + LocalDateTime.now());
                            System.out.println("-----------------------------------------------------------------------------------------");
                            System.out.println("Enter ID Number: ");
                            System.out.println("-----------------------------------------------------------------------------------------");

                            System.out.print(">>>");
                            String id = cursor.nextLine();

                            if (users.get(id) == null) { //if get(id) returns null, return to menu
                                System.out.println("There is no customer with that ID. Please Try again.");
                                adminMenu(users, accounts);
                                break;
                            }
                            makeStatement(id, users, accounts);
                        }
                        default -> {
                            System.out.println("Invalid entry. Please try again.");
                            adminMenu(users, accounts);
                        }
                    }
                }
                case 4 -> {
                    System.out.println("Running Transaction Actions...");
                    transReader(transRead,accounts,users);
                }
                case 5 ->{
                    System.out.println("Logging Admin off...Returning to menu");
                    runningAdminMenu = false;
                }
                case 6 ->{
                    System.out.println("Logging Admin off...Terminating session");
                    runningAdminMenu = false;
                    System.exit(0);
                }
                default -> {
                    System.out.println("Invalid entry. Please try again.");
                    adminMenu(users, accounts);
                }
            }
        }
    }

    /**
     *
     * @param idNo id number
     * @param clients list of clients
     * @param accounts list of accounts
     *
     * This method creates a bank statement for a user, and stores the text file in a folder if successful creation
     */
    public void makeStatement(String idNo, HashMap clients, HashMap accounts){
        String content = BankStatement.makeStatement(idNo,clients,accounts,RunBank.seshLog);
        Customer user = (Customer) clients.get(idNo);
        try{
            FileWriter admin = new FileWriter(bankStatements+user.getFirstName()+"_"+user.getLastName()+".txt");
            admin.append(content);
            admin.close();
            System.out.println("Statement for "+user.getFirstName()+" "+user.getLastName()+" has been created successfully!");
            RunBank.logger("Admin created statement for "+user.getFirstName()+" "+user.getLastName()+" successfully! Timestamp: " + LocalDateTime.now());
        }catch(IOException ex){
            RunBank.logger("Admin could not create bank Statement. An error occurred in Administration.java method makeStatement()");
            System.out.println("Statement creation was unsuccessful. Please try again later.");
        }
    }

    /**
     *
     * @param fName fName
     * @param lName Lname
     * @param clients list of customers
     * @return user object or null
     *
     * This method will return a correct user object or a null object if no customer is found
     */
    public Customer getByName(String fName, String lName, HashMap clients){
        for(Object customer: clients.values()){
            Customer user = (Customer) customer;
            if(user.getFirstName().equalsIgnoreCase(fName) && user.getLastName().equalsIgnoreCase(lName)){
                return user;
            }
        }
        System.out.println("There is no customer with that name. Please Try again.");
        return null;
    }

    public Customer getByAccount(String userAccount, HashMap clients){
        for(Object customer: clients.values()){
            Customer user = (Customer) customer;

            if(user.getCheckingNumber().equalsIgnoreCase(userAccount)){
                return user;
            }
            else if(user.getSavingNumber().equalsIgnoreCase(userAccount)){
                return user;
            }
            else if(user.getCreditNumber().equalsIgnoreCase(userAccount)){
                return user;
            }
        }
        System.out.println("There is no customer with that account number. Please Try again.");
        return null;
    }



    /**
     *
     * @param filename file name
     * @param accounts Hashmap of accounts
     * @param clients Hashmap of client
     *
     * This method reads a csv file containing transactions and executes said transactions for bank users
     */
    public void transReader(String filename, HashMap accounts, HashMap clients){
        File actions = new File(filename);
        if(actions.exists()){
            HashMap<String,Integer> headers = new HashMap<String,Integer>();
            Customer uFrom = new Customer();
            Customer uTo = new Customer();
            Checking from = new Checking();
            Checking to = new Checking();
            Scanner input;
            try{
                input = new Scanner(actions);
            }catch(IOException e){
                RunBank.logger("Admin could not open Transaction Actions.csv. Administration.java method transReader()");
                return;
            }
            String[] line = input.nextLine().split(",");
            while(input.hasNextLine()){
                line = input.nextLine().split(",");
                if(line[3].equalsIgnoreCase("inquires")){
                    uFrom = getByName(line[0],line[1],clients);
                    from = (Checking) accounts.get(uFrom.getIdNo());
                    if(line[2].equalsIgnoreCase("Checking")){
                        from.inquire(); }
                    else if(line[2].equalsIgnoreCase("Savings")){ from.getSaveAcc().inquire();}
                    else{from.getCredAcc().inquire(); }
                }else if(line[3].equalsIgnoreCase("pays")){
                    uFrom = getByName(line[0],line[1],clients);
                    from = (Checking) accounts.get(uFrom.getIdNo());
                    uTo = getByName(line[4],line[5],clients);
                    to = (Checking) accounts.get(uTo.getIdNo());
                    if(line[2].equalsIgnoreCase("Checking")){from.pay(to,line[7]);}
                    else if(line[2].equalsIgnoreCase("Savings")){from.getSaveAcc().pay(to,line[7]);}
                    else{from.getCredAcc().pay(to, line[7]);}
                }else if(line[3].equalsIgnoreCase("transfers")){
                    uFrom = getByName(line[0],line[1],clients);
                    from = (Checking) accounts.get(uFrom.getIdNo());
                    if(line[2].equalsIgnoreCase("Checking")){
                        if(line[6].equalsIgnoreCase("Savings")){from.transfer("2",line[7]);}
                        else if(line[6].equalsIgnoreCase("Credit")){from.transfer("3",line[7]);}
                        else{System.out.println("Cannot transfer from source account to source account");}
                    }
                    else if(line[2].equalsIgnoreCase("Savings")){
                        if(line[6].equalsIgnoreCase("Checking")){from.getSaveAcc().transfer("1",line[7]);}
                        else if(line[6].equalsIgnoreCase("Credit")){from.getSaveAcc().transfer("3",line[7]);}
                        else{System.out.println("Cannot transfer from source account to source account");}
                    }
                    else{
                        if(line[6].equalsIgnoreCase("Savings")){from.getCredAcc().transfer("2",line[7]);}
                        else if(line[6].equalsIgnoreCase("Checking")){from.getCredAcc().transfer("1",line[7]);}
                        else{System.out.println("Cannot transfer from source account to source account");}
                    }
                }else if(line[3].equalsIgnoreCase("withdraws")){
                    uFrom = getByName(line[0],line[1],clients);
                    from = (Checking) accounts.get(uFrom.getIdNo());
                    if(line[2].equalsIgnoreCase("Checking")){from.withdraw(line[7]);}
                    else if(line[2].equalsIgnoreCase("Savings")){from.getSaveAcc().withdraw(line[7]);}
                    else{from.getCredAcc().withdraw(line[7]);}
                }else if(line[3].equalsIgnoreCase("deposits")){
                    uTo = getByName(line[4],line[5],clients);
                    to = (Checking) accounts.get(uTo.getIdNo());
                    if(line[6].equalsIgnoreCase("Checking")){to.deposit(line[7],null);}
                    else if(line[6].equalsIgnoreCase("Savings")){to.getSaveAcc().deposit(line[7],null);}
                    else{to.getCredAcc().deposit(line[7],null);}
                }else{
                    System.out.println("Operation not recognized! Moving on");
                }
            }
            RunBank.balanceSheet(accounts,clients);
        }
    }

    /**
     *
     * @param attempt attempt
     * @return boolean
     *
     * This method verifies employee
     */
    @Override
    public boolean employeeVerification(String attempt) {
        if (this.getPasscode().equals(attempt)) return true;
        System.out.println("Invalid Password try again.");
        return false;
    }

    /**
     *
     * @param attempt attempt
     * @return boolean
     *
     * This method verifies customer
     */
    @Override
    public boolean customerVerification(String attempt) {
        return false;
    }
}