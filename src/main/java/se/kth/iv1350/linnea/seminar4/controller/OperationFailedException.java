package se.kth.iv1350.linnea.seminar3.controller;

/**
 * Thrown when an operation could not be completed and the cause is unknown.
 */
public class OperationFailedException extends Exception{
    
    /**
     * Creates a new instance with the specified message and root cause.
     *
     * @param messgage   The exception message.
     * @param cause The exception that caused this exception.
     */
    public OperationFailedException(String message, Exception cause) {
        super(message, cause);
    }

}
