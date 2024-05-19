package se.kth.iv1350.linnea.seminar3.integration;

/**
 * Represents a customer.
 */
public class CustomerDTO {
    private String customerID;
    
    /**
     * Creates a new instance.
     * 
     * @param customerID of the customer.
     */
    public CustomerDTO (String customerID){
        this.customerID = customerID;
    }
    
    /**
     * @return the customerID attribute.
     */
    public String getCustomerID(){
        return customerID;
    }
}
