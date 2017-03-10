 /**
  * @(#)AascPrinterDAO.java        
  *
  * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
  * All rights reserved.
  */
 package com.aasc.erp.model;

import java.util.LinkedList;
import java.util.List;


/**
 * AascPrinterSetupDAO is the interface has two abstract methods called 
 * savePrinterSetupInfo(),updatePrinterInfo those are implemented in  
 * AascOraclePrinterSetupDAO. AascOraclePrinterSetupDAO is a class which interacts with 
 * the database and corresponding bean file.
 * @author Y Pradeep
 * @version - 1
 * @since
 * 
 * 23/06/2015   Y Pradeep       Added this file for Printer Setup Popup page.
 */
public interface AascPrinterSetupDAO {

     /** abstract method to save Printer Setup information.
      * @return List  
      * @param printerList LinkedList
      */
    public abstract List savePrinterSetupInfo(LinkedList printerList);

    /** abstract method to get Printer Setup information.
     * @return HashMap  
     * @param clientId int, locationId int
     */
    public abstract LinkedList getPrinterSetupInfo(int clientId, int locationId);    
 
                                             
}

