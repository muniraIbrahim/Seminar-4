package se.kth.iv1350.linnea.seminar3.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LineItemTest {
    private ItemDTO item;
    private LineItem lineItem;
    
    @BeforeEach
    public void setUp() {
        item = new ItemDTO("BigWheel Oatmeal", "abc123", new Amount(2821), "BigWheel Oatmeal 500 g, "
                    + "whole grain oats, high fiber, gluten free", 6);
        lineItem = new LineItem(item, 1);
    }
    
    @AfterEach
    public void tearDown() {
        item = null;
        lineItem = null;
    }

    @Test
    public void testaddQuantityNonZero() {
        int quantity = 2;
        int expectedResult = lineItem.getQuantity() + quantity;
        lineItem.addQuantity(quantity);
        int result = lineItem.getQuantity();
        assertEquals(expectedResult, result, "addQuantity not adding quantity correctly.");
    }

    @Test
    public void testaddQuantityZero() {
        int quantity = 0;
        int expectedResult = lineItem.getQuantity() + quantity;
        lineItem.addQuantity(quantity);
        int result = lineItem.getQuantity();
        assertEquals(expectedResult, result, "addQuantity not adding quantity correctly.");
    }    
    
    @Test
    public void testToStringForContent() {
        String string = lineItem.toString();
        assertTrue(string.contains(item.getName()), "String does not contain item name.");
        assertTrue(string.contains(Integer.toString(lineItem.getQuantity())), "String does not contain quantity.");
        assertTrue(string.contains(item.getPrice().plus(item.getVat()).toString()), "String does not contain item price.");
        assertTrue(string.contains(item.getPrice().plus(item.getVat()).multiply(lineItem.getQuantity()).toString()), "String does not contain price * quantity.");
    }
    
}
