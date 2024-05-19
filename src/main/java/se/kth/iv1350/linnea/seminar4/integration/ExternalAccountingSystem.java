package se.kth.iv1350.linnea.seminar3.integration;

import se.kth.iv1350.linnea.seminar3.model.SaleDTO;

/**
 * Handles all communication with the external accounting system.
 */
public class ExternalAccountingSystem {
    private static final ExternalAccountingSystem EXTERNAL_ACCOUNTING_SYSTEM = new ExternalAccountingSystem();
    
    /**
     * @return The only instance of this singleton. 
     */
    public static ExternalAccountingSystem getExernalAccountingSystem() {
        return EXTERNAL_ACCOUNTING_SYSTEM;
    }
    
    private ExternalAccountingSystem(){
    }
    
    /**
     * Updates the external accounting system with the information from the current sale.
     * 
     * @param sale the current sale.
     */
    public void update(SaleDTO sale){
    }
}
