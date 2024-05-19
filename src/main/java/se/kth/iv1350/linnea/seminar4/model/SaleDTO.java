package se.kth.iv1350.linnea.seminar3.model;

import java.util.ArrayList;

/**
 * Contains information about a single sale.
 */
public class SaleDTO {
    private final Amount runningTotal;
    private final Amount runningVat;
    private final ArrayList<LineItemDTO> itemList;

    /**
     * Creates a new instance.
     * 
     * @param sale the current sale.
     */
    public SaleDTO(Sale sale){
        this.runningTotal = sale.getRunningTotal();
        this.runningVat = sale.getRunningVat();
        this.itemList = sale.getItemListDTO();
    }
    
    /**
     * Returns runningTotal attribute.
     * 
     * @return runningTotal.
     */
    public Amount getRunningTotal(){
        return runningTotal;
    }
    
    /**
     * @return itemList attribute. 
     */
    public ArrayList<LineItemDTO> getItemList(){
        return itemList;
    }
    
}
