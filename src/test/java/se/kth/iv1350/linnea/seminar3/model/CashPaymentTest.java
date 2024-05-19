package se.kth.iv1350.linnea.seminar3.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CashPaymentTest {
    private Amount paidAmount;
    private Sale sale;
    
    @BeforeEach
    public void setUp() {
        paidAmount = new Amount(15000);
        sale = new Sale();
    }
    
    @AfterEach
    public void tearDown() {
        paidAmount = null;
        sale = null;
    }

    @Test
    public void testCashPaymentChangeCalculationNonZero() {
        ItemDTO item = new ItemDTO("BigWheel Oatmeal", "abc123", new Amount(2821), "BigWheel Oatmeal 500 g, "
                    + "whole grain oats, high fiber, gluten free", 6);
        sale.registerItem(item);
        CashPayment payment = new CashPayment(paidAmount, sale);
        Amount expectedResult = paidAmount.minus(sale.getRunningTotal());
        Amount result = payment.getChange();
        assertEquals(expectedResult, result, "Calculated change is incorrect");
    }
    
    @Test
    public void testCashPaymentChangeCalculationZero() {
        CashPayment payment = new CashPayment(paidAmount, sale);
        Amount expectedResult = paidAmount.minus(sale.getRunningTotal());
        Amount result = payment.getChange();
        assertEquals(expectedResult, result, "Calculated change is incorrect");
    }
}
