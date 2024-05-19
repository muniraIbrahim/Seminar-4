package se.kth.iv1350.linnea.seminar3.model;

/**
 * Represents a line item in the sale. All fields are immutable.
 */
public class LineItemDTO {
    private final ItemDTO item;
    private final int quantity;
    
    /**
     * Creates a new instance.
     * 
     * @param lineItem the instance to be copied
     */
    LineItemDTO(LineItem lineItem){
        this.item = lineItem.getItem();
        this.quantity = lineItem.getQuantity();
    }
    
    /**
     * Returns item attribute.
     * 
     * @return item
     */
    public ItemDTO getItem(){
        return item;
    }

    /**
     * Returns quantity attribute.
     * 
     * @return quantity
     */
    public int getQuantity(){
        return quantity;
    }
}
