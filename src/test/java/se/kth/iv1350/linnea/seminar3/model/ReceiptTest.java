package se.kth.iv1350.linnea.seminar3.model;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReceiptTest {
    private Sale sale;
    private CashPayment payment;
    
    @BeforeEach
    public void setUp() {
        Amount paidAmount = new Amount(10000);
        sale = new Sale();
        ItemDTO item = new ItemDTO("BigWheel Oatmeal", "abc123", new Amount(2821), "BigWheel Oatmeal 500 g, "
                    + "whole grain oats, high fiber, gluten free", 6);
        sale.registerItem(item);
        payment = new CashPayment(paidAmount, sale);
    }
    
    @AfterEach
    public void tearDown() {
        sale = null;
        payment = null;
    }

    @Test
    public void testToStringForContent() {
        Receipt receipt = new Receipt(sale, payment);
        String string = receipt.toString();
        assertTrue(string.contains(sale.getDateOfSale().toString()), "String does not contain date.");
        assertTrue(string.contains(sale.getTimeOfSale().withNano(0).toString()), "String does not contain time.");
        ArrayList<LineItem> itemList = sale.getItemList();
        for (int i = 0; i < itemList.size(); i++){
            assertTrue(string.contains(itemList.get(i).toString()), "String does not contain item information.");
        }
        assertTrue(string.contains(sale.getRunningTotal().toString()), "String does not contain total price.");
        assertTrue(string.contains(sale.getRunningVat().toString()), "String does not contain total VAT.");
        assertTrue(string.contains(payment.getPaidAmount().toString()), "String does not contain paid amount.");
        assertTrue(string.contains(payment.getChange().toString()), "String does not contain change.");
    }
    
}
