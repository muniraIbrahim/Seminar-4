package se.kth.iv1350.linnea.seminar3.controller;

import java.io.IOException;
import java.util.ArrayList;
import se.kth.iv1350.linnea.seminar3.integration.CustomerDTO;
import se.kth.iv1350.linnea.seminar3.integration.DiscountCalculationCustomerID;
import se.kth.iv1350.linnea.seminar3.integration.DiscountCalculationItemList;
import se.kth.iv1350.linnea.seminar3.integration.DiscountCalculationTotalCost;
import se.kth.iv1350.linnea.seminar3.integration.ExternalAccountingSystem;
import se.kth.iv1350.linnea.seminar3.integration.ExternalInventorySystem;
import se.kth.iv1350.linnea.seminar3.integration.ExternalInventorySystemException;
import se.kth.iv1350.linnea.seminar3.integration.ExternalSystemCreator;
import se.kth.iv1350.linnea.seminar3.integration.InvalidIdentifierException;
import se.kth.iv1350.linnea.seminar3.integration.Printer;
import se.kth.iv1350.linnea.seminar3.model.Amount;
import se.kth.iv1350.linnea.seminar3.model.CashPayment;
import se.kth.iv1350.linnea.seminar3.model.CashRegister;
import se.kth.iv1350.linnea.seminar3.model.ItemAndRunningTotalDTO;
import se.kth.iv1350.linnea.seminar3.model.ItemDTO;
import se.kth.iv1350.linnea.seminar3.model.Receipt;
import se.kth.iv1350.linnea.seminar3.model.Sale;
import se.kth.iv1350.linnea.seminar3.model.SaleDTO;
import se.kth.iv1350.linnea.seminar3.model.SaleObserver;
import se.kth.iv1350.linnea.seminar3.util.LogHandler;

/**
 * This is the application's only controller class. All calls to the model pass
 * through here.
 */
public class Controller {
    private Printer printer;
    private ExternalAccountingSystem externalAccountingSystem;
    private ExternalInventorySystem externalInventorySystem;
    private CashRegister cashRegister;
    private Sale sale;
    private LogHandler logger;
    private ArrayList<SaleObserver> saleObservers = new ArrayList<>();
    
    /**
     * Creates a new Controller instance.
     * 
     * @param creator Used to get all classes that handle calls to external systems.
     * @param printer Interface to printer.
     * @throws IOException if LogHandler cannot be started
     */
    public Controller (ExternalSystemCreator creator, Printer printer) throws IOException{
        this.externalAccountingSystem = creator.getExternalAccountingSystem();
        this.externalInventorySystem = creator.getExternalInventorySystem();
        this.printer = printer;
        this.cashRegister = new CashRegister();
        this.logger = new LogHandler();
    }
    
    /**
     * Starts a new sale by creating and storing a Sale object. Must be carried 
     * out before doing anything else during a sale.
     */
    public void startSale(){
        sale = new Sale();
        sale.addSaleObservers(saleObservers);
    }
    
    /**
     * Searches for an item with the specified item identifier. Registers it in 
     * the sale and returns the item and current running total of the sale.
     * 
     * @param itemIdentifier identifier of the searched for item.
     * @return item and running total of sale.
     * @throws InvalidIdentifierException if the searched for item identifier is invalid.
     * @throws OperationFailedException if external inventory system could not be accessed.
     */
    public ItemAndRunningTotalDTO enterIdentifier(String itemIdentifier) throws InvalidIdentifierException, ExternalInventorySystemException, OperationFailedException {
        try {
        ItemDTO searchedItem = externalInventorySystem.findItem(itemIdentifier);
        return sale.registerItem(searchedItem);
        }
        catch (ExternalInventorySystemException e) {
            logger.logException(e);
            throw new OperationFailedException("Could not enter identifier.", e);
        }
    }
    
    /**
     * Ends the current sale and returns the total price.
     * 
     * @return total price.
     */
    public Amount endSale(){
        return sale.getRunningTotal();
    }
    
    /**
     * Pays for the current sale. Updates the balance of the cash register where
     * the payment was performed. Calculates change. Prints the receipt.
     * 
     * @param paidAmount the amount the customer has paid.
     * @return the change for the sale.
     */
    public Amount pay(Amount paidAmount){
        CashPayment payment = sale.pay(paidAmount);
        
        SaleDTO saleDTO = new SaleDTO(sale);
        externalAccountingSystem.update(saleDTO);
        externalInventorySystem.update(saleDTO);
        
        Receipt receipt = new Receipt(sale, payment);
        printer.printReceipt(receipt);
        
        cashRegister.addPayment(sale.getRunningTotal());
        
        return payment.getChange();
    }
    
    /**
     * The specified observer will be notified when a sale has been paid.
     * There will be notifications only for sales that are started after this
     * method is called.
     *
     * @param obs The observer to notify.
     */
    public void addSaleObserver(SaleObserver obs) {
        saleObservers.add(obs);
    }
    
    /**
     * Calculates the new total price for the sale based on the discount rules.
     * 
     * @param customer the CustomerID for the customer requesting discount.
     * @return new total price for the sale after discount has been applied.
     */
    public Amount requestDiscount(CustomerDTO customer){
        sale.setDiscountCalculation(new DiscountCalculationItemList());
        sale.calculateDiscount(customer);
        sale.setDiscountCalculation(new DiscountCalculationCustomerID());
        sale.calculateDiscount(customer);
        sale.setDiscountCalculation(new DiscountCalculationTotalCost());
        sale.calculateDiscount(customer);
        return sale.getRunningTotal();
    }
}
