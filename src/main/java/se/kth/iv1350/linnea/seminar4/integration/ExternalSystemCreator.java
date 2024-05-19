
package se.kth.iv1350.linnea.seminar3.integration;

/**
 * This class creates and passes references to the external systems.
 * 
 */
public class ExternalSystemCreator {
    private ExternalAccountingSystem externalAccountingSystem;
    private ExternalInventorySystem externalInventorySystem;
    
    /**
    * Creates a new instance of the external inventory system and
    * gets the reference to the singleton class ExternalACCountingSystem.
    */
    public ExternalSystemCreator(){
        this.externalAccountingSystem = ExternalAccountingSystem.getExernalAccountingSystem();
        this.externalInventorySystem = new ExternalInventorySystem();
    }
    
    /**
     * Returns the ExternalAccountingSystem.
     * 
     * @return the ExternalAccountingSystem.
     */
    public ExternalAccountingSystem getExternalAccountingSystem(){
        return this.externalAccountingSystem;
    }
    
    /**
     * Returns the ExternalInventorySystem.
     * 
     * @return the ExternalInventorySystem.
     */
    public ExternalInventorySystem getExternalInventorySystem(){
        return this.externalInventorySystem;
    }
    
}
