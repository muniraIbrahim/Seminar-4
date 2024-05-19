package se.kth.iv1350.linnea.seminar3.view;

import java.io.IOException;
import se.kth.iv1350.linnea.seminar3.controller.Controller;
import se.kth.iv1350.linnea.seminar3.controller.OperationFailedException;
import se.kth.iv1350.linnea.seminar3.integration.CustomerDTO;
import se.kth.iv1350.linnea.seminar3.integration.InvalidIdentifierException;
import se.kth.iv1350.linnea.seminar3.model.Amount;
import se.kth.iv1350.linnea.seminar3.model.ItemAndRunningTotalDTO;
import se.kth.iv1350.linnea.seminar3.util.LogHandler;

/**
 * This is a placeholder for the real view. It contains a hardcoded execution with calls to
 * all system operations in the Controller.
 */
public class View {
    private Controller contr;
    private ErrorMessageHandler errorMsgHandler = new ErrorMessageHandler();
    private LogHandler logger;
    
    /**
     * Creates a new instance of View.
     * 
     * @param contr Controller that is used for all operations.
     * @throws IOException if the logger cannot be initialized.
     */
    public View(Controller contr) throws IOException{
        this.contr = contr;
        logger = new LogHandler();
        contr.addSaleObserver(new TotalRevenueView());
        contr.addSaleObserver(new TotalRevenueFileOutput());
    }
    
    /**
     * Performs a fake sale, calls all the system operations in the controller.
     */
    public void runFakeExecution(){
        try {
            contr.startSale();
            ItemAndRunningTotalDTO multipleItem = contr.enterIdentifier("abc123");
            System.out.println("Add 1 item with item id abc123:");
            System.out.println(itemAndRunningTotalDTOString(multipleItem));
            multipleItem = contr.enterIdentifier("abc123");
            System.out.println("Add 1 item with item id abc123:");
            System.out.println(itemAndRunningTotalDTOString(multipleItem));
            ItemAndRunningTotalDTO singleItem = contr.enterIdentifier("def456");
            System.out.println("Add 1 item with item id def456:");
            System.out.println(itemAndRunningTotalDTOString(singleItem));
            System.out.println("End sale:");
            Amount totalPrice = contr.endSale();
            System.out.println("Total cost (incl VAT): " + totalPrice.toString() + "\n");
            CustomerDTO firstCustomer = new CustomerDTO("CustomerID with discount");
            totalPrice = contr.requestDiscount(firstCustomer);
            System.out.println("Total cost (incl VAT) after discount has been applied: " + totalPrice.toString() + "\n");
            Amount paidAmount = new Amount(10000);
            System.out.println("Customer pays " + paidAmount + ":");
            Amount change = contr.pay(paidAmount);
            System.out.println("Change to give the customer: " + change);
            
            System.out.println("New sale \n");
            contr.startSale();
            multipleItem = contr.enterIdentifier("abc123");
            System.out.println("Add 1 item with item id abc123:");
            System.out.println(itemAndRunningTotalDTOString(multipleItem));
            singleItem = contr.enterIdentifier("def456");
            System.out.println("Add 1 item with item id def456:");
            System.out.println(itemAndRunningTotalDTOString(singleItem));
            System.out.println("End sale:");
            totalPrice = contr.endSale();
            System.out.println("Total cost (incl VAT): " + totalPrice.toString() + "\n");
            CustomerDTO secondCustomer = new CustomerDTO("CustomerID without discount");
            totalPrice = contr.requestDiscount(secondCustomer);
            System.out.println("Total cost (incl VAT) after discount has been applied: " + totalPrice.toString() + "\n");
            paidAmount = new Amount(20000);
            System.out.println("Customer pays " + paidAmount + ":");
            change = contr.pay(paidAmount);
            System.out.println("Change to give the customer: " + change);
            System.out.println("\n");
            try {
                System.out.println("Trying to enter an invalid item identifier.");
                contr.enterIdentifier("invalid identifier");
                errorMsgHandler.showErrorMsg("Managed to search for an invalid identifier.");
            }
            catch(InvalidIdentifierException e) {
                errorMsgHandler.showErrorMsg("Correctly failed to retrieve item with invalid identifier.");
            }
            catch(OperationFailedException e) {
                errorMsgHandler.showErrorMsg("Wrong exception was thrown.");
            }
            
            try {
                System.out.println("Simulating error in connecting with external inventory system.");
                contr.enterIdentifier("Simulate database error");
                errorMsgHandler.showErrorMsg("Managed to contact external inventory system.");
            }
            catch(InvalidIdentifierException e) {
                errorMsgHandler.showErrorMsg("Wrong exception was thrown.");
            }
            catch(OperationFailedException e) {
                writeToLogAndUI("Correctly failed to connect to external inventory system.", e);
            }
        
        }
        catch (InvalidIdentifierException e){
            errorMsgHandler.showErrorMsg("No item found, entered idenfitifer " + e.getInvalidIdentifier() + " is invalid.");
        }
        catch (OperationFailedException e){
            writeToLogAndUI("Failed register item, please try again.", e);
        }

    }
    
    private String itemAndRunningTotalDTOString(ItemAndRunningTotalDTO item){
        StringBuilder builder = new StringBuilder();
        builder.append("Item ID: " + item.getItem().getIdentifier() + "\n");
        builder.append("Item name: " + item.getItem().getName() + "\n");
        builder.append("Item cost: " + item.getItem().getPrice().plus(item.getItem().getVat()).toString() + "\n");
        builder.append("VAT: " + item.getItem().getVatRate() + "% \n");
        builder.append("Item description: " + item.getItem().getDescription() + "\n");
        builder.append("\n");
        builder.append("Total cost (incl VAT): " + item.getRunningTotal().toString() + "\n");
        builder.append("Total VAT: " + item.getRunningVat().toString() + "\n");
        return builder.toString();
    }
    
    private void writeToLogAndUI(String uiMsg, Exception exc) {
        errorMsgHandler.showErrorMsg(uiMsg);
        logger.logException(exc);
    }

}
