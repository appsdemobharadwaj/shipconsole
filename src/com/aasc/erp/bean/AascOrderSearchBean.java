/*

 * @(#)AascOrderSearchBean.java        21/01/2015

 * Copyright (c) Apps Associates Pvt. Ltd.

 * All rights reserved.

 */

package com.aasc.erp.bean;

import java.util.LinkedList;


/**

 AascOrderSearchBean Bean class for Search order Information with setXxx() and getXxx() Methods.

 These Methods are used to get and set the Search Order Information.

 */


 /*

   * @author Jagadish Jain

   * @version 1.1

   */

public class AascOrderSearchBean {
    public AascOrderSearchBean() {
    }
    private String orderNumber; // 
       private String shipmentDate;
       private String customerName;
       private String shipMethod;
       private int status;
       private LinkedList orderNumberList =  null; // LinkedList object for OrderNumber


        /**
         Method setOrder_number sets the order_number.
         @param orderNumber String.
         */
        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        /**
         Method getOrder_number is used to get the order_number.
         @return ordernumber String.
         */
        public String getOrderNumber() {
            return orderNumber;
        }

        /**
         Method setShipment_date sets the shipment_date.
         @param shipmentDate String.
         */
        public void setShipmentDate(String shipmentDate) {
            this.shipmentDate = shipmentDate;
        }

        /**
         Method getShipment_date is used to get the shipment_date.
         @return shipment_date String.
         */
        public String getShipmentDate() {
            return shipmentDate;
        }

        /**
         Method setCustomer_name sets the customer_name.
         @param customerName String.
         */
        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        /**
         Method getCustomer_name is used to get the customer_name.
         @return customer_name String.
         */
        public String getCustomerName() {
            return customerName;
        }

        /**
         Method setShip_method sets the ship_method.
         @param shipMethod String.
         */
        public void setShipMethod(String shipMethod) {
            this.shipMethod = shipMethod;
        }

        /**
         Method getShip_method is used to get the ship_method.
         @return ship_method String.
         */
        public String getShipMethod() {
            return shipMethod;
        }

        /**
         Method setOrderNumberList() sets aascTrackingDeliveryInfo object of type LinkedList.
         @param aascAdhocOrderSearchInfo LinkedList.
         */
        public void setOrderNumberList(LinkedList aascAdhocOrderSearchInfo) {
            this.orderNumberList = aascAdhocOrderSearchInfo;
        }

        /**
         Method getOrderNumberList gets the list of order names.
         @return orderNumberList LinkedList.
         */
        public LinkedList getOrderNumberList() {
            return orderNumberList;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

    
    
}
