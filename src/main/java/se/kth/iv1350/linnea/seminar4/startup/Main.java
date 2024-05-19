package se.kth.iv1350.linnea.seminar3.startup;

import java.io.IOException;
import se.kth.iv1350.linnea.seminar3.controller.Controller;
import se.kth.iv1350.linnea.seminar3.integration.ExternalSystemCreator;
import se.kth.iv1350.linnea.seminar3.integration.Printer;
import se.kth.iv1350.linnea.seminar3.view.View;

/**
 * Starts the entire application, contains the main method used to start the application.
 */
public class Main {
    
    /**
     * The main method used to start the application.
     * 
     * @param args The application does not take any command line parameters.
     */
    public static void main (String[] args) throws IOException{
        ExternalSystemCreator creator = new ExternalSystemCreator();
        Printer printer = new Printer();
        Controller contr = new Controller(creator, printer);
        View view = new View(contr);
        view.runFakeExecution();
    }

}
