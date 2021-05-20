import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class RunBankTest {

    private static String csvToRead = "src\\CS 3331 - Bank Users 4.csv";
    private static String updatedCSV = "src\\UpdatedCSV.csv";
    private static String transLog = "src\\Translog.txt";
    private static HashMap<String,String> seshLog = new HashMap<String,String>();

    Checking subject = new Checking("1","1","2","3",100.00,100.00,-500.00,1000.00);
    Checking customer = new Checking("2","12","22","32",100.00,100.00,-100.00,1000.00);

    @Test
    public void inquireUpdating(){
        assertEquals(100.00,subject.inquire());
        assertEquals(-500.00,subject.getCredAcc().inquire());
        subject.getCredAcc().deposit("400.00",null);
        assertEquals(-100.00,subject.getCredAcc().inquire());
        subject.withdraw("50");
        assertEquals(50,subject.inquire());
    }
    @Test
    public void depositUpdating(){
        subject.deposit("1.00",null);
        assertEquals(101.00,subject.inquire());
        assertFalse(subject.deposit("0.00",null));
        assertTrue(subject.deposit("10000.00",null));
        assertFalse(subject.deposit("-500.00",null));
    }
    @Test
    public void transferUpdating(){
        assertTrue(subject.transfer("2","10.00"));
        assertFalse(subject.transfer("2","500.00"));
        assertFalse(subject.transfer("2","-500.00"));
        assertTrue(subject.getSaveAcc().transfer("1","5.00"));
    }
    @Test
    public void withdrawUpdating(){
        assertTrue(subject.withdraw("99.99"));
        assertFalse(subject.withdraw("1.00"));
        assertFalse(subject.getCredAcc().withdraw("-501.00"));
        assertTrue(subject.getCredAcc().withdraw("499.99"));
    }
    @Test
    public void payUpdating(){
        subject.deposit("5000.00",null);
        assertTrue(subject.pay(customer,"2.00"));
        assertTrue(subject.getSaveAcc().pay(customer,"7.00"));
        assertFalse(subject.pay(customer,"-1.00"));
        assertTrue(subject.pay(customer,"0.00"));
    }

}