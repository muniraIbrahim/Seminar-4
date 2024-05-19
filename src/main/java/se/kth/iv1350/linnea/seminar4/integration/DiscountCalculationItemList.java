package se.kth.iv1350.linnea.seminar3.integration;

import java.util.ArrayList;
import se.kth.iv1350.linnea.seminar3.model.Amount;
import se.kth.iv1350.linnea.seminar3.model.ItemDTO;
import se.kth.iv1350.linnea.seminar3.model.LineItemDTO;
import se.kth.iv1350.linnea.seminar3.model.SaleDTO;

/**
 * Calculates discount for sale based on items bought.
 */
public class DiscountCalculationItemList implements DiscountCalculation {
    
    /**
     * Calculates discount based on items bought.
     * 
     * @param customer customerID of customer requesting discount.
     * @param sale current sale.
     * @return total price after discount has been applied.
     */
    @Override
    public Amount calculateDiscount(CustomerDTO customer, SaleDTO sale){
        Amount priceAfterDiscount = sale.getRunningTotal();
        ArrayList<LineItemDTO> itemList = sale.getItemList();
        ItemDTO discountItem = new ItemDTO("YouGoGo Blueberry", "def456", new Amount(1406), "YouGoGo Blueberry 240 g, "
                    + "low sugar youghurt, blueberry flavour", 6);
        for (LineItemDTO lineItem : itemList) {
            if (lineItem.getItem().equals(discountItem)){
                Amount amountToBeReduced = new Amount(500);
                priceAfterDiscount = priceAfterDiscount.minus(amountToBeReduced.multiply(lineItem.getQuantity()));
            }
        }
        return priceAfterDiscount;
    }
}
