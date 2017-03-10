/*

 * @AascERPImportOrdHTBL.java        24/12/2014

 * Copyright (c) 2014-15 Apps Associates Pvt. Ltd.

 * All rights reserved.

 */

/**

 * AascERPImportShipToLocationTBL is a bean class used to hold multiple record data for imported ship to locations.

 * @author Jagadish Jain

 * @creation 19/03/2015
  
 * @History
  
 * 23/03/2014   Jagadish Jain   Added required to hold import ship to location data records.

 
 */

package com.aasc.erp.bean;

public class AascERPImportShipToLocationTBL {
    public AascERPImportShipToLocationTBL() {
    }
    AascShipToLocationsInfo[] aascShipToLocationsInfo;


    public void setAascShipToLocationsInfo(AascShipToLocationsInfo[] aascShipToLocationsInfo) {
        this.aascShipToLocationsInfo = aascShipToLocationsInfo;
    }

    public AascShipToLocationsInfo[] getAascShipToLocationsInfo() {
        return aascShipToLocationsInfo;
    }
}
