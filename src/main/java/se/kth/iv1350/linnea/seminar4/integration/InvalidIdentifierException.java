package se.kth.iv1350.linnea.seminar3.integration;

/**
 * Thrown when the searched for item identifier does not match any item in the inventory system.
 */
public class InvalidIdentifierException extends Exception {
    private String invalidIdentifier;
    
    /**
     * Creates a new instance with a message specifying the entered invalid identifier.
     * 
     * @param invalidIdentifier the entered identifier which is invalid.
     */
    InvalidIdentifierException(String invalidIdentifier){
        super("Unable to find item, " + invalidIdentifier + " is an invalid identifier.");
        this.invalidIdentifier = invalidIdentifier;
    }
    
    /**
     * Returns the invalidIdentifier parameter.
     * 
     * @return invalidIdentifier.
     */
    public String getInvalidIdentifier(){
        return invalidIdentifier;
    }

}
