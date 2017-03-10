/*
  * @(#)AascVoidShipmentDAO.java        27/01/2015
  * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
  * All rights reserved.
  */

package com.aasc.erp.model;

/*
 * AascVoidShipmentDAO is an interface for void shipment
 * @version 2
 * @author Kondapalli Sunanda
 */
public interface AascVoidShipmentDAO {

    /* 
     * This method voids the shipment
     * @param clientID    containing    ClientId of the user logged in
     * @param orderNumber containing OrderNumber of the shipment
     * @param locationID  containing ShipFromLocationId 
     * @param msg containing msg  
     */
    public int voidShipment(String orderNumber, int clientID, String msg,int locationID);

    /* This method voids the shipmentpackage
     * @param clientID    containing clientId of the user logged in
     * @param locationID  containing  ShipFromLocationId
     * @param orderNumber containing  OrderNumber of the shipment
     * @param packageID   containing  PackageId of the shipment
     */
    public int voidShipmentPackage(String orderNumber, int packageID, int locationID,int clientID);

    /*
     * Method used to update shipment cost after voiding few packages from multi package shipment.
     * Calls AASC_ERP_SHIPMENT_ORDERS.update_shipment_cost procedure where in we pass shipment_id,
     * package_id and package_cost as in parameters and op_status and op_error_status are
     * returned to indicate the status of update.
     * @param packageCost  - shipment cost per package
     * @param clientID     - clientID
     * @param LocationID   - ShipFromLocationId
     * @param orderNumber  - Order Number of the shipment
     */
    public int updateShipmentCost(String orderNumber, double packageCost, 
                           int clientID,int locatonId1);

    //public HashMap getVoidPackageDetails(int shipmentId);

}
