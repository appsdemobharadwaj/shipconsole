package com.aasc.erp.model;

import com.aasc.erp.bean.AascShipToLocationsInfo;

import java.util.LinkedList;

 /**
  * AascShipToLocationsDAO is an interface has abstract method 
       which is implemented by the  AascOracleShipToLocationsDAO class.

  * @author     K Sunanda
  * @version 1
  * @since
  * History:
  * 20/1/2015   K Sunanda       Added class level Description.
  * 13/05/2015   Y Pradeep      Removed locationId from getAllCustomerLocationDetails(), getShipToCustomersList() method call to get Ship To Locations independent to Ship From Location.
  * 10/06/2015  Suman G         Added userId in getAllCustomerLocationDetails() to fix #2962
  */
  

public interface AascShipToLocationsDAO {

    /**
     * This method for create customer locations.
     * 
     * @param aascShipToLocationsInfo AascShipToLocationsInfo
     * 
     * @return int
     */
    public int createCustomerLocation(AascShipToLocationsInfo aascShipToLocationsInfo);
    
    /**
     * This method for edit customer locations.
     * 
     * @param aascShipToLocationsInfo AascShipToLocationsInfo
     * 
     * @return int
     */
    public int editCusotmerLocation(AascShipToLocationsInfo aascShipToLocationsInfo);
    
    /**
     * This method for get all customer location details.
     * 
     * @param  userId int and  clientId int
     * 
     * @return LinkedList
     */
    public LinkedList getAllCustomerLocationDetails(int clientId, int userId );
    
    /**
     * This method for get ship to customers list.
     * 
     * @param  locationId int and  clientId int
     * 
     * @return LinkedList
     */
    public LinkedList getShipToCustomersList(int clientId);

}
