package se.kth.iv1350.linnea.seminar3.view;

import se.kth.iv1350.linnea.seminar3.model.Amount;
import se.kth.iv1350.linnea.seminar3.model.SaleDTO;
import se.kth.iv1350.linnea.seminar3.model.SaleObserver;

/**
 * Shows total income from all sales.
 */
public class TotalRevenueView implements SaleObserver {
    private Amount totalRevenue = new Amount();
    
    /**
     * Invoked when a sale has been completed.
     * 
     * @param sale the sale that was completed.
     */
    @Override
    public void newSale(SaleDTO sale){
        totalRevenue = totalRevenue.plus(sale.getRunningTotal());
        printCurrentTotalRevenue();
    }
    
    private void printCurrentTotalRevenue(){
        System.out.println("Current total revenue is: " + totalRevenue.toString());
    }

}
