package com.aasc.erp.model;

import com.aasc.erp.bean.AascHazmatPackageInfo;

import java.util.LinkedList;
/*History
 * 30/10/2013 Tina, Mahesh & Sanjay	Added code for Multiple Hazmat Enhancement for Hologic.
 * 12/12/2013  Y Pradeep         Added file for Multiple Hazmat Enhancement from 7.1.7.6 release.
 **/
    public interface AascHazmatPackageDAO {

            /**
             * This method saves the header information into backend tables for fedex Hazardous shipments.
             * @param orderNumber
             * @param aascHazmatPackageInfo
             * @return int
             */
       
        public int saveHazmatPackageDetails(AascHazmatPackageInfo aascHazmatPackageInfo, int pkgSequenceId, String orderNumber);
                                           


        /**
         * This method deleted the Fedex Hazardous information from backend tables for fedex shipments.
         * @return int
         */
        public int deleteHazmatPackageDetails(int pkgSequenceId, String orderNumber, int commodityNo);
        
        /**
         * This method is to get complete hazmat commodities to pass in request.
         * @return LinkedList
         */
        public LinkedList getHazmatShipmentDetails(int pkgSequenceId, String orderNumber);
          
    }


