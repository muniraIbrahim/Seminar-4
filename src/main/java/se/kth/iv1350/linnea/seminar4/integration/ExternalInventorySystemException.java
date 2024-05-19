package se.kth.iv1350.linnea.seminar3.integration;

/**
 * Thrown when there is an issue calling the external inventory system.
 */
public class ExternalInventorySystemException extends RuntimeException {
    
    /**
     * Creates a new instance.
     * 
     * @param message that recounts what went wrong.
     */
    ExternalInventorySystemException(String message){
        super(message);
    }

}
