package se.kth.iv1350.linnea.seminar3.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import se.kth.iv1350.linnea.seminar3.integration.CustomerDTO;
import se.kth.iv1350.linnea.seminar3.integration.DiscountCalculation;

/**
 * Represents one sale, paid for by one payment.
 */
public class Sale {
    private LocalDate dateOfSale;
    private LocalTime timeOfSale;
    private Amount runningTotal;
    private Amount runningVat;
    private ArrayList<LineItem> itemList;
    private ArrayList<SaleObserver> saleObservers;
    private DiscountCalculation discountCalculation;
    
    /**
     * Creates a new instance and saves the date and time of sale.
     */
    public Sale(){
        runningTotal = new Amount(0);
        runningVat = new Amount(0);
        itemList = new ArrayList<>();
        saleObservers = new ArrayList<>();
        
        dateOfSale = LocalDate.now();
        timeOfSale = LocalTime.now();
    }
    
    /**
     * Registers the entered item in the sale, returns the item and the current running total.
     * 
     * @param item current item being registered.
     * @return the item and running total.
     */
    public ItemAndRunningTotalDTO registerItem (ItemDTO item){
        int indexOfItem = indexIfAlreadyRegistered(item);
        if (indexOfItem != -1){
            itemList.get(indexOfItem).addQuantity(1);
        }
        else {
            itemList.add(new LineItem(item, 1));
        }
        runningTotal = runningTotal.plus(item.getPrice()).plus(item.getVat());
        runningVat = runningVat.plus(item.getVat());
        return new ItemAndRunningTotalDTO(item, runningTotal, runningVat);
    }
    
    private int indexIfAlreadyRegistered(ItemDTO searchedItem){
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getItem().equals(searchedItem)){
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Pays for the sale with the paid amount.
     * 
     * @param paidAmount the amount paid.
     * @return a new CashPayment based on the paid amount and current sale.
     */
    public CashPayment pay (Amount paidAmount){
        CashPayment payment = new CashPayment(paidAmount, this);
        notifyObservers();
        return payment;
    }
    
    /**
     * Returns the runningTotal attribute.
     * 
     * @return runningTotal.
     */
    public Amount getRunningTotal(){
        return runningTotal;
    }
    
    /**
     * Returns the runningVat attribute.
     * 
     * @return runningVat.
     */
    public Amount getRunningVat(){
        return runningVat;
    }
    
    /**
     * Returns an immutable copy of itemList.
     * 
     * @return an ArrayList with LineItemDTOs reflecting the content of itemList.
     */
    public ArrayList<LineItemDTO> getItemListDTO(){
        ArrayList<LineItemDTO> copy = new ArrayList<>();
        for (LineItem element : itemList) {
            copy.add(new LineItemDTO(element)); 
        }
        return copy;
    }
    
    /**
     * Returns the itemList attribute.
     * 
     * @return itemList.
     */
    public ArrayList<LineItem> getItemList(){
        return itemList;
    }
    
    /**
     * Returns the dateOfSale attribute.
     * 
     * @return dateOfSale.
     */
    public LocalDate getDateOfSale(){
        return dateOfSale;
    }
    
    /**
     * Returns the timeOfSale attribute.
     * 
     * @return timeOfSale.
     */
    public LocalTime getTimeOfSale(){
        return timeOfSale;
    }

    private void notifyObservers() {
        for (SaleObserver obs : saleObservers) {
            obs.newSale(new SaleDTO(this));
        }
    }
    
    /**
     * The specified observer will be notified when this sale has been paid.
     * 
     * @param obs The observer to notify. 
     */
    public void addSaleObserver(SaleObserver obs) {
        saleObservers.add(obs);
    }
    

    /**
     * All the specified observers will be notified when this rental has been paid.
     * 
     * @param observers The observers to notify. 
     */
    public void addSaleObservers(ArrayList<SaleObserver> observers) {
        saleObservers.addAll(observers);
    }
    
    /**
     * Sets the discountCalculation.
     * 
     * @param discountCalculation the DiscountCalculation to be set.
     */
    public void setDiscountCalculation(DiscountCalculation discountCalculation){
        this.discountCalculation = discountCalculation;
    }
    
    /**
     * Calculates the discount for the current sale.
     * 
     * @param customer the CustomerID for the customer requesting the discount.
     * @return new total price for the sale after discount has been applied.
     */
    public Amount calculateDiscount(CustomerDTO customer){
        Amount newTotalPrice = discountCalculation.calculateDiscount(customer, new SaleDTO(this));
        runningTotal = newTotalPrice;
        return newTotalPrice;
    }
}
