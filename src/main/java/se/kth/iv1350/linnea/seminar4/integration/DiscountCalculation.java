package se.kth.iv1350.linnea.seminar3.integration;

import se.kth.iv1350.linnea.seminar3.model.Amount;
import se.kth.iv1350.linnea.seminar3.model.SaleDTO;

/**
 * Specifies an object that calculates the discount on a sale.
 */
public interface DiscountCalculation {
    
    /**
     * Calculates a new total price for the sale based on discount rules.
     * 
     * @param customer the customer requesting a discount.
     * @param sale the current sale.
     * @return a new total price for the sale.
     */
    public Amount calculateDiscount (CustomerDTO customer, SaleDTO sale);
    
}
