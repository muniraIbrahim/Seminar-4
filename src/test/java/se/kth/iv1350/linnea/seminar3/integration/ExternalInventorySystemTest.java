package se.kth.iv1350.linnea.seminar3.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExternalInventorySystemTest {
    private ExternalInventorySystem instanceToTest;
    
    @BeforeEach
    public void setUp() {
        instanceToTest = new ExternalInventorySystem();
    }
    
    @AfterEach
    public void tearDown() {
        instanceToTest = null;
    }

    @Test
    public void testFindItemExists() {
        String itemIdentifier = "abc123";
        String expResult = itemIdentifier;
        try {
            String result = instanceToTest.findItem(itemIdentifier).getIdentifier();
            assertEquals(expResult, result, "Existing ItemDTO not found.");
        }
        catch (InvalidIdentifierException e){
            fail("Got an exception.");
        }
    }
    
    @Test
    public void testFindItemNotFound(){
        String itemIdentifier = "wrong itemIdentifier";
        try {
            instanceToTest.findItem(itemIdentifier);
            fail("Able to search for invalid identifier");
        }
        catch (InvalidIdentifierException e) {
            assertTrue(e.getMessage().contains(itemIdentifier), "Wrong exception message does not contain entered identifier");
            assertTrue(e.getInvalidIdentifier().equals(itemIdentifier), "Wrong identifier is specified, " + e.getInvalidIdentifier());
        }
    }
    
    @Test
    public void testFindItemNoConnection(){
        String itemIdentifier = "Simulate database error";
        try {
            instanceToTest.findItem(itemIdentifier);
            fail("Able to search for identifier when connection to external inventory system is faulty.");
        }
        catch (ExternalInventorySystemException e){
            assertTrue(e.getMessage().contains("Call to external inventory system failed"), "Wrong error message");
        }
        catch (InvalidIdentifierException e) {
            fail("Wrong exception thrown.");
        }
    }
    
}
