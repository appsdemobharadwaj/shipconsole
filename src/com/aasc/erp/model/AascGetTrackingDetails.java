package com.aasc.erp.model;

/**
 * AascGetTrackingDetails class is used to contact FEDEX or UPS tracking classes to get the delivery status.
 * @version   1.1
 * @author    Eshwari M
 * History
 *
 * 30/01/2015   Eshwari M    Formatted code, did self audit and added java documentation
 * 17/03/2015   Eshwari M    Modified code to get label path that is placed in session at the time of user login and removed Profile option object
 * */


import com.aasc.erp.bean.AascShipmentPackageInfo;
import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascTrackingOrderInfo;
import com.aasc.erp.bean.AascTrackingDetailsInfo;
import com.aasc.erp.carrier.AascFedexTrackService;
import com.aasc.erp.carrier.AascFedexTrackingInfo;

import com.aasc.erp.carrier.AascTrackingInfo;
import com.aasc.erp.carrier.AascTrackingRequest;

import com.aasc.erp.util.AascLogger;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Logger;

public class  AascGetTrackingDetails {
    private static Logger logger = AascLogger.getLogger("AascGetTrackingDetails"); 

    /**
      Method getTrackingDetails() gets the Tracking details by sending request to carriers and updates the status of the delivered orders.
      @param locationId  Id of the location seleced in the Reports page
      @param clientId    Id of the client for whom the report has to be generated
      @return integer, which is the status of saving the tracking information.
     */ 
    public int getTrackingDetails(int locationId, int clientId, String labelsPath) {
        logger.info("Inside getTrackingDetails") ;
        AascReportsInfoDAO aascReportsInfoDAO = new AascOracleReportsInfoDAO();
        int status = 0;
        LinkedList trackingList = 
            aascReportsInfoDAO.getTrackingDetails(locationId, clientId);
        Iterator iterator = trackingList.listIterator();
        try {
            while (iterator.hasNext()) {

                AascTrackingDetailsInfo trackingDetails = 
                    (AascTrackingDetailsInfo)iterator.next();
                int carrierCode = trackingDetails.getCarrierCode();
                if (carrierCode == 100) {

                    String accessLicenseNumber = 
                        trackingDetails.getAccessLicenseNumber();
                    String prefix = trackingDetails.getPrefix();
                    String userName = trackingDetails.getUserName();
                    String password = trackingDetails.getPassword();
                    String serverIp = trackingDetails.getServerIpAddress();

                    String trackingNumber = 
                        trackingDetails.getTrackingNumber();
                    AascTrackingOrderInfo aascTrackingDeliveryInfo2 = 
                        new AascTrackingOrderInfo();

                    aascTrackingDeliveryInfo2.setProtocol("https");

                    aascTrackingDeliveryInfo2.setUserName(userName);

                    aascTrackingDeliveryInfo2.setPassword(password);

                    aascTrackingDeliveryInfo2.setHostName(serverIp);

                    aascTrackingDeliveryInfo2.setService("Track");

                    aascTrackingDeliveryInfo2.setPrefix(prefix);

                    aascTrackingDeliveryInfo2.setAccessLicenseNumber(accessLicenseNumber);

                    AascTrackingRequest aascTrackingRequest = 
                        new AascTrackingRequest();

                    AascTrackingInfo aascTrackingInfo = 
                        aascTrackingRequest.createRequest("TRACKING", 
                                                          trackingNumber, 
                                                          aascTrackingDeliveryInfo2, 
                                                          labelsPath);

                    if ("success".equalsIgnoreCase(aascTrackingInfo.getResponseStatus())) {
                        trackingDetails.setDeliveryStatus(aascTrackingInfo.getStatus());

                        String deliveredDateStr = 
                            aascTrackingInfo.getDeliveredOn();

                        if (!"".equals(deliveredDateStr) && 
                            deliveredDateStr != null) {
                            trackingDetails.setDeliveredDate(deliveredDateStr);
                        }

                    }


                } else {
                    String accountNumber = trackingDetails.getAccountNumber();
                    String meterNumber = trackingDetails.getMeterNumber();
                    int port = trackingDetails.getPort();
                    String userName = trackingDetails.getUserName();
                    String password = trackingDetails.getPassword();
                    String serverIp = trackingDetails.getServerIpAddress();
                    String trackingNumber = 
                        trackingDetails.getTrackingNumber();

                    AascFedexTrackService aascFedexTrackService = 
                        new AascFedexTrackService();


                    String trackReply = 
                        aascFedexTrackService.processTrackRequest(userName, 
                                                                  password, 
                                                                  accountNumber, 
                                                                  meterNumber, 
                                                                  trackingNumber, 
                                                                  "Tracking", 
                                                                  "", serverIp, 
                                                                  port);


                    LinkedList linkedlist = new LinkedList();

                    AascShipmentPackageInfo aascPackageInfo = new AascShipmentPackageInfo();

                    aascPackageInfo.setPackageCount("1");

                    linkedlist.add(aascPackageInfo);

                    AascTrackingOrderInfo aascTrackingDeliveryInfo = 
                        new AascTrackingOrderInfo();
                    aascTrackingDeliveryInfo.setActionType("TRACKING");

                    aascTrackingDeliveryInfo.setPackageInfo(linkedlist);


                    AascFedexTrackingInfo aascFedexTrackInfo = 
                        new AascFedexTrackingInfo();

                    AascFedexTrackingInfo aascFedexTrackingInfo = 
                        aascFedexTrackInfo.parseWebServiceTrackResponse(trackReply, 
                                                                        aascTrackingDeliveryInfo);


                    if ("success".equalsIgnoreCase(aascFedexTrackingInfo.getResponseStatus())) {
                        trackingDetails.setDeliveryStatus(aascFedexTrackingInfo.getStatus());

                        String deliveredDateStr = 
                            aascFedexTrackingInfo.getDateTime();
                        logger.info("deliveredDateStr...." + 
                                           deliveredDateStr);
                        if (!"".equals(deliveredDateStr) && 
                            deliveredDateStr != null) {

                            trackingDetails.setDeliveredDate(deliveredDateStr);
                        }

                    }

                }

            }
            status = aascReportsInfoDAO.saveTrackingDetails(trackingList);
            logger.info("status from save tracking details::" + status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}
