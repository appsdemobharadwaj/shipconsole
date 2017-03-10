/*

 * @AascERPImportOrdHTBL.java        24/12/2014

 * Copyright (c) 2014-15 Apps Associates Pvt. Ltd.

 * All rights reserved.

 */

/**

 * AascERPImportOrdHTBL class extends Action class. AascERPImportOrdHTBL is a bean class used to hold multiple record data for imported orders.

 * @author Jagadish Jain

 * @creation 24/12/2014
  
 * @History
  
 * 24/12/2014   Jagadish Jain   Added required to hold import order data records.

 
 */


package com.aasc.erp.bean;

public class AascERPImportOrdHTBL {
    
    AascERPImportOrdHREC aascERPImportOrdHREC[];
    
    public AascERPImportOrdHTBL() {
    }

    public void setAascERPImportOrdHREC(AascERPImportOrdHREC[] aascERPImportOrdHREC) {
        this.aascERPImportOrdHREC = aascERPImportOrdHREC;
    }

    public AascERPImportOrdHREC[] getAascERPImportOrdHREC() {
        return aascERPImportOrdHREC;
    }
}
