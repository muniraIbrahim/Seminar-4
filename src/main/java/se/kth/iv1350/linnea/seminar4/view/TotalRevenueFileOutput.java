package se.kth.iv1350.linnea.seminar3.view;

import java.io.IOException;
import se.kth.iv1350.linnea.seminar3.model.Amount;
import se.kth.iv1350.linnea.seminar3.model.SaleDTO;
import se.kth.iv1350.linnea.seminar3.model.SaleObserver;
import se.kth.iv1350.linnea.seminar3.util.TotalRevenueLogger;

/**
 * Shows total income from all sales.
 */
public class TotalRevenueFileOutput implements SaleObserver {
    private Amount totalRevenue;
    private TotalRevenueLogger logger;
    
    /**
     * Creates a new instance
     * 
     * @throws IOException if the LogHandler cannot be initialized.
     */
    public TotalRevenueFileOutput() throws IOException{
        totalRevenue = new Amount();
        this.logger = new TotalRevenueLogger();
    }
    
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
        logger.log("Current total revenue is: " + totalRevenue.toString());
    }

}