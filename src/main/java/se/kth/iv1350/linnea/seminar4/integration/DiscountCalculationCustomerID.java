package se.kth.iv1350.linnea.seminar3.integration;

import se.kth.iv1350.linnea.seminar3.model.Amount;
import se.kth.iv1350.linnea.seminar3.model.SaleDTO;

/**
 * Calculates discount of sale based on customer ID
 */
public class DiscountCalculationCustomerID implements DiscountCalculation {
    
    /**
     * Calculates the discount of a sale based on the customer ID.
     * 
     * @param customer customerID of customer requesting discount.
     * @param sale current sale.
     * @return the new total price of the sale.
     */
    @Override
    public Amount calculateDiscount(CustomerDTO customer, SaleDTO sale){
        if (customer.getCustomerID().equals("CustomerID with discount")){
            double percentageToBeReduced = 0.1;
            return sale.getRunningTotal().multiply(1-percentageToBeReduced);
        }
        else {
            return sale.getRunningTotal();
        }
    }
}
