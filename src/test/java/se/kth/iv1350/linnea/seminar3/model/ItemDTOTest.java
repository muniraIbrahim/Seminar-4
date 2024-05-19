package se.kth.iv1350.linnea.seminar3.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ItemDTOTest {
    private ItemDTO item;
    
    @BeforeEach
    public void setUp() {
        item = new ItemDTO("BigWheel Oatmeal", "abc123", new Amount(2821), "BigWheel Oatmeal 500 g, "
                    + "whole grain oats, high fiber, gluten free", 6);
    }
    
    @AfterEach
    public void tearDown() {
        item = null;
    }
    
    @Test
    public void testItemDTO(){
        double vatRate = (double) item.getVatRate()/100;
        Amount expectedResult = item.getPrice().multiply(vatRate);
        Amount result = item.getVat();
        assertEquals(expectedResult, result, "VAT Amount is not correct.");
    }

    @Test
    public void testNotEqualsNull() {
        Object other = null;
        boolean expResult = false;
        boolean result = item.equals(other);
        assertEquals(expResult, result, "ItemDTO instance equal to null.");
    }

    @Test
    public void testNotEqualsJavaLangObject() {
        Object other = new Object();
        boolean expResult = false;
        boolean result = item.equals(other);
        assertEquals(expResult, result, "ItemDTO instance equal to java.lang.Object instance.");
    }

    @Test
    public void testEqual() {
        String name = "BigWheel Oatmeal";
        String identifier = "abc123";
        Amount price = new Amount(2821);
        String description = "BigWheel Oatmeal 500 g, whole grain oats, high fiber, gluten free";
        int vatRate = 6;
        ItemDTO otherItem = new ItemDTO(name, identifier, price, description, vatRate);
        boolean expResult = true;
        boolean result = item.equals(otherItem);
        assertEquals(expResult, result, "ItemDTO instances with same states are not equal.");
    }
    
    @Test
    public void testNotEqualName() {
        String name = "Wrong name";
        String identifier = "abc123";
        Amount price = new Amount(2821);
        String description = "BigWheel Oatmeal 500 g, whole grain oats, high fiber, gluten free";
        int vatRate = 6;
        ItemDTO otherItem = new ItemDTO(name, identifier, price, description, vatRate);
        boolean expResult = false;
        boolean result = item.equals(otherItem);
        assertEquals(expResult, result, "ItemDTO instances with different states are equal.");
    }
    
    @Test
    public void testNotEqualIdentifier() {
        String name = "BigWheel Oatmeal";
        String identifier = "Wrong Identifier";
        Amount price = new Amount(2821);
        String description = "BigWheel Oatmeal 500 g, whole grain oats, high fiber, gluten free";
        int vatRate = 6;
        ItemDTO otherItem = new ItemDTO(name, identifier, price, description, vatRate);
        boolean expResult = false;
        boolean result = item.equals(otherItem);
        assertEquals(expResult, result, "ItemDTO instances with different states are equal.");
    }
    
    @Test
    public void testNotEqualPrice() {
        String name = "BigWheel Oatmeal";
        String identifier = "abc123";
        Amount price = new Amount(0);
        String description = "BigWheel Oatmeal 500 g, whole grain oats, high fiber, gluten free";
        int vatRate = 6;
        ItemDTO otherItem = new ItemDTO(name, identifier, price, description, vatRate);
        boolean expResult = false;
        boolean result = item.equals(otherItem);
        assertEquals(expResult, result, "ItemDTO instances with different states are equal.");
    }
    
    @Test
    public void testNotEqualDescription() {
        String name = "BigWheel Oatmeal";
        String identifier = "abc123";
        Amount price = new Amount(2821);
        String description = "Wrong Description";
        int vatRate = 6;
        ItemDTO otherItem = new ItemDTO(name, identifier, price, description, vatRate);
        boolean expResult = false;
        boolean result = item.equals(otherItem);
        assertEquals(expResult, result, "ItemDTO instances with different states are equal.");
    }
    
    @Test
    public void testNotEqualVatRate() {
        String name = "BigWheel Oatmeal";
        String identifier = "abc123";
        Amount price = new Amount(2821);
        String description = "BigWheel Oatmeal 500 g, whole grain oats, high fiber, gluten free";
        int vatRate = 0;
        ItemDTO otherItem = new ItemDTO(name, identifier, price, description, vatRate);
        boolean expResult = false;
        boolean result = item.equals(otherItem);
        assertEquals(expResult, result, "ItemDTO instances with different states are equal.");
    }

    
}
