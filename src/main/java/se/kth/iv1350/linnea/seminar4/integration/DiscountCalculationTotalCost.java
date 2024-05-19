package se.kth.iv1350.linnea.seminar3.integration;

import se.kth.iv1350.linnea.seminar3.model.Amount;
import se.kth.iv1350.linnea.seminar3.model.SaleDTO;

/**
 * Calculates discount of sale based on the total cost of the sale.
 */
public class DiscountCalculationTotalCost implements DiscountCalculation {
    
    /**
     * Calculates discount of sale based on total cost of sale.
     * 
     * @param customer customerID of customer requesting discount.
     * @param sale current sale.
     * @return new total price for sale after discount has been applied.
     */
    @Override
    public Amount calculateDiscount(CustomerDTO customer, SaleDTO sale){
        Amount costThatGivesDiscount = new Amount(5000);
        if (sale.getRunningTotal().compareTo(costThatGivesDiscount) >= 0){
            double percentageToBeReduced = 0.1;
            return sale.getRunningTotal().multiply(1-percentageToBeReduced);
        }
        else {
            return sale.getRunningTotal();
        }
    }
}
