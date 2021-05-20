/**
 * Runs through a csv value and places customer data into HashMaps, from here a menu guides you through functions
 * relatable to a bank interaction
 * @author Mario Everardo Macias
 * @version 1.2.0
 * @since 3/14/2021
 * PA3
 * CS 3331 - AOOP - Spring 2021
 * Dr. Daniel Mejia
 * This Lab simulates a bank interaction between a customer and teller or manager and admin system
 *
 * I confirm that the work of this assignment is completely my own. By turning in this assignment,
 * I declare that I did not receive unauthorized assistance.
 * Moreover, all deliverables including, but not limited to the source code, lab report and output files were written and produced by me alone.
 *
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import static java.lang.System.currentTimeMillis;

/**
 * @author Mario Macias, Kevin Aofia, Angel Ramos
 * @version PA4
 * @since April 6th, 2021
 *
 * RunBank is a class that contains our main method and most functionality of our program
 */
public class RunBank{

    //private static final String csvToRead = "src\\CS 3331 - Bank Users 4.csv";
    private static final String csvToRead = "src\\CS 3331 - Bank Users 5 - S21.csv";
    private static final String updatedCSV = "src\\UpdatedCSV_Team3_PA4.csv";
    private static final String logPath = "src\\TransLog_Team3_PA4.txt";

    static HashMap<String,String> seshLog = new HashMap<String,String>();

