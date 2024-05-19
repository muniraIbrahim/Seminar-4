package se.kth.iv1350.linnea.seminar3.controller;

import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.linnea.seminar3.integration.ExternalSystemCreator;
import se.kth.iv1350.linnea.seminar3.integration.InvalidIdentifierException;
import se.kth.iv1350.linnea.seminar3.integration.Printer;
import se.kth.iv1350.linnea.seminar3.model.Amount;
import se.kth.iv1350.linnea.seminar3.model.ItemAndRunningTotalDTO;
import se.kth.iv1350.linnea.seminar3.model.ItemDTO;

public class ControllerTest {
    private Printer printer;
    private ExternalSystemCreator creator;
    private Controller contr;
    
    @BeforeEach
    public void setUp() throws IOException {
        printer = new Printer();
        creator = new ExternalSystemCreator();
        contr = new Controller(creator, printer);
        contr.startSale();
    }
    
    @AfterEach
    public void tearDown() {
        printer = null;
        creator = null;
        contr = null;
    }

    @Test
    public void testEnterIdentifierValidIdentifier() {
        String validIdentifier = "abc123";
        ItemDTO expectedResult = new ItemDTO("BigWheel Oatmeal", "abc123", new Amount(2821), "BigWheel Oatmeal 500 g, "
                    + "whole grain oats, high fiber, gluten free", 6);
        try {
            ItemAndRunningTotalDTO result = contr.enterIdentifier(validIdentifier);
            assertEquals(expectedResult, result.getItem(), "enterIdentifier not returning correct item.");
            assertEquals(expectedResult.getPrice().plus(expectedResult.getVat()), result.getRunningTotal(), "enterIdentifier not returning correct running total.");
        }
        catch (Exception e) {
            fail("Got an exception");
        }
        
    }
    
    @Test
    public void testEnterIdentifierInvalidIdentifier(){
        String invalidIdentifier = "invalid identifier";
        try {
            contr.enterIdentifier(invalidIdentifier);
            fail("Able to enter identifier that is invalid.");
        }
        catch (InvalidIdentifierException e){
            assertTrue(e.getMessage().contains(invalidIdentifier), "Wrong exception message does not contain entered identifier");
            assertTrue(e.getInvalidIdentifier().equals(invalidIdentifier), "Wrong identifier is specified, " + e.getInvalidIdentifier());
        }
        catch (OperationFailedException e){
            fail("Wrong exception thrown.");
        }
    }
    
    @Test
    public void testEnterIdentifierOperationFailed(){
        String itemIdentifier = "Simulate database error";
        try {
            contr.enterIdentifier(itemIdentifier);
            fail("Able to enter identifier that should trigger error in external inventory system.");
        }
        catch (InvalidIdentifierException e){
            fail("Wrong exception thrown.");
        }
        catch (OperationFailedException e){
            assertTrue(e.getMessage().contains("Could not enter identifier."), "Wrong error message");
        }
    }

    @Test
    public void testEndSale(){
        ItemDTO item = new ItemDTO("BigWheel Oatmeal", "abc123", new Amount(2821), "BigWheel Oatmeal 500 g, "
                    + "whole grain oats, high fiber, gluten free", 6);
        try {
            contr.enterIdentifier("abc123");
            contr.enterIdentifier("abc123");
            Amount expectedResult = item.getPrice().plus(item.getVat()).multiply(2);
            Amount result = contr.endSale();
            assertEquals(expectedResult, result, "endSale not returning correct total price.");
        }
        catch (Exception e){
            fail("An exception was thrown.");
        }
    }

    @Test
    public void testPay() {
        ItemDTO item = new ItemDTO("BigWheel Oatmeal", "abc123", new Amount(2821), "BigWheel Oatmeal 500 g, "
                    + "whole grain oats, high fiber, gluten free", 6);
        try {
            contr.enterIdentifier("abc123");
            contr.enterIdentifier("abc123");
            Amount totalPrice = item.getPrice().plus(item.getVat()).multiply(2);
            Amount amountPaid = new Amount(10000);
            Amount expectedResult = amountPaid.minus(totalPrice);
            Amount result = contr.pay(amountPaid);
            assertEquals(expectedResult, result, "pay not returning correct change.");
        }
        catch (Exception e){
            fail("An exception was thrown.");
        }
    }
    
}
