package se.kth.iv1350.linnea.seminar3.model;

/**
 * A listener interface for receiving notifications about sales.
 */
public interface SaleObserver {
    
    /**
     * Invoked when a sale has been completed.
     * 
     * @param sale the sale that was completed.
     */
    void newSale(SaleDTO sale);

}