    /**
     *
     * @param args main
     *
     * This method is the main method where most of the user interface and is the heart of the program
     */
    public static void main(String[] args){
        clearLog();
        HashMap<String,Integer> headers = new HashMap<String,Integer>();
        HashMap<String,Customer> clients = new HashMap<String, Customer>();
        HashMap<String,Checking> accounts = new HashMap<String, Checking>();

        try{
            Scanner input = new Scanner(new File(csvToRead));
            String[] line = input.nextLine().split(",");
            int index = 0;
            for(String title :line){
                headers.put(title,index++);
                if (title.equals("Address")) index+=2;
                if (title.equals("Date of Birth")) index++;
            }
            while(input.hasNextLine()){
                line = input.nextLine().split(",");
                clients.put(line[headers.get("Identification Number")],new Customer(line[headers.get("First Name")],line[headers.get("Last Name")],line[headers.get("Date of Birth")]+line[headers.get("Date of Birth")+1],(line[headers.get("Address")]+line[headers.get("Address")+1]+line[headers.get("Address")+2]),line[headers.get("Phone Number")],line[headers.get("Identification Number")],line[headers.get("Checking Account Number")],line[headers.get("Savings Account Number")],line[headers.get("Credit Account Number")],line[headers.get("Password")],line[headers.get("Email")]));
                accounts.put(line[headers.get("Identification Number")],new Checking(line[headers.get("Identification Number")],line[headers.get("Checking Account Number")],line[headers.get("Savings Account Number")],line[headers.get("Credit Account Number")],Double.parseDouble(line[headers.get("Checking Starting Balance")]),Double.parseDouble(line[headers.get("Savings Starting Balance")]),Double.parseDouble(line[headers.get("Credit Starting Balance")]),Double.parseDouble(line[headers.get("Credit Max")])));
            }
            input.close();
            logger("Files configured, HashMaps are ready");
        } catch(FileNotFoundException e){
            logger("Original CSV file not found"); // logger error finding file
            System.exit(1);
        } catch(NumberFormatException e){
            logger("Parsing failure. Indexing issue creating Customer/Account"); // logger error parsing accounts
            System.exit(1);
        }
        int lastA = accounts.size()-1+1000;
        int lastC = clients.size()-1;
        Scanner cursor = new Scanner(System.in);

        System.out.println("\n\t\tWelcome to the Bank of Miners!\n");
        logger("Initiate Session at: "+ new Timestamp(currentTimeMillis()));

        while (true) {
            System.out.println("-----------------------------------------------------------------------------------------");
            System.out.println("\tAre you a member of management? (y/n/exit)");
            System.out.println("-----------------------------------------------------------------------------------------");
            System.out.print(">>>");
            String ans = cursor.nextLine();
            ans = ans.toLowerCase(); //handle variations of uppercase/lowercase inputs

            if (ans.equals("exit")) {
                logger("User exited bank from main menu on " + LocalDateTime.now());
                balanceSheet(accounts, clients); //updates balance sheet at every exit point
                System.out.println("\tThank you for being a preferred customer of Miner Bank! See you soon!\n");
                System.exit(0);
            }
            else if (ans.equals("y") || ans.equals("yes")) {
                logger("Admin attempt at " + LocalDateTime.now());
                int attempts = 3;
                boolean adminAccessing = true;
                while(adminAccessing){
                    Administrator admin = new Administrator();
                    System.out.println("-----------------------------------------------------------------------------------------");
                    System.out.println("\tEnter management password: (management password is \"password\")");
                    System.out.println("-----------------------------------------------------------------------------------------");
                    System.out.print(">>>");
                    String pass = cursor.nextLine();

                    try {
                        //continue to admin login if password is verified, otherwise try again
                        if(admin.verify(pass, attempts--)){
                            logger("Admin login successful after " + attempts + " attempts on "+ new Timestamp(currentTimeMillis()));
                            admin.adminMenu(clients,accounts);
                            //the only way admin can exit is after we have been inside admin menu, so we must exit after accessing menu
                            adminAccessing = false;
                        }
                    } catch (UserException errorMessageIn) {
                        System.out.println(errorMessageIn.toString());
                        adminAccessing = false;
                    }
                    logger("Admin login failed. Password entered -> " + pass + ". Attempts left " + attempts + ".");
                }

            }
            //if user is not a member of admin
            else if(ans.equals("n") || ans.equals("no")) {
                try {
                    boolean loggedOn = true;
                    logger("Customer login attempt at " + new Timestamp(currentTimeMillis()));
                    System.out.println("-----------------------------------------------------------------------------------------");
                    System.out.println("\tAre you a new or existing customer? (type \"exit\" to terminate session)\n\t\t1) Login(existing users)\n\t\t2) Create an account(new users) ");
                    System.out.println("-----------------------------------------------------------------------------------------");

                    System.out.print(">>>");
                    String loginOrCreate = cursor.nextLine();

                    if (Integer.parseInt(loginOrCreate) == 1) {
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.println("\tEnter your ID to login or type \"exit\" to terminate session: ");
                        System.out.println("-----------------------------------------------------------------------------------------");

                        System.out.print(">>>");
                        String id = cursor.nextLine();

                        if (id.equalsIgnoreCase("exit")) {
                            System.out.println("\n\t\tThank you for using Miner Bank! See you soon!");
                            logger("User terminated session at " + new Timestamp(currentTimeMillis()));
                            balanceSheet(accounts, clients);
                            break;
                        }
                        if (clients.get(id) == null) {
                            System.out.println("Invalid ID Number");
                            logger("User entered invalid ID Number -> " + id);
                            continue;
                        }
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.println("\tEnter your password: ");
                        System.out.println("-----------------------------------------------------------------------------------------");

                        System.out.print(">>>");
                        String pass = cursor.nextLine();

                        Customer userName = clients.get(id);
                        Checking user = accounts.get(id);

                        if (!userName.getPassword().equals(pass)) {
                            System.out.println("Invalid Password");
                            logger("User entered invalid password -> " + pass);
                            continue;
                        }
                        logger("User successfully authorized and logged in as " + userName.getFirstName() + " " + userName.getLastName());
                        user.setsCheck(user.getBalance());
                        user.setsSave(user.getSaveAcc().getBalance());
                        user.setsCred(user.getCredAcc().getBalance());
                        System.out.println("\n\t\tWelcome back " + userName.getFirstName() + " " + userName.getLastName() + "!");

                        while (loggedOn) {
                            System.out.println("-----------------------------------------------------------------------------------------");
                            System.out.println("\t\tLogged in as [" + userName.getFirstName() + " " + userName.getLastName() + "]\n");
                            System.out.println("\tMain Menu:\n\t\t1) Inquiry\n\t\t2) Deposit\n\t\t3) Withdraw\n\t\t4) Pay\n\t\t5) Transfer\n\t\t6) Return To Menu\n\t\t7) Exit");
                            System.out.println("-----------------------------------------------------------------------------------------");

                            int sels;
                            try {
                                System.out.print(">>>");
                                sels = Integer.parseInt(cursor.nextLine());
                            } catch (NumberFormatException e) {
                                logger("User entered invalid option at menu selection in RunBank.java method main() ");
                                System.out.println("Invalid entry. Please try again.");
                                continue;
                            }
                            switch (sels) {
                                case 1 -> {
                                    System.out.println("-----------------------------------------------------------------------------------------");
                                    System.out.println("\tWhich account are you inquiring about?\n\t1) Checking\n\t2) Savings\n\t3) Credit");
                                    System.out.println("-----------------------------------------------------------------------------------------");

                                    try {
                                        System.out.print(">>>");
                                        sels = Integer.parseInt(cursor.nextLine());
                                    } catch (NumberFormatException e) {
                                        logger("User entered invalid option in RunBank.java method main() ");
                                        System.out.println("Invalid entry. Please try again.");
                                        break;
                                    }
                                    //valid entry, enter switch
                                    switch (sels) {
                                        case 1 -> {
                                            sessionActions(userName, "Inquiry on Checking: " + user.inquire());
                                            logger("User inquired Checking: " + user.inquire());
                                            System.out.println("Current balance: $" + user.inquire());
                                        }
                                        case 2 -> {
                                            sessionActions(userName, "Inquiry on Savings: " + user.getSaveAcc().inquire());
                                            logger("User inquired Savings: " + user.getSaveAcc().inquire());
                                            System.out.println("Current balance: $" + user.getSaveAcc().inquire());
                                        }
                                        case 3 -> {
                                            sessionActions(userName, "Inquiry on Credit: " + user.getCredAcc().inquire());
                                            logger("User inquired on Credit: " + user.getCredAcc().inquire());
                                            System.out.println("Current balance: $" + user.getCredAcc().inquire());
                                        }
                                        default -> {
                                            logger("User entered invalid inquiry selection: " + sels);
                                            System.out.println("Invalid entry. Please try again.");
                                        }
                                    }
                                }
                                case 2 -> {
                                    System.out.println("-----------------------------------------------------------------------------------------");
                                    System.out.println("\tDeposit to: \n\t\t1) Checking\n\t\t2) Savings\n\t\t3) Credit");
                                    System.out.println("-----------------------------------------------------------------------------------------");

                                    try {
                                        System.out.print(">>>");
                                        sels = Integer.parseInt(cursor.nextLine());
                                    } catch (NumberFormatException e) {
                                        logger("User entered invalid option in RunBank.java method main()");
                                        System.out.println("Invalid entry. Please try again.");
                                        break;
                                    }
                                    System.out.println("-----------------------------------------------------------------------------------------");
                                    System.out.println("\tEnter amount: ");
                                    System.out.println("-----------------------------------------------------------------------------------------");

                                    System.out.print(">>>");
                                    String amount = cursor.nextLine();

                                    switch (sels) {
                                        case 1:
                                            if (user.deposit(amount, null)) {
                                                sessionActions(userName, "Deposit to Checking: " + amount);
                                                logger("Deposit to Checking: " + amount);
                                                System.out.println("New balance: $" + user.inquire());
                                            }
                                            break;
                                        case 2:
                                            if (user.getSaveAcc().deposit(amount, null)) {
                                                sessionActions(userName, "Deposit to Savings: " + amount);
                                                logger("Deposit to Savings: " + amount);
                                                System.out.println("New balance: $" + user.getSaveAcc().inquire());
                                            }
                                            break;
                                        case 3:
                                            if (user.getCredAcc().deposit(amount, null)) {
                                                sessionActions(userName, "Deposit to Credit: " + amount);
                                                logger("Deposit to Credit: " + amount);
                                                System.out.println("New balance: $" + user.getCredAcc().inquire());
                                            }
                                            break;
                                        default:
                                            logger("User entered invalid option selection: " + sels);
                                            System.out.println("Invalid entry. Please try again.");
                                    }
                                }
                                case 3 -> {
                                    System.out.println("-----------------------------------------------------------------------------------------");
                                    System.out.println("\tWithdraw from: \n\t\t1) Checking\n\t\t2) Savings\n\t\t3) Credit");
                                    System.out.println("-----------------------------------------------------------------------------------------");

                                    try {
                                        System.out.print(">>>");
                                        sels = Integer.parseInt(cursor.nextLine());
                                    } catch (NumberFormatException e) {
                                        System.out.println("Please enter a valid option .");
                                        break;
                                    }
                                    System.out.println("-----------------------------------------------------------------------------------------");
                                    System.out.println("\tEnter amount: ");
                                    System.out.println("-----------------------------------------------------------------------------------------");

                                    System.out.print(">>>");
                                    String with = cursor.nextLine();

                                    switch (sels) {
                                        case 1:
                                            if (user.withdraw(with)) {
                                                sessionActions(userName, "Withdraw from Checking: " + with);
                                                logger("Withdraw from Checking: " + with);
                                                System.out.println("New balance: $" + user.inquire());
                                            }
                                            break;
                                        case 2:
                                            if (user.getSaveAcc().withdraw(with)) {
                                                sessionActions(userName, "Withdraw from Savings: " + with);
                                                logger("Withdraw from Savings: " + with);
                                                System.out.println("New balance: $" + user.getSaveAcc().inquire());
                                            }
                                            break;
                                        case 3:
                                            if (user.getCredAcc().withdraw(with)) {
                                                sessionActions(userName, "Withdraw from Credit: " + with);
                                                logger("Withdraw from Credit: " + with);
                                                System.out.println("New balance: $" + user.getCredAcc().inquire());
                                            }
                                            break;
                                        default:
                                            logger("User entered invalid option in RunBank.java method main() -> " + sels);
                                            System.out.println("Invalid entry. Please try again.");
                                    }
                                }
                                case 4 -> {
                                    System.out.println("-----------------------------------------------------------------------------------------");
                                    System.out.println("\tEnter Payee's ID Number: ");
                                    System.out.println("-----------------------------------------------------------------------------------------");

                                    System.out.print(">>>");
                                    String idNo = cursor.nextLine();

                                    Checking payee;
                                    if (accounts.get(idNo) != null) {
                                        payee = accounts.get(idNo);
                                        //payee and payer account number MUST be different
                                        if (payee.getIDNumber().equals(user.getIDNumber())) {
                                            System.out.println("Cannot pay same account. Please enter a valid ID number.");
                                            break;
                                        }
                                    } else {
                                        logger(userName.getFirstName() + " provided an invalid payee ID Number -> " + idNo);
                                        System.out.println("Customer could not be found. Please enter a valid ID Number");
                                        break;
                                    }
                                    System.out.println("-----------------------------------------------------------------------------------------");
                                    System.out.println("\tEnter amount to pay: ");
                                    System.out.println("-----------------------------------------------------------------------------------------");

                                    System.out.print(">>>");
                                    String payment = cursor.nextLine();
                                    System.out.println("-----------------------------------------------------------------------------------------");
                                    System.out.println("\tPay from: \n\t\t1) Checking\n\t\t2) Savings\n\t\t3) Credit");
                                    System.out.println("-----------------------------------------------------------------------------------------");

                                    try {
                                        System.out.print(">>>");
                                        sels = Integer.parseInt(cursor.nextLine());
                                    } catch (NumberFormatException e) {
                                        logger("User entered invalid option in RunBank.java method main()");
                                        System.out.println("Invalid entry. Please try again.");
                                        break;
                                    }
                                    switch (sels) {
                                        case 1:
                                            if (user.pay(payee, payment)) {
                                                sessionActions(userName, "payed " + clients.get(payee.getIDNumber()).getFirstName() + " " + clients.get(payee.getIDNumber()).getLastName() + " " + payment + " from Checking");
                                                logger(userName.getFirstName() + " " + userName.getLastName() + " payed " + clients.get(payee.getIDNumber()).getFirstName() + " " + clients.get(payee.getIDNumber()).getLastName() + " " + payment + " from Checking");
                                                System.out.println("New balance: $" + user.inquire());
                                            }
                                            break;
                                        case 2:
                                            if (user.getSaveAcc().pay(payee, payment)) {
                                                sessionActions(userName, "payed " + clients.get(payee.getIDNumber()).getFirstName() + " " + clients.get(payee.getIDNumber()).getLastName() + " " + payment + " from Savings");
                                                logger(userName.getFirstName() + " " + userName.getLastName() + " payed " + clients.get(payee.getIDNumber()).getFirstName() + " " + clients.get(payee.getIDNumber()).getLastName() + " " + payment + " from Savings");
                                                System.out.println("New balance: $" + user.getSaveAcc().inquire());
                                            }
                                            break;
                                        case 3:
                                            if (user.getCredAcc().pay(payee, payment)) {
                                                sessionActions(userName, "payed " + clients.get(payee.getIDNumber()).getFirstName() + " " + clients.get(payee.getIDNumber()).getLastName() + " " + payment + " from Credit");
                                                logger(userName.getFirstName() + " " + userName.getLastName() + " payed " + clients.get(payee.getIDNumber()).getFirstName() + " " + clients.get(payee.getIDNumber()).getLastName() + " " + payment + " from Credit");
                                                System.out.println("New balance: $" + user.getCredAcc().inquire());
                                            }
                                            break;
                                        default:
                                            logger("User entered invalid option in RunBank.java method main() -> " + sels);
                                            System.out.println("Invalid entry. Please try again.");
                                    }
                                }
                                case 5 -> {
                                    System.out.println("-----------------------------------------------------------------------------------------");
                                    System.out.println("\tTransfer from: \n\t\t1) Checking\n\t\t2) Savings\n\t\t3) Credit");
                                    System.out.println("-----------------------------------------------------------------------------------------");

                                    try {
                                        System.out.print(">>>");
                                        sels = Integer.parseInt(cursor.nextLine());
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid entry. Please try again.");
                                        break;
                                    }
                                    int to;
                                    System.out.println("-----------------------------------------------------------------------------------------");
                                    System.out.println("\tTransfer to:\n\t\t1) Checking\n\t\t2) Savings\n\t\t3) Credit");
                                    System.out.println("-----------------------------------------------------------------------------------------");

                                    try {
                                        System.out.print(">>>");
                                        to = Integer.parseInt(cursor.nextLine());
                                    } catch (NumberFormatException e) {
                                        logger("User entered invalid option in RunBank.java method main()");
                                        System.out.println("Invalid entry. Please try again.");
                                        break;
                                    }
                                    System.out.println("-----------------------------------------------------------------------------------------");
                                    System.out.println("\tEnter transfer amount: ");
                                    System.out.println("-----------------------------------------------------------------------------------------");

                                    System.out.print(">>>");
                                    String trans = cursor.nextLine();

                                    switch (sels) {
                                        case 1:
                                            switch (to) {
                                                case 2:
                                                    if (user.transfer(user.getSaveAcc().getAccount(), trans)) {
                                                        sessionActions(userName, "Transfer of " + trans + " Checking to Savings");
                                                        logger("Transfer of " + trans + " Checking to Savings");
                                                        System.out.println("New checking balance: $" + user.inquire());
                                                        System.out.println("New savings balance: $" + user.getSaveAcc().inquire());
                                                    }
                                                    break;
                                                case 3:
                                                    if (user.transfer(user.getCredAcc().getAccount(), trans)) {
                                                        sessionActions(userName, "Transfer of " + trans + " Checking to Credit");
                                                        logger("Transfer of " + trans + " Checking to Credit");
                                                        System.out.println("New checking balance: $" + user.inquire());
                                                        System.out.println("New credit balance: $" + user.getCredAcc().inquire());
                                                    }
                                                    break;
                                                default:
                                                    logger("User entered invalid option selection -> " + to);
                                                    System.out.println("Invalid entry. Please try again.");
                                            }
                                            break;
                                        case 2:
                                            switch (to) {
                                                case 1:
                                                    if (user.getSaveAcc().transfer(user, user.getAccount(), trans)) {
                                                        sessionActions(userName, "Transfer of " + trans + " Savings to Checking");
                                                        logger("Transfer of " + trans + " Savings to Checking");
                                                        System.out.println("New savings balance: $" + user.getSaveAcc().inquire());
                                                        System.out.println("New checking balance: $" + user.inquire());
                                                    }
                                                    break;
                                                case 3:
                                                    if (user.getSaveAcc().transfer(user, user.getCredAcc().getAccount(), trans)) {
                                                        sessionActions(userName, "Transfer of " + trans + " Savings to Credit");
                                                        logger("Transfer of " + trans + " Savings to Credit");
                                                        System.out.println("New savings balance: $" + user.getSaveAcc().inquire());
                                                        System.out.println("New credit balance: $" + user.getCredAcc().inquire());
                                                    }
                                                    break;
                                                default:
                                                    logger("User entered invalid option selection -> " + to);
                                                    System.out.println("Invalid entry. Please try again.");
                                            }
                                            break;
                                        case 3:
                                            switch (to) {
                                                case 1:
                                                    if (user.getCredAcc().transfer(user, user.getAccount(), trans)) {
                                                        sessionActions(userName, "Transfer of " + trans + " Credit to Checking");
                                                        logger("Transfer of " + trans + " Credit to Checking");
                                                        System.out.println("New credit balance: $" + user.getCredAcc().inquire());
                                                        System.out.println("New checking balance: $" + user.inquire());
                                                    }
                                                    break;
                                                case 2:
                                                    if (user.getCredAcc().transfer(user, user.getSaveAcc().getAccount(), trans)) {
                                                        sessionActions(userName, "Transfer of " + trans + " Credit to Savings");
                                                        logger("Transfer of " + trans + " Credit to Savings");
                                                        System.out.println("New credit balance: $" + user.getCredAcc().inquire());
                                                        System.out.println("New savings balance: $" + user.getSaveAcc().inquire());
                                                    }
                                                    break;
                                                default:
                                                    logger("User entered invalid option selection -> " + to);
                                                    System.out.println("Invalid entry. Please try again.");
                                            }
                                            break;
                                        default:
                                            logger("User entered invalid option selection -> " + sels);
                                            System.out.println("Invalid entry. Please try again.");
                                    }
                                }
                                case 6 -> {
                                    logger(userName.getFirstName() + " " + userName.getLastName() + " logged out at " + new Timestamp(currentTimeMillis()));
                                    System.out.println("...successfully logged out");
                                    balanceSheet(accounts, clients);
                                    loggedOn = false;
                                }
                                case 7 -> {
                                    logger(userName.getFirstName() + " " + userName.getLastName() + " terminated entire session at " + new Timestamp(currentTimeMillis()));
                                    System.out.println("\tThank you for being a preferred customer of Miner Bank! See you soon!\n");
                                    balanceSheet(accounts, clients);
                                    loggedOn = false;
                                    System.exit(0);
                                }
                                default -> {
                                    System.out.println();
                                    logger("Invalid option selection: " + sels);
                                    System.out.println("Invalid Entry, Please try again");
                                }
                            }
                        }
                    }
                    //else if user is not in system and would like to create a new account
                    else if (Integer.parseInt(loginOrCreate) == 2) {
                        String fName, lName, address, DOB, phone, email, password;

                        System.out.println("Step 1 of 7: Enter First Name");
                        System.out.print(">>>");
                        fName = cursor.nextLine();
                        if (fName.length() < 1) {
                            System.out.println("Invalid credentials. Please start over and try again");
                        }
                        //proceed
                        else {
                            System.out.println("Step 2 of 7: Enter Last Name");
                            System.out.print(">>>");
                            lName = cursor.nextLine();
                            if (lName.length() < 1) {
                                System.out.println("Invalid credentials. Please start over and try again");
                            }
                            //proceed
                            else {
                                System.out.println("Step 3 of 7: Enter Address");
                                System.out.print(">>>");
                                address = cursor.nextLine();
                                if (address.length() < 1) {
                                    System.out.println("Invalid credentials. Please start over and try again");
                                }
                                //proceed
                                else {
                                    System.out.println("Step 4 of 7: Enter DOB");
                                    System.out.print(">>>");
                                    DOB = cursor.nextLine();
                                    if (DOB.length() < 1) {
                                        System.out.println("Invalid credentials. Please start over and try again");
                                    }
                                    //proceed
                                    else {
                                        System.out.println("Step 5 of 7: Enter Phone Number");
                                        System.out.print(">>>");
                                        phone = cursor.nextLine();
                                        if (phone.length() < 1) {
                                            System.out.println("Invalid credentials. Please start over and try again");
                                        }
                                        //proceed
                                        else {
                                            System.out.println("Step 6 of 7: Enter Email");
                                            System.out.print(">>>");
                                            email = cursor.nextLine();
                                            if (email.length() < 1) {
                                                System.out.println("Invalid credentials. Please start over and try again");
                                            }
                                            //proceed
                                            else {
                                                System.out.println("Step 7 of 7: Create password");
                                                System.out.print(">>>");
                                                password = cursor.nextLine();
                                                if (password.length() < 1) {
                                                    System.out.println("Invalid credentials. Please start over and try again");
                                                }
                                                //successful creation
                                                else {
                                                    //we need to parse to the end, considering our minus one index
                                                    lastA++;
                                                    lastC++;
                                                    //then parse one more index after to add new account
                                                    clients.put(String.valueOf(lastC + 1), new Customer(fName, lName, DOB, address, phone, String.valueOf(lastC + 1), String.valueOf(lastA + 1), String.valueOf(lastA + 1001), String.valueOf(lastA + 2001), password, email));
                                                    accounts.put(String.valueOf(lastC + 1), new Checking(String.valueOf(lastC + 1), String.valueOf(lastA + 1), String.valueOf(lastA + 1001), String.valueOf(lastA + 2001), 0.00, 0.00, 0.00, 0.00));
                                                    System.out.println("Account for " + fName + " " + lName + " has been created successfully!");
                                                    logger("Created account for " + fName + " " + lName);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //catch if user enters a non integer as an option to login or create an account
                catch (NumberFormatException e){
                    logger("Number format exception when choosing to login or create an account. Error caught");
                    System.out.println("Invalid Entry, please try again");
                }
            }
            //else if user did not enter a yes or no answer
            else{
                logger("Invalid option selection");
                System.out.println("Invalid Entry, please try again");
            }
        }
    }

    /**
     * This method clears the Trans_log.txt file by either creating if need be, or overwriting
     */
    public static void clearLog(){
        try {
            File transLog = new File(logPath);
            //if translog does not exist, we create, timestamp, and then prepare to write to logger
            if(!transLog.exists()) {
                FileWriter transactionLogWriter = new FileWriter(transLog, true);
                transactionLogWriter.write("\t\tTransaction Log Created on " + LocalDateTime.now() + "\n");
                transactionLogWriter.close();
            }
            //otherwise if file exist, we overwrite previous versions to prepare our new session logger
            else {
                FileWriter transactionLogWriter = new FileWriter(transLog, false);
                transactionLogWriter.write("\t\tTransaction Log Created on " + LocalDateTime.now() + "\n");
                transactionLogWriter.close();
            }
        }
        //this is a general Exception because we have handled any case of the file not being present by creating one
        catch (Exception errorInMethodClearLog) {
            System.out.println("Check for errors in method clearLog");
            errorInMethodClearLog.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * @param log is the data to be added to Trans_log.txt
     * This method appends new data to Trans_log.txt
     */
    public static void logger(String log){
        try {
            File transLog = new File("src\\TransLog_Team3_PA4.txt");
            FileWriter transactionLogWriter = new FileWriter(transLog, true);
            transactionLogWriter.write(log + "\n");
            transactionLogWriter.close();
        }
        //this is a general Exception because we have handled any case of the file not being present in method clearLog
        catch (Exception errorInMethodLogger) {
            System.out.println("Check for errors in method logger");
            errorInMethodLogger.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates BalanceSheet.csv where any changes to accounts can be seen after end of session
     *
     * @param accounts the map for account data
     * @param customers the map for customer data
     */
    public static void balanceSheet(Map accounts, Map customers){
        try {
            Files.deleteIfExists(Path.of(updatedCSV));
            FileWriter data = new FileWriter(updatedCSV);
            Set keys = accounts.keySet();
            for (Object key : keys) {
                String idNo = (String) key;
                Checking acc = (Checking) accounts.get(idNo);
                Customer customer = (Customer) customers.get(idNo);
                data.write("First Name,Last Name,Date of Birth,Address,Phone Number,Email,Password,ID Number,Checking Account Number,Checking Account Balance,Savings Account Number,Savings Account Balance,Credit Account Number,Credit Account Balance,Credit Account Limit\n");
                data.write(customer.toCSV() + acc.toCSV());
                data.append("\n");
            }
            data.close();
        } catch (IOException e){
            logger("Error clearing previous balance sheet in RunBank.java method balanceSheet");
            System.exit(1);
        }
    }

    /**
     * Stores actions done by customers in session for bank statement creation
     *
     * @param cUser the map for account data
     * @param action the recorded action
     */
    public static void sessionActions(Customer cUser, String action){
        if(seshLog.get(cUser.getIdNo()) == null){
            seshLog.put(cUser.getIdNo(),action+"\n");
        }else{
            seshLog.put(cUser.getIdNo(),seshLog.get(cUser.getIdNo()+action+"\n"));
        }
    }
}

